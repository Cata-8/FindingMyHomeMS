package Duoc.cl.SolicitudMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import Duoc.cl.SolicitudMS.service.SolicitudService;

@RestController
public class SolicitudController {

    @Autowired
    private SolicitudService soliService;
}
