package org.kuali.kpme.edo.item.type.web;

import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.item.type.EdoItemType;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */

public class EdoItemTypeForm extends EdoForm {

    public BigDecimal itemTypeID;
    public String itemTypeName;
    public String itemTypeDescription;
    public String itemTypeInstructions;
    public boolean itemTypeExtAvailability = false;
    public BigDecimal updatedBy;
    public BigDecimal createdBy;
    public String createDate = DateFormat.getDateInstance(DateFormat.SHORT).format( new Date() );
    public String lastUpdated = createDate;

    private List<EdoItemType> itemTypeList = EdoServiceLocator.getEdoItemTypeService().getItemTypeList();

    public EdoItemType getItemType(BigDecimal itemTypeID) {
        return EdoServiceLocator.getEdoItemTypeService().getItemType(itemTypeID);
    }

    public List<EdoItemType> getItemTypeList() {
        return itemTypeList;
    }

    public BigDecimal getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(BigDecimal itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getItemTypeDescription() {
        return itemTypeDescription;
    }

    public void setItemTypeDescription(String itemTypeDescription) {
        this.itemTypeDescription = itemTypeDescription;
    }

    public String getItemTypeInstructions() {
        return itemTypeInstructions;
    }

    public void setItemTypeInstructions(String itemTypeInstructions) {
        this.itemTypeInstructions = itemTypeInstructions;
    }

    public Boolean getItemTypeExtAvailability() {
        return itemTypeExtAvailability;
    }

    public void setItemTypeExtAvailability(String itemTypeExtAvailability) {
        if ((itemTypeExtAvailability == "on") ||
            (itemTypeExtAvailability == "true") ||
            (itemTypeExtAvailability == "yes") )
        {
            this.itemTypeExtAvailability = true;
        } else {
            this.itemTypeExtAvailability = false;
        }
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String itemTypeLastUpdated) {
        this.lastUpdated = itemTypeLastUpdated;

    }

    public BigDecimal getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigDecimal createdBy) {
        this.createdBy = createdBy;
    }

    public BigDecimal getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(BigDecimal updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
