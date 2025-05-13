package integration;

import com.inditex.zara.api.infrastructure.adapters.inbound.rest.controller.price.dtos.PriceResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(
        classes = com.inditex.zara.api.ApiRestInditexZaraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class PriceControllerBehaviorIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/api/v1/prices";

    @Test
    @DisplayName("Debería devolver 404 cuando no se encuentra precio aplicable")
    void shouldReturnNotFoundWhenNoPriceApplies() {
        String url = BASE_URL + "?applicationDate=2020-01-01T00:00:00&productId=35455&brandId=1";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("No se encontró un precio para el productId 35455 y el brandId 1");
    }

    @Test
    @DisplayName("Debería devolver 400 cuando falta un parámetro requerido")
    void shouldReturnBadRequestWhenMissingParameter() {
        String url = BASE_URL + "?applicationDate=2020-06-14T10:00:00&brandId=1"; // falta productId
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Falta el parámetro requerido: 'productId'");
    }

    @Test
    @DisplayName("Debería devolver 400 cuando el formato de fecha es inválido")
    void shouldReturnBadRequestWhenDateIsInvalid() {
        String url = BASE_URL + "?applicationDate=fecha-invalida&productId=35455&brandId=1";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Parámetros inválidos");
    }

    @Test
    @DisplayName("Debería devolver 500 cuando ocurre una excepción no controlada")
    void shouldHandleGenericExceptionAsInternalServerError() {
        String url = "/api/v1/fault";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).contains("Error interno del servidor");
    }


    @Test
    @DisplayName("Debería devolver 200 OK con contenido válido")
    void shouldReturn200WithValidResponse() {
        String url = BASE_URL + "?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1";

        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(url, PriceResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().productId()).isEqualTo(35455);
        assertThat(response.getBody().brandId()).isEqualTo(1);
        assertThat(response.getBody().priceList()).isEqualTo(1);
        assertThat(response.getBody().startDate()).isEqualTo("2020-06-14T00:00:00");
        assertThat(response.getBody().endDate()).isEqualTo("2020-12-31T23:59:59");
        assertThat(response.getBody().price()).isEqualByComparingTo(new BigDecimal("35.5"));
        assertThat(response.getBody().currency()).isEqualTo("EUR");
    }



    @Test
    @DisplayName("Debe crear y validar correctamente los campos del PriceResponseDTO")
    void shouldCreateDtoManually() {
        PriceResponseDTO dto = new PriceResponseDTO(
                35455L, 1L, 1,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                new BigDecimal("35.50"),
                "EUR"
        );

        assertThat(dto.productId()).isEqualTo(35455L);
        assertThat(dto.brandId()).isEqualTo(1L);
        assertThat(dto.priceList()).isEqualTo(1);
        assertThat(dto.startDate()).isEqualTo(LocalDateTime.parse("2020-06-14T00:00:00"));
        assertThat(dto.endDate()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
        assertThat(dto.price()).isEqualByComparingTo("35.50");
        assertThat(dto.currency()).isEqualTo("EUR");
    }
}
