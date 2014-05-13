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
package org.kuali.kpme.pm.api.positiontype;

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
import org.kuali.kpme.core.api.groupkey.HrGroupKeyContract;
import org.kuali.kpme.pm.api.positionreportgroup.PositionReportGroup.Builder;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.springframework.expression.spel.ast.OpOr;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionType.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionType.Constants.TYPE_NAME, propOrder = {
    PositionType.Elements.DESCRIPTION,
    PositionType.Elements.ACADEMIC_FLAG,
    PositionType.Elements.POSITION_TYPE,
    PositionType.Elements.PM_POSITION_TYPE_ID,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionType.Elements.ACTIVE,
    PositionType.Elements.ID,
    PositionType.Elements.EFFECTIVE_LOCAL_DATE,
    PositionType.Elements.CREATE_TIME,
    PositionType.Elements.USER_PRINCIPAL_ID,
    
    PositionType.Elements.LOCATION,
    PositionType.Elements.INSTITUTION,
//    PositionType.Elements.GROUP_KEY,
//    PositionType.Elements.GROUP_KEY_CODE,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionType
    extends AbstractDataTransferObject
    implements PositionTypeContract
{

    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.ACADEMIC_FLAG, required = false)
    private final boolean academicFlag;
    @XmlElement(name = Elements.POSITION_TYPE, required = false)
    private final String positionType;
    @XmlElement(name = Elements.PM_POSITION_TYPE_ID, required = false)
    private final String pmPositionTypeId;
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
    
    @XmlElement(name = Elements.LOCATION, required = false)
    private final String location;
    @XmlElement(name = Elements.INSTITUTION, required = false)
    private final String institution;
    
//    @XmlElement(name = Elements.GROUP_KEY, required = false)
//    private final HrGroupKey groupKey;
//    @XmlElement(name = Elements.GROUP_KEY_CODE, required = false)
//    private final String groupKeyCode;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PositionType() {
        this.description = null;
        this.academicFlag = false;
        this.positionType = null;
        this.pmPositionTypeId = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
        
        this.location = null;
        this.institution = null;
//        this.groupKey = null;
//        this.groupKeyCode = null;
    }

    private PositionType(Builder builder) {
        this.description = builder.getDescription();
        this.academicFlag = builder.isAcademicFlag();
        this.positionType = builder.getPositionType();
        this.pmPositionTypeId = builder.getPmPositionTypeId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
        
        this.location = builder.getLocation();
        this.institution = builder.getInstitution();
//        this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
//        this.groupKeyCode = builder.getGroupKeyCode();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean isAcademicFlag() {
        return this.academicFlag;
    }

    @Override
    public String getPositionType() {
        return this.positionType;
    }

    @Override
    public String getPmPositionTypeId() {
        return this.pmPositionTypeId;
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
    public String getLocation() {
        return this.location;
    }
    
    @Override
    public String getInstitution() {
        return this.institution;
    }

//    @Override
//    public HrGroupKey getGroupKey() {
//        return this.groupKey;
//    }
//
//    @Override
//    public String getGroupKeyCode() {
//        return this.groupKeyCode;
//    }


    /**
     * A builder which can be used to construct {@link PositionType} instances.  Enforces the constraints of the {@link PositionTypeContract}.
     * 
     */
    public final static class Builder
        implements Serializable, PositionTypeContract, ModelBuilder
    {

        private String description;
        private boolean academicFlag;
        private String positionType;
        private String pmPositionTypeId;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
        
        private String location;
        private String institution;
//        private String groupKeyCode;
//        private HrGroupKey.Builder groupKey;


        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }
        
        private Builder(String positionType) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	//setGroupKeyCode(groupKeyCode);
        	setPositionType(positionType);
        }

        public static Builder create(String positionType) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(positionType);
        }

        public static Builder create(PositionTypeContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create(contract.getPositionType());
           
            builder.setLocation(contract.getLocation());
            builder.setInstitution(contract.getInstitution());
            
            builder.setDescription(contract.getDescription());
            builder.setAcademicFlag(contract.isAcademicFlag());
            builder.setPositionType(contract.getPositionType());
            builder.setPmPositionTypeId(contract.getPmPositionTypeId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            //builder.setGroupKeyCode(contract.getGroupKeyCode());
            //builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));
            return builder;
        }

        public PositionType build() {
            return new PositionType(this);
        }

        @Override
        public String getLocation() {
            return this.location;
        }

        @Override
        public String getInstitution() {
            return this.institution;
        }

        
        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public boolean isAcademicFlag() {
            return this.academicFlag;
        }

        @Override
        public String getPositionType() {
            return this.positionType;
        }

        @Override
        public String getPmPositionTypeId() {
            return this.pmPositionTypeId;
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

//        @Override
//        public HrGroupKey.Builder getGroupKey() {
//            return this.groupKey;
//        }
//
//        @Override
//        public String getGroupKeyCode() {
//            return this.groupKeyCode;
//        }

        
        public void setLocation(String location) {
            this.location = location;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }
        
        public void setDescription(String description) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.description = description;
        }

        public void setAcademicFlag(boolean academicFlag) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.academicFlag = academicFlag;
        }

        public void setPositionType(String positionType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.positionType = positionType;
        }

        public void setPmPositionTypeId(String pmPositionTypeId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPositionTypeId = pmPositionTypeId;
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

//        public void setGroupKeyCode(String groupKeyCode) {
//            if (StringUtils.isWhitespace(groupKeyCode)) {
//                throw new IllegalArgumentException("groupKeyCode is blank");
//            }
//            this.groupKeyCode = groupKeyCode;
//        }
//
//        public void setGroupKey(HrGroupKey.Builder groupKey) {
//            this.groupKey = groupKey;
//        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "positionType";
        final static String TYPE_NAME = "PositionTypeType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        //public static final String LOCATION = null;
		final static String DESCRIPTION = "description";
        final static String ACADEMIC_FLAG = "academicFlag";
        final static String POSITION_TYPE = "positionType";
        final static String PM_POSITION_TYPE_ID = "pmPositionTypeId";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        
        final static String LOCATION = "location";
        final static String INSTITUTION = "institution";
//        final static String GROUP_KEY = "groupKey";
//        final static String GROUP_KEY_CODE = "groupKeyCode";

    }

}

