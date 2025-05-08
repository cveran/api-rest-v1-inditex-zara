package integration;

import com.inditex.zara.api.infrastructure.adapters.inbound.rest.PriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = com.inditex.zara.api.ApiRestInditexZaraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

class PriceControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/api/v1/prices";

    private ResponseEntity<PriceResponse> getPrice(String dateTime, Long productId, Long brandId) {
        String url = String.format("%s?applicationDate=%s&productId=%d&brandId=%d", BASE_URL, dateTime, productId, brandId);
        return restTemplate.exchange(url, HttpMethod.GET, null, PriceResponse.class);
    }

    @Test
    @DisplayName("Test 1: 14-Jun-2020 10:00")
    void test1() {
        var response = getPrice("2020-06-14T10:00:00", 35455L, 1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(1);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo("35.50");
    }

    @Test
    @DisplayName("Test 2: 14-Jun-2020 16:00")
    void test2() {
        var response = getPrice("2020-06-14T16:00:00", 35455L, 1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(2);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo("25.45");
    }

    @Test
    @DisplayName("Test 3: 14-Jun-2020 21:00")
    void test3() {
        var response = getPrice("2020-06-14T21:00:00", 35455L, 1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(1);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo("35.50");
    }

    @Test
    @DisplayName("Test 4: 15-Jun-2020 10:00")
    void test4() {
        var response = getPrice("2020-06-15T10:00:00", 35455L, 1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(3);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo("30.50");
    }

    @Test
    @DisplayName("Test 5: 16-Jun-2020 21:00")
    void test5() {
        var response = getPrice("2020-06-16T21:00:00", 35455L, 1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(4);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo("38.95");
    }

    @Test
    @DisplayName("Test Negativo: No hay precio para fecha fuera de rango")
    void testNotFound() {
        String url = String.format("%s?applicationDate=2020-01-01T00:00:00&productId=35455&brandId=1", BASE_URL);
        var response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("No price found for productId=35455 and brandId=1");
    }
}
