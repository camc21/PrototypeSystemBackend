package br.com.carlos.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carlos.model.Login;
import br.com.carlos.repository.LoginRepository;

@Service
@Transactional
public class LoginService {

	@Autowired
	LoginRepository loginRepository;

	public LoginService(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	public Login findByUserName(String userName) {
		return loginRepository.findByUserName(userName);
	}
}
