//package br.com.carlos.config;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.LockedException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.stereotype.Component;
//
//import br.com.carlos.dto.FunctionalityPermissionDTO.FuncionalidadePermissaoDTO;
//import br.com.carlos.enumerate.BlockUnblockEnum;
//import br.com.carlos.enumerate.TypePermission;
//import br.com.carlos.exception.WrongCredentialsException;
//import br.com.carlos.model.Login;
//import br.com.carlos.service.LoginService;
//import br.com.carlos.service.PermissionService;
//import br.com.carlos.service.SystemParameterService;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private LoginService loginService;
//    
//    @Autowired
//    private PermissionService permissaoService;
//    
//    @Autowired
//    private SystemParameterService systemParameterService;
//    
//    @Autowired
//    private TentativaLoginNaoAutorizadaService tentativaLoginNaoAutorizadaService;
//
//    @Override
//    public Authentication authenticate(Authentication auth) throws AuthenticationException {
//        String userName = auth.getName();
//        String password = auth.getCredentials().toString();
//        Login userLogin = loginService.findByUserName(userName);
//
//        doLogin(userLogin, password, userName);
//        
//        List<GrantedAuthority> authorities = prepararAuthorities(userLogin.getId());
//        
//        return new UsernamePasswordAuthenticationToken(userName, password, authorities);
//    }
//
//    @Override
//    public boolean supports(Class<?> auth) {
//        return auth.equals(UsernamePasswordAuthenticationToken.class);
//    }
//
//    private void doLogin(Login login, String password, String loginInformado) {
//    	
//    	Double maxAttemptsParameters = systemParameterService.getValorParametro("LIMITE_TENTATIVAS_LOGIN");
//    	Short numeroMaximoTentativasNaoAutorizadas = systemParameterService.getValorParametro("LIMITE_TENTATIVAS_NAO_AUTORIZADAS").shortValue();
//    	
//    	Long maxAttempts = Double.valueOf(maxAttemptsParameters).longValue();
//    	
//        if (login == null) {
//        	TentativaLoginNaoAutorizada tlna = tentativaLoginNaoAutorizadaService.buscarPorId(1l);
//        	if(tlna == null) {
//        		tlna = new TentativaLoginNaoAutorizada();
//        	}
//        	if(tlna.getId() != null && tlna.getNaoAutorizado()) {
//            	HashMap<String, String> loginUsuario = new HashMap<>();
//    	        loginUsuario.put("CHAVE", BlockUnblockEnum.UNAUTHORIZED_ACCESS.name());
//    	        loginUsuario.put("LOGIN",  loginInformado);
//    	        EmissorAlerta.emitirAlerta(loginUsuario);
//    	        tlna.setNaoAutorizado(false);
//    	        Short zero = 0;
//    	        tlna.setTentativasNaoAutorizadas(zero);
//        		tentativaLoginNaoAutorizadaService.salvar(tlna);
//        	} else {
//        		if(tlna.getId() != null && tlna.getTentativasNaoAutorizadas() == numeroMaximoTentativasNaoAutorizadas -1) {
//            		tlna.setNaoAutorizado(true);
//            		tentativaLoginNaoAutorizadaService.salvar(tlna);
//            	} else {
//            		Integer quantidade = (Short) tlna.getTentativasNaoAutorizadas() + 1  ;
//            		tlna.setTentativasNaoAutorizadas(quantidade.shortValue());
//            		tentativaLoginNaoAutorizadaService.salvar(tlna);
//            	}
//        	}
//        	
//            throw new WrongCredentialsException("Bad credentials");
//        }
//     
//        Boolean bloqueado = loginService.isLoginBloqueado(login.getIdLogin());
//        Long tentativas = login.getTentativasIncorretas();
//
//      if (!Boolean.TRUE.equals(BCrypt.checkpw(password, login.getSenhaAtual())) &&
//          !Boolean.TRUE.equals(bloqueado) && tentativas < maxAttempts - 1) {
//            login.setTentativasIncorretas(tentativas + 1);
//            loginService.save(login);
//            throw new WrongCredentialsException("Bad credentials");
//        }
//
//        if (login.getTentativasIncorretas() == maxAttempts - 1) {
//            login.setTentativasIncorretas(tentativas + 1);
//            loginService.save(login);
//            throw new LastLoginAttemptException("Last login attempt");
//        }
//
//      if (tentativas == maxAttempts && !Boolean.TRUE.equals
//          (BCrypt.checkpw(password, login.getSenhaAtual()))) {
//            login.setBloqueado(true);
//            login.setTentativasIncorretas(0l);
//            loginService.save(login);
//            HashMap<String, String> loginUsuario = new HashMap<>();
//            loginUsuario.put("CHAVE", BlockUnblockEnum.BLOCK_USER_MAX_ATTEMPTS.name());
//            loginUsuario.put("LOGIN",  login.getLogin());
//            EmissorAlerta.emitirAlerta(loginUsuario);
//            throw new MaximumLoginAttemptsException("Maximum login attempts");
//        } else {
//        	if(!bloqueado && tentativas > 0) {
//        		login.setTentativasIncorretas(0l);
//            	loginService.save(login);
//        	}
//		}
//        
//        if (isPasswordExpired(login)) {
//            throw new ExpiredPasswordException("Password is expired", "");
//        }
//        
//        if (bloqueado) {
//            throw new LockedException("Locked");
//        }
//    }
//
//	private boolean isPasswordExpired(Login login) {
//        if (login == null || login.getDataCriacaoSenha() == null) {
//            return false;
//        }
//
//        // TODO: Pegar tempo de expiração da senha dos parâmetros de sistema
//        int passwordExpiringTime = 60;
//
//        return login.getDataCriacaoSenha()
//                        .until(LocalDateTime.now(), ChronoUnit.DAYS) > passwordExpiringTime;
//    }
//
//    private List<GrantedAuthority> prepararAuthorities (Long idLogin) {
//    	List<GrantedAuthority> authorities = new ArrayList<>();
//    	List<PerfilAcesso> perfisDoLogin = loginService.recuperaPerfisDoLogin(idLogin);
//    	List<Long> idsPerfis = perfisDoLogin.stream().map(PerfilAcesso::getId).collect(Collectors.toList());
//    	
//    	if (idsPerfis == null || idsPerfis.isEmpty()) {
//    		return authorities;
//    	}
//    	
//    	List<FuncionalidadePermissaoDTO> permissoesDoLogin = permissaoService.recuperaPermissoesDosPerfis(idsPerfis);
//    	
//    	for (FuncionalidadePermissaoDTO permissao : permissoesDoLogin) {
//    		StringBuilder sb = new StringBuilder();
//    		sb.append("ROLE_");
//    		sb.append(permissao.getRole());
//    		
//    		if (permissao.getPermissao().equals(TypePermission.READ.getCode())) {
//    			sb.append("_LEITURA");
//    			authorities.add(new SimpleGrantedAuthority(sb.toString()));
//    			
//    		} else if (permissao.getPermissao().equals(TypePermission.WRITE.getCode())) {
//    			sb.append("_ESCRITA");
//    			authorities.add(new SimpleGrantedAuthority(sb.toString()));
//    			
//    			StringBuilder sbLeitura = new StringBuilder();
//    			sbLeitura.append("ROLE_");
//    			sbLeitura.append(permissao.getRole());
//    			sbLeitura.append("_LEITURA");
//    			authorities.add(new SimpleGrantedAuthority(sbLeitura.toString()));
//    		}
//		}
//    	
//    	return authorities;
//    }
//}
