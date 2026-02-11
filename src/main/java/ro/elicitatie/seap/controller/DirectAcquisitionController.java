package ro.elicitatie.seap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.elicitatie.seap.model.SearchRequest;
import ro.elicitatie.seap.model.SearchResponse;
import ro.elicitatie.seap.service.DirectAcquisitionService;

/**
 * REST controller for direct acquisition search operations.
 * This controller acts as a proxy to the e-licitatie.ro API.
 */
@RestController
@RequestMapping("/api/v1/direct-acquisitions")
@RequiredArgsConstructor
@Slf4j
@Validated
public class DirectAcquisitionController {

    private final DirectAcquisitionService directAcquisitionService;

    /**
     * Search for direct acquisitions.
     * This endpoint forwards the request to the e-licitatie.ro API.
     *
     * Example request:
     * POST /api/v1/direct-acquisitions/search
     * {
     *   "page": 1,
     *   "pageSize": 10,
     *   "companyName": "Example Company",
     *   "publicationDateFrom": "2024-01-01"
     * }
     *
     * @param searchRequest the search criteria
     * @return search results from e-licitatie.ro
     */
    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest searchRequest) {
        log.info("Received search request");
        
        // Set default values if not provided
        if (searchRequest.getPage() == null) {
            searchRequest.setPage(1);
        }
        if (searchRequest.getPageSize() == null) {
            searchRequest.setPageSize(10);
        }

        SearchResponse response = directAcquisitionService.search(searchRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Service is running");
    }
}
