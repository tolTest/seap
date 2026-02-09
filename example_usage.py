#!/usr/bin/env python3
"""
Example demonstrating how to use the Web Form Search MCP Server.

This shows how to configure the server and perform searches.
"""

import asyncio
from fastmcp import Client

async def main():
    """Example usage of the MCP server"""
    print("=" * 60)
    print("Web Form Search MCP Server - Usage Example")
    print("=" * 60 + "\n")
    
    # Connect to the MCP server (assuming it's running)
    # In practice, you would start the server in a separate process
    # and connect to it using the Client
    
    print("Example 1: Performing a search")
    print("-" * 60)
    print("Tool: search")
    print("Parameters:")
    print("  - query: 'python programming'")
    print("  - language: 'en'")
    print("  - num_results: 10")
    print("\nThis would forward a search request to the configured")
    print("form submission URL with the specified parameters.")
    print()
    
    print("Example 2: Discovering form fields")
    print("-" * 60)
    print("Tool: get_form_fields")
    print("Parameters:")
    print("  - url: 'https://example.com/search'")
    print("\nThis would extract all form fields from the target page,")
    print("helping you understand what parameters are available.")
    print()
    
    print("Example 3: Configuring target URL")
    print("-" * 60)
    print("Tool: configure_target")
    print("Parameters:")
    print("  - page_url: 'https://example.com/search'")
    print("  - action_url: 'https://example.com/results'")
    print("\nThis would update the target URLs that the server")
    print("uses for subsequent search requests.")
    print()
    
    print("=" * 60)
    print("To use this server:")
    print("1. Start the server: python3 search_server.py")
    print("2. Connect using an MCP client")
    print("3. Call the available tools with appropriate parameters")
    print("=" * 60)

if __name__ == "__main__":
    asyncio.run(main())
