package ro.elicitatie.seap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response model for direct acquisition search results.
 * This mirrors the structure returned by the e-licitatie.ro API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {

    /**
     * List of direct acquisitions
     */
    @JsonProperty("items")
    private List<DirectAcquisition> items;

    /**
     * Total count of results (for pagination)
     */
    @JsonProperty("total")
    private Integer total;

    /**
     * Current page
     */
    @JsonProperty("page")
    private Integer page;

    /**
     * Page size
     */
    @JsonProperty("pageSize")
    private Integer pageSize;

    /**
     * Represents a single direct acquisition entry
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DirectAcquisition {
        
        @JsonProperty("directAcquisitionId")
        private Long directAcquisitionId;
        
        @JsonProperty("directAcquisitionName")
        private String directAcquisitionName;
        
        @JsonProperty("directAcquisitionIdentifier")
        private String directAcquisitionIdentifier;
        
        @JsonProperty("companyName")
        private String companyName;
        
        @JsonProperty("companyFiscalNumber")
        private String companyFiscalNumber;
        
        @JsonProperty("publicationDate")
        private String publicationDate;
        
        @JsonProperty("finalizationDate")
        private String finalizationDate;
        
        @JsonProperty("estimatedValue")
        private Double estimatedValue;
        
        @JsonProperty("closingValue")
        private Double closingValue;
        
        @JsonProperty("cpvCode")
        private String cpvCode;
        
        @JsonProperty("cpvDescription")
        private String cpvDescription;
        
        @JsonProperty("municipality")
        private String municipality;
        
        @JsonProperty("county")
        private String county;
        
        @JsonProperty("acquisitionState")
        private String acquisitionState;
        
        @JsonProperty("financingType")
        private String financingType;
    }
}
