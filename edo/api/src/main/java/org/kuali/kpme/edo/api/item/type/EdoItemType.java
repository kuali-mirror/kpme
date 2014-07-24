package org.kuali.kpme.edo.api.item.type;

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
import org.kuali.kpme.edo.api.item.EdoItem.Builder;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoItemType.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoItemType.Constants.TYPE_NAME, propOrder = {
    EdoItemType.Elements.ITEM_TYPE_EXT_AVAILABLE,
    EdoItemType.Elements.EDO_ITEM_TYPE_I_D,
    EdoItemType.Elements.ITEM_TYPE_NAME,
    EdoItemType.Elements.ITEM_TYPE_INSTRUCTIONS,
    EdoItemType.Elements.ITEM_TYPE_DESCRIPTION,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoItemType.Elements.ACTIVE,
    EdoItemType.Elements.ID,
    EdoItemType.Elements.EFFECTIVE_LOCAL_DATE,
    EdoItemType.Elements.CREATE_TIME,
    EdoItemType.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoItemType
    extends AbstractDataTransferObject
    implements EdoItemTypeContract
{

    @XmlElement(name = Elements.ITEM_TYPE_EXT_AVAILABLE, required = false)
    private final boolean itemTypeExtAvailable;
    @XmlElement(name = Elements.EDO_ITEM_TYPE_I_D, required = false)
    private final String edoItemTypeId;
    @XmlElement(name = Elements.ITEM_TYPE_NAME, required = false)
    private final String itemTypeName;
    @XmlElement(name = Elements.ITEM_TYPE_INSTRUCTIONS, required = false)
    private final String itemTypeInstructions;
    @XmlElement(name = Elements.ITEM_TYPE_DESCRIPTION, required = false)
    private final String itemTypeDescription;
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
    private EdoItemType() {
        this.itemTypeExtAvailable = false;
        this.edoItemTypeId = null;
        this.itemTypeName = null;
        this.itemTypeInstructions = null;
        this.itemTypeDescription = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private EdoItemType(Builder builder) {
        this.itemTypeExtAvailable = builder.isItemTypeExtAvailable();
        this.edoItemTypeId = builder.getEdoItemTypeId();
        this.itemTypeName = builder.getItemTypeName();
        this.itemTypeInstructions = builder.getItemTypeInstructions();
        this.itemTypeDescription = builder.getItemTypeDescription();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public boolean isItemTypeExtAvailable() {
        return this.itemTypeExtAvailable;
    }

    @Override
    public String getEdoItemTypeId() {
        return this.edoItemTypeId;
    }

    @Override
    public String getItemTypeName() {
        return this.itemTypeName;
    }

    @Override
    public String getItemTypeInstructions() {
        return this.itemTypeInstructions;
    }

    @Override
    public String getItemTypeDescription() {
        return this.itemTypeDescription;
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
     * A builder which can be used to construct {@link EdoItemType} instances.  Enforces the constraints of the {@link EdoItemTypeContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoItemTypeContract, ModelBuilder
    {

        private boolean itemTypeExtAvailable;
        private String edoItemTypeId;
        private String itemTypeName;
        private String itemTypeInstructions;
        private String itemTypeDescription;
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
        
        private Builder(String itemTypeName) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setItemTypeName(itemTypeName);
        }

        public static Builder create(String itemTypeName) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(itemTypeName);
        }

        public static Builder create(EdoItemTypeContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setItemTypeExtAvailable(contract.isItemTypeExtAvailable());
            builder.setEdoItemTypeId(contract.getEdoItemTypeId());
            builder.setItemTypeName(contract.getItemTypeName());
            builder.setItemTypeInstructions(contract.getItemTypeInstructions());
            builder.setItemTypeDescription(contract.getItemTypeDescription());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public EdoItemType build() {
            return new EdoItemType(this);
        }

        @Override
        public boolean isItemTypeExtAvailable() {
            return this.itemTypeExtAvailable;
        }

        @Override
        public String getEdoItemTypeId() {
            return this.edoItemTypeId;
        }

        @Override
        public String getItemTypeName() {
            return this.itemTypeName;
        }

        @Override
        public String getItemTypeInstructions() {
            return this.itemTypeInstructions;
        }

        @Override
        public String getItemTypeDescription() {
            return this.itemTypeDescription;
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

        public void setItemTypeExtAvailable(boolean itemTypeExtAvailable) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.itemTypeExtAvailable = itemTypeExtAvailable;
        }

        public void setEdoItemTypeId(String edoItemTypeId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoItemTypeId = edoItemTypeId;
        }

        public void setItemTypeName(String itemTypeName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.itemTypeName = itemTypeName;
        }

        public void setItemTypeInstructions(String itemTypeInstructions) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.itemTypeInstructions = itemTypeInstructions;
        }

        public void setItemTypeDescription(String itemTypeDescription) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.itemTypeDescription = itemTypeDescription;
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

        final static String ROOT_ELEMENT_NAME = "edoItemType";
        final static String TYPE_NAME = "EdoItemTypeType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String ITEM_TYPE_EXT_AVAILABLE = "itemTypeExtAvailable";
        final static String EDO_ITEM_TYPE_I_D = "edoItemTypeId";
        final static String ITEM_TYPE_NAME = "itemTypeName";
        final static String ITEM_TYPE_INSTRUCTIONS = "itemTypeInstructions";
        final static String ITEM_TYPE_DESCRIPTION = "itemTypeDescription";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

