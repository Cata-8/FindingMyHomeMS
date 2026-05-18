package cl.duoc.mensajeriaMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.mensajeriaMS.model.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer>{

}
