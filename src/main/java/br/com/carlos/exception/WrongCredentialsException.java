package br.com.carlos.exception;

import org.springframework.security.core.AuthenticationException;

public class WrongCredentialsException extends AuthenticationException {

	private static final long serialVersionUID = 3642777737017825774L;

	public WrongCredentialsException(String msg) {
        super(msg);
    }
}
