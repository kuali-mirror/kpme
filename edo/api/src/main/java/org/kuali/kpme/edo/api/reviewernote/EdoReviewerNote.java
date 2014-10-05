package org.kuali.kpme.edo.api.reviewernote;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoReviewerNote.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoReviewerNote.Constants.TYPE_NAME, propOrder = {
    EdoReviewerNote.Elements.CREATED_AT_VAL,
    EdoReviewerNote.Elements.EDO_REVIEWER_NOTE_ID,
    EdoReviewerNote.Elements.EDO_DOSSIER_ID,
    EdoReviewerNote.Elements.REVIEW_DATE_VAL,
    EdoReviewerNote.Elements.USER_PRINCIPAL_ID,
    EdoReviewerNote.Elements.NOTE,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})

public final class EdoReviewerNote
    extends AbstractDataTransferObject
    implements EdoReviewerNoteContract
{

    @XmlElement(name = Elements.CREATED_AT_VAL, required = false)
    private final Timestamp createdAtVal;
    @XmlElement(name = Elements.EDO_REVIEWER_NOTE_ID, required = false)
    private final String edoReviewerNoteId;
    @XmlElement(name = Elements.EDO_DOSSIER_ID, required = false)
    private final String edoDossierId;
    @XmlElement(name = Elements.REVIEW_DATE_VAL, required = false)
    private final Date reviewDateVal;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.NOTE, required = false)
    private final String note;
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
    private EdoReviewerNote() {
        this.createdAtVal = null;
        this.edoReviewerNoteId = null;
        this.edoDossierId = null;
        this.reviewDateVal = null;
        this.userPrincipalId = null;
        this.note = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private EdoReviewerNote(Builder builder) {
        this.createdAtVal = builder.getCreatedAtVal();
        this.edoReviewerNoteId = builder.getEdoReviewerNoteId();
        this.edoDossierId = builder.getEdoDossierId();
        this.reviewDateVal = builder.getReviewDateVal();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.note = builder.getNote();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public Timestamp getCreatedAtVal() {
        return this.createdAtVal;
    }

    @Override
    public String getEdoReviewerNoteId() {
        return this.edoReviewerNoteId;
    }

    @Override
    public String getEdoDossierId() {
        return this.edoDossierId;
    }

    @Override
    public Date getReviewDateVal() {
        return this.reviewDateVal;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public String getNote() {
        return this.note;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }


    /**
     * A builder which can be used to construct {@link EdoReviewerNote} instances.  Enforces the constraints of the {@link EdoReviewerNoteContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoReviewerNoteContract, ModelBuilder
    {

        private Timestamp createdAtVal;
        private String edoReviewerNoteId;
        private String edoDossierId;
        private Date reviewDateVal;
        private String userPrincipalId;
        private String note;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(EdoReviewerNoteContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setCreatedAtVal(contract.getCreatedAtVal());
            builder.setEdoReviewerNoteId(contract.getEdoReviewerNoteId());
            builder.setEdoDossierId(contract.getEdoDossierId());
            builder.setReviewDateVal(contract.getReviewDateVal());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setNote(contract.getNote());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public EdoReviewerNote build() {
            return new EdoReviewerNote(this);
        }

        @Override
        public Timestamp getCreatedAtVal() {
            return this.createdAtVal;
        }

        @Override
        public String getEdoReviewerNoteId() {
            return this.edoReviewerNoteId;
        }

        @Override
        public String getEdoDossierId() {
            return this.edoDossierId;
        }

        @Override
        public Date getReviewDateVal() {
            return this.reviewDateVal;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        @Override
        public String getNote() {
            return this.note;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setCreatedAtVal(Timestamp createdAtVal) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.createdAtVal = createdAtVal;
        }

        public void setEdoReviewerNoteId(String edoReviewerNoteId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoReviewerNoteId = edoReviewerNoteId;
        }

        public void setEdoDossierId(String edoDossierId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoDossierId = edoDossierId;
        }

        public void setReviewDateVal(Date reviewDateVal) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.reviewDateVal = reviewDateVal;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

        public void setNote(String note) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.note = note;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectId = objectId;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "edoReviewerNote";
        final static String TYPE_NAME = "EdoReviewerNoteType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String CREATED_AT_VAL = "createdAtVal";
        final static String EDO_REVIEWER_NOTE_ID = "edoReviewerNoteId";
        final static String EDO_DOSSIER_ID = "edoDossierId";
        final static String REVIEW_DATE_VAL = "reviewDateVal";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String NOTE = "note";

    }

}

