package org.kuali.kpme.pm.api.classification.duty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.classification.Classification;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = ClassificationDuty.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = ClassificationDuty.Constants.TYPE_NAME, propOrder = {
    ClassificationDuty.Elements.NAME,
    ClassificationDuty.Elements.DESCRIPTION,
    ClassificationDuty.Elements.PERCENTAGE,
    ClassificationDuty.Elements.PM_DUTY_ID,
    ClassificationDuty.Elements.PM_POSITION_CLASS_ID,
    ClassificationDuty.Elements.OWNER,
    ClassificationDuty.Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class ClassificationDuty extends AbstractDataTransferObject implements ClassificationDutyContract {

	private static final long serialVersionUID = -3986671277585724026L;
	
	@XmlElement(name = Elements.NAME, required = false)
    private final String name;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.PERCENTAGE, required = false)
    private final BigDecimal percentage;
    @XmlElement(name = Elements.PM_DUTY_ID, required = false)
    private final String pmDutyId;
    @XmlElement(name = Elements.PM_POSITION_CLASS_ID, required = false)
    private final String pmPositionClassId;
    @XmlElement(name = Elements.OWNER, required = false)
    private final Classification owner;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER, required = false)
    private final LocalDate effectiveLocalDateOfOwner;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private ClassificationDuty() {
        this.name = null;
        this.description = null;
        this.percentage = null;
        this.pmDutyId = null;
        this.pmPositionClassId = null;
        this.owner = null;
        this.effectiveLocalDateOfOwner = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private ClassificationDuty(Builder builder) {
        this.name = builder.getName();
        this.description = builder.getDescription();
        this.percentage = builder.getPercentage();
        this.pmDutyId = builder.getPmDutyId();
        this.pmPositionClassId = builder.getPmPositionClassId();
        this.owner = builder.getOwner() == null ? null : builder.getOwner().build();
        this.effectiveLocalDateOfOwner = builder.getEffectiveLocalDateOfOwner();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public BigDecimal getPercentage() {
        return this.percentage;
    }

    @Override
    public String getPmDutyId() {
        return this.pmDutyId;
    }

    @Override
    public String getPmPositionClassId() {
        return this.pmPositionClassId;
    }

    @Override
    public Classification getOwner() {
        return this.owner;
    }

    @Override
    public LocalDate getEffectiveLocalDateOfOwner() {
        return this.effectiveLocalDateOfOwner;
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
     * A builder which can be used to construct {@link ClassificationDuty} instances.  Enforces the constraints of the {@link ClassificationDutyContract}.
     * 
     */
    public final static class Builder implements Serializable, ClassificationDutyContract, ModelBuilder {

    	private static final long serialVersionUID = 8215232787709296730L;
    	
		private String name;
        private String description;
        private BigDecimal percentage;
        private String pmDutyId;
        private String pmPositionClassId;
        private Classification.Builder owner;
        private LocalDate effectiveLocalDateOfOwner;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(ClassificationDutyContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setName(contract.getName());
            builder.setDescription(contract.getDescription());
            builder.setPercentage(contract.getPercentage());
            builder.setPmDutyId(contract.getPmDutyId());
            builder.setPmPositionClassId(contract.getPmPositionClassId());
            builder.setOwner(contract.getOwner() == null ? null : Classification.Builder.create(contract.getOwner()));
            builder.setEffectiveLocalDateOfOwner(contract.getEffectiveLocalDateOfOwner());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public ClassificationDuty build() {
            return new ClassificationDuty(this);
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public BigDecimal getPercentage() {
            return this.percentage;
        }

        @Override
        public String getPmDutyId() {
            return this.pmDutyId;
        }

        @Override
        public String getPmPositionClassId() {
            return this.pmPositionClassId;
        }

        @Override
        public Classification.Builder getOwner() {
            return this.owner;
        }

        @Override
        public LocalDate getEffectiveLocalDateOfOwner() {
            return this.effectiveLocalDateOfOwner;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setName(String name) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.name = name;
        }

        public void setDescription(String description) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.description = description;
        }

        public void setPercentage(BigDecimal percentage) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.percentage = percentage;
        }

        public void setPmDutyId(String pmDutyId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmDutyId = pmDutyId;
        }

        public void setPmPositionClassId(String pmPositionClassId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPositionClassId = pmPositionClassId;
        }

        public void setOwner(Classification.Builder owner) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.owner = owner;
        }

        public void setEffectiveLocalDateOfOwner(LocalDate effectiveLocalDateOfOwner) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.effectiveLocalDateOfOwner = effectiveLocalDateOfOwner;
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

        final static String ROOT_ELEMENT_NAME = "classificationDuty";
        final static String TYPE_NAME = "ClassificationDutyType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String NAME = "name";
        final static String DESCRIPTION = "description";
        final static String PERCENTAGE = "percentage";
        final static String PM_DUTY_ID = "pmDutyId";
        final static String PM_POSITION_CLASS_ID = "pmPositionClassId";
        final static String OWNER = "owner";
        final static String EFFECTIVE_LOCAL_DATE_OF_OWNER = "effectiveLocalDateOfOwner";

    }

}