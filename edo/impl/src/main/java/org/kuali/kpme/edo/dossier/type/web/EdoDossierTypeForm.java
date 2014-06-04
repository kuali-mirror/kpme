package org.kuali.kpme.edo.dossier.type.web;

import java.util.Date;
import java.util.List;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.dossier.type.EdoDossierType;

import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoDossierTypeForm extends EdoForm {

    public BigDecimal dossierTypeID;
    public String dossierTypeName;
    public String dossierTypeCode;
    public Date lastUpdated;

    private List<EdoDossierType> dossierTypeList = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeList();

    public EdoDossierType getEdoDossierType(BigDecimal dossierTypeID) {
        return EdoServiceLocator.getEdoDossierTypeService().getEdoDossierType(dossierTypeID);
    }

    public List<EdoDossierType> getEdoDossierTypeList() {
        return dossierTypeList;
    }

    public String getDossierTypeCode() {
        return dossierTypeCode;
    }

    public void setDossierTypeCode(String dossierTypeCode) {
        this.dossierTypeCode = dossierTypeCode;
    }

    public String getDossierTypeName() {
        return dossierTypeName;
    }

    public void setDossierTypeName(String dossierTypeName) {
        this.dossierTypeName = dossierTypeName;
    }

    public BigDecimal getDossierTypeID() {
        return dossierTypeID;
    }

    public void setDossierTypeID(BigDecimal dossierTypeID) {
        this.dossierTypeID = dossierTypeID;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date dossierLastUpdated) {
        this.lastUpdated = dossierLastUpdated;
    }
}
