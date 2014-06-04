package org.kuali.kpme.edo.item.count;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 6/21/13
 * Time: 10:39 AM
 */
public class EdoItemCountV  {

    private BigDecimal docCount;
    private BigDecimal checklistItemId;
    private String checklistItemName;
    private BigDecimal dossierId;
    private BigDecimal checklistSectionId;

    public BigDecimal getDocCount() {
        return docCount;
    }

    public void setDocCount(BigDecimal docCount) {
        this.docCount = docCount;
    }

    public BigDecimal getChecklistItemId() {
        return checklistItemId;
    }

    public void setChecklistItemId(BigDecimal checklistItemId) {
        this.checklistItemId = checklistItemId;
    }

    public String getChecklistItemName() {
        return checklistItemName;
    }

    public void setChecklistItemName(String checklistItemName) {
        this.checklistItemName = checklistItemName;
    }

    public BigDecimal getDossierId() {
        return dossierId;
    }

    public void setDossierId(BigDecimal dossierId) {
        this.dossierId = dossierId;
    }

    public BigDecimal getChecklistSectionId() {
        return checklistSectionId;
    }

    public void setChecklistSectionId(BigDecimal checklistSectionId) {
        this.checklistSectionId = checklistSectionId;
    }

    public String getItemCountJSON() {
        ArrayList<String> tmp = new ArrayList<String>();
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        tmp.add(this.getDocCount().toString());
        tmp.add(this.getChecklistItemName());
        tmp.add(this.getChecklistItemId().toString());
        tmp.add(this.getChecklistSectionId().toString());
        tmp.add(this.getDossierId().toString());

        return gson.toJson(tmp, tmpType).toString();
    }
}
