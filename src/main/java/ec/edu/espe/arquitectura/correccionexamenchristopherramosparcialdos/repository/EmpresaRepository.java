package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.Empresa;

public interface EmpresaRepository extends MongoRepository<Empresa, String> {

    Empresa findByRuc(String ruc);
    
    Empresa findByRucAndCuentaPrincipal(String ruc, String cuentaPrincipal);

}