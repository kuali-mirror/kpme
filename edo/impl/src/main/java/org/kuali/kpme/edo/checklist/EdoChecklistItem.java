package org.kuali.kpme.edo.checklist;

import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 9:54 AM
 */
public class EdoChecklistItem {
    private BigDecimal checklistItemID;
    private BigDecimal checklistSectionID;
    private String checklistItemName;
    private String itemDescription;
    private int required;
    private BigDecimal checklistItemOrdinal;

    public BigDecimal getChecklistItemID() {
        return checklistItemID;
    }

    public void setChecklistItemID(BigDecimal checklistItemID) {
        this.checklistItemID = checklistItemID;
    }

    public BigDecimal getChecklistSectionID() {
        return checklistSectionID;
    }

    public void setChecklistSectionID(BigDecimal checklistSectionID) {
        this.checklistSectionID = checklistSectionID;
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

}
