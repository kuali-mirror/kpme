package org.kuali.kpme.edo.dossier.type.service;

import org.kuali.kpme.edo.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.dossier.type.dao.EdoDossierTypeDao;

import java.util.List;
import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 9:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoDossierTypeServiceImpl implements EdoDossierTypeService {

    private EdoDossierTypeDao edoDossierTypeDao;

    public EdoDossierType getEdoDossierType(BigDecimal dossierTypeID) {
        return edoDossierTypeDao.getEdoDossierType( dossierTypeID);
    }

    public List<EdoDossierType> getEdoDossierTypeList() {
        return edoDossierTypeDao.getEdoDossierTypeList();
    }

    public void setEdoDossierTypeDao(EdoDossierTypeDao dossierType) {
        this.edoDossierTypeDao = dossierType;
    }

    public void saveOrUpdate(EdoDossierType dossierType) {
        this.edoDossierTypeDao.saveOrUpdate(dossierType);
    }

    public EdoDossierType getEdoDossierType(String dossierTypeName) {
        return this.edoDossierTypeDao.getEdoDossierType(dossierTypeName);
    }
}
