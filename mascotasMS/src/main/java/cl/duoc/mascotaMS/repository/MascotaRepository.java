package cl.duoc.mascotaMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.mascotaMS.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Integer>{

    List<Mascota> findByEstado(String estado);
}
