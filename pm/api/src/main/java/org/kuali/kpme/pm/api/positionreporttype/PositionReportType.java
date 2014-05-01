package org.kuali.kpme.pm.api.positionreporttype;

import java.io.Serializable;
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
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.groupkey.HrGroupKeyContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionReportType.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionReportType.Constants.TYPE_NAME, propOrder = {
    PositionReportType.Elements.POSITION_REPORT_TYPE,
    PositionReportType.Elements.DESCRIPTION,
    PositionReportType.Elements.PM_POSITION_REPORT_TYPE_ID,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionReportType.Elements.ACTIVE,
    PositionReportType.Elements.ID,
    PositionReportType.Elements.EFFECTIVE_LOCAL_DATE,
    PositionReportType.Elements.CREATE_TIME,
    PositionReportType.Elements.USER_PRINCIPAL_ID,
    PositionReportType.Elements.GROUP_KEY_CODE,
    PositionReportType.Elements.GROUP_KEY,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionReportType
    extends AbstractDataTransferObject
    implements PositionReportTypeContract
{

    @XmlElement(name = Elements.POSITION_REPORT_TYPE, required = false)
    private final String positionReportType;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.PM_POSITION_REPORT_TYPE_ID, required = false)
    private final String pmPositionReportTypeId;
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
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = true)
    private final String groupKeyCode;
    @XmlElement(name = Elements.GROUP_KEY, required = true)
    private final HrGroupKey groupKey;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PositionReportType() {
        this.positionReportType = null;
        this.description = null;
        this.pmPositionReportTypeId = null;
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

    private PositionReportType(Builder builder) {
        this.positionReportType = builder.getPositionReportType();
        this.description = builder.getDescription();
        this.pmPositionReportTypeId = builder.getPmPositionReportTypeId();
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
    public String getPositionReportType() {
        return this.positionReportType;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getPmPositionReportTypeId() {
        return this.pmPositionReportTypeId;
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
     * A builder which can be used to construct {@link PositionReportType} instances.  Enforces the constraints of the {@link PositionReportTypeContract}.
     * 
     */
    public final static class Builder
        implements Serializable, PositionReportTypeContract, ModelBuilder
    {

        private String positionReportType;
        private String description;
        private String pmPositionReportTypeId;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
        private String groupKeyCode;
        private HrGroupKey.Builder groupKey;

        private Builder(String groupKeyCode, String positionReportType) {
        	setGroupKeyCode(groupKeyCode);
        	setPositionReportType(positionReportType);
        }

        public static Builder create(String groupKeyCode, String positionReportType) {
            return new Builder(groupKeyCode,positionReportType);
        }

        public static Builder create(PositionReportTypeContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create(contract.getGroupKeyCode(),contract.getPositionReportType());
            builder.setDescription(contract.getDescription());
            builder.setPmPositionReportTypeId(contract.getPmPositionReportTypeId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));
            return builder;
        }

        public PositionReportType build() {
            return new PositionReportType(this);
        }

        @Override
        public String getPositionReportType() {
            return this.positionReportType;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getPmPositionReportTypeId() {
            return this.pmPositionReportTypeId;
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

        public void setPositionReportType(String positionReportType) {
        	if (StringUtils.isWhitespace(positionReportType)) {
                throw new IllegalArgumentException("positionReportType is blank");
            }
            this.positionReportType = positionReportType;
        }

        public void setDescription(String description) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.description = description;
        }

        public void setPmPositionReportTypeId(String pmPositionReportTypeId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPositionReportTypeId = pmPositionReportTypeId;
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

        final static String ROOT_ELEMENT_NAME = "positionReportType";
        final static String TYPE_NAME = "PositionReportTypeType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String POSITION_REPORT_TYPE = "positionReportType";
        final static String DESCRIPTION = "description";
        final static String PM_POSITION_REPORT_TYPE_ID = "pmPositionReportTypeId";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";

    }

}

