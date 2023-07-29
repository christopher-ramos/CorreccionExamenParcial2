package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto.EmpleadoPagoRQ;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto.PagoRolRQ;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.EmpleadoPago;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.Empresa;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.PagoRol;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.repository.EmpresaRepository;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.repository.PagoRolRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoRolService {

    private final PagoRolRepository pagoRolRepository;
    private final EmpresaRepository empresaRepository;

    public PagoRol realizarPago(PagoRolRQ pagoRolRQ) {
        PagoRol rolDePago = this.transformarPagoRolRQ(pagoRolRQ);

        Empresa existeEmpresa = empresaRepository.findByRucAndCuentaPrincipal(
                rolDePago.getRucEmpresa(),
                rolDePago.getCuentaPrincipal());

        if (existeEmpresa == null) {
            throw new RuntimeException("No existe la empresa y/o cuenta");
        }

        PagoRol existeRolPago = pagoRolRepository.findByMesAndRucEmpresa(
                rolDePago.getMes(),
                rolDePago.getRucEmpresa());

        if (existeRolPago != null) {
            throw new RuntimeException("No se puede realizar dos pagos en el mismo mes");
        }

        return pagoRolRepository.save(rolDePago);

    }

    private PagoRol transformarPagoRolRQ(PagoRolRQ pagoRolRQ) {
        PagoRol rolDePago = PagoRol.builder()
                .mes(pagoRolRQ.getMes())
                .fechaProceso(new Date())
                .rucEmpresa(pagoRolRQ.getRucEmpresa())
                .cuentaPrincipal(pagoRolRQ.getCuentaPrincipal())
                .valorTotal(this.obtenerValorTotal(pagoRolRQ.getEmpleadosPagos()))
                .valorReal(BigDecimal.ZERO)
                .empleadosPagos(this.transformarEmpleadosPagoRQ(pagoRolRQ.getEmpleadosPagos()))
                .build();

        return rolDePago;
    }

    private List<EmpleadoPago> transformarEmpleadosPagoRQ(List<EmpleadoPagoRQ> empleadoPagoRQ) {
        List<EmpleadoPago> pagosEmpleados = new ArrayList<>();

        for (EmpleadoPagoRQ pagoEmpleadoRQ : empleadoPagoRQ) {
            EmpleadoPago pagoEmpleado = EmpleadoPago.builder()
                    .cuenta(pagoEmpleadoRQ.getCuenta())
                    .valor(pagoEmpleadoRQ.getValor())
                    .status("PEN")
                    .build();

            pagosEmpleados.add(pagoEmpleado);
        }

        return pagosEmpleados;

    }

    private BigDecimal obtenerValorTotal(List<EmpleadoPagoRQ> empleadoPagoRQ) {
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (EmpleadoPagoRQ pagoEmpleadoRQ : empleadoPagoRQ) {
            BigDecimal valor = pagoEmpleadoRQ.getValor();
            valorTotal = valorTotal.add(valor);
        }

        return valorTotal;
    }

}
