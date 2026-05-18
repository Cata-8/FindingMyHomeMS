package cl.duoc.fichaSaludMS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.fichaSaludMS.model.FichaSalud;

@Repository
public interface FichaSaludRepository extends JpaRepository<FichaSalud, Integer>{

    Optional<FichaSalud> findByIdMascota(Integer idMascota);
}
