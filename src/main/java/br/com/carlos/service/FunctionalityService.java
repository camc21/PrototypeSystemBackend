package br.com.carlos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.com.carlos.dto.ComboBoxDTO;
import br.com.carlos.model.Functionality;
import br.com.carlos.repository.FunctionalityRepository;

@Service
public class FunctionalityService {

	@Autowired
	FunctionalityRepository functionalityRepository;

	@PreAuthorize("hasAuthority('REGISTER_FUNCTIONALITY_READING')")
	public List<ComboBoxDTO> comboBox() {
		List<ComboBoxDTO> comboBox = functionalityRepository.comboBox();
		if (!comboBox.isEmpty()) {
			return comboBox;
		}
		return new ArrayList<>();
	}
	
	@PreAuthorize("hasAuthority('REGISTER_FUNCTIONALITY_READING')")
	public List<Functionality> findFunctionalityByIds(List<Long> ids) {
		List<Functionality> functionalities = functionalityRepository.findFunctionalityByIds(ids);
		if (!functionalities.isEmpty()) {
			return functionalities;
		}
		return new ArrayList<>();
	}
	

}
