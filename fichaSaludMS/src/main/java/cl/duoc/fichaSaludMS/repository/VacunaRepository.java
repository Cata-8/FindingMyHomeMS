package cl.duoc.fichaSaludMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.fichaSaludMS.model.Vacuna;

@Repository
public interface VacunaRepository extends JpaRepository<Vacuna, Integer>{

}
