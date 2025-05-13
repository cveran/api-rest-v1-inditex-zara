package integration;

import com.inditex.zara.api.infrastructure.adapters.inbound.rest.controller.price.dtos.PriceResponseDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = com.inditex.zara.api.ApiRestInditexZaraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class PriceUseCaseIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/api/v1/prices";


    @ParameterizedTest(name = "[{index}] {0} -> Lista {4}")
    @CsvSource({
            "'2020-06-14T10:00:00', 35455, 1, 35.50, 1, '2020-06-14T00:00:00', '2020-12-31T23:59:59', 'EUR'",
            "'2020-06-14T16:00:00', 35455, 1, 25.45, 2, '2020-06-14T15:00:00', '2020-06-14T18:30:00', 'EUR'",
            "'2020-06-14T21:00:00', 35455, 1, 35.50, 1, '2020-06-14T00:00:00', '2020-12-31T23:59:59', 'EUR'",
            "'2020-06-15T10:00:00', 35455, 1, 30.50, 3, '2020-06-15T00:00:00', '2020-06-15T11:00:00', 'EUR'",
            "'2020-06-16T21:00:00', 35455, 1, 38.95, 4, '2020-06-15T16:00:00', '2020-12-31T23:59:59', 'EUR'"
    })
    @DisplayName("Debería devolver el precio esperado para fecha y producto dados")
    void shouldReturnExpectedPrice(String applicationDate, Long productId, Long brandId,
                                   double expectedPrice, int expectedList,
                                   String expectedStartDate, String expectedEndDate, String expectedCurrency) {

        String url = String.format("%s?applicationDate=%s&productId=%d&brandId=%d", BASE_URL, applicationDate, productId, brandId);
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(url, PriceResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        var body = response.getBody();
        assertThat(body.productId()).isEqualTo(productId);
        assertThat(body.brandId()).isEqualTo(brandId);
        assertThat(body.priceList()).isEqualTo(expectedList);
        assertThat(body.price()).isEqualByComparingTo(String.valueOf(expectedPrice));
        assertThat(body.startDate()).isEqualTo(expectedStartDate);
        assertThat(body.endDate()).isEqualTo(expectedEndDate);
        assertThat(body.currency()).isEqualTo(expectedCurrency);
    }
}
