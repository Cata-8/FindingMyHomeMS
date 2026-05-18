package cl.duoc.donacionMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.donacionMS.model.Donacion;

@Repository
public interface DonacionRepository extends JpaRepository<Donacion, Integer>{

}
