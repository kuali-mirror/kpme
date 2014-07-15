package org.kuali.kpme.edo.submitButton.dao;

import java.util.List;

import org.kuali.kpme.edo.submitButton.EdoSubmitButton;

public interface EdoDisplaySubmitButtonDao {
	
	 public boolean saveOrUpdate( EdoSubmitButton submitButton );
	 public EdoSubmitButton getEdoSubmitButton(String campusCode);
	 public List<EdoSubmitButton> getEdoSubmitButtonList();
}
