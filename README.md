# Web Form Search MCP Server

An MCP (Model Context Protocol) server that interfaces with web forms, extracts search parameters, and forwards requests to form submission URLs.

## Features

- **Search Method**: Accepts parameters matching web form input fields and forwards requests to the form submission URL
- **Form Field Extraction**: Automatically discovers form fields from target web pages
- **Configurable Target**: Dynamically configure which web page and form to target
- **Async Support**: Built with async/await for efficient request handling

## Installation

1. Install dependencies:
```bash
pip install -r requirements.txt
```

## Usage

### Running the Server

Start the MCP server:
```bash
python search_server.py
```

Or make it executable and run directly:
```bash
chmod +x search_server.py
./search_server.py
```

### Available Tools

#### 1. search
Performs a search using web form parameters and forwards the request to the form submission URL.

**Parameters:**
- `query` (str): The search query string
- `language` (str, optional): Language code for search results (default: "en")
- `num_results` (int, optional): Number of results to return (default: 10)

**Example:**
```json
{
  "query": "python programming",
  "language": "en",
  "num_results": 10
}
```

#### 2. get_form_fields
Extracts form fields from a web page to understand what parameters are available.

**Parameters:**
- `url` (str, optional): The URL of the web page to inspect

**Example:**
```json
{
  "url": "https://example.com/search"
}
```

#### 3. configure_target
Configures the target page URL and form action URL dynamically.

**Parameters:**
- `page_url` (str): The URL of the web page containing the form
- `action_url` (str, optional): The URL where the form submits to

**Example:**
```json
{
  "page_url": "https://example.com/search",
  "action_url": "https://example.com/results"
}
```

## Configuration

The server is pre-configured with Google Search as an example. To target a different web form:

1. Use the `configure_target` tool to set new URLs
2. Use the `get_form_fields` tool to discover available form parameters
3. Call the `search` tool with appropriate parameters

## Architecture

The server is built using:
- **FastMCP**: Ergonomic MCP interface for tool definition
- **httpx**: Async HTTP client for making requests
- **BeautifulSoup4**: HTML parsing for form field extraction

## Example Workflow

1. **Discover form fields:**
   ```python
   get_form_fields(url="https://example.com/search")
   ```

2. **Configure target (if needed):**
   ```python
   configure_target(
       page_url="https://example.com/search",
       action_url="https://example.com/search"
   )
   ```

3. **Perform search:**
   ```python
   search(
       query="your search term",
       language="en",
       num_results=10
   )
   ```

## Development

### Project Structure
```
seap/
├── search_server.py     # Main MCP server implementation
├── requirements.txt     # Python dependencies
└── README.md           # This file
```

### Testing

To test the server manually:

```bash
# Start the server
python search_server.py

# In another terminal, test with MCP client
# (Example using fastmcp client)
```

## Notes

- The default configuration uses Google Search as an example
- Some websites may have anti-bot protection that could block automated requests
- Always respect robots.txt and terms of service of target websites
- The server includes appropriate User-Agent headers for compatibility

## License

This is a demonstration project showing how to create an MCP server for web form interaction.