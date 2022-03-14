package br.com.carlos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.com.carlos.dto.SystemParameterDTO;
import br.com.carlos.model.SystemParameter;
import br.com.carlos.repository.SystemParameterRepository;

@Service
public class SystemParameterService {

	@Autowired
	private SystemParameterRepository repository;

	@PreAuthorize("hasAuthority('REGISTER_SYSTEM_PARAMETER_READING')")
	public Page<SystemParameterDTO> listaParametroSistema(Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		Page<SystemParameterDTO> parametrosPaginados = repository
				.parameterSystemList(PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy));
		return parametrosPaginados;
	}

	@PreAuthorize("hasAuthority('REGISTER_SYSTEM_PARAMETER_READING')")
	public Double getValorParametro(String nome) {
		SystemParameter parametro = repository.findByParameterName(nome);
		return  Double.parseDouble(parametro.getParameterValue());
	}

	@PreAuthorize("hasAuthority('REGISTER_SYSTEM_PARAMETER_READING')")
	public SystemParameterDTO findByNome(String nome) {
		SystemParameterDTO param = new SystemParameterDTO();
		SystemParameter parametro = repository.findByParameterName(nome);
		return param.modelToDTO(parametro);
	}
	
	@PreAuthorize("hasAuthority('REGISTER_SYSTEM_PARAMETER_WRITING')")
	public SystemParameter editarParametroSistema(SystemParameterDTO parametroDto) {
		SystemParameter parametro = repository.findById(parametroDto.getId()).get();
		parametro.setParameterValue(parametroDto.getParameterValue());
		return repository.save(parametro);
	}

}