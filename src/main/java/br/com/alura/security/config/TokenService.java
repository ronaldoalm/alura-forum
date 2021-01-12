package br.com.alura.security.config;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong("8640000000"));
		
		return Jwts.builder()
				.setIssuer("API_DO_FORUM_ALURA")
				.setSubject(logado.getId().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, "$EYJ#%@7Ee8zDcF").compact();
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey("$EYJ#%@7Ee8zDcF").parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey("$EYJ#%@7Ee8zDcF").parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
