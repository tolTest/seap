#!/usr/bin/env python3
"""
MCP Server for Web Form Search
This server provides a search method that accepts form parameters
and forwards requests to a web form submission URL.
"""

from fastmcp import FastMCP
import httpx
from bs4 import BeautifulSoup
from typing import Dict, Any, Optional
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Initialize FastMCP server
mcp = FastMCP("Web Form Search Server")

# Configuration for the target web page
# This is a generic example - replace with actual target URL
TARGET_PAGE_URL = "https://www.google.com/search"
FORM_ACTION_URL = "https://www.google.com/search"

@mcp.tool()
async def search(
    query: str,
    language: str = "en",
    num_results: int = 10
) -> Dict[str, Any]:
    """
    Search for content using web form parameters.
    
    This tool accepts search parameters matching the input fields from the web form
    and forwards the request to the form submission URL.
    
    Args:
        query: The search query string
        language: Language code for search results (default: "en")
        num_results: Number of results to return (default: 10)
    
    Returns:
        Dictionary containing search results and metadata
    """
    try:
        logger.info(f"Performing search with query: {query}")
        
        # Prepare form parameters
        params = {
            "q": query,
            "hl": language,
            "num": num_results
        }
        
        # Forward the request to the form submission URL
        async with httpx.AsyncClient() as client:
            response = await client.get(
                FORM_ACTION_URL,
                params=params,
                headers={
                    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
                },
                follow_redirects=True,
                timeout=30.0
            )
            
            response.raise_for_status()
            
            # Parse the response
            soup = BeautifulSoup(response.text, 'html.parser')
            
            return {
                "status": "success",
                "query": query,
                "url": str(response.url),
                "status_code": response.status_code,
                "content_length": len(response.text),
                "title": soup.title.string if soup.title else None,
                "message": f"Successfully retrieved search results for '{query}'"
            }
            
    except httpx.HTTPError as e:
        logger.error(f"HTTP error occurred: {e}")
        return {
            "status": "error",
            "error": str(e),
            "query": query
        }
    except Exception as e:
        logger.error(f"Unexpected error: {e}")
        return {
            "status": "error",
            "error": str(e),
            "query": query
        }

@mcp.tool()
async def get_form_fields(url: Optional[str] = None) -> Dict[str, Any]:
    """
    Extract form fields from a web page.
    
    This tool inspects a web page and extracts all form input fields,
    helping to understand what parameters are available for search.
    
    Args:
        url: The URL of the web page to inspect (default: TARGET_PAGE_URL)
    
    Returns:
        Dictionary containing form fields information
    """
    target_url = url or TARGET_PAGE_URL
    
    try:
        logger.info(f"Extracting form fields from: {target_url}")
        
        async with httpx.AsyncClient() as client:
            response = await client.get(
                target_url,
                headers={
                    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
                },
                follow_redirects=True,
                timeout=30.0
            )
            
            response.raise_for_status()
            soup = BeautifulSoup(response.text, 'html.parser')
            
            # Find all forms on the page
            forms = soup.find_all('form')
            form_data = []
            
            for idx, form in enumerate(forms):
                action = form.get('action', '')
                method = form.get('method', 'get').upper()
                
                # Extract input fields
                inputs = []
                for input_field in form.find_all(['input', 'select', 'textarea']):
                    field_info = {
                        "name": input_field.get('name'),
                        "type": input_field.get('type', 'text'),
                        "id": input_field.get('id'),
                        "placeholder": input_field.get('placeholder'),
                        "required": input_field.has_attr('required')
                    }
                    if field_info["name"]:  # Only include fields with names
                        inputs.append(field_info)
                
                form_data.append({
                    "form_index": idx,
                    "action": action,
                    "method": method,
                    "fields": inputs
                })
            
            return {
                "status": "success",
                "url": target_url,
                "forms_found": len(forms),
                "forms": form_data
            }
            
    except Exception as e:
        logger.error(f"Error extracting form fields: {e}")
        return {
            "status": "error",
            "error": str(e),
            "url": target_url
        }

@mcp.tool()
async def configure_target(
    page_url: str,
    action_url: Optional[str] = None
) -> Dict[str, str]:
    """
    Configure the target page URL and form action URL.
    
    This allows dynamic configuration of which web page and form
    submission URL the search should target.
    
    Args:
        page_url: The URL of the web page containing the form
        action_url: The URL where the form submits to (optional, defaults to page_url)
    
    Returns:
        Configuration status
    """
    global TARGET_PAGE_URL, FORM_ACTION_URL
    
    TARGET_PAGE_URL = page_url
    FORM_ACTION_URL = action_url or page_url
    
    logger.info(f"Updated configuration - Page: {TARGET_PAGE_URL}, Action: {FORM_ACTION_URL}")
    
    return {
        "status": "success",
        "target_page_url": TARGET_PAGE_URL,
        "form_action_url": FORM_ACTION_URL,
        "message": "Configuration updated successfully"
    }

if __name__ == "__main__":
    # Run the MCP server
    mcp.run()
