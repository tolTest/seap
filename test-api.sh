#!/bin/bash

# Example script to test the SEAP Direct Acquisitions API
# Make sure the application is running before executing this script

API_BASE_URL="http://localhost:8080/api/v1/direct-acquisitions"

echo "=========================================="
echo "Testing SEAP Direct Acquisitions API"
echo "=========================================="
echo ""

# Test 1: Health check
echo "1. Health Check"
echo "GET $API_BASE_URL/health"
echo ""
curl -s "$API_BASE_URL/health"
echo ""
echo ""

# Test 2: Basic search with pagination
echo "2. Basic Search with Pagination"
echo "POST $API_BASE_URL/search"
echo ""
curl -s -X POST "$API_BASE_URL/search" \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 5
  }' | jq '.' || cat
echo ""
echo ""

# Test 3: Search by company name
echo "3. Search by Company Name"
echo "POST $API_BASE_URL/search"
echo ""
curl -s -X POST "$API_BASE_URL/search" \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 10,
    "companyName": "PRIMARIA"
  }' | jq '.' || cat
echo ""
echo ""

# Test 4: Search with date range
echo "4. Search with Date Range"
echo "POST $API_BASE_URL/search"
echo ""
curl -s -X POST "$API_BASE_URL/search" \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 10,
    "publicationDateFrom": "2024-01-01",
    "publicationDateTo": "2024-12-31"
  }' | jq '.' || cat
echo ""
echo ""

# Test 5: Search with value range
echo "5. Search with Value Range"
echo "POST $API_BASE_URL/search"
echo ""
curl -s -X POST "$API_BASE_URL/search" \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 10,
    "minEstimatedValue": 10000,
    "maxEstimatedValue": 100000
  }' | jq '.' || cat
echo ""
echo ""

# Test 6: Search with multiple filters
echo "6. Search with Multiple Filters"
echo "POST $API_BASE_URL/search"
echo ""
curl -s -X POST "$API_BASE_URL/search" \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 10,
    "county": "Bucuresti",
    "minEstimatedValue": 5000,
    "publicationDateFrom": "2024-01-01",
    "sortField": "publicationDate",
    "sortDirection": "desc"
  }' | jq '.' || cat
echo ""
echo ""

echo "=========================================="
echo "Tests completed!"
echo "=========================================="
