package org.kuali.kpme.edo.api.checklist;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoChecklistSection.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoChecklistSection.Constants.TYPE_NAME, propOrder = {
    EdoChecklistSection.Elements.CHECKLIST_SECTION_ORDINAL,
    EdoChecklistSection.Elements.EDO_CHECKLIST_SECTION_ID,
    EdoChecklistSection.Elements.EDO_CHECKLIST_ID,
    EdoChecklistSection.Elements.DESCRIPTION,
    EdoChecklistSection.Elements.CHECKLIST_SECTION_NAME,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoChecklistSection
    extends AbstractDataTransferObject
    implements EdoChecklistSectionContract
{

    @XmlElement(name = Elements.CHECKLIST_SECTION_ORDINAL, required = false)
    private final int checklistSectionOrdinal;
    @XmlElement(name = Elements.EDO_CHECKLIST_SECTION_ID, required = false)
    private final String edoChecklistSectionId;
    @XmlElement(name = Elements.EDO_CHECKLIST_ID, required = false)
    private final String edoChecklistId;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.CHECKLIST_SECTION_NAME, required = false)
    private final String checklistSectionName;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EdoChecklistSection() {
        this.checklistSectionOrdinal = 0;
        this.edoChecklistSectionId = null;
        this.edoChecklistId = null;
        this.description = null;
        this.checklistSectionName = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private EdoChecklistSection(Builder builder) {
        this.checklistSectionOrdinal = builder.getChecklistSectionOrdinal();
        this.edoChecklistSectionId = builder.getEdoChecklistSectionId();
        this.edoChecklistId = builder.getEdoChecklistId();
        this.description = builder.getDescription();
        this.checklistSectionName = builder.getChecklistSectionName();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public int getChecklistSectionOrdinal() {
        return this.checklistSectionOrdinal;
    }

    @Override
    public String getEdoChecklistSectionId() {
        return this.edoChecklistSectionId;
    }

    @Override
    public String getEdoChecklistId() {
        return this.edoChecklistId;
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


    /**
     * A builder which can be used to construct {@link EdoChecklistSection} instances.  Enforces the constraints of the {@link EdoChecklistSectionContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoChecklistSectionContract, ModelBuilder
    {

        private int checklistSectionOrdinal;
        private String edoChecklistSectionId;
        private String edoChecklistId;
        private String description;
        private String checklistSectionName;
        private Long versionNumber;
        private String objectId;

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
            builder.setEdoChecklistSectionId(contract.getEdoChecklistSectionId());
            builder.setEdoChecklistId(contract.getEdoChecklistId());
            builder.setDescription(contract.getDescription());
            builder.setChecklistSectionName(contract.getChecklistSectionName());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
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
        public String getEdoChecklistSectionId() {
            return this.edoChecklistSectionId;
        }

        @Override
        public String getEdoChecklistId() {
            return this.edoChecklistId;
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

        public void setChecklistSectionOrdinal(int checklistSectionOrdinal) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.checklistSectionOrdinal = checklistSectionOrdinal;
        }

        public void setEdoChecklistSectionId(String edoChecklistSectionId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoChecklistSectionId = edoChecklistSectionId;
        }

        public void setEdoChecklistId(String edoChecklistId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoChecklistId = edoChecklistId;
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
        final static String EDO_CHECKLIST_SECTION_ID = "edoChecklistSectionId";
        final static String EDO_CHECKLIST_ID = "edoChecklistId";
        final static String DESCRIPTION = "description";
        final static String CHECKLIST_SECTION_NAME = "checklistSectionName";

    }

}

