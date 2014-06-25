package org.kuali.kpme.edo.dossier.dao;

import java.util.List;

import org.kuali.kpme.edo.dossier.EdoDossierBo;


public interface EdoDossierDao {

	public EdoDossierBo getEdoDossierById(String edoDossierId);
	
    public EdoDossierBo getCurrentDossierPrincipalname(String candidatePrincipalname);
    
    public List<EdoDossierBo> getDossierList();
    
    public void saveOrUpdate(EdoDossierBo edoDossier);
    
    public List<EdoDossierBo> getDossierListByUserName(String userName);
   
    public EdoDossierBo getDossier(String documentId);
}
