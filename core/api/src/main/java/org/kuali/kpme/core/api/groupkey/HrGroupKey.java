package org.kuali.kpme.core.api.groupkey;

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
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.institution.Institution;
import org.kuali.kpme.core.api.location.Location;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.kuali.rice.location.api.campus.Campus;
import org.w3c.dom.Element;

@XmlRootElement(name = HrGroupKey.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = HrGroupKey.Constants.TYPE_NAME, propOrder = {
        HrGroupKey.Elements.LOCATION,
        HrGroupKey.Elements.INSTITUTION_CODE,
        HrGroupKey.Elements.DESCRIPTION,
        HrGroupKey.Elements.CAMPUS,
        HrGroupKey.Elements.INSTITUTION,
        HrGroupKey.Elements.GROUP_KEY_CODE,
        HrGroupKey.Elements.LOCATION_ID,
        HrGroupKey.Elements.CAMPUS_CODE,
        HrGroupKey.Elements.ACTIVE,
        HrGroupKey.Elements.ID,
        HrGroupKey.Elements.EFFECTIVE_LOCAL_DATE,
        HrGroupKey.Elements.CREATE_TIME,
        HrGroupKey.Elements.USER_PRINCIPAL_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class HrGroupKey
        extends AbstractDataTransferObject
        implements HrGroupKeyContract
{

    @XmlElement(name = Elements.LOCATION, required = false)
    private final Location location;
    @XmlElement(name = Elements.INSTITUTION_CODE, required = false)
    private final String institutionCode;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.CAMPUS, required = false)
    private final Campus campus;
    @XmlElement(name = Elements.INSTITUTION, required = false)
    private final Institution institution;
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = false)
    private final String groupKeyCode;
    @XmlElement(name = Elements.LOCATION_ID, required = false)
    private final String locationId;
    @XmlElement(name = Elements.CAMPUS_CODE, required = false)
    private final String campusCode;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate effectiveLocalDate;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
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
    private HrGroupKey() {
        this.location = null;
        this.institutionCode = null;
        this.description = null;
        this.campus = null;
        this.institution = null;
        this.groupKeyCode = null;
        this.locationId = null;
        this.campusCode = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private HrGroupKey(Builder builder) {
        this.location = builder.getLocation() == null ? null : builder.getLocation().build();
        this.institutionCode = builder.getInstitutionCode();
        this.description = builder.getDescription();
        this.campus = builder.getCampus() == null ? null : builder.getCampus().build();
        this.institution = builder.getInstitution() == null ? null : builder.getInstitution().build();
        this.groupKeyCode = builder.getGroupKeyCode();
        this.locationId = builder.getLocationId();
        this.campusCode = builder.getCampusCode();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public String getInstitutionCode() {
        return this.institutionCode;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Campus getCampus() {
        return this.campus;
    }

    @Override
    public Institution getInstitution() {
        return this.institution;
    }

    @Override
    public String getGroupKeyCode() {
        return this.groupKeyCode;
    }

    @Override
    public String getLocationId() {
        return this.locationId;
    }

    @Override
    public String getCampusCode() {
        return this.campusCode;
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


    /**
     * A builder which can be used to construct {@link HrGroupKey} instances.  Enforces the constraints of the {@link HrGroupKeyContract}.
     *
     */
    public final static class Builder
            implements Serializable, HrGroupKeyContract, ModelBuilder
    {

        private Location.Builder location;
        private String institutionCode;
        private String description;
        private Campus.Builder campus;
        private Institution.Builder institution;
        private String groupKeyCode;
        private String locationId;
        private String campusCode;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public static Builder create(HrGroupKeyContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create();
            builder.setLocation(contract.getLocation() == null ? null : Location.Builder.create(contract.getLocation()));
            builder.setInstitutionCode(contract.getInstitutionCode());
            builder.setDescription(contract.getDescription());
            builder.setCampus(contract.getCampus() == null ? null : Campus.Builder.create(contract.getCampusCode()));
            builder.setInstitution(contract.getInstitution() == null ? null : Institution.Builder.create(contract.getInstitution()));
            builder.setGroupKeyCode(contract.getGroupKeyCode());
            builder.setLocationId(contract.getLocationId());
            builder.setCampusCode(contract.getCampusCode());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public HrGroupKey build() {
            return new HrGroupKey(this);
        }

        @Override
        public Location.Builder getLocation() {
            return this.location;
        }

        @Override
        public String getInstitutionCode() {
            return this.institutionCode;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public Campus.Builder getCampus() {
            return this.campus;
        }

        @Override
        public Institution.Builder getInstitution() {
            return this.institution;
        }

        @Override
        public String getGroupKeyCode() {
            return this.groupKeyCode;
        }

        @Override
        public String getLocationId() {
            return this.locationId;
        }

        @Override
        public String getCampusCode() {
            return this.campusCode;
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

        public void setLocation(Location.Builder location) {
            this.location = location;
        }

        public void setInstitutionCode(String institutionCode) {
            this.institutionCode = institutionCode;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCampus(Campus.Builder campus) {
            this.campus = campus;
        }

        public void setInstitution(Institution.Builder institution) {
            this.institution = institution;
        }

        public void setGroupKeyCode(String groupKeyCode) {
            this.groupKeyCode = groupKeyCode;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public void setCampusCode(String campusCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.campusCode = campusCode;
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

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "hrGroupKey";
        final static String TYPE_NAME = "HRGroupKeyType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {
        final static String ID = "id";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String INSTITUTION_CODE = "institutionCode";
        final static String LOCATION_ID = "locationId";
        final static String CAMPUS_CODE = "campusCode";
        final static String LOCATION = "location";

        final static String DESCRIPTION = "description";
        final static String CAMPUS = "campus";
        final static String INSTITUTION = "institution";


        final static String ACTIVE = "active";

        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}