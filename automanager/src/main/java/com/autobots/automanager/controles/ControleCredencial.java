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

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.repositorios.RepositorioCredencial;

@RestController
public class ControleCredencial {

    @Autowired
    private RepositorioCredencial repositorio;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/cadastrar-credencial")
    public ResponseEntity<?> cadastrar(@RequestBody Credencial c) {
        repositorio.save(c);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/obter-credenciais")
    public ResponseEntity<List<Credencial>> obter() {
        return new ResponseEntity<>(repositorio.findAll(), HttpStatus.FOUND);
    }
}
