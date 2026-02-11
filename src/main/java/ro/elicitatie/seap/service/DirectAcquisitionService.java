package ro.elicitatie.seap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.elicitatie.seap.config.ApiConfig;
import ro.elicitatie.seap.model.SearchRequest;
import ro.elicitatie.seap.model.SearchResponse;

/**
 * Service for interacting with the e-licitatie.ro API
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DirectAcquisitionService {

    private final RestTemplate restTemplate;
    private final ApiConfig apiConfig;

    /**
     * Search for direct acquisitions by forwarding the request to e-licitatie.ro API
     *
     * @param searchRequest the search criteria
     * @return search results from e-licitatie.ro
     * @throws RestClientException if the API call fails
     */
    public SearchResponse search(SearchRequest searchRequest) {
        log.info("Forwarding search request to e-licitatie.ro API");
        log.debug("Search parameters: {}", searchRequest);

        try {
            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create HTTP entity with request body
            HttpEntity<SearchRequest> entity = new HttpEntity<>(searchRequest, headers);

            // Call the e-licitatie.ro API
            String url = apiConfig.getDirectAcquisitionListUrl();
            log.debug("Calling URL: {}", url);

            ResponseEntity<SearchResponse> response = restTemplate.postForEntity(
                    url,
                    entity,
                    SearchResponse.class
            );

            log.info("Received response with status: {}", response.getStatusCode());
            return response.getBody();

        } catch (RestClientException e) {
            log.error("Error calling e-licitatie.ro API", e);
            throw e;
        }
    }
}
