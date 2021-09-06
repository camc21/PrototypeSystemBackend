package br.com.carlos.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.carlos.exception.ErrorHandler.ExceptionRestResponse;
import br.com.carlos.repository.LoginRepository;
import br.com.carlos.security.AccountCredentialsVO;
import br.com.carlos.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	LoginRepository repository;

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/signin", produces = { "application/json" }, consumes = { "application/json" })
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		
		try {
			var username =data.getUsername();
			var password = data.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			var user = repository.findByUserName(username);

			var token = "";

			if (user != null) {
				token = jwtTokenProvider.createToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("username " + username + "not found");
			}

			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			
			return ResponseEntity.ok().body(model);

		} catch (BadCredentialsException bc) {
			System.out.println(bc);
			return ResponseEntity.ok().body(new ExceptionRestResponse(HttpStatus.UNAUTHORIZED.value(), bc.getMessage()));
		}
	}
	
	@GetMapping(value = "/validateToken")
	public boolean validateToken(@RequestParam String token){
		String[] tokenTratado = token.split(" ");
		Boolean retorno = jwtTokenProvider.isTokenValid(tokenTratado[1]);
		return retorno;
		
	}
}