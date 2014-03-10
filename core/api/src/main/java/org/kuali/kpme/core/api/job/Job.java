package org.kuali.kpme.core.api.job;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.w3c.dom.Element;

@XmlRootElement(name = Job.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = Job.Constants.TYPE_NAME, propOrder = {
        Job.Elements.NAME,
        Job.Elements.LOCATION,
        Job.Elements.ID,
        Job.Elements.PAY_GRADE,
        Job.Elements.STANDARD_HOURS,
        Job.Elements.PRINCIPAL_ID,
        Job.Elements.HR_SAL_GROUP,
        Job.Elements.COMP_RATE,
        Job.Elements.PAY_TYPE_OBJ,
        Job.Elements.PRIMARY_JOB,
        Job.Elements.POSITION_NUMBER,
        Job.Elements.UNIQUE_KEY,
        Job.Elements.ELIGIBLE_FOR_LEAVE,
        Job.Elements.JOB_NUMBER,
        Job.Elements.HR_PAY_TYPE,
        Job.Elements.HR_JOB_ID,
        Job.Elements.DEPT,
        Job.Elements.FTE,
        Job.Elements.FIRST_NAME,
        Job.Elements.LAST_NAME,
        Job.Elements.PRINCIPAL_NAME,
        Job.Elements.FLSA_STATUS,
        Job.Elements.ACTIVE,
        Job.Elements.EFFECTIVE_LOCAL_DATE,
        Job.Elements.CREATE_TIME,
        Job.Elements.USER_PRINCIPAL_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class Job
        extends AbstractDataTransferObject
        implements JobContract
{

    private static final long serialVersionUID = 271124129309463092L;
    @XmlElement(name = Elements.NAME, required = false)
    private final String name;
    @XmlElement(name = Elements.LOCATION, required = false)
    private final String location;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.PAY_GRADE, required = false)
    private final String payGrade;
    @XmlElement(name = Elements.STANDARD_HOURS, required = false)
    private final BigDecimal standardHours;
    @XmlElement(name = Elements.PRINCIPAL_ID, required = false)
    private final String principalId;
    @XmlElement(name = Elements.HR_SAL_GROUP, required = false)
    private final String hrSalGroup;
    @XmlElement(name = Elements.COMP_RATE, required = false)
    private final KualiDecimal compRate;
    @XmlElement(name = Elements.PAY_TYPE_OBJ, required = false)
    private final PayType payTypeObj;
    @XmlElement(name = Elements.PRIMARY_JOB, required = false)
    private final Boolean primaryJob;
    @XmlElement(name = Elements.POSITION_NUMBER, required = false)
    private final String positionNumber;
    @XmlElement(name = Elements.UNIQUE_KEY, required = false)
    private final String uniqueKey;
    @XmlElement(name = Elements.ELIGIBLE_FOR_LEAVE, required = false)
    private final boolean eligibleForLeave;
    @XmlElement(name = Elements.JOB_NUMBER, required = false)
    private final Long jobNumber;
    @XmlElement(name = Elements.HR_PAY_TYPE, required = false)
    private final String hrPayType;
    @XmlElement(name = Elements.HR_JOB_ID, required = false)
    private final String hrJobId;
    @XmlElement(name = Elements.DEPT, required = false)
    private final String dept;
    @XmlElement(name = Elements.FTE, required = false)
    private final BigDecimal fte;
    @XmlElement(name = Elements.FIRST_NAME, required = false)
    private final String firstName;
    @XmlElement(name = Elements.LAST_NAME, required = false)
    private final String lastName;
    @XmlElement(name = Elements.PRINCIPAL_NAME, required = false)
    private final String principalName;
    @XmlElement(name = Elements.FLSA_STATUS, required = false)
    private final String flsaStatus;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    private final LocalDate effectiveLocalDate;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    private final DateTime createTime;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private Job() {
        this.name = null;
        this.location = null;
        this.id = null;
        this.payGrade = null;
        this.standardHours = null;
        this.principalId = null;
        this.hrSalGroup = null;
        this.compRate = null;
        this.payTypeObj = null;
        this.primaryJob = null;
        this.positionNumber = null;
        this.uniqueKey = null;
        this.eligibleForLeave = false;
        this.jobNumber = null;
        this.hrPayType = null;
        this.hrJobId = null;
        this.dept = null;
        this.fte = null;
        this.firstName = null;
        this.lastName = null;
        this.principalName = null;
        this.flsaStatus = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private Job(Builder builder) {
        this.name = builder.getName();
        this.location = builder.getLocation();
        this.id = builder.getId();
        this.payGrade = builder.getPayGrade();
        this.standardHours = builder.getStandardHours();
        this.principalId = builder.getPrincipalId();
        this.hrSalGroup = builder.getHrSalGroup();
        this.compRate = builder.getCompRate();
        this.payTypeObj = builder.getPayTypeObj() == null ? null : builder.getPayTypeObj().build();
        this.primaryJob = builder.isPrimaryJob();
        this.positionNumber = builder.getPositionNumber();
        this.uniqueKey = builder.getUniqueKey();
        this.eligibleForLeave = builder.isEligibleForLeave();
        this.jobNumber = builder.getJobNumber();
        this.hrPayType = builder.getHrPayType();
        this.hrJobId = builder.getHrJobId();
        this.dept = builder.getDept();
        this.fte = builder.getFte();
        this.firstName = builder.getFirstName();
        this.lastName = builder.getLastName();
        this.principalName = builder.getPrincipalName();
        this.flsaStatus = builder.getFlsaStatus();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getPayGrade() {
        return this.payGrade;
    }

    @Override
    public BigDecimal getStandardHours() {
        return this.standardHours;
    }

    @Override
    public String getPrincipalId() {
        return this.principalId;
    }

    @Override
    public String getHrSalGroup() {
        return this.hrSalGroup;
    }

    @Override
    public KualiDecimal getCompRate() {
        return this.compRate;
    }

    @Override
    public PayType getPayTypeObj() {
        return this.payTypeObj;
    }

    @Override
    public Boolean isPrimaryJob() {
        return this.primaryJob;
    }

    @Override
    public String getPositionNumber() {
        return this.positionNumber;
    }

    @Override
    public String getUniqueKey() {
        return this.uniqueKey;
    }

    @Override
    public boolean isEligibleForLeave() {
        return this.eligibleForLeave;
    }

    @Override
    public Long getJobNumber() {
        return this.jobNumber;
    }

    @Override
    public String getHrPayType() {
        return this.hrPayType;
    }

    @Override
    public String getHrJobId() {
        return this.hrJobId;
    }

    @Override
    public String getDept() {
        return this.dept;
    }

    @Override
    public BigDecimal getFte() {
        return this.fte;
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
    public String getPrincipalName() {
        return this.principalName;
    }

    @Override
    public String getFlsaStatus() {
        return this.flsaStatus;
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


    /**
     * A builder which can be used to construct {@link Job} instances.  Enforces the constraints of the {@link JobContract}.
     *
     */
    public final static class Builder
            implements Serializable, JobContract, ModelBuilder
    {

        private String name;
        private String location;
        private String id;
        private String payGrade;
        private BigDecimal standardHours;
        private String principalId;
        private String hrSalGroup;
        private KualiDecimal compRate;
        private PayType.Builder payTypeObj;
        private Boolean primaryJob;
        private String positionNumber;
        private String uniqueKey;
        private boolean eligibleForLeave;
        private Long jobNumber;
        private String hrPayType;
        private String hrJobId;
        private String dept;
        private BigDecimal fte;
        private String firstName;
        private String lastName;
        private String principalName;
        private String flsaStatus;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder(String principalId, Long jobNumber) {
            setPrincipalId(principalId);
            setJobNumber(jobNumber);
        }

        public static Builder create(String principalId, Long jobNumber) {
            return new Builder(principalId, jobNumber);
        }

        public static Builder create(JobContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getPrincipalId(), contract.getJobNumber());
            builder.setName(contract.getName());
            builder.setLocation(contract.getLocation());
            builder.setId(contract.getId());
            builder.setPayGrade(contract.getPayGrade());
            builder.setStandardHours(contract.getStandardHours());
            builder.setHrSalGroup(contract.getHrSalGroup());
            builder.setCompRate(contract.getCompRate());
            builder.setPayTypeObj(contract.getPayTypeObj() == null ? null : PayType.Builder.create(contract.getPayTypeObj()));
            builder.setPrimaryJob(contract.isPrimaryJob());
            builder.setPositionNumber(contract.getPositionNumber());
            builder.setUniqueKey(contract.getUniqueKey());
            builder.setEligibleForLeave(contract.isEligibleForLeave());
            builder.setHrPayType(contract.getHrPayType());
            builder.setHrJobId(contract.getHrJobId());
            builder.setDept(contract.getDept());
            builder.setFte(contract.getFte());
            builder.setFirstName(contract.getFirstName());
            builder.setLastName(contract.getLastName());
            builder.setPrincipalName(contract.getPrincipalName());
            builder.setFlsaStatus(contract.getFlsaStatus());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public Job build() {
            return new Job(this);
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getLocation() {
            return this.location;
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public String getPayGrade() {
            return this.payGrade;
        }

        @Override
        public BigDecimal getStandardHours() {
            return this.standardHours;
        }

        @Override
        public String getPrincipalId() {
            return this.principalId;
        }

        @Override
        public String getHrSalGroup() {
            return this.hrSalGroup;
        }

        @Override
        public KualiDecimal getCompRate() {
            return this.compRate;
        }

        @Override
        public PayType.Builder getPayTypeObj() {
            return this.payTypeObj;
        }

        @Override
        public Boolean isPrimaryJob() {
            return this.primaryJob;
        }

        @Override
        public String getPositionNumber() {
            return this.positionNumber;
        }

        @Override
        public String getUniqueKey() {
            return this.uniqueKey;
        }

        @Override
        public boolean isEligibleForLeave() {
            return this.eligibleForLeave;
        }

        @Override
        public Long getJobNumber() {
            return this.jobNumber;
        }

        @Override
        public String getHrPayType() {
            return this.hrPayType;
        }

        @Override
        public String getHrJobId() {
            return this.hrJobId;
        }

        @Override
        public String getDept() {
            return this.dept;
        }

        @Override
        public BigDecimal getFte() {
            return this.fte;
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
        public String getPrincipalName() {
            return this.principalName;
        }

        @Override
        public String getFlsaStatus() {
            return this.flsaStatus;
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

        public void setName(String name) {
            this.name = name;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setPayGrade(String payGrade) {
            this.payGrade = payGrade;
        }

        public void setStandardHours(BigDecimal standardHours) {
            this.standardHours = standardHours;
        }

        public void setPrincipalId(String principalId) {
            if (StringUtils.isEmpty(principalId)) {
                throw new IllegalArgumentException("principalId is blank");
            }
            this.principalId = principalId;
        }

        public void setHrSalGroup(String hrSalGroup) {
            this.hrSalGroup = hrSalGroup;
        }

        public void setCompRate(KualiDecimal compRate) {
            this.compRate = compRate;
        }

        public void setPayTypeObj(PayType.Builder payTypeObj) {
            this.payTypeObj = payTypeObj;
        }

        public void setPrimaryJob(Boolean primaryJob) {
            this.primaryJob = primaryJob;
        }

        public void setPositionNumber(String positionNumber) {
            this.positionNumber = positionNumber;
        }

        public void setUniqueKey(String uniqueKey) {
            this.uniqueKey = uniqueKey;
        }

        public void setEligibleForLeave(boolean eligibleForLeave) {
            this.eligibleForLeave = eligibleForLeave;
        }

        public void setJobNumber(Long jobNumber) {
            if (jobNumber == null) {
                throw new IllegalArgumentException("jobNumber is null");
            }
            this.jobNumber = jobNumber;
        }

        public void setHrPayType(String hrPayType) {
            this.hrPayType = hrPayType;
        }

        public void setHrJobId(String hrJobId) {
            this.hrJobId = hrJobId;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public void setFte(BigDecimal fte) {
            this.fte = fte;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setPrincipalName(String principalName) {
            this.principalName = principalName;
        }

        public void setFlsaStatus(String flsaStatus) {
            this.flsaStatus = flsaStatus;
        }

        public void setVersionNumber(Long versionNumber) {
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
            this.effectiveLocalDate = effectiveLocalDate;
        }

        public void setCreateTime(DateTime createTime) {
            this.createTime = createTime;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            this.userPrincipalId = userPrincipalId;
        }

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "job";
        final static String TYPE_NAME = "JobType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String NAME = "name";
        final static String LOCATION = "location";
        final static String ID = "id";
        final static String PAY_GRADE = "payGrade";
        final static String STANDARD_HOURS = "standardHours";
        final static String PRINCIPAL_ID = "principalId";
        final static String HR_SAL_GROUP = "hrSalGroup";
        final static String COMP_RATE = "compRate";
        final static String PAY_TYPE_OBJ = "payTypeObj";
        final static String PRIMARY_JOB = "primaryJob";
        final static String POSITION_NUMBER = "positionNumber";
        final static String UNIQUE_KEY = "uniqueKey";
        final static String ELIGIBLE_FOR_LEAVE = "eligibleForLeave";
        final static String JOB_NUMBER = "jobNumber";
        final static String HR_PAY_TYPE = "hrPayType";
        final static String HR_JOB_ID = "hrJobId";
        final static String DEPT = "dept";
        final static String FTE = "fte";
        final static String FIRST_NAME = "firstName";
        final static String LAST_NAME = "lastName";
        final static String PRINCIPAL_NAME = "principalName";
        final static String FLSA_STATUS = "flsaStatus";
        final static String ACTIVE = "active";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}