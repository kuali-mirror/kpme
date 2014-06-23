package org.kuali.kpme.edo.api.checklist;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoChecklistSection.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoChecklistSection.Constants.TYPE_NAME, propOrder = {
    EdoChecklistSection.Elements.CHECKLIST_SECTION_ORDINAL,
    EdoChecklistSection.Elements.EDO_CHECKLIST_SECTION_I_D,
    EdoChecklistSection.Elements.EDO_CHECKLIST_I_D,
    EdoChecklistSection.Elements.DESCRIPTION,
    EdoChecklistSection.Elements.CHECKLIST_SECTION_NAME,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoChecklistSection.Elements.ACTIVE,
    EdoChecklistSection.Elements.ID,
    EdoChecklistSection.Elements.EFFECTIVE_LOCAL_DATE,
    EdoChecklistSection.Elements.CREATE_TIME,
    EdoChecklistSection.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoChecklistSection
    extends AbstractDataTransferObject
    implements EdoChecklistSectionContract
{

    @XmlElement(name = Elements.CHECKLIST_SECTION_ORDINAL, required = false)
    private final int checklistSectionOrdinal;
    @XmlElement(name = Elements.EDO_CHECKLIST_SECTION_I_D, required = false)
    private final String edoChecklistSectionID;
    @XmlElement(name = Elements.EDO_CHECKLIST_I_D, required = false)
    private final String edoChecklistID;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.CHECKLIST_SECTION_NAME, required = false)
    private final String checklistSectionName;
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
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EdoChecklistSection() {
        this.checklistSectionOrdinal = 0;
        this.edoChecklistSectionID = null;
        this.edoChecklistID = null;
        this.description = null;
        this.checklistSectionName = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private EdoChecklistSection(Builder builder) {
        this.checklistSectionOrdinal = builder.getChecklistSectionOrdinal();
        this.edoChecklistSectionID = builder.getEdoChecklistSectionID();
        this.edoChecklistID = builder.getEdoChecklistID();
        this.description = builder.getDescription();
        this.checklistSectionName = builder.getChecklistSectionName();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public int getChecklistSectionOrdinal() {
        return this.checklistSectionOrdinal;
    }

    @Override
    public String getEdoChecklistSectionID() {
        return this.edoChecklistSectionID;
    }

    @Override
    public String getEdoChecklistID() {
        return this.edoChecklistID;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getChecklistSectionName() {
        return this.checklistSectionName;
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
     * A builder which can be used to construct {@link EdoChecklistSection} instances.  Enforces the constraints of the {@link EdoChecklistSectionContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoChecklistSectionContract, ModelBuilder
    {

        private int checklistSectionOrdinal;
        private String edoChecklistSectionID;
        private String edoChecklistID;
        private String description;
        private String checklistSectionName;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(EdoChecklistSectionContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setChecklistSectionOrdinal(contract.getChecklistSectionOrdinal());
            builder.setEdoChecklistSectionID(contract.getEdoChecklistSectionID());
            builder.setEdoChecklistID(contract.getEdoChecklistID());
            builder.setDescription(contract.getDescription());
            builder.setChecklistSectionName(contract.getChecklistSectionName());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public EdoChecklistSection build() {
            return new EdoChecklistSection(this);
        }

        @Override
        public int getChecklistSectionOrdinal() {
            return this.checklistSectionOrdinal;
        }

        @Override
        public String getEdoChecklistSectionID() {
            return this.edoChecklistSectionID;
        }

        @Override
        public String getEdoChecklistID() {
            return this.edoChecklistID;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getChecklistSectionName() {
            return this.checklistSectionName;
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

        public void setChecklistSectionOrdinal(int checklistSectionOrdinal) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.checklistSectionOrdinal = checklistSectionOrdinal;
        }

        public void setEdoChecklistSectionID(String edoChecklistSectionID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoChecklistSectionID = edoChecklistSectionID;
        }

        public void setEdoChecklistID(String edoChecklistID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoChecklistID = edoChecklistID;
        }

        public void setDescription(String description) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.description = description;
        }

        public void setChecklistSectionName(String checklistSectionName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.checklistSectionName = checklistSectionName;
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

        final static String ROOT_ELEMENT_NAME = "edoChecklistSection";
        final static String TYPE_NAME = "EdoChecklistSectionType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String CHECKLIST_SECTION_ORDINAL = "checklistSectionOrdinal";
        final static String EDO_CHECKLIST_SECTION_I_D = "edoChecklistSectionID";
        final static String EDO_CHECKLIST_I_D = "edoChecklistID";
        final static String DESCRIPTION = "description";
        final static String CHECKLIST_SECTION_NAME = "checklistSectionName";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

