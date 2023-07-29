package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidacionPagoRolRS {

    private Integer mes;
    private String rucEmpresa;
    private BigDecimal valorTotal;
    private BigDecimal valorReal;
    private Integer totalTransacciones;
    private Integer errores;
}
