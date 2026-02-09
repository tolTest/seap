#!/usr/bin/env python3
"""
Test script for the Web Form Search MCP Server
Validates that the server can be imported and has the required structure.
"""

import sys
import subprocess

def test_import():
    """Test that the server module can be imported"""
    print("Testing server import...")
    try:
        import search_server
        print("✓ Server module imported successfully\n")
        return True
    except Exception as e:
        print(f"✗ Failed to import server: {e}\n")
        return False

def test_server_attributes():
    """Test that the server has the required attributes"""
    print("Testing server attributes...")
    try:
        import search_server
        
        # Check that mcp server exists
        assert hasattr(search_server, 'mcp'), "Server should have 'mcp' attribute"
        assert hasattr(search_server, 'search'), "Server should have 'search' function"
        assert hasattr(search_server, 'get_form_fields'), "Server should have 'get_form_fields' function"
        assert hasattr(search_server, 'configure_target'), "Server should have 'configure_target' function"
        assert hasattr(search_server, 'TARGET_PAGE_URL'), "Server should have 'TARGET_PAGE_URL' variable"
        assert hasattr(search_server, 'FORM_ACTION_URL'), "Server should have 'FORM_ACTION_URL' variable"
        
        print("✓ Server has all required attributes")
        print()
        return True
    except Exception as e:
        print(f"✗ Server attributes test failed: {e}\n")
        import traceback
        traceback.print_exc()
        return False

def test_syntax():
    """Test that the server syntax is valid"""
    print("Testing Python syntax...")
    result = subprocess.run(
        ["python3", "-m", "py_compile", "search_server.py"],
        capture_output=True,
        text=True
    )
    if result.returncode == 0:
        print("✓ Python syntax is valid\n")
        return True
    else:
        print(f"✗ Syntax error:\n{result.stderr}\n")
        return False

def test_dependencies():
    """Test that required dependencies are available"""
    print("Testing dependencies...")
    required = {'fastmcp': 'fastmcp', 'httpx': 'httpx', 'beautifulsoup4': 'bs4'}
    missing = []
    
    for display_name, import_name in required.items():
        try:
            __import__(import_name)
        except ImportError:
            missing.append(display_name)
    
    if missing:
        print(f"✗ Missing dependencies: {', '.join(missing)}\n")
        return False
    else:
        print(f"✓ All required dependencies are available\n")
        return True

def main():
    """Run all tests"""
    print("=" * 60)
    print("Web Form Search MCP Server - Test Suite")
    print("=" * 60 + "\n")
    
    all_passed = True
    
    # Test syntax first
    if not test_syntax():
        all_passed = False
    
    # Test dependencies
    if not test_dependencies():
        all_passed = False
    
    # Test import
    if not test_import():
        all_passed = False
        # If import fails, can't continue with other tests
        print("=" * 60)
        print("Tests failed - server cannot be imported")
        print("=" * 60)
        return 1
    
    # Test server attributes
    if not test_server_attributes():
        all_passed = False
    
    print("=" * 60)
    if all_passed:
        print("All tests passed! ✓")
        print("\nThe MCP server is ready to use.")
        print("Run it with: python3 search_server.py")
    else:
        print("Some tests failed! ✗")
    print("=" * 60)
    
    return 0 if all_passed else 1

if __name__ == "__main__":
    exit_code = main()
    sys.exit(exit_code)
