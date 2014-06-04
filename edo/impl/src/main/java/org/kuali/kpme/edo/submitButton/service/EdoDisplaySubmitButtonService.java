package org.kuali.kpme.edo.submitButton.service;

import java.util.List;

import org.kuali.kpme.edo.submitButton.EdoSubmitButton;

public interface EdoDisplaySubmitButtonService {
	
	 public boolean saveOrUpdate( EdoSubmitButton submitButton );
	 public EdoSubmitButton getEdoSubmitButton(String campusCode);
	 public List<EdoSubmitButton> getEdoSubmitButtonList();
	 

}
