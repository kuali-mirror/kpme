package org.kuali.kpme.edo.submitButton.service;

import java.util.List;

import org.kuali.kpme.edo.submitButton.EdoSubmitButton;
import org.kuali.kpme.edo.submitButton.dao.EdoDisplaySubmitButtonDao;

public class EdoDisplaySubmitButtonServiceImpl implements EdoDisplaySubmitButtonService {
	
	 private EdoDisplaySubmitButtonDao edoDisplaySubmitButtonDao;
	
	 public EdoDisplaySubmitButtonDao getEdoDisplaySubmitButtonDao() {
		return edoDisplaySubmitButtonDao;
	}
	public void setEdoDisplaySubmitButtonDao(
			EdoDisplaySubmitButtonDao edoDisplaySubmitButtonDao) {
		this.edoDisplaySubmitButtonDao = edoDisplaySubmitButtonDao;
	}
	
	public boolean saveOrUpdate( EdoSubmitButton edoSubmitButton ) {
	        
		return this.edoDisplaySubmitButtonDao.saveOrUpdate( edoSubmitButton );
	    }
	 public EdoSubmitButton getEdoSubmitButton(String campusCode) {
		 
		 return this.edoDisplaySubmitButtonDao.getEdoSubmitButton( campusCode );
	 }
	 public List<EdoSubmitButton> getEdoSubmitButtonList() {
		 
		 return this.edoDisplaySubmitButtonDao.getEdoSubmitButtonList();
		 
	 }
	
	

}
