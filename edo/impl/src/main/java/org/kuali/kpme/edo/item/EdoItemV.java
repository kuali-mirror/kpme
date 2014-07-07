package org.kuali.kpme.edo.item;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.edo.api.item.EdoItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/16/12
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoItemV implements Comparable<EdoItemV> {

    private String edoItemID;
    private String edoItemTypeID;
    private String edoChecklistItemID;
    private String edoDossierID;
   
    private String fileName;
    private String fileLocation;
    private String notes;
    private Timestamp actionTimestamp;
    private String userPrincipalId;

    private String contentType;
    private String edoChecklistSectionID;
    private String itemTypeName;
    private String typeDescription;
    private String instructions;
    private boolean extAvailable;
    private Integer rowIndex;
    private String edoReviewLayerDefID;
    private String reviewLayerDescription;
    private BigDecimal reviewLevel;
    private String fileDescription;
    private boolean routed;
    private String action;
    private boolean active;
    
    public EdoItemV() {}

    public EdoItemV(EdoItem edoItem) {
        this.fileName = edoItem.getFileName();
        this.fileLocation = edoItem.getFileLocation();
        this.notes = edoItem.getNotes();
        this.routed = edoItem.isRouted();
        this.contentType = edoItem.getContentType();
        this.rowIndex = edoItem.getRowIndex();
        this.edoReviewLayerDefID = edoItem.getEdoReviewLayerDefID();
        this.fileDescription = edoItem.getFileDescription();
        this.userPrincipalId = edoItem.getUserPrincipalId();
        this.actionTimestamp = new Timestamp(edoItem.getActionFullDateTime().getMillis());    
    }


    public String getEdoItemID() {
        return edoItemID;
    }

    public void setEdoItemID(String edoItemID) {
        this.edoItemID = edoItemID;
    }

    public String getEdoItemTypeID() {
        return edoItemTypeID;
    }

    public void setEdoItemTypeID(String edoItemTypeID) {
        this.edoItemTypeID = edoItemTypeID;
    }

    public String getEdoChecklistItemID() {
        return edoChecklistItemID;
    }

    public void setEdoChecklistItemID(String edoChecklistItemID) {
        this.edoChecklistItemID = edoChecklistItemID;
    }

    public String getEdoDossierID() {
        return edoDossierID;
    }

    public void setEdoDossierID(String edoDossierID) {
        this.edoDossierID = edoDossierID;
    }

   public Timestamp getActionTimestamp() {
		return actionTimestamp;
	}

	public void setActionTimestamp(Timestamp actionTimestamp) {
		this.actionTimestamp = actionTimestamp;
	}
    

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isRouted() {
		return routed;
	}

	public void setRouted(boolean routed) {
		this.routed = routed;
	}

	public String getUserPrincipalId() {
        return userPrincipalId;
    }

    public void setUserPrincipalId(String userPrincipalId) {
        this.userPrincipalId = userPrincipalId;
    }
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEdoChecklistSectionID() {
        return edoChecklistSectionID;
    }

    public void setEdoChecklistSectionID(String edoChecklistSectionID) {
        this.edoChecklistSectionID = edoChecklistSectionID;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isExtAvailable() {
        return extAvailable;
    }

    public void setExtAvailable(boolean extAvailable) {
        this.extAvailable = extAvailable;
    }

    public String getEdoReviewLayerDefID() {
        return edoReviewLayerDefID;
    }

    public void setEdoReviewLayerDefID(String edoReviewLayerDefID) {
        this.edoReviewLayerDefID = edoReviewLayerDefID;
    }

    public String getReviewLayerDescription() {
        return reviewLayerDescription;
    }

    public void setReviewLayerDescription(String reviewLayerDescription) {
        this.reviewLayerDescription = reviewLayerDescription;
    }

    public BigDecimal getReviewLevel() {
        return reviewLevel;
    }

    public void setReviewLevel(BigDecimal reviewLevel) {
        this.reviewLevel = reviewLevel;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }
    
    public boolean isActive() {
    	return active;
    }
    
    public void setActive(boolean active) {
    	this.active = active;
    }

    public String getItemJSONString() {
        ArrayList<String> tmp = new ArrayList<String>();
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();
        String uploadString = new SimpleDateFormat("yyyy-MM-dd hh:mma").format(this.getActionTimestamp());

        tmp.add(this.getRowIndex() == null ? "1" : this.getRowIndex().toString());
        tmp.add(this.getEdoItemID());
        tmp.add(this.getItemTypeName());
        tmp.add(this.getEdoItemTypeID());
        tmp.add(this.getFileName());
        tmp.add(this.getFileLocation());
        tmp.add(this.getEdoChecklistItemID());
        tmp.add(this.getEdoChecklistSectionID());
        tmp.add(this.isExtAvailable() == true ? "Y" : "N");
        tmp.add(uploadString);
        tmp.add(this.getUserPrincipalId());
        tmp.add("");
        tmp.add(this.getEdoDossierID());
        tmp.add(this.getEdoReviewLayerDefID() == null ? "" : this.getEdoReviewLayerDefID());
        tmp.add(this.getReviewLayerDescription());
        tmp.add(this.getReviewLevel() == null ? "" : this.getReviewLevel().toString());
        tmp.add(this.isRouted() == true ? "Y" : "N");
        tmp.add(this.getFileDescription());

        // TODO Find out if we need to add action and active and if so, where
        
        return gson.toJson(tmp, tmpType);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    @Override
    public int compareTo(EdoItemV o) {
         return(rowIndex - o.rowIndex);
    }
}
