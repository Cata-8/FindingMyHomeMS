package cl.duoc.UsuarioMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.UsuarioMS.model.Adoptante;
@Repository
public interface AdoptanteRepository extends JpaRepository<Adoptante, Integer>{

}
