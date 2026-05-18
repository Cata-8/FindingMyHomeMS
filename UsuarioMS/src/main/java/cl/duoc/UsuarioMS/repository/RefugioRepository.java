package cl.duoc.UsuarioMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.UsuarioMS.model.Refugio;

@Repository
public interface RefugioRepository extends JpaRepository<Refugio, Integer>{

}
