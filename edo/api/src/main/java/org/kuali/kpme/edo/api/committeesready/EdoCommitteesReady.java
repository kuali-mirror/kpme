package org.kuali.kpme.edo.api.committeesready;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoCommitteesReady.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoCommitteesReady.Constants.TYPE_NAME, propOrder = {
    EdoCommitteesReady.Elements.EDO_COMMITTEES_READY_ID,
    EdoCommitteesReady.Elements.READY,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoCommitteesReady.Elements.USER_PRINCIPAL_ID,
    EdoCommitteesReady.Elements.GROUP_KEY_CODE,
    EdoCommitteesReady.Elements.GROUP_KEY,
    EdoCommitteesReady.Elements.CREATE_TIME,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoCommitteesReady
    extends AbstractDataTransferObject
    implements EdoCommitteesReadyContract
{

    @XmlElement(name = Elements.EDO_COMMITTEES_READY_ID, required = false)
    private final String edoCommitteesReadyId;
    @XmlElement(name = Elements.READY, required = false)
    private final boolean ready;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = false)
    private final String groupKeyCode;
    @XmlElement(name = Elements.GROUP_KEY, required = false)
    private final HrGroupKey groupKey;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime createTime;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EdoCommitteesReady() {
        this.edoCommitteesReadyId = null;
        this.ready = false;
        this.versionNumber = null;
        this.objectId = null;
        this.userPrincipalId = null;
        this.groupKeyCode = null;
        this.groupKey = null;
        this.createTime = null;
    }

    private EdoCommitteesReady(Builder builder) {
        this.edoCommitteesReadyId = builder.getEdoCommitteesReadyId();
        this.ready = builder.isReady();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.groupKeyCode = builder.getGroupKeyCode();
        this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
        this.createTime = builder.getCreateTime();
    }

    @Override
    public String getEdoCommitteesReadyId() {
        return this.edoCommitteesReadyId;
    }

    @Override
    public boolean isReady() {
        return this.ready;
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
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public String getGroupKeyCode() {
        return this.groupKeyCode;
    }

    @Override
    public HrGroupKey getGroupKey() {
        return this.groupKey;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
    }


    /**
     * A builder which can be used to construct {@link EdoCommitteesReady} instances.  Enforces the constraints of the {@link EdoCommitteesReadyContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoCommitteesReadyContract, ModelBuilder
    {

        private String edoCommitteesReadyId;
        private DateTime fullDateTime;
        private boolean ready;
        private Long versionNumber;
        private String objectId;
        private String userPrincipalId;
        private String groupKeyCode;
        private HrGroupKey.Builder groupKey;
        private DateTime createTime;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(EdoCommitteesReadyContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setEdoCommitteesReadyId(contract.getEdoCommitteesReadyId());
            builder.setReady(contract.isReady());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setGroupKeyCode(contract.getGroupKeyCode());
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));
            builder.setCreateTime(contract.getCreateTime());
            return builder;
        }

        public EdoCommitteesReady build() {
            return new EdoCommitteesReady(this);
        }

        @Override
        public String getEdoCommitteesReadyId() {
            return this.edoCommitteesReadyId;
        }

        @Override
        public boolean isReady() {
            return this.ready;
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
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        @Override
        public String getGroupKeyCode() {
            return this.groupKeyCode;
        }

        @Override
        public HrGroupKey.Builder getGroupKey() {
            return this.groupKey;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
        }

        public void setEdoCommitteesReadyId(String edoCommitteesReadyId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoCommitteesReadyId = edoCommitteesReadyId;
        }

        public void setFullDateTime(DateTime fullDateTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.fullDateTime = fullDateTime;
        }

        public void setReady(boolean ready) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.ready = ready;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectId = objectId;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

        public void setGroupKeyCode(String groupKeyCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKeyCode = groupKeyCode;
        }

        public void setGroupKey(HrGroupKey.Builder groupKey) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKey = groupKey;
        }

        public void setCreateTime(DateTime createTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.createTime = createTime;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "edoCommitteesReady";
        final static String TYPE_NAME = "EdoCommitteesReadyType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String EDO_COMMITTEES_READY_ID = "edoCommitteesReadyId";
        final static String READY = "ready";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";
        final static String CREATE_TIME = "createTime";

    }

}