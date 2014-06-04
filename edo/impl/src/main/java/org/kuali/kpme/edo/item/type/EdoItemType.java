package org.kuali.kpme.edo.item.type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EdoItemType {

    private BigDecimal itemTypeID;
    private String itemTypeName;
    private String itemTypeDescription;
    private String itemTypeInstructions;
    private boolean itemTypeExtAvailability;
    private Date createDate;
    private BigDecimal createdBy;
    private Date lastUpdated;
    private BigDecimal updatedBy;

    public void EdoItemType() throws ParseException {
        this.setItemTypeID(null);
        this.setItemTypeName(null);
        this.setItemTypeDescription(null);
        this.setItemTypeExtAvailability(false);
        this.setItemTypeInstructions(null);
        this.setCreateDate( new Date() );
        this.setCreatedBy(null);
        this.setLastUpdated( this.getCreateDate() );
        this.setUpdatedBy(null);
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

    public boolean getItemTypeExtAvailability() {
        return itemTypeExtAvailability;
    }

    public void setItemTypeExtAvailability(boolean itemTypeExtAvailability) {
        this.itemTypeExtAvailability = itemTypeExtAvailability;
    }

    public String getCreateDate() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);

        return df.format(createDate);
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setCreateDate(String createDate) throws ParseException {

        this.createDate = DateFormat.getDateInstance(DateFormat.SHORT).parse(createDate);
    }

    public BigDecimal getCreatedBy() {
        if (createdBy == null) {
            return BigDecimal.ZERO;
        }
        return createdBy;
    }

    public void setCreatedBy(BigDecimal createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdated() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);

        return df.format(lastUpdated);
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) throws ParseException {

        this.lastUpdated = DateFormat.getDateInstance(DateFormat.SHORT).parse(lastUpdated);
    }


    public BigDecimal getUpdatedBy() {
        if (updatedBy == null) {
            return BigDecimal.ZERO;
        }
        return updatedBy;
    }

    public void setUpdatedBy(BigDecimal updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getEdoItemTypeJSONString() {
        ArrayList<String> tmp = new ArrayList<String>();
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        tmp.add(this.getItemTypeID().toString());
        tmp.add(this.getItemTypeName());
        tmp.add(this.getItemTypeDescription());
        tmp.add(this.getItemTypeInstructions());
        tmp.add(String.valueOf(this.getItemTypeExtAvailability()));
        tmp.add(this.getCreateDate().toString());
        tmp.add(this.getCreatedBy().toString());
        tmp.add(this.getLastUpdated().toString());
        tmp.add(this.getUpdatedBy().toString());

        return gson.toJson(tmp, tmpType).toString();
    }

}
