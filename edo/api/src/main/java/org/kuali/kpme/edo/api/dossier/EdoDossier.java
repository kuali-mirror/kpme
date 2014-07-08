package org.kuali.kpme.edo.api.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoDossier.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoDossier.Constants.TYPE_NAME, propOrder = {
    EdoDossier.Elements.WORKFLOW_ID,
    EdoDossier.Elements.SECONDARY_UNIT,
    EdoDossier.Elements.DOSSIER_STATUS,
    EdoDossier.Elements.RANK_SOUGHT,
    EdoDossier.Elements.CURRENT_RANK,
    EdoDossier.Elements.DEPARTMENT_ID,
    EdoDossier.Elements.ORGANIZATION_CODE,
    EdoDossier.Elements.EDO_DOSSIER_ID,
    EdoDossier.Elements.EDO_DOSSIER_TYPE_ID,
    EdoDossier.Elements.CANDIDATE_PRINCIPALNAME,
    EdoDossier.Elements.EDO_CHECKLIST_ID,
    EdoDossier.Elements.DUE_DATE,
    EdoDossier.Elements.AOE_CODE,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoDossier.Elements.ACTIVE,
    EdoDossier.Elements.ID,
    EdoDossier.Elements.EFFECTIVE_LOCAL_DATE,
    EdoDossier.Elements.CREATE_TIME,
    EdoDossier.Elements.USER_PRINCIPAL_ID,
    EdoDossier.Elements.GROUP_KEY_CODE,
    EdoDossier.Elements.GROUP_KEY,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoDossier
    extends AbstractDataTransferObject
    implements EdoDossierContract
{

    @XmlElement(name = Elements.WORKFLOW_ID, required = false)
    private final String workflowId;
    @XmlElement(name = Elements.SECONDARY_UNIT, required = false)
    private final String secondaryUnit;
    @XmlElement(name = Elements.DOSSIER_STATUS, required = false)
    private final String dossierStatus;
    @XmlElement(name = Elements.RANK_SOUGHT, required = false)
    private final String rankSought;
    @XmlElement(name = Elements.CURRENT_RANK, required = false)
    private final String currentRank;
    @XmlElement(name = Elements.DEPARTMENT_ID, required = false)
    private final String departmentID;
    @XmlElement(name = Elements.ORGANIZATION_CODE, required = false)
    private final String organizationCode;
    @XmlElement(name = Elements.EDO_DOSSIER_ID, required = false)
    private final String edoDossierID;
    @XmlElement(name = Elements.EDO_DOSSIER_TYPE_ID, required = false)
    private final String edoDossierTypeID;
    @XmlElement(name = Elements.CANDIDATE_PRINCIPALNAME, required = false)
    private final String candidatePrincipalName;
    @XmlElement(name = Elements.EDO_CHECKLIST_ID, required = false)
    private final String edoChecklistID;
    @XmlElement(name = Elements.DUE_DATE, required = false)
    private final Date dueDate;
    @XmlElement(name = Elements.AOE_CODE, required = false)
    private final String aoeCode;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    private final LocalDate effectiveLocalDate;
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
    private EdoDossier() {
        this.workflowId = null;
        this.secondaryUnit = null;
        this.dossierStatus = null;
        this.rankSought = null;
        this.currentRank = null;
        this.departmentID = null;
        this.organizationCode = null;
        this.edoDossierID = null;
        this.edoDossierTypeID = null;
        this.candidatePrincipalName = null;
        this.edoChecklistID = null;
        this.dueDate = null;
        this.aoeCode = null;
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

    private EdoDossier(Builder builder) {
        this.workflowId = builder.getWorkflowId();
        this.secondaryUnit = builder.getSecondaryUnit();
        this.dossierStatus = builder.getDossierStatus();
        this.rankSought = builder.getRankSought();
        this.currentRank = builder.getCurrentRank();
        this.departmentID = builder.getDepartmentID();
        this.organizationCode = builder.getOrganizationCode();
        this.edoDossierID = builder.getEdoDossierID();
        this.edoDossierTypeID = builder.getEdoDossierTypeID();
        this.candidatePrincipalName = builder.getCandidatePrincipalName();
        this.edoChecklistID = builder.getEdoChecklistID();
        this.dueDate = builder.getDueDate();
        this.aoeCode = builder.getAoeCode();
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
    public String getWorkflowId() {
        return this.workflowId;
    }

    @Override
    public String getSecondaryUnit() {
        return this.secondaryUnit;
    }

    @Override
    public String getDossierStatus() {
        return this.dossierStatus;
    }

    @Override
    public String getRankSought() {
        return this.rankSought;
    }

    @Override
    public String getCurrentRank() {
        return this.currentRank;
    }

    @Override
    public String getDepartmentID() {
        return this.departmentID;
    }

    @Override
    public String getOrganizationCode() {
        return this.organizationCode;
    }

    @Override
    public String getEdoDossierID() {
        return this.edoDossierID;
    }

    @Override
    public String getEdoDossierTypeID() {
        return this.edoDossierTypeID;
    }

    @Override
    public String getCandidatePrincipalName() {
        return this.candidatePrincipalName;
    }

    @Override
    public String getEdoChecklistID() {
        return this.edoChecklistID;
    }

    @Override
    public Date getDueDate() {
        return this.dueDate;
    }

    @Override
    public String getAoeCode() {
        return this.aoeCode;
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
     * A builder which can be used to construct {@link EdoDossier} instances.  Enforces the constraints of the {@link EdoDossierContract}.
     * 
     */
    public final static class Builder implements Serializable, EdoDossierContract, ModelBuilder
    {

        private String workflowId;
        private String secondaryUnit;
        private String dossierStatus;
        private String rankSought;
        private String currentRank;
        private String departmentID;
        private String organizationCode;
        private String edoDossierID;
        private String edoDossierTypeID;
        private String candidatePrincipalName;
        private String edoChecklistID;
        private Date dueDate;
        private String aoeCode;
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

        private Builder(String candidatePrincipalName) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setCandidatePrincipalName(candidatePrincipalName);
        }

        public static Builder create(String candidatePrincipalName) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(candidatePrincipalName);
        }
        
        public static Builder create(EdoDossierContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setWorkflowId(contract.getWorkflowId());
            builder.setSecondaryUnit(contract.getSecondaryUnit());
            builder.setDossierStatus(contract.getDossierStatus());
            builder.setRankSought(contract.getRankSought());
            builder.setCurrentRank(contract.getCurrentRank());
            builder.setDepartmentID(contract.getDepartmentID());
            builder.setOrganizationCode(contract.getOrganizationCode());
            builder.setEdoDossierID(contract.getEdoDossierID());
            builder.setEdoDossierTypeID(contract.getEdoDossierTypeID());
            builder.setCandidatePrincipalName(contract.getCandidatePrincipalName());
            builder.setEdoChecklistID(contract.getEdoChecklistID());
            builder.setDueDate(contract.getDueDate());
            builder.setAoeCode(contract.getAoeCode());
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

        public EdoDossier build() {
            return new EdoDossier(this);
        }

        @Override
        public String getWorkflowId() {
            return this.workflowId;
        }

        @Override
        public String getSecondaryUnit() {
            return this.secondaryUnit;
        }

        @Override
        public String getDossierStatus() {
            return this.dossierStatus;
        }

        @Override
        public String getRankSought() {
            return this.rankSought;
        }

        @Override
        public String getCurrentRank() {
            return this.currentRank;
        }

        @Override
        public String getDepartmentID() {
            return this.departmentID;
        }

        @Override
        public String getOrganizationCode() {
            return this.organizationCode;
        }

        @Override
        public String getEdoDossierID() {
            return this.edoDossierID;
        }

        @Override
        public String getEdoDossierTypeID() {
            return this.edoDossierTypeID;
        }

        @Override
        public String getCandidatePrincipalName() {
            return this.candidatePrincipalName;
        }

        @Override
        public String getEdoChecklistID() {
            return this.edoChecklistID;
        }

        @Override
        public Date getDueDate() {
            return this.dueDate;
        }

        @Override
        public String getAoeCode() {
            return this.aoeCode;
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
        
        public void setWorkflowId(String workflowId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.workflowId = workflowId;
        }

        public void setSecondaryUnit(String secondaryUnit) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.secondaryUnit = secondaryUnit;
        }

        public void setDossierStatus(String dossierStatus) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.dossierStatus = dossierStatus;
        }

        public void setRankSought(String rankSought) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.rankSought = rankSought;
        }

        public void setCurrentRank(String currentRank) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.currentRank = currentRank;
        }

        public void setDepartmentID(String departmentID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.departmentID = departmentID;
        }

        public void setOrganizationCode(String organizationCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.organizationCode = organizationCode;
        }

        public void setEdoDossierID(String edoDossierID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoDossierID = edoDossierID;
        }

        public void setEdoDossierTypeID(String edoDossierTypeID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoDossierTypeID = edoDossierTypeID;
        }

        public void setCandidatePrincipalName(String candidatePrincipalName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.candidatePrincipalName = candidatePrincipalName;
        }

        public void setEdoChecklistID(String edoChecklistID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoChecklistID = edoChecklistID;
        }

        public void setDueDate(Date dueDate) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.dueDate = dueDate;
        }

        public void setAoeCode(String aoeCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.aoeCode = aoeCode;
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

        final static String ROOT_ELEMENT_NAME = "edoDossier";
        final static String TYPE_NAME = "EdoDossierType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String WORKFLOW_ID = "workflowId";
        final static String SECONDARY_UNIT = "secondaryUnit";
        final static String DOSSIER_STATUS = "dossierStatus";
        final static String RANK_SOUGHT = "rankSought";
        final static String CURRENT_RANK = "currentRank";
        final static String DEPARTMENT_ID = "departmentID";
        final static String ORGANIZATION_CODE = "organizationCode";
        final static String EDO_DOSSIER_ID = "edoDossierID";
        final static String EDO_DOSSIER_TYPE_ID = "edoDossierTypeID";
        final static String CANDIDATE_PRINCIPALNAME = "candidatePrincipalName";
        final static String EDO_CHECKLIST_ID = "edoChecklistID";
        final static String DUE_DATE = "dueDate";
        final static String AOE_CODE = "aoeCode";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";
    }

}

