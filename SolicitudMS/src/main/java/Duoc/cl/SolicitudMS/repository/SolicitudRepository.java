package Duoc.cl.SolicitudMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Duoc.cl.SolicitudMS.model.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud,Integer>{

}
