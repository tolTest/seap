package ro.elicitatie.seap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Basic test to verify the Spring Boot application context loads correctly
 */
@SpringBootTest
@TestPropertySource(properties = {
    "elicitatie.api.base-url=https://www.e-licitatie.ro/api-pub"
})
class SeapApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads without errors
    }
}
