package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto.ValidacionPagoRolRQ;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto.ValidacionPagoRolRS;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.Empleado;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.EmpleadoPago;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.Empresa;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.PagoRol;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.repository.EmpresaRepository;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.repository.PagoRolRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidacionPagoRolService {

    private final PagoRolRepository pagoRolRepository;
    private final EmpresaRepository empresaRepository;

    public ValidacionPagoRolRS validarPagoRol(ValidacionPagoRolRQ validacionPagoRolRQ) {
        PagoRol rolDePago = pagoRolRepository.findByMesAndRucEmpresa(
                validacionPagoRolRQ.getMes(),
                validacionPagoRolRQ.getRucEmpresa());

        if (rolDePago == null) {
            throw new RuntimeException("No existe el rol de pagos");
        }

        // Guardamos los empleados de la empresa en una lista
        Empresa empresa = empresaRepository.findByRuc(validacionPagoRolRQ.getRucEmpresa());
        List<Empleado> empleadosEmpresa = empresa.getEmpleados();

        // Guardamos los empleados del rol de pagos en una lista
        List<EmpleadoPago> empleadosRolPago = rolDePago.getEmpleadosPagos();

        // Lista para almacenar empleados con cambios
        List<EmpleadoPago> empleadosConCambios = new ArrayList<>();

        BigDecimal valorReal = BigDecimal.ZERO;
        Integer totalTransacciones = 0;
        Integer errores = 0;

        for (EmpleadoPago empleadoRolPago : empleadosRolPago) {
            boolean cuentaEncontrada = false;
            for (Empleado empleadoEmpresa : empleadosEmpresa) {
                if (empleadoEmpresa.getCuenta().equals(empleadoRolPago.getCuenta())) {
                    cuentaEncontrada = true;
                    empleadoRolPago.setStatus("VAL");
                    empleadosConCambios.add(empleadoRolPago);
                    valorReal = valorReal.add(empleadoRolPago.getValor());
                    totalTransacciones = totalTransacciones + 1;
                    break;
                }
            }
            if (!cuentaEncontrada) {
                empleadoRolPago.setStatus("BAD");
                empleadosConCambios.add(empleadoRolPago);
                errores = errores + 1;
            }
        }

        if (!empleadosConCambios.isEmpty()) {
            rolDePago.setEmpleadosPagos(empleadosConCambios);
            rolDePago.setValorReal(valorReal);
            pagoRolRepository.save(rolDePago);
        }

        return this.transformarPagoRol(rolDePago, totalTransacciones, errores);
    }

    private ValidacionPagoRolRS transformarPagoRol(PagoRol pagoRol, Integer totalTransacciones, Integer errores) {
        ValidacionPagoRolRS validacionPagoRolRS = ValidacionPagoRolRS.builder()
                .mes(pagoRol.getMes())
                .rucEmpresa(pagoRol.getRucEmpresa())
                .valorTotal(pagoRol.getValorTotal())
                .valorReal(pagoRol.getValorReal())
                .totalTransacciones(totalTransacciones)
                .errores(errores)
                .build();

        return validacionPagoRolRS;
    }
}
