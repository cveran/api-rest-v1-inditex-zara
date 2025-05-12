package integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class PriceControllerErrorIntegrationTest {

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
}
