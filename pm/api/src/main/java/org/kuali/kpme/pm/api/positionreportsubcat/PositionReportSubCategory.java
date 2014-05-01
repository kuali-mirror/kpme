package org.kuali.kpme.pm.api.positionreportsubcat;

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
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategory;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionReportSubCategory.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionReportSubCategory.Constants.TYPE_NAME, propOrder = {
    PositionReportSubCategory.Elements.POSITION_REPORT_TYPE,
    PositionReportSubCategory.Elements.DESCRIPTION,
    PositionReportSubCategory.Elements.INSTITUTION,
    PositionReportSubCategory.Elements.POSITION_REPORT_SUB_CAT,
    PositionReportSubCategory.Elements.POSITION_REPORT_CAT,
    PositionReportSubCategory.Elements.PRC_OBJ,
    PositionReportSubCategory.Elements.PM_POSITION_REPORT_SUB_CAT_ID,
    PositionReportSubCategory.Elements.LOCATION,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionReportSubCategory.Elements.ACTIVE,
    PositionReportSubCategory.Elements.ID,
    PositionReportSubCategory.Elements.EFFECTIVE_LOCAL_DATE,
    PositionReportSubCategory.Elements.CREATE_TIME,
    PositionReportSubCategory.Elements.USER_PRINCIPAL_ID,
    PositionReportSubCategory.Elements.GROUP_KEY_CODE,
    PositionReportSubCategory.Elements.GROUP_KEY,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionReportSubCategory
    extends AbstractDataTransferObject
    implements PositionReportSubCategoryContract
{

    @XmlElement(name = Elements.POSITION_REPORT_TYPE, required = false)
    private final String positionReportType;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.INSTITUTION, required = false)
    private final String institution;
    @XmlElement(name = Elements.POSITION_REPORT_SUB_CAT, required = false)
    private final String positionReportSubCat;
    @XmlElement(name = Elements.POSITION_REPORT_CAT, required = false)
    private final String positionReportCat;
    @XmlElement(name = Elements.PRC_OBJ, required = false)
    private final PositionReportCategory prcObj;
    @XmlElement(name = Elements.PM_POSITION_REPORT_SUB_CAT_ID, required = false)
    private final String pmPositionReportSubCatId;
    @XmlElement(name = Elements.LOCATION, required = false)
    private final String location;
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
    private PositionReportSubCategory() {
        this.positionReportType = null;
        this.description = null;
        this.institution = null;
        this.positionReportSubCat = null;
        this.positionReportCat = null;
        this.prcObj = null;
        this.pmPositionReportSubCatId = null;
        this.location = null;
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

    private PositionReportSubCategory(Builder builder) {
        this.positionReportType = builder.getPositionReportType();
        this.description = builder.getDescription();
        this.institution = builder.getInstitution();
        this.positionReportSubCat = builder.getPositionReportSubCat();
        this.positionReportCat = builder.getPositionReportCat();
        this.prcObj = builder.getPrcObj() == null ? null : builder.getPrcObj().build();
        this.pmPositionReportSubCatId = builder.getPmPositionReportSubCatId();
        this.location = builder.getLocation();
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
    public String getInstitution() {
        return this.institution;
    }

    @Override
    public String getPositionReportSubCat() {
        return this.positionReportSubCat;
    }

    @Override
    public String getPositionReportCat() {
        return this.positionReportCat;
    }

    @Override
    public PositionReportCategory getPrcObj() {
        return this.prcObj;
    }

    @Override
    public String getPmPositionReportSubCatId() {
        return this.pmPositionReportSubCatId;
    }

    @Override
    public String getLocation() {
        return this.location;
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
     * A builder which can be used to construct {@link PositionReportSubCategory} instances.  Enforces the constraints of the {@link PositionReportSubCategoryContract}.
     * 
     */
    public final static class Builder
        implements Serializable, PositionReportSubCategoryContract, ModelBuilder
    {

        private String positionReportType;
        private String description;
        private String institution;
        private String positionReportSubCat;
        private String positionReportCat;
        private PositionReportCategory.Builder prcObj;
        private String pmPositionReportSubCatId;
        private String location;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
        private String groupKeyCode;
        private HrGroupKey.Builder groupKey;

        private Builder(String groupKeyCode, String positionReportSubCat, String positionReportCat) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setGroupKeyCode(groupKeyCode);
        	setPositionReportSubCat(positionReportSubCat);
        	setPositionReportCat(positionReportCat);
        }

        public static Builder create(String groupKeyCode, String positionReportSubCat, String positionReportCat) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(groupKeyCode, positionReportSubCat, positionReportCat);
        }

        public static Builder create(PositionReportSubCategoryContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create(contract.getGroupKeyCode(), contract.getPositionReportSubCat(), contract.getPositionReportCat());
            builder.setPositionReportType(contract.getPositionReportType());
            builder.setDescription(contract.getDescription());
            builder.setInstitution(contract.getInstitution());
            builder.setPrcObj(contract.getPrcObj() == null ? null : PositionReportCategory.Builder.create(contract.getPrcObj()));
            builder.setPmPositionReportSubCatId(contract.getPmPositionReportSubCatId());
            builder.setLocation(contract.getLocation());
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

        public PositionReportSubCategory build() {
            return new PositionReportSubCategory(this);
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
        public String getInstitution() {
            return this.institution;
        }

        @Override
        public String getPositionReportSubCat() {
            return this.positionReportSubCat;
        }

        @Override
        public String getPositionReportCat() {
            return this.positionReportCat;
        }

        @Override
        public PositionReportCategory.Builder getPrcObj() {
            return this.prcObj;
        }

        @Override
        public String getPmPositionReportSubCatId() {
            return this.pmPositionReportSubCatId;
        }

        @Override
        public String getLocation() {
            return this.location;
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
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.positionReportType = positionReportType;
        }

        public void setDescription(String description) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.description = description;
        }

        public void setInstitution(String institution) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.institution = institution;
        }

        public void setPositionReportSubCat(String positionReportSubCat) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
        	if (StringUtils.isWhitespace(positionReportSubCat)) {
                throw new IllegalArgumentException("positionReportSubCat is blank");
            }
            this.positionReportSubCat = positionReportSubCat;
        }

        public void setPositionReportCat(String positionReportCat) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
        	if (StringUtils.isWhitespace(positionReportCat)) {
                throw new IllegalArgumentException("positionReportCat is blank");
            }
            this.positionReportCat = positionReportCat;
        }

        public void setPrcObj(PositionReportCategory.Builder prcObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.prcObj = prcObj;
        }

        public void setPmPositionReportSubCatId(String pmPositionReportSubCatId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPositionReportSubCatId = pmPositionReportSubCatId;
        }

        public void setLocation(String location) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.location = location;
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

        final static String ROOT_ELEMENT_NAME = "positionReportSubCategory";
        final static String TYPE_NAME = "PositionReportSubCategoryType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String POSITION_REPORT_TYPE = "positionReportType";
        final static String DESCRIPTION = "description";
        final static String INSTITUTION = "institution";
        final static String POSITION_REPORT_SUB_CAT = "positionReportSubCat";
        final static String POSITION_REPORT_CAT = "positionReportCat";
        final static String PRC_OBJ = "prcObj";
        final static String PM_POSITION_REPORT_SUB_CAT_ID = "pmPositionReportSubCatId";
        final static String LOCATION = "location";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";

    }

}

