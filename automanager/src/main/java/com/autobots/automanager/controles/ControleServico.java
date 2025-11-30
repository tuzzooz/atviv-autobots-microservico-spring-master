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

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.RepositorioServico;

@RestController
public class ControleServico {

    @Autowired
    private RepositorioServico repositorio;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar-servico")
    public ResponseEntity<?> cadastrarServico(@RequestBody Servico s) {
        repositorio.save(s);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/obter-servicos")
    public ResponseEntity<List<Servico>> obterServicos() {
        return new ResponseEntity<>(repositorio.findAll(), HttpStatus.OK);
    }
}
