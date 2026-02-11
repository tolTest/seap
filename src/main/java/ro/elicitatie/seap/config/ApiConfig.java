package ro.elicitatie.seap.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * Configuration properties for the e-licitatie.ro API
 */
@Configuration
@ConfigurationProperties(prefix = "elicitatie.api")
@Data
public class ApiConfig {

    /**
     * Base URL for the e-licitatie.ro API
     */
    private String baseUrl = "https://www.e-licitatie.ro/api-pub";

    /**
     * Endpoint path for direct acquisition list
     */
    private String directAcquisitionListPath = "/DirectAcquisitionCommon/GetDirectAcquisitionList/";

    /**
     * Connection timeout in milliseconds
     */
    private int connectionTimeout = 10000;

    /**
     * Read timeout in milliseconds
     */
    private int readTimeout = 30000;

    /**
     * Get the full URL for direct acquisition list endpoint
     */
    public String getDirectAcquisitionListUrl() {
        return baseUrl + directAcquisitionListPath;
    }
}
