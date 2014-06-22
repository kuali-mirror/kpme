package org.kuali.kpme.edo.dossier.type.dao;

import org.kuali.kpme.edo.dossier.type.EdoDossierTypeBo;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 9:57 AM
 *
 * This interface defines the retrieval methods for getting dossier type
 * records from the database.
 *
 */
public interface EdoDossierTypeDao {

    public EdoDossierTypeBo getEdoDossierTypeById(String dossierTypeID);
    
    public List<EdoDossierTypeBo> getEdoDossierTypeList();
    
    public void saveOrUpdate(EdoDossierTypeBo dossierType);
    
    public EdoDossierTypeBo getEdoDossierTypeByName(String dossierTypeName);
    
    public EdoDossierTypeBo getEdoDossierTypeByCode(String dossierTypeCode);
    
}
