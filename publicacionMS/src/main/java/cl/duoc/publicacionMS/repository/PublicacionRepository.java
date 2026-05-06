package cl.duoc.publicacionMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.publicacionMS.model.Publicacion;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Integer>{

}
