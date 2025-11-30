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

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
public class ControleVenda {

    @Autowired
    private RepositorioVenda repositorio;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PostMapping("/cadastrar-venda")
    public ResponseEntity<?> cadastrarVenda(@RequestBody Venda v) {
        repositorio.save(v);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    @GetMapping("/obter-vendas")
    public ResponseEntity<List<Venda>> obterVendas() {
        return new ResponseEntity<>(repositorio.findAll(), HttpStatus.FOUND);
    }
}
