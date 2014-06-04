package org.kuali.kpme.edo.item;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private BigDecimal itemID;
    private String uploaderUsername;
    private BigDecimal itemTypeID;
    private BigDecimal checklistItemID;
    private BigDecimal dossierID;
   // private Date uploadDate;
    //edo-69
    private Timestamp uploadDate;
    private String fileName;
    private String fileLocation;
    private String notes;
    private BigDecimal addendumRouted;
    private Date createDate;
    private String createdBy;
    private Date lastUpdateDate;
    private String updatedBy;
    private String layerLevel;
    private String contentType;
    private BigDecimal checklistSectionID;
    private String itemTypeName;
    private String typeDescription;
    private String instructions;
    private BigDecimal extAvailability;
    private Integer rowIndex;
    private BigDecimal reviewLayerDefID;
    private String reviewLayerDescription;
    private BigDecimal reviewLevel;
    private String fileDescription;

    public EdoItemV() {}

    public EdoItemV(EdoItem edoItem) {
        this.uploadDate = edoItem.getUploadDate();
        this.fileName = edoItem.getFileName();
        this.fileLocation = edoItem.getFileLocation();
        this.notes = edoItem.getNotes();
        this.addendumRouted = edoItem.getAddendumRouted();
        this.createDate = edoItem.getCreateDate();
        this.createdBy = edoItem.getCreatedBy();
        this.lastUpdateDate = edoItem.getLastUpdateDate();
        this.updatedBy = edoItem.getUpdatedBy();
        this.layerLevel = edoItem.getLayerLevel();
        this.contentType = edoItem.getContentType();
        this.rowIndex = edoItem.getRowIndex();
        this.reviewLayerDefID = edoItem.getReviewLayerDefID();
        this.fileDescription = edoItem.getFileDescription();
    }


    public BigDecimal getItemID() {
        return itemID;
    }

    public void setItemID(BigDecimal itemID) {
        this.itemID = itemID;
    }

    public BigDecimal getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(BigDecimal itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public BigDecimal getChecklistItemID() {
        return checklistItemID;
    }

    public void setChecklistItemID(BigDecimal checklistItemID) {
        this.checklistItemID = checklistItemID;
    }

    public BigDecimal getDossierID() {
        return dossierID;
    }

    public void setDossierID(BigDecimal dossierID) {
        this.dossierID = dossierID;
    }

  /* public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }*/
   public Timestamp getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
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

    public BigDecimal getAddendumRouted() {
        return addendumRouted;
    }

    public void setAddendumRouted(BigDecimal isRouted) {
        this.addendumRouted = isRouted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getLayerLevel() {
        return layerLevel;
    }

    public void setLayerLevel(String layerLevel) {
        this.layerLevel = layerLevel;
    }

    public BigDecimal getChecklistSectionID() {
        return checklistSectionID;
    }

    public void setChecklistSectionID(BigDecimal checklistSectionID) {
        this.checklistSectionID = checklistSectionID;
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

    public BigDecimal getExtAvailability() {
        return extAvailability;
    }

    public void setExtAvailability(BigDecimal extAvailability) {
        this.extAvailability = extAvailability;
    }

    public String getUploaderUsername() {
        return uploaderUsername;
    }

    public void setUploaderUsername(String uploaderUsername) {
        this.uploaderUsername = uploaderUsername;
    }

    public BigDecimal getReviewLayerDefID() {
        return reviewLayerDefID;
    }

    public void setReviewLayerDefID(BigDecimal reviewLayerDefID) {
        this.reviewLayerDefID = reviewLayerDefID;
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

    public String getItemJSONString() {
        ArrayList<String> tmp = new ArrayList<String>();
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();
        String uploadString = new SimpleDateFormat("yyyy-MM-dd hh:mma").format(this.getUploadDate());

        tmp.add(this.getRowIndex() == null ? "1" : this.getRowIndex().toString());
        tmp.add(this.getItemID().toString());
        tmp.add(this.getItemTypeName());
        tmp.add(this.getItemTypeID().toString());
        tmp.add(this.getFileName());
        tmp.add(this.getFileLocation());
        tmp.add(this.getUploaderUsername());
        tmp.add(this.getChecklistItemID().toString());
        tmp.add(this.getChecklistSectionID().toString());
        tmp.add(this.getLayerLevel());
        tmp.add(this.getExtAvailability().toString());
        tmp.add(uploadString);
        tmp.add(this.getUpdatedBy());
        tmp.add("");
        tmp.add(this.getDossierID().toString());
        tmp.add(this.getReviewLayerDefID() == null ? "" : this.getReviewLayerDefID().toString());
        tmp.add(this.getReviewLayerDescription());
        tmp.add(this.getReviewLevel() == null ? "" : this.getReviewLevel().toString());
        tmp.add(this.getAddendumRouted() == null ? "" : this.getAddendumRouted().toString());
        tmp.add(this.getFileDescription());

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
