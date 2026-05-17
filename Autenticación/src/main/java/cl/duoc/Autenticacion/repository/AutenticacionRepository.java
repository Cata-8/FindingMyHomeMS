package cl.duoc.Autenticacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Autenticacion.model.Autenticacion;

@Repository
public interface AutenticacionRepository extends JpaRepository<Autenticacion, Integer>{

    Autenticacion findByIdUsuario (Integer idUsuario);

}
