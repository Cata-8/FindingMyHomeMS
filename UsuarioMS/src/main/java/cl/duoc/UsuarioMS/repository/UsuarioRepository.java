package cl.duoc.UsuarioMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.UsuarioMS.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{


    Usuario findByEmail(String email);
}
