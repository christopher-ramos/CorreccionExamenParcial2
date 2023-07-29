package ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ec.edu.espe.arquitectura.correccionexamenchristopherramosparcialdos.model.PagoRol;

public interface PagoRolRepository extends MongoRepository<PagoRol, String> {

    PagoRol findByMesAndRucEmpresa(Integer mes, String rucEmpresa);

}