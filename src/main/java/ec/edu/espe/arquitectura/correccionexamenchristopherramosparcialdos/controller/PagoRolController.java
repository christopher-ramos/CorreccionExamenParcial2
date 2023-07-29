package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto.PagoRolRQ;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.PagoRol;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.service.PagoRolService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pago-rol")
@RequiredArgsConstructor
public class PagoRolController {

    private final PagoRolService pagoRolService;

    @PostMapping
    public ResponseEntity<?> realizarPago(@RequestBody PagoRolRQ pagoRolRQ) {
        try {
            PagoRol pagoRol = pagoRolService.realizarPago(pagoRolRQ);
            return ResponseEntity.ok(pagoRol);
        } catch (RuntimeException rte) {
            return ResponseEntity.badRequest().body(rte.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
