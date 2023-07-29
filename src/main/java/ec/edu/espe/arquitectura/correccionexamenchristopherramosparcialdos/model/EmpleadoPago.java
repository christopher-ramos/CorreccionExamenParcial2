package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpleadoPago {

    private String cuenta;
    private BigDecimal valor;
    private String status;

}
