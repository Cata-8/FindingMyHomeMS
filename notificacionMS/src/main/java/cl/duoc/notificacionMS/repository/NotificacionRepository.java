package cl.duoc.notificacionMS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.notificacionMS.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository <Notificacion, Integer>{

    Optional<Notificacion> findByUsuarioId(Integer id);
}
