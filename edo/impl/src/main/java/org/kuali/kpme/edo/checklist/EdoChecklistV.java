package org.kuali.kpme.edo.checklist;

import java.math.BigDecimal;
import java.util.Date;
/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/6/12
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */

public class EdoChecklistV {
    private BigDecimal checklistItemID;
    private String checklistDescription;
    private String departmentID;
    private String schoolID;
    private String campusCode;
    private String dossierTypeCode;
    private Date effectiveDate;
    private String checklistSectionName;
    private BigDecimal checklistSectionID;
    private String sectionDescription;
    private String checklistItemName;
    private String itemDescription;
    private int required;
    private BigDecimal checklistItemOrdinal;
    private BigDecimal checklistSectionOrdinal;

    public BigDecimal getChecklistItemID() {
        return checklistItemID;
    }

    public void setChecklistItemID(BigDecimal checklistItemID) {
        this.checklistItemID = checklistItemID;
    }

    public String getChecklistDescription() {
        return checklistDescription;
    }

    public void setChecklistDescription(String checklistDescription) {
        this.checklistDescription = checklistDescription;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    public String getDossierTypeCode() {
        return dossierTypeCode;
    }

    public void setDossierTypeCode(String dossierTypeCode) {
        this.dossierTypeCode = dossierTypeCode;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getChecklistSectionName() {
        return checklistSectionName;
    }

    public void setChecklistSectionName(String checklistSectionName) {
        this.checklistSectionName = checklistSectionName;
    }

    public String getSectionDescription() {
        return sectionDescription;
    }

    public void setSectionDescription(String sectionDescription) {
        this.sectionDescription = sectionDescription;
    }

    public String getChecklistItemName() {
        return checklistItemName;
    }

    public void setChecklistItemName(String checklistItemName) {
        this.checklistItemName = checklistItemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public BigDecimal getChecklistItemOrdinal() {
        return checklistItemOrdinal;
    }

    public void setChecklistItemOrdinal(BigDecimal checklistItemOrdinal) {
        this.checklistItemOrdinal = checklistItemOrdinal;
    }

    public BigDecimal getChecklistSectionOrdinal() {
        return checklistSectionOrdinal;
    }

    public void setChecklistSectionOrdinal(BigDecimal checklistSectionOrdinal) {
        this.checklistSectionOrdinal = checklistSectionOrdinal;
    }

    public BigDecimal getChecklistSectionID() {
        return checklistSectionID;
    }

    public void setChecklistSectionID(BigDecimal checklistSectionID) {
        this.checklistSectionID = checklistSectionID;
    }
}
