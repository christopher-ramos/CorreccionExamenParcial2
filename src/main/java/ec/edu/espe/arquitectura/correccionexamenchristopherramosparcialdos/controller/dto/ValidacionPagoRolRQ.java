package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidacionPagoRolRQ {

    private Integer mes;
    private String rucEmpresa;

}
