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
import java.util.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.groupkey.HrGroupKeyContract;
import org.kuali.kpme.core.api.mo.EffectiveKey;
import org.kuali.kpme.core.api.mo.EffectiveKeyContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.springframework.expression.spel.ast.OpOr;
import org.w3c.dom.Element;


@XmlRootElement(name = PositionType.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionType.Constants.TYPE_NAME, propOrder = {
    PositionType.Elements.DESCRIPTION,
    PositionType.Elements.EFFECTIVE_KEY_SET,
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

    PositionType.Elements.GROUP_KEY_CODE_SET,
    PositionType.Elements.GROUP_KEY_SET,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionType
    extends AbstractDataTransferObject
    implements PositionTypeContract
{

    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;

    @XmlElement(name = Elements.EFFECTIVE_KEY_SET, required = false)
    private final Set<EffectiveKey> effectiveKeySet;
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

    @XmlElement(name = Elements.GROUP_KEY_CODE_SET, required = false)
    private final Set<String> groupKeyCodeSet;
    @XmlElement(name = Elements.GROUP_KEY_SET, required = false)
    private final Set<HrGroupKey> groupKeySet;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PositionType() {
        this.description = null;
        this.effectiveKeySet = null;
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
        
        this.groupKeySet = null;
        this.groupKeyCodeSet = null;
    }

    private PositionType(Builder builder) {
        this.description = builder.getDescription();
        this.effectiveKeySet = ModelObjectUtils.<EffectiveKey>buildImmutableCopy(builder.getEffectiveKeySet());
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
        
        this.groupKeyCodeSet = builder.getGroupKeyCodeSet();
        this.groupKeySet = ModelObjectUtils.<HrGroupKey>buildImmutableCopy(builder.getGroupKeySet());
        PositionType testPt = this;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Set<EffectiveKey> getEffectiveKeySet()
    {
        return this.effectiveKeySet;
    }

    public List<EffectiveKey> getEffectiveKeyList() {
        if (CollectionUtils.isEmpty(this.effectiveKeySet)) {
            return Collections.emptyList();
        }
        List<EffectiveKey> copy = new ArrayList<EffectiveKey>();
        for (EffectiveKey key: this.effectiveKeySet) {
            copy.add(key);
        }

        return Collections.unmodifiableList(copy);
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
    public Set<String> getGroupKeyCodeSet() {
        return this.groupKeyCodeSet;
    }

    @Override
    public Set<HrGroupKey> getGroupKeySet() {
        return this.groupKeySet;
    }



    /**
     * A builder which can be used to construct {@link PositionType} instances.  Enforces the constraints of the {@link PositionTypeContract}.
     * 
     */
    public final static class Builder
        implements Serializable, PositionTypeContract, ModelBuilder
    {
        private static final long serialVersionUID = 6845248197362411410L;

        private String description;
        private Set<EffectiveKey.Builder> effectiveKeySet;
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

        private Set<String> groupKeyCodeSet;
        private Set<HrGroupKey.Builder> groupKeySet;

        private static final ModelObjectUtils.Transformer<EffectiveKeyContract, EffectiveKey.Builder> toEffectiveKeyBuilder
                = new ModelObjectUtils.Transformer<EffectiveKeyContract, EffectiveKey.Builder>() {
            public EffectiveKey.Builder transform(EffectiveKeyContract input) {
                return EffectiveKey.Builder.create(input);
            }
        };

        private static final ModelObjectUtils.Transformer<HrGroupKeyContract, HrGroupKey.Builder> toHrGroupKeyBuilder
                = new ModelObjectUtils.Transformer<HrGroupKeyContract, HrGroupKey.Builder>() {
            public HrGroupKey.Builder transform(HrGroupKeyContract input) {
                return HrGroupKey.Builder.create(input);
            }
        };

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {

            return new Builder();
        }
        
        private Builder(String positionType) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	//setGroupKeyCode(groupKeyCode);
        	setPositionType(positionType);
        }

        public static Builder create(String positionType) {

            return new Builder(positionType);
        }

        public static Builder create(PositionTypeContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

            Builder builder = create(contract.getPositionType());
           
            builder.setEffectiveKeySet(ModelObjectUtils.transformSet(contract.getEffectiveKeySet(), toEffectiveKeyBuilder));

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
            builder.setGroupKeyCodeSet(contract.getGroupKeyCodeSet());
            builder.setGroupKeySet(ModelObjectUtils.transformSet(contract.getGroupKeySet(), toHrGroupKeyBuilder));
            return builder;
        }

        public PositionType build() {
            PositionType pt = new PositionType(this);
            return pt;
        }

        @Override
        public Set<EffectiveKey.Builder> getEffectiveKeySet() {
            return this.effectiveKeySet;
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
        public Set<String> getGroupKeyCodeSet() {
            return this.groupKeyCodeSet;
        }

        @Override
        public Set<HrGroupKey.Builder> getGroupKeySet() {
            return this.groupKeySet;
        }


        public void setEffectiveKeySet(Set<EffectiveKey.Builder> effectiveKeySet) {

            this.effectiveKeySet = effectiveKeySet;
        }

        public void setDescription(String description) {

            this.description = description;
        }

        public void setAcademicFlag(boolean academicFlag) {

            this.academicFlag = academicFlag;
        }

        public void setPositionType(String positionType) {

            this.positionType = positionType;
        }

        public void setPmPositionTypeId(String pmPositionTypeId) {

            this.pmPositionTypeId = pmPositionTypeId;
        }

        public void setVersionNumber(Long versionNumber) {

            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {

            this.objectId = objectId;
        }

        public void setActive(boolean active) {

            this.active = active;
        }

        public void setId(String id) {

            this.id = id;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {

            this.effectiveLocalDate = effectiveLocalDate;
        }

        public void setCreateTime(DateTime createTime) {

            this.createTime = createTime;
        }

        public void setUserPrincipalId(String userPrincipalId) {

            this.userPrincipalId = userPrincipalId;
        }

        public void setGroupKeyCodeSet(Set<String> groupKeyCodeSet) {

            this.groupKeyCodeSet = groupKeyCodeSet;
        }

        public void setGroupKeySet(Set<HrGroupKey.Builder> groupKeySet) {

            this.groupKeySet = groupKeySet;
        }

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
		final static String DESCRIPTION = "description";
        final static String EFFECTIVE_KEY_SET = "effectiveKeySet";
        final static String ACADEMIC_FLAG = "academicFlag";
        final static String POSITION_TYPE = "positionType";
        final static String PM_POSITION_TYPE_ID = "pmPositionTypeId";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        
        final static String GROUP_KEY_CODE_SET = "groupKeyCodeSet";
        final static String GROUP_KEY_SET = "groupKeySet";

    }

}

