package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.RepositorioMercadoria;

@RestController
public class ControleMercadoria {

    @Autowired
    private RepositorioMercadoria repositorio;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar-mercadoria")
    public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria m) {
        repositorio.save(m);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/obter-mercadorias")
    public ResponseEntity<List<Mercadoria>> obterMercadorias() {
        return new ResponseEntity<>(repositorio.findAll(), HttpStatus.OK);
    }
}
