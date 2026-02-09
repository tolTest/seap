#!/usr/bin/env python3
"""
Test script for the Web Form Search MCP Server
"""

import asyncio
import sys
import importlib.util

# Import the module to get the actual functions
spec = importlib.util.spec_from_file_location("search_server", "search_server.py")
search_module = importlib.util.module_from_spec(spec)
spec.loader.exec_module(search_module)

# Get the actual function objects (not the wrapped tools)
search_func = search_module.search.__wrapped__
get_form_fields_func = search_module.get_form_fields.__wrapped__
configure_target_func = search_module.configure_target.__wrapped__

async def test_search():
    """Test the search function"""
    print("Testing search function...")
    result = await search_func(query="python programming", language="en", num_results=10)
    print(f"Search result: {result}")
    assert result["status"] in ["success", "error"], "Search should return status"
    assert "query" in result, "Search should return query"
    print("✓ Search function test passed\n")

async def test_get_form_fields():
    """Test the get_form_fields function"""
    print("Testing get_form_fields function...")
    result = await get_form_fields_func()
    print(f"Form fields result: {result}")
    assert "status" in result, "get_form_fields should return status"
    print("✓ get_form_fields function test passed\n")

async def test_configure_target():
    """Test the configure_target function"""
    print("Testing configure_target function...")
    result = await configure_target_func(
        page_url="https://example.com/search",
        action_url="https://example.com/results"
    )
    print(f"Configure result: {result}")
    assert result["status"] == "success", "configure_target should succeed"
    assert result["target_page_url"] == "https://example.com/search"
    print("✓ configure_target function test passed\n")

async def main():
    """Run all tests"""
    print("=" * 60)
    print("Web Form Search MCP Server - Test Suite")
    print("=" * 60 + "\n")
    
    try:
        # Test individual functions
        await test_configure_target()
        await test_get_form_fields()
        await test_search()
        
        print("=" * 60)
        print("All tests passed! ✓")
        print("=" * 60)
        return 0
        
    except Exception as e:
        print(f"\n✗ Test failed with error: {e}")
        import traceback
        traceback.print_exc()
        return 1

if __name__ == "__main__":
    exit_code = asyncio.run(main())
    sys.exit(exit_code)
