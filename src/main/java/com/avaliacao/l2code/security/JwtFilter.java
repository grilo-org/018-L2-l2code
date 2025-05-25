package com.avaliacao.l2code.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

	private String HEADER_STRING = "Authorization";
	private String TOKEN_PREFIX = "Bearer";

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String header = req.getHeader(HEADER_STRING);
		String username = null;
		String authToken = null;

		if (header != null && header.startsWith(TOKEN_PREFIX)) {
			authToken = header.replace(TOKEN_PREFIX, "");

			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException e) {
				logger.error("Ocorreu um erro ao buscar o nome de usuário do token.", e);
			} catch (ExpiredJwtException e) {
				logger.warn("O token expirou.", e);
			} catch (SignatureException e) {
				logger.error("Falha na autenticação. Nome de usuário ou senha inválidos.");
			}

		} else {
			logger.warn("Não foi possível encontrar a string 'Bearer', o cabeçalho será ignorado.");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			if (jwtTokenUtil.validateToken(authToken, username)) {

				UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(
						authToken
						);

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

				logger.info("authenticated user " + username + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(req, res);
	}
}