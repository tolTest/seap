# SEAP Direct Acquisitions API

A Java Spring Boot REST API that acts as a proxy to the e-licitatie.ro direct acquisitions search functionality. This API provides a clean interface to search for direct acquisitions without needing to interact with the web page directly.

## Overview

This API forwards search requests to the official e-licitatie.ro API endpoint:
- **Target API**: `https://www.e-licitatie.ro/api-pub/DirectAcquisitionCommon/GetDirectAcquisitionList/`
- **Original Page**: `https://www.e-licitatie.ro/pub/direct-acquisitions/list/1`

## Features

- ✅ RESTful API for searching direct acquisitions
- ✅ Comprehensive search filters (company name, dates, values, CPV codes, etc.)
- ✅ Pagination support
- ✅ Error handling and logging
- ✅ Configurable timeouts and API endpoints
- ✅ Spring Boot application with easy deployment

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Building the Application

```bash
mvn clean package
```

## Running the Application

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/seap-api-1.0.0.jar
```

The application will start on port 8080 by default.

## API Documentation

### Health Check

**Endpoint**: `GET /api/v1/direct-acquisitions/health`

**Description**: Check if the service is running.

**Response**:
```
Service is running
```

### Search Direct Acquisitions

**Endpoint**: `POST /api/v1/direct-acquisitions/search`

**Description**: Search for direct acquisitions with various filters.

**Request Body** (all fields are optional):

```json
{
  "page": 1,
  "pageSize": 10,
  "companyName": "Company Name",
  "directAcquisitionName": "Acquisition Title",
  "directAcquisitionIdentifier": "ACQ123",
  "publicationDateFrom": "2024-01-01",
  "publicationDateTo": "2024-12-31",
  "finalizationDateFrom": "2024-01-01",
  "finalizationDateTo": "2024-12-31",
  "minEstimatedValue": 1000.0,
  "maxEstimatedValue": 100000.0,
  "cpvCode": "45000000",
  "municipality": "Bucharest",
  "county": "Bucuresti",
  "financingType": "BUGET",
  "acquisitionState": "FINALIZED",
  "sortField": "publicationDate",
  "sortDirection": "desc"
}
```

**Request Parameters**:

| Parameter | Type | Description |
|-----------|------|-------------|
| `page` | Integer | Page number (default: 1) |
| `pageSize` | Integer | Items per page (default: 10) |
| `companyName` | String | Filter by company name |
| `directAcquisitionName` | String | Filter by acquisition title |
| `directAcquisitionIdentifier` | String | Filter by acquisition ID |
| `publicationDateFrom` | String | Publication start date (ISO format: yyyy-MM-dd) |
| `publicationDateTo` | String | Publication end date (ISO format: yyyy-MM-dd) |
| `finalizationDateFrom` | String | Finalization start date (ISO format: yyyy-MM-dd) |
| `finalizationDateTo` | String | Finalization end date (ISO format: yyyy-MM-dd) |
| `minEstimatedValue` | Double | Minimum estimated value |
| `maxEstimatedValue` | Double | Maximum estimated value |
| `cpvCode` | String | CPV code filter (Common Procurement Vocabulary) |
| `municipality` | String | Municipality/locality filter |
| `county` | String | County filter |
| `financingType` | String | Financing type filter |
| `acquisitionState` | String | Acquisition state filter |
| `sortField` | String | Field to sort by |
| `sortDirection` | String | Sort direction (asc/desc) |

**Response**:

```json
{
  "items": [
    {
      "directAcquisitionId": 12345,
      "directAcquisitionName": "Acquisition Name",
      "directAcquisitionIdentifier": "ACQ123",
      "companyName": "Company Name",
      "companyFiscalNumber": "RO12345678",
      "publicationDate": "2024-01-15",
      "finalizationDate": "2024-02-01",
      "estimatedValue": 50000.0,
      "closingValue": 48000.0,
      "cpvCode": "45000000",
      "cpvDescription": "Construction work",
      "municipality": "Bucharest",
      "county": "Bucuresti",
      "acquisitionState": "FINALIZED",
      "financingType": "BUGET"
    }
  ],
  "total": 100,
  "page": 1,
  "pageSize": 10
}
```

### Example Usage with cURL

#### Basic Search
```bash
curl -X POST http://localhost:8080/api/v1/direct-acquisitions/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 10
  }'
```

#### Search by Company Name
```bash
curl -X POST http://localhost:8080/api/v1/direct-acquisitions/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 20,
    "companyName": "SC EXAMPLE SRL"
  }'
```

#### Search with Date Range
```bash
curl -X POST http://localhost:8080/api/v1/direct-acquisitions/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 10,
    "publicationDateFrom": "2024-01-01",
    "publicationDateTo": "2024-12-31"
  }'
```

#### Search with Multiple Filters
```bash
curl -X POST http://localhost:8080/api/v1/direct-acquisitions/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 10,
    "county": "Bucuresti",
    "minEstimatedValue": 10000,
    "maxEstimatedValue": 100000,
    "publicationDateFrom": "2024-01-01"
  }'
```

## Configuration

You can customize the application by modifying `src/main/resources/application.properties`:

```properties
# Server port
server.port=8080

# e-licitatie.ro API settings
elicitatie.api.base-url=https://www.e-licitatie.ro/api-pub
elicitatie.api.direct-acquisition-list-path=/DirectAcquisitionCommon/GetDirectAcquisitionList/
elicitatie.api.connection-timeout=10000
elicitatie.api.read-timeout=30000

# Logging level
logging.level.ro.elicitatie.seap=DEBUG
```

## Error Handling

The API includes comprehensive error handling:

- **400-499**: Client errors from the e-licitatie.ro API
- **500**: Internal server error
- **502**: Bad gateway (e-licitatie.ro API error)
- **503**: Service unavailable (cannot connect to e-licitatie.ro)

Error responses follow this format:
```json
{
  "error": "Error description",
  "status": 503,
  "message": "The external API is not reachable"
}
```

## Project Structure

```
seap/
├── src/
│   ├── main/
│   │   ├── java/ro/elicitatie/seap/
│   │   │   ├── SeapApplication.java          # Main application class
│   │   │   ├── config/
│   │   │   │   ├── ApiConfig.java            # API configuration
│   │   │   │   └── RestClientConfig.java     # REST client setup
│   │   │   ├── controller/
│   │   │   │   ├── DirectAcquisitionController.java  # REST endpoints
│   │   │   │   └── GlobalExceptionHandler.java       # Error handling
│   │   │   ├── model/
│   │   │   │   ├── SearchRequest.java        # Request DTO
│   │   │   │   └── SearchResponse.java       # Response DTO
│   │   │   └── service/
│   │   │       └── DirectAcquisitionService.java     # Business logic
│   │   └── resources/
│   │       └── application.properties         # Configuration
│   └── test/
│       └── java/ro/elicitatie/seap/          # Unit tests
├── pom.xml                                     # Maven configuration
└── README.md                                   # This file
```

## Technology Stack

- **Java 17**: Programming language
- **Spring Boot 3.2.2**: Application framework
- **Spring Web**: REST API support
- **Spring Validation**: Request validation
- **Lombok**: Boilerplate code reduction
- **Maven**: Build and dependency management

## License

This project is provided as-is for educational and development purposes.

## Notes

- This API is a proxy/wrapper around the official e-licitatie.ro API
- All data comes directly from the e-licitatie.ro system
- Ensure you have network access to e-licitatie.ro when running the application
- The API contract is based on typical e-licitatie.ro search parameters and may need adjustments based on actual API responses