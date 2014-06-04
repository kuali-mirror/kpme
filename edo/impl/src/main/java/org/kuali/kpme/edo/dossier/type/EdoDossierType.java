package org.kuali.kpme.edo.dossier.type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoDossierType {

    private BigDecimal dossierTypeID;
    private String dossierTypeName;
    private String dossierTypeCode;
    private String documentTypeName;
    private Date createDate;
    private BigDecimal createdBy;
    private Date lastUpdated;
    private BigDecimal updatedBy;

    public BigDecimal getDossierTypeID() {
        return dossierTypeID;
    }

    public void setDossierTypeID(BigDecimal dossierTypeID) {
        this.dossierTypeID = dossierTypeID;
    }

    public String getDossierTypeName() {
        return dossierTypeName;
    }

    public void setDossierTypeName(String dossierTypeName) {
        this.dossierTypeName = dossierTypeName;
    }

    public String getDossierTypeCode() {
        return dossierTypeCode;
    }

    public void setDossierTypeCode(String dossierTypeCode) {
        this.dossierTypeCode = dossierTypeCode;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public Date getCreatedDate() {
        return createDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createDate = createdDate;
    }

    public BigDecimal getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigDecimal createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public BigDecimal getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(BigDecimal updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getEdoDossierTypeJSONString() {
        ArrayList<String> tmp = new ArrayList<String>();
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        tmp.add(this.getDossierTypeID().toString());
        tmp.add(this.getDossierTypeName());
        tmp.add(this.getDossierTypeCode());
        tmp.add(this.getLastUpdated().toString());

        return gson.toJson(tmp, tmpType).toString();
    }
}
