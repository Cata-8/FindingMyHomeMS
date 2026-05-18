package cl.Duoc.HistorialAdopcionMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.Duoc.HistorialAdopcionMS.model.HistorialAdopcion;

@Repository
public interface HistorialRepository extends JpaRepository<HistorialAdopcion, Integer> {

}
