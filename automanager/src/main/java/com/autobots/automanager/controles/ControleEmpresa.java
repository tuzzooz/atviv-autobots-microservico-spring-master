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

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.RepositorioEmpresa;

@RestController
public class ControleEmpresa {

    @Autowired
    private RepositorioEmpresa repositorio;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/cadastrar-empresa")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
        repositorio.save(empresa);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/obter-empresas")
    public ResponseEntity<List<Empresa>> obterEmpresas() {
        return new ResponseEntity<>(repositorio.findAll(), HttpStatus.OK);
    }
}
