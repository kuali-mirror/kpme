package org.kuali.kpme.edo.api.supplemental;

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

@XmlRootElement(name = EdoSupplementalTracking.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoSupplementalTracking.Constants.TYPE_NAME, propOrder = {
    EdoSupplementalTracking.Elements.ACTION_FULL_DATE_TIME,
    EdoSupplementalTracking.Elements.USER_PRINCIPAL_ID,
    EdoSupplementalTracking.Elements.ACKNOWLEDGED,
    EdoSupplementalTracking.Elements.EDO_SUPPLEMENTAL_TRACKING_ID,
    EdoSupplementalTracking.Elements.EDO_DOSSIER_ID,
    EdoSupplementalTracking.Elements.REVIEW_LEVEL,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoSupplementalTracking
    extends AbstractDataTransferObject
    implements EdoSupplementalTrackingContract
{

    @XmlElement(name = Elements.ACTION_FULL_DATE_TIME, required = false)
    private final DateTime actionFullDateTime;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.ACKNOWLEDGED, required = false)
    private final boolean acknowledged;
    @XmlElement(name = Elements.EDO_SUPPLEMENTAL_TRACKING_ID, required = false)
    private final String edoSupplementalTrackingId;
    @XmlElement(name = Elements.EDO_DOSSIER_ID, required = false)
    private final String edoDossierId;
    @XmlElement(name = Elements.REVIEW_LEVEL, required = false)
    private final int reviewLevel;
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
    private EdoSupplementalTracking() {
        this.actionFullDateTime = null;
        this.userPrincipalId = null;
        this.acknowledged = false;
        this.edoSupplementalTrackingId = null;
        this.edoDossierId = null;
        this.reviewLevel = 0;
        this.versionNumber = null;
        this.objectId = null;
    }

    private EdoSupplementalTracking(Builder builder) {
        this.actionFullDateTime = builder.getActionFullDateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.acknowledged = builder.isAcknowledged();
        this.edoSupplementalTrackingId = builder.getEdoSupplementalTrackingId();
        this.edoDossierId = builder.getEdoDossierId();
        this.reviewLevel = builder.getReviewLevel();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
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
    public boolean isAcknowledged() {
        return this.acknowledged;
    }

    @Override
    public String getEdoSupplementalTrackingId() {
        return this.edoSupplementalTrackingId;
    }

    @Override
    public String getEdoDossierId() {
        return this.edoDossierId;
    }

    @Override
    public int getReviewLevel() {
        return this.reviewLevel;
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
     * A builder which can be used to construct {@link EdoSupplementalTracking} instances.  Enforces the constraints of the {@link EdoSupplementalTrackingContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoSupplementalTrackingContract, ModelBuilder
    {

        private DateTime actionFullDateTime;
        private String userPrincipalId;
        private boolean acknowledged;
        private String edoSupplementalTrackingId;
        private String edoDossierId;
        private int reviewLevel;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }
        
        private Builder(String edoDossierId, int reviewLevel) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setEdoDossierId(edoDossierId);
        	setReviewLevel(reviewLevel);
        }

        public static Builder create(String edoDossierId, int reviewLevel) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(edoDossierId, reviewLevel);
        }

        public static Builder create(EdoSupplementalTrackingContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setActionFullDateTime(contract.getActionFullDateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setAcknowledged(contract.isAcknowledged());
            builder.setEdoSupplementalTrackingId(contract.getEdoSupplementalTrackingId());
            builder.setEdoDossierId(contract.getEdoDossierId());
            builder.setReviewLevel(contract.getReviewLevel());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public EdoSupplementalTracking build() {
            return new EdoSupplementalTracking(this);
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
        public boolean isAcknowledged() {
            return this.acknowledged;
        }

        @Override
        public String getEdoSupplementalTrackingId() {
            return this.edoSupplementalTrackingId;
        }

        @Override
        public String getEdoDossierId() {
            return this.edoDossierId;
        }

        @Override
        public int getReviewLevel() {
            return this.reviewLevel;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setActionFullDateTime(DateTime actionFullDateTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.actionFullDateTime = actionFullDateTime;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

        public void setAcknowledged(boolean acknowledged) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.acknowledged = acknowledged;
        }

        public void setEdoSupplementalTrackingId(String edoSupplementalTrackingId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoSupplementalTrackingId = edoSupplementalTrackingId;
        }

        public void setEdoDossierId(String edoDossierId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoDossierId = edoDossierId;
        }

        public void setReviewLevel(int reviewLevel) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.reviewLevel = reviewLevel;
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

        final static String ROOT_ELEMENT_NAME = "edoSupplementalTracking";
        final static String TYPE_NAME = "EdoSupplementalTrackingType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String ACTION_FULL_DATE_TIME = "actionFullDateTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String ACKNOWLEDGED = "acknowledged";
        final static String EDO_SUPPLEMENTAL_TRACKING_ID = "edoSupplementalTrackingId";
        final static String EDO_DOSSIER_ID = "edoDossierId";
        final static String REVIEW_LEVEL = "reviewLevel";

    }

}

