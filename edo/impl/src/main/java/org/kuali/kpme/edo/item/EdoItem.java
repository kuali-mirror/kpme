package org.kuali.kpme.edo.item;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;



/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/16/12
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoItem {
    private BigDecimal itemID;
    private String uploaderUsername;
    private BigDecimal itemTypeID;
    private BigDecimal checklistItemID;
    private BigDecimal dossierID;
   // private Date uploadDate;
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
    private Integer rowIndex;
    private BigDecimal reviewLayerDefID;
    private String fileDescription;

    public EdoItem() {}

    public EdoItem(EdoItemV edoItemV) {
        this.itemID = edoItemV.getItemID();
        this.uploaderUsername = edoItemV.getUploaderUsername();
        this.itemTypeID = edoItemV.getItemTypeID();
        this.checklistItemID = edoItemV.getChecklistItemID();
        this.dossierID = edoItemV.getDossierID();
        this.uploadDate = edoItemV.getUploadDate();
        this.fileName = edoItemV.getFileName();
        this.fileLocation = edoItemV.getFileLocation();
        this.notes = edoItemV.getNotes();
        this.addendumRouted = edoItemV.getAddendumRouted();
        this.createDate = edoItemV.getCreateDate();
        this.createdBy = edoItemV.getCreatedBy();
        this.lastUpdateDate = edoItemV.getLastUpdateDate();
        this.updatedBy = edoItemV.getUpdatedBy();
        this.layerLevel = edoItemV.getLayerLevel();
        this.reviewLayerDefID = edoItemV.getReviewLayerDefID();
        this.contentType = edoItemV.getContentType();
    }

    public BigDecimal getItemID() {
        return itemID;
    }

    public void setItemID(BigDecimal itemID) {
        this.itemID = itemID;
    }

    public String getUploaderUsername() {
        return uploaderUsername;
    }

    public void setUploaderUsername(String uploaderUsername) {
        this.uploaderUsername = uploaderUsername;
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
    //edo-69
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

    public BigDecimal getReviewLayerDefID() {
        return reviewLayerDefID;
    }

    public void setReviewLayerDefID(BigDecimal reviewLayerDefID) {
        this.reviewLayerDefID = reviewLayerDefID;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getFilenameFromPath() {
        String[] pathElements = this.getFileLocation().split("/");

        return pathElements[pathElements.length - 1];
    }
}
