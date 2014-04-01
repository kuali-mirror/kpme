package org.kuali.kpme.pm.api.positionresponsibility;

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
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.location.api.campus.CampusContract;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionResponsibility.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionResponsibility.Constants.TYPE_NAME, propOrder = {
    PositionResponsibility.Elements.CAMPUS_OBJ,
    PositionResponsibility.Elements.POSITION_RESPONSIBILITY_OPTION,
    PositionResponsibility.Elements.PERCENT_TIME,
    PositionResponsibility.Elements.POSITION_RESPONSIBILITY_ID,
    PositionResponsibility.Elements.HR_POSITION_ID,
    PositionResponsibility.Elements.OWNER,
    PositionResponsibility.Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionResponsibility extends AbstractDataTransferObject implements PositionResponsibilityContract {

	private static final long serialVersionUID = 9087038146966583259L;
	
	@XmlElement(name = Elements.CAMPUS_OBJ, required = false)
    private final CampusContract campusObj;
    @XmlElement(name = Elements.POSITION_RESPONSIBILITY_OPTION, required = false)
    private final String positionResponsibilityOption;
    @XmlElement(name = Elements.PERCENT_TIME, required = false)
    private final BigDecimal percentTime;
    @XmlElement(name = Elements.POSITION_RESPONSIBILITY_ID, required = false)
    private final String positionResponsibilityId;
    @XmlElement(name = Elements.HR_POSITION_ID, required = false)
    private final String hrPositionId;
    @XmlElement(name = Elements.OWNER, required = false)
    private final Position owner;
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
    private PositionResponsibility() {
        this.campusObj = null;
        this.positionResponsibilityOption = null;
        this.percentTime = null;
        this.positionResponsibilityId = null;
        this.hrPositionId = null;
        this.owner = null;
        this.effectiveLocalDateOfOwner = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private PositionResponsibility(Builder builder) {
        this.campusObj = builder.getCampusObj();
        this.positionResponsibilityOption = builder.getPositionResponsibilityOption();
        this.percentTime = builder.getPercentTime();
        this.positionResponsibilityId = builder.getPositionResponsibilityId();
        this.hrPositionId = builder.getHrPositionId();
        this.owner =  builder.getOwner() == null ? null : builder.getOwner().build();
        this.effectiveLocalDateOfOwner = builder.getEffectiveLocalDateOfOwner();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public CampusContract getCampusObj() {
        return this.campusObj;
    }

    @Override
    public String getPositionResponsibilityOption() {
        return this.positionResponsibilityOption;
    }

    @Override
    public BigDecimal getPercentTime() {
        return this.percentTime;
    }

    @Override
    public String getPositionResponsibilityId() {
        return this.positionResponsibilityId;
    }
    
    @Override
    public String getHrPositionId() {
        return this.hrPositionId;
    }

    @Override
    public Position getOwner() {
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
     * A builder which can be used to construct {@link PositionResponsibility} instances.  Enforces the constraints of the {@link PositionResponsibilityContract}.
     * 
     */
    public final static class Builder implements Serializable, PositionResponsibilityContract, ModelBuilder {

    	private static final long serialVersionUID = 9129941203942056769L;
    	
		private CampusContract campusObj;
        private String positionResponsibilityOption;
        private BigDecimal percentTime;
        private String positionResponsibilityId;
        private String hrPositionId;
        private Position.Builder owner;
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

        public static Builder create(PositionResponsibilityContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setCampusObj(contract.getCampusObj());
            builder.setPositionResponsibilityOption(contract.getPositionResponsibilityOption());
            builder.setPercentTime(contract.getPercentTime());
            builder.setPositionResponsibilityId(contract.getPositionResponsibilityId());
            builder.setHrPositionId(contract.getHrPositionId());
            builder.setOwner(contract.getOwner() == null ? null : Position.Builder.create(contract.getOwner()));
            builder.setEffectiveLocalDateOfOwner(contract.getEffectiveLocalDateOfOwner());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public PositionResponsibility build() {
            return new PositionResponsibility(this);
        }

        @Override
        public CampusContract getCampusObj() {
            return this.campusObj;
        }

        @Override
        public String getPositionResponsibilityOption() {
            return this.positionResponsibilityOption;
        }

        @Override
        public BigDecimal getPercentTime() {
            return this.percentTime;
        }

        @Override
        public String getPositionResponsibilityId() {
            return this.positionResponsibilityId;
        }
        
        @Override
        public String getHrPositionId() {
            return this.hrPositionId;
        }        

        @Override
        public Position.Builder getOwner() {
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

        public void setCampusObj(CampusContract campusObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.campusObj = campusObj;
        }

        public void setPositionResponsibilityOption(String positionResponsibilityOption) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.positionResponsibilityOption = positionResponsibilityOption;
        }

        public void setPercentTime(BigDecimal percentTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.percentTime = percentTime;
        }

        public void setPositionResponsibilityId(String positionResponsibilityId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.positionResponsibilityId = positionResponsibilityId;
        }
        
        public void setHrPositionId(String hrPositionId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hrPositionId = hrPositionId;
        }

        public void setOwner(Position.Builder owner) {
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

        final static String ROOT_ELEMENT_NAME = "positionResponsibility";
        final static String TYPE_NAME = "PositionResponsibilityType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String CAMPUS_OBJ = "campusObj";
        final static String POSITION_RESPONSIBILITY_OPTION = "positionResponsibilityOption";
        final static String PERCENT_TIME = "percentTime";
        final static String POSITION_RESPONSIBILITY_ID = "positionResponsibilityId";
        final static String HR_POSITION_ID = "hrPositionId";
        final static String OWNER = "owner";
        final static String EFFECTIVE_LOCAL_DATE_OF_OWNER = "effectiveLocalDateOfOwner";

    }

}