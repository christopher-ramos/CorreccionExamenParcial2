package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto.EmpleadoRQ;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.controller.dto.EmpresaRQ;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.Empleado;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.Empresa;
import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public Empresa crear(EmpresaRQ empresaRQ) {

        Empresa empresa = this.transformarEmpresaRQ(empresaRQ);

        Empresa existeEmpresa = empresaRepository.findByRuc(empresa.getRuc());

        if (existeEmpresa != null) {
            throw new RuntimeException("El ruc ya esta registrado");
        }

        return empresaRepository.save(empresa);

    }

    private Empresa transformarEmpresaRQ(EmpresaRQ empresaRQ) {
        Empresa empresa = Empresa.builder()
                .ruc(empresaRQ.getRuc())
                .razonSocial(empresaRQ.getRazonSocial())
                .cuentaPrincipal(empresaRQ.getCuentaPrincipal())
                .empleados(this.transformarEmpleadosRQ(empresaRQ.getEmpleados()))
                .build();

        return empresa;
    }

    private List<Empleado> transformarEmpleadosRQ(List<EmpleadoRQ> empleadosRQ) {
        List<Empleado> empleados = new ArrayList<>();

        if (empleadosRQ == null || empleadosRQ.isEmpty()) {
            throw new RuntimeException("La empresa debe tener minimo 1 empleado");
        }

        for (EmpleadoRQ empleadoRQ : empleadosRQ) {
            Empleado empleado = Empleado.builder()
                    .cedula(empleadoRQ.getCedula())
                    .apellidos(empleadoRQ.getApellidos())
                    .nombres(empleadoRQ.getNombres())
                    .cuenta(empleadoRQ.getCuenta())
                    .build();

            empleados.add(empleado);
        }

        return empleados;
    }
}
