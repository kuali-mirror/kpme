/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.pm.api.positiondepartment;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.KPMEConstants;
import org.kuali.kpme.core.api.departmentaffiliation.DepartmentAffiliation;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionDepartment.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionDepartment.Constants.TYPE_NAME, propOrder = {
//    PositionDepartment.Elements.LOCATION,
    PositionDepartment.Elements.PM_POSITION_DEPT_ID,
//    PositionDepartment.Elements.LOCATION_OBJ,
    PositionDepartment.Elements.DEPT_AFFL_OBJ,
//    PositionDepartment.Elements.INSTITUTION,
//    PositionDepartment.Elements.INSTITUTION_OBJ,
    PositionDepartment.Elements.DEPARTMENT,
    PositionDepartment.Elements.DEPT_AFFL,
    PositionDepartment.Elements.HR_POSITION_ID,
    PositionDepartment.Elements.OWNER,
    PositionDepartment.Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER,
    KPMEConstants.CommonElements.GROUP_KEY_CODE,
 	KPMEConstants.CommonElements.GROUP_KEY,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionDepartment extends AbstractDataTransferObject implements PositionDepartmentContract {

    private static final long serialVersionUID = -9138371031422545617L;
	
//	@XmlElement(name = Elements.LOCATION, required = false)
//    private final String location;
    @XmlElement(name = Elements.PM_POSITION_DEPT_ID, required = false)
    private final String pmPositionDeptId;
//    @XmlElement(name = Elements.LOCATION_OBJ, required = false)
//    private final Location locationObj;
    @XmlElement(name = Elements.DEPT_AFFL_OBJ, required = false)
    private final DepartmentAffiliation deptAfflObj;
    @XmlElement(name = KPMEConstants.CommonElements.GROUP_KEY_CODE, required = true)
	private final String groupKeyCode;
 	@XmlElement(name = KPMEConstants.CommonElements.GROUP_KEY, required = false)
 	private final HrGroupKey groupKey;
//    @XmlElement(name = Elements.INSTITUTION, required = false)
//    private final String institution;
//    @XmlElement(name = Elements.INSTITUTION_OBJ, required = false)
//    private final Institution institutionObj;
    @XmlElement(name = Elements.DEPARTMENT, required = false)
    private final String department;
    @XmlElement(name = Elements.DEPT_AFFL, required = false)
    private final String deptAffl;
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
    private PositionDepartment() {
//        this.location = null;
        this.pmPositionDeptId = null;
//        this.locationObj = null;
        this.deptAfflObj = null;
        this.groupKey = null;
        this.groupKeyCode = null;
//        this.institution = null;
//        this.institutionObj = null;
        this.department = null;
        this.deptAffl = null;
        this.hrPositionId = null;
        this.owner = null;
        this.effectiveLocalDateOfOwner = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private PositionDepartment(Builder builder) {
//        this.location = builder.getLocation();
        this.pmPositionDeptId = builder.getPmPositionDeptId();
//        this.locationObj = builder.getLocationObj() == null ? null : builder.getLocationObj().build();
        this.deptAfflObj =  builder.getDeptAfflObj() == null ? null : builder.getDeptAfflObj().build();
//        this.institution = builder.getInstitution();
//        this.institutionObj =  builder.getInstitutionObj() == null ? null : builder.getInstitutionObj().build();
        this.groupKeyCode = builder.getGroupKeyCode();
 	 	this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
        this.department = builder.getDepartment();
        this.deptAffl = builder.getDeptAffl();
        this.hrPositionId = builder.getHrPositionId();
        this.owner =  builder.getOwner() == null ? null : builder.getOwner().build();
        this.effectiveLocalDateOfOwner = builder.getEffectiveLocalDateOfOwner();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    
    @Override
 	public String getGroupKeyCode() {
    	return this.groupKeyCode;
    }
    
    @Override
    public HrGroupKey getGroupKey() {
    	return this.groupKey;
    }
    
    @Override
    public String getPmPositionDeptId() {
        return this.pmPositionDeptId;
    }

    @Override
    public DepartmentAffiliation getDeptAfflObj() {
        return this.deptAfflObj;
    }

    @Override
    public String getDepartment() {
        return this.department;
    }

    @Override
    public String getDeptAffl() {
        return this.deptAffl;
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
     * A builder which can be used to construct {@link PositionDepartment} instances.  Enforces the constraints of the {@link PositionDepartmentContract}.
     * 
     */
    public final static class Builder implements Serializable, PositionDepartmentContract, ModelBuilder {

    	private static final long serialVersionUID = -114427722527252268L;
		
//    	private String location;
        private String pmPositionDeptId;
//        private Location.Builder locationObj;
        private DepartmentAffiliation.Builder deptAfflObj;
//        private String institution;
//        private Institution.Builder institutionObj;
        private String groupKeyCode;
 	 	private HrGroupKey.Builder groupKey;
        private String department;
        private String deptAffl;
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

        public static Builder create(PositionDepartmentContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
//            builder.setLocation(contract.getLocation());
            builder.setPmPositionDeptId(contract.getPmPositionDeptId());
//            builder.setLocationObj(contract.getLocationObj() == null ? null : Location.Builder.create(contract.getLocationObj()));
            builder.setDeptAfflObj(contract.getDeptAfflObj() == null ? null : DepartmentAffiliation.Builder.create(contract.getDeptAfflObj()));
//            builder.setInstitution(contract.getInstitution());
//            builder.setInstitutionObj(contract.getInstitutionObj() == null ? null : Institution.Builder.create(contract.getInstitutionObj()));
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));
            builder.setDepartment(contract.getDepartment());
            builder.setDeptAffl(contract.getDeptAffl());
            builder.setHrPositionId(contract.getHrPositionId());
            builder.setOwner(contract.getOwner() == null ? null : Position.Builder.create(contract.getOwner()));
            builder.setEffectiveLocalDateOfOwner(contract.getEffectiveLocalDateOfOwner());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        
        @Override
 	 	public String getGroupKeyCode() {
 	 	 	 	return groupKeyCode;
        }

        public void setGroupKeyCode(String groupKeyCode) {
        		this.groupKeyCode = groupKeyCode;
        }
        
        @Override
        public HrGroupKey.Builder getGroupKey() {
        	return groupKey;
        }
        
        public void setGroupKey(HrGroupKey.Builder groupKey) {
        	this.groupKey = groupKey;
        }
        
        public PositionDepartment build() {
            return new PositionDepartment(this);
        }

        @Override
        public String getPmPositionDeptId() {
            return this.pmPositionDeptId;
        }

        @Override
        public DepartmentAffiliation.Builder getDeptAfflObj() {
            return this.deptAfflObj;
        }

        @Override
        public String getDepartment() {
            return this.department;
        }

        @Override
        public String getDeptAffl() {
            return this.deptAffl;
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

        public void setPmPositionDeptId(String pmPositionDeptId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPositionDeptId = pmPositionDeptId;
        }

        public void setDeptAfflObj(DepartmentAffiliation.Builder deptAfflObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.deptAfflObj = deptAfflObj;
        }

        public void setDepartment(String department) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.department = department;
        }

        public void setDeptAffl(String deptAffl) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.deptAffl = deptAffl;
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

        final static String ROOT_ELEMENT_NAME = "positionDepartment";
        final static String TYPE_NAME = "PositionDepartmentType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

//        final static String LOCATION = "location";
        final static String PM_POSITION_DEPT_ID = "pmPositionDeptId";
//        final static String LOCATION_OBJ = "locationObj";
        final static String DEPT_AFFL_OBJ = "deptAfflObj";
//        final static String INSTITUTION = "institution";
//        final static String INSTITUTION_OBJ = "institutionObj";
        final static String DEPARTMENT = "department";
        final static String DEPT_AFFL = "deptAffl";
        final static String HR_POSITION_ID = "hrPositionId";
        final static String OWNER = "owner";
        final static String EFFECTIVE_LOCAL_DATE_OF_OWNER = "effectiveLocalDateOfOwner";

    }

}