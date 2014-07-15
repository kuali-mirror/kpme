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

@XmlRootElement(name = EdoChecklistItem.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoChecklistItem.Constants.TYPE_NAME, propOrder = {
    EdoChecklistItem.Elements.CHECKLIST_ITEM_ORDINAL,
    EdoChecklistItem.Elements.EDO_CHECKLIST_ITEM_I_D,
    EdoChecklistItem.Elements.EDO_CHECKLIST_SECTION_I_D,
    EdoChecklistItem.Elements.ITEM_DESCRIPTION,
    EdoChecklistItem.Elements.CHECKLIST_ITEM_NAME,
    EdoChecklistItem.Elements.REQUIRED,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoChecklistItem.Elements.ACTIVE,
    EdoChecklistItem.Elements.ID,
    EdoChecklistItem.Elements.EFFECTIVE_LOCAL_DATE,
    EdoChecklistItem.Elements.CREATE_TIME,
    EdoChecklistItem.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoChecklistItem
    extends AbstractDataTransferObject
    implements EdoChecklistItemContract
{

    @XmlElement(name = Elements.CHECKLIST_ITEM_ORDINAL, required = false)
    private final int checklistItemOrdinal;
    @XmlElement(name = Elements.EDO_CHECKLIST_ITEM_I_D, required = false)
    private final String edoChecklistItemId;
    @XmlElement(name = Elements.EDO_CHECKLIST_SECTION_I_D, required = false)
    private final String edoChecklistSectionId;
    @XmlElement(name = Elements.ITEM_DESCRIPTION, required = false)
    private final String itemDescription;
    @XmlElement(name = Elements.CHECKLIST_ITEM_NAME, required = false)
    private final String checklistItemName;
    @XmlElement(name = Elements.REQUIRED, required = false)
    private final boolean required;
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
    private EdoChecklistItem() {
        this.checklistItemOrdinal = 0;
        this.edoChecklistItemId = null;
        this.edoChecklistSectionId = null;
        this.itemDescription = null;
        this.checklistItemName = null;
        this.required = false;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private EdoChecklistItem(Builder builder) {
        this.checklistItemOrdinal = builder.getChecklistItemOrdinal();
        this.edoChecklistItemId = builder.getEdoChecklistItemId();
        this.edoChecklistSectionId = builder.getEdoChecklistSectionId();
        this.itemDescription = builder.getItemDescription();
        this.checklistItemName = builder.getChecklistItemName();
        this.required = builder.isRequired();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public int getChecklistItemOrdinal() {
        return this.checklistItemOrdinal;
    }

    @Override
    public String getEdoChecklistItemId() {
        return this.edoChecklistItemId;
    }

    @Override
    public String getEdoChecklistSectionId() {
        return this.edoChecklistSectionId;
    }

    @Override
    public String getItemDescription() {
        return this.itemDescription;
    }

    @Override
    public String getChecklistItemName() {
        return this.checklistItemName;
    }

    @Override
    public boolean isRequired() {
        return this.required;
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
     * A builder which can be used to construct {@link EdoChecklistItem} instances.  Enforces the constraints of the {@link EdoChecklistItemContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoChecklistItemContract, ModelBuilder
    {

        private int checklistItemOrdinal;
        private String edoChecklistItemId;
        private String edoChecklistSectionId;
        private String itemDescription;
        private String checklistItemName;
        private boolean required;
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

        public static Builder create(EdoChecklistItemContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setChecklistItemOrdinal(contract.getChecklistItemOrdinal());
            builder.setEdoChecklistItemId(contract.getEdoChecklistItemId());
            builder.setEdoChecklistSectionId(contract.getEdoChecklistSectionId());
            builder.setItemDescription(contract.getItemDescription());
            builder.setChecklistItemName(contract.getChecklistItemName());
            builder.setRequired(contract.isRequired());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public EdoChecklistItem build() {
            return new EdoChecklistItem(this);
        }

        @Override
        public int getChecklistItemOrdinal() {
            return this.checklistItemOrdinal;
        }

        @Override
        public String getEdoChecklistItemId() {
            return this.edoChecklistItemId;
        }

        @Override
        public String getEdoChecklistSectionId() {
            return this.edoChecklistSectionId;
        }

        @Override
        public String getItemDescription() {
            return this.itemDescription;
        }

        @Override
        public String getChecklistItemName() {
            return this.checklistItemName;
        }

        @Override
        public boolean isRequired() {
            return this.required;
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

        public void setChecklistItemOrdinal(int checklistItemOrdinal) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.checklistItemOrdinal = checklistItemOrdinal;
        }

        public void setEdoChecklistItemId(String edoChecklistItemId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoChecklistItemId = edoChecklistItemId;
        }

        public void setEdoChecklistSectionId(String edoChecklistSectionId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoChecklistSectionId = edoChecklistSectionId;
        }

        public void setItemDescription(String itemDescription) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.itemDescription = itemDescription;
        }

        public void setChecklistItemName(String checklistItemName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.checklistItemName = checklistItemName;
        }

        public void setRequired(boolean required) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.required = required;
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

        final static String ROOT_ELEMENT_NAME = "edoChecklistItem";
        final static String TYPE_NAME = "EdoChecklistItemType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String CHECKLIST_ITEM_ORDINAL = "checklistItemOrdinal";
        final static String EDO_CHECKLIST_ITEM_I_D = "edoChecklistItemId";
        final static String EDO_CHECKLIST_SECTION_I_D = "edoChecklistSectionId";
        final static String ITEM_DESCRIPTION = "itemDescription";
        final static String CHECKLIST_ITEM_NAME = "checklistItemName";
        final static String REQUIRED = "required";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

