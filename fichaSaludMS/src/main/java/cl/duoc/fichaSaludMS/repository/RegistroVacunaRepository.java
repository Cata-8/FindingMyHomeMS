package cl.duoc.fichaSaludMS.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.fichaSaludMS.model.RegistroVacuna;

@Repository
public interface RegistroVacunaRepository extends JpaRepository<RegistroVacuna, Integer>{

}
