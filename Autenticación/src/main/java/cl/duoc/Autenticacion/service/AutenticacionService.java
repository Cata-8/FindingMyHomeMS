package cl.duoc.Autenticacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Autenticacion.repository.AutenticacionRepository;

@Service
public class AutenticacionService {

    @Autowired
    private AutenticacionRepository authRepo;

    
}
