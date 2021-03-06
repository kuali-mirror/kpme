package org.kuali.kpme.edo.api.candidate;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.util.jaxb.LocalTimeAdapter;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoCandidate.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoCandidate.Constants.TYPE_NAME, propOrder = {
    
    EdoCandidate.Elements.CANDIDACY_SCHOOL,
    EdoCandidate.Elements.EDO_CANDIDATE_ID,
    EdoCandidate.Elements.PRINCIPAL_NAME,
    EdoCandidate.Elements.PRIMARY_DEPT_ID,
    EdoCandidate.Elements.TNP_DEPT_ID,
    EdoCandidate.Elements.FIRST_NAME,
    EdoCandidate.Elements.LAST_NAME,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoCandidate.Elements.ACTIVE,
    EdoCandidate.Elements.ID,
    EdoCandidate.Elements.EFFECTIVE_LOCAL_DATE,
    EdoCandidate.Elements.CREATE_TIME,
    EdoCandidate.Elements.USER_PRINCIPAL_ID,
    EdoCandidate.Elements.GROUP_KEY_CODE,
    EdoCandidate.Elements.GROUP_KEY,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoCandidate extends AbstractDataTransferObject implements EdoCandidateContract
{    
    @XmlElement(name = Elements.CANDIDACY_SCHOOL, required = false)
    private final String candidacySchool;
    @XmlElement(name = Elements.EDO_CANDIDATE_ID, required = false)
    private final String edoCandidateId;
    @XmlElement(name = Elements.PRINCIPAL_NAME, required = false)
    private final String principalName;
    @XmlElement(name = Elements.PRIMARY_DEPT_ID, required = false)
    private final String primaryDeptId;
    @XmlElement(name = Elements.TNP_DEPT_ID, required = false)
    private final String tnpDeptId;
    @XmlElement(name = Elements.FIRST_NAME, required = false)
    private final String firstName;
    @XmlElement(name = Elements.LAST_NAME, required = false)
    private final String lastName;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    private final LocalDate effectiveLocalDate;
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    private final DateTime createTime;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = false)
    private final String groupKeyCode;
    @XmlElement(name = Elements.GROUP_KEY, required = false)
    private final HrGroupKey groupKey;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EdoCandidate() {
        this.candidacySchool = null;
        this.edoCandidateId = null;
        this.principalName = null;
        this.primaryDeptId = null;
        this.tnpDeptId = null;
        this.firstName = null;
        this.lastName = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
        this.groupKeyCode = null;
        this.groupKey = null;
    }
    
    private EdoCandidate(Builder builder) {
        this.candidacySchool = builder.getCandidacySchool();
        this.edoCandidateId = builder.getEdoCandidateId();
        this.principalName = builder.getPrincipalName();
        this.primaryDeptId = builder.getPrimaryDeptId();
        this.tnpDeptId = builder.getTnpDeptId();
        this.firstName = builder.getFirstName();
        this.lastName = builder.getLastName();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.groupKeyCode = builder.getGroupKeyCode();
        this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
    }

    @Override
    public String getCandidacySchool() {
        return this.candidacySchool;
    }

    @Override
    public String getEdoCandidateId() {
        return this.edoCandidateId;
    }

    @Override
    public String getPrincipalName() {
        return this.principalName;
    }

    @Override
    public String getPrimaryDeptId() {
        return this.primaryDeptId;
    }


    @Override
    public String getTnpDeptId() {
        return this.tnpDeptId;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
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
    public boolean isActive() {
        return this.active;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public LocalDate getEffectiveLocalDate() {
        return this.effectiveLocalDate;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
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


    /**
     * A builder which can be used to construct {@link EdoCandidate} instances.  Enforces the constraints of the {@link EdoCandidateContract}.
     * 
     */
    public final static class Builder implements Serializable, EdoCandidateContract, ModelBuilder
    {
        private String candidacySchool;
        private String edoCandidateId;
        private String principalName;
        private String primaryDeptId;
        private String tnpDeptId;
        private String firstName;
        private String lastName;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
        private String groupKeyCode;
        private HrGroupKey.Builder groupKey;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        private Builder(String principalName) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setPrincipalName(principalName);
        }

        public static Builder create(String principalName) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(principalName);
        }
        
        public static Builder create(EdoCandidateContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setCandidacySchool(contract.getCandidacySchool());
            builder.setEdoCandidateId(contract.getEdoCandidateId());
            builder.setPrincipalName(contract.getPrincipalName());
            builder.setPrimaryDeptId(contract.getPrimaryDeptId());
            builder.setTnpDeptId(contract.getTnpDeptId());
            builder.setFirstName(contract.getFirstName());
            builder.setLastName(contract.getLastName());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setGroupKeyCode(contract.getGroupKeyCode());
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));
            return builder;
        }

        public EdoCandidate build() {
            return new EdoCandidate(this);
        }


        @Override
        public String getCandidacySchool() {
            return this.candidacySchool;
        }

        @Override
        public String getEdoCandidateId() {
            return this.edoCandidateId;
        }

        @Override
        public String getPrincipalName() {
            return this.principalName;
        }

        @Override
        public String getPrimaryDeptId() {
            return this.primaryDeptId;
        }
        
        @Override
        public String getTnpDeptId() {
            return this.tnpDeptId;
        }

        @Override
        public String getFirstName() {
            return this.firstName;
        }

        @Override
        public String getLastName() {
            return this.lastName;
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
        public boolean isActive() {
            return this.active;
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public LocalDate getEffectiveLocalDate() {
            return this.effectiveLocalDate;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
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

        public void setCandidacySchool(String candidacySchool) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.candidacySchool = candidacySchool;
        }

        public void setEdoCandidateId(String edoCandidateId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoCandidateId = edoCandidateId;
        }

        public void setPrincipalName(String principalName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.principalName = principalName;
        }

        public void setPrimaryDeptId(String primaryDeptId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.primaryDeptId = primaryDeptId;
        }

        public void setTnpDeptId(String tnpDeptId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.tnpDeptId = tnpDeptId;
        }

        public void setFirstName(String firstName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.lastName = lastName;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectId = objectId;
        }

        public void setActive(boolean active) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.active = active;
        }

        public void setId(String id) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.id = id;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.effectiveLocalDate = effectiveLocalDate;
        }

        public void setCreateTime(DateTime createTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.createTime = createTime;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

        public void setGroupKeyCode(String groupKeyCode) {
            if (StringUtils.isWhitespace(groupKeyCode)) {
                throw new IllegalArgumentException("groupKeyCode is blank");
            }
            this.groupKeyCode = groupKeyCode;
        }

        public void setGroupKey(HrGroupKey.Builder groupKey) {
            this.groupKey = groupKey;
        }
    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "edoCandidate";
        final static String TYPE_NAME = "EdoCandidateType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {
        final static String CANDIDACY_SCHOOL = "candidacySchool";
        final static String EDO_CANDIDATE_ID = "edoCandidateId";
        final static String PRINCIPAL_NAME = "principalName";
        final static String PRIMARY_DEPT_ID = "primaryDeptId";
        final static String TNP_DEPT_ID = "tnpDeptId";
        final static String FIRST_NAME = "firstName";
        final static String LAST_NAME = "lastName";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";
    }

}


