package com.autobots.automanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.Perfil;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@SpringBootApplication
public class AutomanagerApplication implements CommandLineRunner {

	@Autowired
	private RepositorioUsuario repositorio;

	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
		// Criar usuário administrador
		Usuario admin = new Usuario();
		admin.setNome("administrador");
		admin.getPerfis().add(Perfil.ROLE_ADMIN);
		Credencial credAdmin = new Credencial();
		credAdmin.setNomeUsuario("admin");
		credAdmin.setSenha(codificador.encode("123456"));
		admin.setCredencial(credAdmin);
		repositorio.save(admin);

		// Criar usuário gerente
		Usuario gerente = new Usuario();
		gerente.setNome("gerente");
		gerente.getPerfis().add(Perfil.ROLE_GERENTE);
		Credencial credGerente = new Credencial();
		credGerente.setNomeUsuario("gerente");
		credGerente.setSenha(codificador.encode("123456"));
		gerente.setCredencial(credGerente);
		repositorio.save(gerente);

		// Criar usuário vendedor
		Usuario vendedor = new Usuario();
		vendedor.setNome("vendedor");
		vendedor.getPerfis().add(Perfil.ROLE_VENDEDOR);
		Credencial credVendedor = new Credencial();
		credVendedor.setNomeUsuario("vendedor");
		credVendedor.setSenha(codificador.encode("123456"));
		vendedor.setCredencial(credVendedor);
		repositorio.save(vendedor);

		// Criar usuário cliente
		Usuario cliente = new Usuario();
		cliente.setNome("cliente");
		cliente.getPerfis().add(Perfil.ROLE_CLIENTE);
		Credencial credCliente = new Credencial();
		credCliente.setNomeUsuario("cliente");
		credCliente.setSenha(codificador.encode("123456"));
		cliente.setCredencial(credCliente);
		repositorio.save(cliente);
	}
}