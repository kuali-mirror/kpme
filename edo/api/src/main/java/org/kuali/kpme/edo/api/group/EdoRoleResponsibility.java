package org.kuali.kpme.edo.api.group;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.kpme.edo.api.group.EdoGroupDefinition.Builder;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoRoleResponsibility.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoRoleResponsibility.Constants.TYPE_NAME, propOrder = {
    EdoRoleResponsibility.Elements.KIM_FORCE_ACTION,
    EdoRoleResponsibility.Elements.KIM_PRIORITY,
    EdoRoleResponsibility.Elements.KIM_ROLE_NAME,
    EdoRoleResponsibility.Elements.KIM_RESPONSIBILITY_NAME,
    EdoRoleResponsibility.Elements.KIM_ACTION_POLICY_CODE,
    EdoRoleResponsibility.Elements.KIM_ACTION_TYPE_CODE,
    EdoRoleResponsibility.Elements.EDO_KIM_ROLE_RESPONSIBILITY_ID,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoRoleResponsibility
    extends AbstractDataTransferObject
    implements EdoRoleResponsibilityContract
{

    @XmlElement(name = Elements.KIM_FORCE_ACTION, required = false)
    private final boolean kimForceAction;
    @XmlElement(name = Elements.KIM_PRIORITY, required = false)
    private final int kimPriority;
    @XmlElement(name = Elements.KIM_ROLE_NAME, required = false)
    private final String kimRoleName;
    @XmlElement(name = Elements.KIM_RESPONSIBILITY_NAME, required = false)
    private final String kimResponsibilityName;
    @XmlElement(name = Elements.KIM_ACTION_POLICY_CODE, required = false)
    private final String kimActionPolicyCode;
    @XmlElement(name = Elements.KIM_ACTION_TYPE_CODE, required = false)
    private final String kimActionTypeCode;
    @XmlElement(name = Elements.EDO_KIM_ROLE_RESPONSIBILITY_ID, required = false)
    private final String edoKimRoleResponsibilityId;
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
    private EdoRoleResponsibility() {
        this.kimForceAction = false;
        this.kimPriority = 0;
        this.kimRoleName = null;
        this.kimResponsibilityName = null;
        this.kimActionPolicyCode = null;
        this.kimActionTypeCode = null;
        this.edoKimRoleResponsibilityId = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private EdoRoleResponsibility(Builder builder) {
        this.kimForceAction = builder.isKimForceAction();
        this.kimPriority = builder.getKimPriority();
        this.kimRoleName = builder.getKimRoleName();
        this.kimResponsibilityName = builder.getKimResponsibilityName();
        this.kimActionPolicyCode = builder.getKimActionPolicyCode();
        this.kimActionTypeCode = builder.getKimActionTypeCode();
        this.edoKimRoleResponsibilityId = builder.getEdoKimRoleResponsibilityId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public boolean isKimForceAction() {
        return this.kimForceAction;
    }

    @Override
    public int getKimPriority() {
        return this.kimPriority;
    }

    @Override
    public String getKimRoleName() {
        return this.kimRoleName;
    }

    @Override
    public String getKimResponsibilityName() {
        return this.kimResponsibilityName;
    }

    @Override
    public String getKimActionPolicyCode() {
        return this.kimActionPolicyCode;
    }

    @Override
    public String getKimActionTypeCode() {
        return this.kimActionTypeCode;
    }

    @Override
    public String getEdoKimRoleResponsibilityId() {
        return this.edoKimRoleResponsibilityId;
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
     * A builder which can be used to construct {@link EdoRoleResponsibility} instances.  Enforces the constraints of the {@link EdoRoleResponsibilityContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoRoleResponsibilityContract, ModelBuilder
    {

        private boolean kimForceAction;
        private int kimPriority;
        private String kimRoleName;
        private String kimResponsibilityName;
        private String kimActionPolicyCode;
        private String kimActionTypeCode;
        private String edoKimRoleResponsibilityId;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }
        
        private Builder(String kimRoleName, String kimResponsibilityName, String kimActionTypeCode) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setKimRoleName(kimRoleName);
        	setKimResponsibilityName(kimResponsibilityName);
        	setKimActionTypeCode(kimActionTypeCode);
        }

        public static Builder create(String kimRoleName, String kimResponsibilityName, String kimActionTypeCode) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(kimRoleName, kimResponsibilityName, kimActionTypeCode);
        }

        public static Builder create(EdoRoleResponsibilityContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setKimForceAction(contract.isKimForceAction());
            builder.setKimPriority(contract.getKimPriority());
            builder.setKimRoleName(contract.getKimRoleName());
            builder.setKimResponsibilityName(contract.getKimResponsibilityName());
            builder.setKimActionPolicyCode(contract.getKimActionPolicyCode());
            builder.setKimActionTypeCode(contract.getKimActionTypeCode());
            builder.setEdoKimRoleResponsibilityId(contract.getEdoKimRoleResponsibilityId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public EdoRoleResponsibility build() {
            return new EdoRoleResponsibility(this);
        }

        @Override
        public boolean isKimForceAction() {
            return this.kimForceAction;
        }

        @Override
        public int getKimPriority() {
            return this.kimPriority;
        }

        @Override
        public String getKimRoleName() {
            return this.kimRoleName;
        }

        @Override
        public String getKimResponsibilityName() {
            return this.kimResponsibilityName;
        }

        @Override
        public String getKimActionPolicyCode() {
            return this.kimActionPolicyCode;
        }

        @Override
        public String getKimActionTypeCode() {
            return this.kimActionTypeCode;
        }

        @Override
        public String getEdoKimRoleResponsibilityId() {
            return this.edoKimRoleResponsibilityId;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setKimForceAction(boolean kimForceAction) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.kimForceAction = kimForceAction;
        }

        public void setKimPriority(int kimPriority) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.kimPriority = kimPriority;
        }

        public void setKimRoleName(String kimRoleName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.kimRoleName = kimRoleName;
        }

        public void setKimResponsibilityName(String kimResponsibilityName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.kimResponsibilityName = kimResponsibilityName;
        }

        public void setKimActionPolicyCode(String kimActionPolicyCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.kimActionPolicyCode = kimActionPolicyCode;
        }

        public void setKimActionTypeCode(String kimActionTypeCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.kimActionTypeCode = kimActionTypeCode;
        }

        public void setEdoKimRoleResponsibilityId(String edoKimRoleResponsibilityId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoKimRoleResponsibilityId = edoKimRoleResponsibilityId;
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

        final static String ROOT_ELEMENT_NAME = "edoRoleResponsibility";
        final static String TYPE_NAME = "EdoRoleResponsibilityType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String KIM_FORCE_ACTION = "kimForceAction";
        final static String KIM_PRIORITY = "kimPriority";
        final static String KIM_ROLE_NAME = "kimRoleName";
        final static String KIM_RESPONSIBILITY_NAME = "kimResponsibilityName";
        final static String KIM_ACTION_POLICY_CODE = "kimActionPolicyCode";
        final static String KIM_ACTION_TYPE_CODE = "kimActionTypeCode";
        final static String EDO_KIM_ROLE_RESPONSIBILITY_ID = "edoKimRoleResponsibilityId";

    }

}

