package org.kuali.kpme.edo.dossier.type.service;

import org.kuali.kpme.edo.api.dossier.type.EdoDossierType;

import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 9:58 AM
 *
 * This interface defines the retrieval methods to get instances of the
 * DAO objects retrieval methods for dossier type records.
 *
 */
public interface EdoDossierTypeService {

    public EdoDossierType getEdoDossierTypeById(String edoDossierTypeID);
    
    public List<EdoDossierType> getEdoDossierTypeList();
    
    public void saveOrUpdate( EdoDossierType dossierType );
    
    public EdoDossierType getEdoDossierTypeByName(String dossierTypeName);
    
    public EdoDossierType getEdoDossierTypeByCode(String dossierTypeCode);
    
}

