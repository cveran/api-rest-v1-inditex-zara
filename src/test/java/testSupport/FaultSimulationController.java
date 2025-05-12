package testSupport;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Profile("test")
@RestController
@RequestMapping("/api/v1/fault")
class FaultSimulationController {

    @GetMapping
    public ResponseEntity<String> triggerFault() {
        throw new RuntimeException("Fallo simulado");
    }


}
