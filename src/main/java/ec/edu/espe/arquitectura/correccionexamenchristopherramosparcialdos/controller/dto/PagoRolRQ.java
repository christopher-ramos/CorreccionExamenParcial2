package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagoRolRQ {

    private Integer mes;
    private String rucEmpresa;
    private String cuentaPrincipal;
    private List<EmpleadoPagoRQ> empleadosPagos;

}
