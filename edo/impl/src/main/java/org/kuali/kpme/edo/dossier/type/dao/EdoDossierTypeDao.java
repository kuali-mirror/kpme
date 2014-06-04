package org.kuali.kpme.edo.dossier.type.dao;

import org.kuali.kpme.edo.dossier.type.EdoDossierType;

import java.math.BigDecimal;
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

    public EdoDossierType getEdoDossierType(BigDecimal dossierTypeID);
    public List<EdoDossierType> getEdoDossierTypeList();
    public void saveOrUpdate(EdoDossierType dossierType);
    public EdoDossierType getEdoDossierType(String dossierTypeName);
}
