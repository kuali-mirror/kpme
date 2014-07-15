package org.kuali.kpme.edo.api.item;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.DateTime;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoItem.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoItem.Constants.TYPE_NAME, propOrder = {
    EdoItem.Elements.FILE_NAME,
    EdoItem.Elements.CONTENT_TYPE,
    EdoItem.Elements.ACTION_FULL_DATE_TIME,
    EdoItem.Elements.USER_PRINCIPAL_ID,
    EdoItem.Elements.FILE_DESCRIPTION,
    EdoItem.Elements.EDO_REVIEW_LAYER_DEF_I_D,
    EdoItem.Elements.EDO_ITEM_TYPE_I_D,
    EdoItem.Elements.EDO_CHECKLIST_ITEM_I_D,
    EdoItem.Elements.FILE_LOCATION,
    EdoItem.Elements.EDO_DOSSIER_I_D,
    EdoItem.Elements.ACTION,
    EdoItem.Elements.ROW_INDEX,
    EdoItem.Elements.ACTIVE,
    EdoItem.Elements.ROUTED,
    EdoItem.Elements.EDO_ITEM_I_D,
    EdoItem.Elements.NOTES,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoItem
    extends AbstractDataTransferObject
    implements EdoItemContract
{

    @XmlElement(name = Elements.FILE_NAME, required = false)
    private final String fileName;
    @XmlElement(name = Elements.CONTENT_TYPE, required = false)
    private final String contentType;
    @XmlElement(name = Elements.ACTION_FULL_DATE_TIME, required = false)
    private final DateTime actionFullDateTime;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.FILE_DESCRIPTION, required = false)
    private final String fileDescription;
    @XmlElement(name = Elements.EDO_REVIEW_LAYER_DEF_I_D, required = false)
    private final String edoReviewLayerDefID;
    @XmlElement(name = Elements.EDO_ITEM_TYPE_I_D, required = false)
    private final String edoItemTypeID;
    @XmlElement(name = Elements.EDO_CHECKLIST_ITEM_I_D, required = false)
    private final String edoChecklistItemId;
    @XmlElement(name = Elements.FILE_LOCATION, required = false)
    private final String fileLocation;
    @XmlElement(name = Elements.EDO_DOSSIER_I_D, required = false)
    private final String edoDossierID;
    @XmlElement(name = Elements.ACTION, required = false)
    private final String action;
    @XmlElement(name = Elements.ROW_INDEX, required = false)
    private final Integer rowIndex;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ROUTED, required = false)
    private final boolean routed;
    @XmlElement(name = Elements.EDO_ITEM_I_D, required = false)
    private final String edoItemId;
    @XmlElement(name = Elements.NOTES, required = false)
    private final String notes;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EdoItem() {
        this.fileName = null;
        this.contentType = null;
        this.actionFullDateTime = null;
        this.userPrincipalId = null;
        this.fileDescription = null;
        this.edoReviewLayerDefID = null;
        this.edoItemTypeID = null;
        this.edoChecklistItemId = null;
        this.fileLocation = null;
        this.edoDossierID = null;
        this.action = null;
        this.rowIndex = null;
        this.active = false;
        this.routed = false;
        this.edoItemId = null;
        this.notes = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private EdoItem(Builder builder) {
        this.fileName = builder.getFileName();
        this.contentType = builder.getContentType();
        this.actionFullDateTime = builder.getActionFullDateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.fileDescription = builder.getFileDescription();
        this.edoReviewLayerDefID = builder.getEdoReviewLayerDefId();
        this.edoItemTypeID = builder.getEdoItemTypeId();
        this.edoChecklistItemId = builder.getEdoChecklistItemId();
        this.fileLocation = builder.getFileLocation();
        this.edoDossierID = builder.getEdoDossierId();
        this.action = builder.getAction();
        this.rowIndex = builder.getRowIndex();
        this.active = builder.isActive();
        this.routed = builder.isRouted();
        this.edoItemId = builder.getEdoItemId();
        this.notes = builder.getNotes();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public DateTime getActionFullDateTime() {
        return this.actionFullDateTime;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public String getFileDescription() {
        return this.fileDescription;
    }

    @Override
    public String getEdoReviewLayerDefId() {
        return this.edoReviewLayerDefID;
    }

    @Override
    public String getEdoItemTypeId() {
        return this.edoItemTypeID;
    }

    @Override
    public String getEdoChecklistItemId() {
        return this.edoChecklistItemId;
    }

    @Override
    public String getFileLocation() {
        return this.fileLocation;
    }

    @Override
    public String getEdoDossierId() {
        return this.edoDossierID;
    }

    @Override
    public String getAction() {
        return this.action;
    }

    @Override
    public int getRowIndex() {
        return this.rowIndex;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public boolean isRouted() {
        return this.routed;
    }

    @Override
    public String getEdoItemId() {
        return this.edoItemId;
    }

    @Override
    public String getNotes() {
        return this.notes;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }
    
    @Override
    public int compareTo(EdoItemContract eic) {
        return (this.getRowIndex() - eic.getRowIndex());
    }


    /**
     * A builder which can be used to construct {@link EdoItem} instances.  Enforces the constraints of the {@link EdoItemContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoItemContract, ModelBuilder
    {

        private String fileName;
        private String contentType;
        private DateTime actionFullDateTime;
        private String userPrincipalId;
        private String fileDescription;
        private String edoReviewLayerDefID;
        private String edoItemTypeID;
        private String edoChecklistItemId;
        private String fileLocation;
        private String edoDossierID;
        private String action;
        private Integer rowIndex;
        private boolean active;
        private boolean routed;
        private String edoItemId;
        private String notes;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(EdoItemContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setFileName(contract.getFileName());
            builder.setContentType(contract.getContentType());
            builder.setActionFullDateTime(contract.getActionFullDateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setFileDescription(contract.getFileDescription());
            builder.setEdoReviewLayerDefId(contract.getEdoReviewLayerDefId());
            builder.setEdoItemTypeId(contract.getEdoItemTypeId());
            builder.setEdoChecklistItemId(contract.getEdoChecklistItemId());
            builder.setFileLocation(contract.getFileLocation());
            builder.setEdoDossierId(contract.getEdoDossierId());
            builder.setAction(contract.getAction());
            builder.setRowIndex(contract.getRowIndex());
            builder.setActive(contract.isActive());
            builder.setRouted(contract.isRouted());
            builder.setEdoItemId(contract.getEdoItemId());
            builder.setNotes(contract.getNotes());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public EdoItem build() {
            return new EdoItem(this);
        }

        @Override
        public String getFileName() {
            return this.fileName;
        }

        @Override
        public String getContentType() {
            return this.contentType;
        }

        @Override
        public DateTime getActionFullDateTime() {
            return this.actionFullDateTime;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        @Override
        public String getFileDescription() {
            return this.fileDescription;
        }

        @Override
        public String getEdoReviewLayerDefId() {
            return this.edoReviewLayerDefID;
        }

        @Override
        public String getEdoItemTypeId() {
            return this.edoItemTypeID;
        }

        @Override
        public String getEdoChecklistItemId() {
            return this.edoChecklistItemId;
        }

        @Override
        public String getFileLocation() {
            return this.fileLocation;
        }

        @Override
        public String getEdoDossierId() {
            return this.edoDossierID;
        }

        @Override
        public String getAction() {
            return this.action;
        }

        @Override
        public int getRowIndex() {
            return this.rowIndex;
        }

        @Override
        public boolean isActive() {
            return this.active;
        }

        @Override
        public boolean isRouted() {
            return this.routed;
        }

        @Override
        public String getEdoItemId() {
            return this.edoItemId;
        }

        @Override
        public String getNotes() {
            return this.notes;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setFileName(String fileName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.fileName = fileName;
        }

        public void setContentType(String contentType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.contentType = contentType;
        }

        public void setActionFullDateTime(DateTime actionFullDateTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.actionFullDateTime = actionFullDateTime;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

        public void setFileDescription(String fileDescription) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.fileDescription = fileDescription;
        }

        public void setEdoReviewLayerDefId(String edoReviewLayerDefID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoReviewLayerDefID = edoReviewLayerDefID;
        }

        public void setEdoItemTypeId(String edoItemTypeID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoItemTypeID = edoItemTypeID;
        }

        public void setEdoChecklistItemId(String edoChecklistItemId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoChecklistItemId = edoChecklistItemId;
        }

        public void setFileLocation(String fileLocation) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.fileLocation = fileLocation;
        }

        public void setEdoDossierId(String edoDossierID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoDossierID = edoDossierID;
        }

        public void setAction(String action) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.action = action;
        }

        public void setRowIndex(Integer rowIndex) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.rowIndex = rowIndex;
        }

        public void setActive(boolean active) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.active = active;
        }

        public void setRouted(boolean routed) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.routed = routed;
        }

        public void setEdoItemId(String edoItemId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoItemId = edoItemId;
        }

        public void setNotes(String notes) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.notes = notes;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectId = objectId;
        }
        
        @Override
        public int compareTo(EdoItemContract eic) {
            return (this.getRowIndex() - eic.getRowIndex());
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "edoItem";
        final static String TYPE_NAME = "EdoItemType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String FILE_NAME = "fileName";
        final static String CONTENT_TYPE = "contentType";
        final static String ACTION_FULL_DATE_TIME = "actionFullDateTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String FILE_DESCRIPTION = "fileDescription";
        final static String EDO_REVIEW_LAYER_DEF_I_D = "edoReviewLayerDefID";
        final static String EDO_ITEM_TYPE_I_D = "edoItemTypeID";
        final static String EDO_CHECKLIST_ITEM_I_D = "edoChecklistItemId";
        final static String FILE_LOCATION = "fileLocation";
        final static String EDO_DOSSIER_I_D = "edoDossierID";
        final static String ACTION = "action";
        final static String ROW_INDEX = "rowIndex";
        final static String ACTIVE = "active";
        final static String ROUTED = "routed";
        final static String EDO_ITEM_I_D = "edoItemId";
        final static String NOTES = "notes";

    }

}

