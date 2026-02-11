package ro.elicitatie.seap.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request model for direct acquisition search.
 * Based on the parameters typically used by e-licitatie.ro search form.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchRequest {

    /**
     * Page number (1-based)
     */
    @JsonProperty("page")
    private Integer page;

    /**
     * Number of items per page
     */
    @JsonProperty("pageSize")
    private Integer pageSize;

    /**
     * Company name filter
     */
    @JsonProperty("companyName")
    private String companyName;

    /**
     * Direct acquisition title filter
     */
    @JsonProperty("directAcquisitionName")
    private String directAcquisitionName;

    /**
     * Direct acquisition identifier filter
     */
    @JsonProperty("directAcquisitionIdentifier")
    private String directAcquisitionIdentifier;

    /**
     * Publication date from (ISO format: yyyy-MM-dd)
     */
    @JsonProperty("publicationDateFrom")
    private String publicationDateFrom;

    /**
     * Publication date to (ISO format: yyyy-MM-dd)
     */
    @JsonProperty("publicationDateTo")
    private String publicationDateTo;

    /**
     * Finalization date from (ISO format: yyyy-MM-dd)
     */
    @JsonProperty("finalizationDateFrom")
    private String finalizationDateFrom;

    /**
     * Finalization date to (ISO format: yyyy-MM-dd)
     */
    @JsonProperty("finalizationDateTo")
    private String finalizationDateTo;

    /**
     * Minimum estimated value
     */
    @JsonProperty("minEstimatedValue")
    private Double minEstimatedValue;

    /**
     * Maximum estimated value
     */
    @JsonProperty("maxEstimatedValue")
    private Double maxEstimatedValue;

    /**
     * CPV code filter (Common Procurement Vocabulary)
     */
    @JsonProperty("cpvCode")
    private String cpvCode;

    /**
     * Municipality/locality filter
     */
    @JsonProperty("municipality")
    private String municipality;

    /**
     * County filter
     */
    @JsonProperty("county")
    private String county;

    /**
     * Financing type filter
     */
    @JsonProperty("financingType")
    private String financingType;

    /**
     * Acquisition state filter
     */
    @JsonProperty("acquisitionState")
    private String acquisitionState;

    /**
     * Sort field
     */
    @JsonProperty("sortField")
    private String sortField;

    /**
     * Sort direction (asc/desc)
     */
    @JsonProperty("sortDirection")
    private String sortDirection;
}
