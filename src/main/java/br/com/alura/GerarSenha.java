package br.com.alura;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarSenha {

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));

	}

}
