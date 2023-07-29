package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto.ValidacionPagoRolRQ;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto.ValidacionPagoRolRS;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.service.ValidacionPagoRolService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/validar-pago-rol")
@RequiredArgsConstructor
public class ValidacionPagoRolController {

    private final ValidacionPagoRolService validacionPagoRolService;

    @PostMapping
    public ResponseEntity<?> validarPagoRol(@RequestBody ValidacionPagoRolRQ validarPagoRolRQ) {
        try {
            ValidacionPagoRolRS validacionPagoRolRS = validacionPagoRolService.validarPagoRol(validarPagoRolRQ);
            return ResponseEntity.ok(validacionPagoRolRS);
        } catch (RuntimeException rte) {
            return ResponseEntity.badRequest().body(rte.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
