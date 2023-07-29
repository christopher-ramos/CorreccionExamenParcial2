package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Empleado {
    
    @Indexed(unique = true)
    private String cedula;
    private String apellidos;
    private String nombres;
    private String cuenta;

}
