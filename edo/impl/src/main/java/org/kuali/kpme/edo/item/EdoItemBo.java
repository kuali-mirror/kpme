package org.kuali.kpme.edo.item;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.api.item.EdoItemContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;



/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/16/12
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoItemBo extends PersistableBusinessObjectBase implements EdoItemContract {
   
	private String edoItemID;
	private String edoItemTypeID;
	private String edoChecklistItemID;
	private String edoDossierID;
	private String fileName;
	private String fileLocation;
	private String notes;
	private boolean routed;
	private Timestamp actionTimestamp;
	private String userPrincipalId;
	private String contentType;
	private int rowIndex;
	private String edoReviewLayerDefID;
	private String fileDescription;
	private String action;
    private boolean active;
 
    public EdoItemBo() {}

    public EdoItemBo(EdoItemV edoItemV) {
        this.edoItemID = edoItemV.getEdoItemID();
        this.edoItemTypeID = edoItemV.getEdoItemTypeID();
        this.edoChecklistItemID = edoItemV.getEdoChecklistItemID();
        this.edoDossierID = edoItemV.getEdoDossierID();
        this.fileName = edoItemV.getFileName();
        this.fileLocation = edoItemV.getFileLocation();
        this.notes = edoItemV.getNotes();       		
        this.contentType = edoItemV.getContentType();
        this.edoReviewLayerDefID = edoItemV.getEdoReviewLayerDefID();
        this.fileDescription = edoItemV.getFileDescription();   
        this.actionTimestamp = edoItemV.getActionTimestamp();
        this.userPrincipalId = edoItemV.getUserPrincipalId();
        this.action = edoItemV.getAction();
        this.active = edoItemV.isActive();
    }

	public String getId() {
		return getEdoItemID();
	}

	public void setId(String id) {
		setEdoItemID(id);
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getEdoReviewLayerDefID() {
		return edoReviewLayerDefID;
	}

	public void setEdoReviewLayerDefID(String edoReviewLayerDefID) {
		this.edoReviewLayerDefID = edoReviewLayerDefID;
	}

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Timestamp getActionTimestamp() {
		return actionTimestamp;
	}

	public void setActionTimestamp(Timestamp actionTimestamp) {
		this.actionTimestamp = actionTimestamp;
	}

    public DateTime getActionFullDateTime() {
        return actionTimestamp != null ? new DateTime(actionTimestamp) : null;
    }	

	public static EdoItemBo from(EdoItem im) {
        if (im == null) {
            return null;
        }
        EdoItemBo it = new EdoItemBo();
        it.setEdoItemID(im.getEdoItemID());
        it.setEdoItemTypeID(im.getEdoItemTypeID());
        it.setEdoChecklistItemID(im.getEdoChecklistItemID());
        it.setEdoDossierID(im.getEdoDossierID());
        it.setFileName(im.getFileName());
        it.setFileLocation(im.getFileLocation());
        it.setNotes(im.getNotes());
        it.setRouted(im.isRouted());
        it.setContentType(im.getContentType());
        it.setRowIndex(im.getRowIndex());
        it.setEdoReviewLayerDefID(im.getEdoReviewLayerDefID());
        it.setFileDescription(im.getFileDescription());
        it.setActive(im.isActive());
        it.setUserPrincipalId(im.getUserPrincipalId());
        it.setAction(im.getAction());
        it.setActionTimestamp(im.getActionFullDateTime() == null ? null : new Timestamp(im.getActionFullDateTime().getMillis()));
        it.setVersionNumber(im.getVersionNumber());
        it.setObjectId(im.getObjectId());
        
        return it;
    }

    public static EdoItem to(EdoItemBo bo) {
        if (bo == null) {
            return null;
        }

        return EdoItem.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoItemBo, EdoItem> toImmutable = new ModelObjectUtils.Transformer<EdoItemBo, EdoItem>() {
        public EdoItem transform(EdoItemBo input) {
            return EdoItemBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoItem, EdoItemBo> toBo = new ModelObjectUtils.Transformer<EdoItem, EdoItemBo>() {
        public EdoItemBo transform(EdoItem input) {
            return EdoItemBo.from(input);
        };
    };

}
