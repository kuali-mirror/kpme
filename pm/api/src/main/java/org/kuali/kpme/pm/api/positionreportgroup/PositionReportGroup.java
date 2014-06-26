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
package org.kuali.kpme.pm.api.positionreportgroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
import org.w3c.dom.Element;

@XmlRootElement(name = PositionReportGroup.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionReportGroup.Constants.TYPE_NAME, propOrder = {
    PositionReportGroup.Elements.PM_POSITION_REPORT_GROUP_ID,
    PositionReportGroup.Elements.EFFECTIVE_KEY_SET,
    PositionReportGroup.Elements.DESCRIPTION,
    PositionReportGroup.Elements.POSITION_REPORT_GROUP,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionReportGroup.Elements.ACTIVE,
    PositionReportGroup.Elements.ID,
    PositionReportGroup.Elements.EFFECTIVE_LOCAL_DATE,
    PositionReportGroup.Elements.CREATE_TIME,
    PositionReportGroup.Elements.USER_PRINCIPAL_ID,
    PositionReportGroup.Elements.GROUP_KEY_CODE_SET,
    PositionReportGroup.Elements.GROUP_KEY_SET,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionReportGroup extends AbstractDataTransferObject implements PositionReportGroupContract {

	private static final long serialVersionUID = -41287964136831033L;
	
	@XmlElement(name = Elements.PM_POSITION_REPORT_GROUP_ID, required = false)
    private final String pmPositionReportGroupId;
    @XmlElement(name = Elements.EFFECTIVE_KEY_SET, required = false)
    private final Set<EffectiveKey> effectiveKeySet;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.POSITION_REPORT_GROUP, required = false)
    private final String positionReportGroup;
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
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PositionReportGroup() {
        this.pmPositionReportGroupId = null;
        this.effectiveKeySet = null;
        this.description = null;
        this.positionReportGroup = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
        this.groupKeyCodeSet = null;
        this.groupKeySet = null;
    }

    private PositionReportGroup(Builder builder) {
        this.pmPositionReportGroupId = builder.getPmPositionReportGroupId();
        this.effectiveKeySet = ModelObjectUtils.<EffectiveKey>buildImmutableCopy(builder.getEffectiveKeySet());        
        this.description = builder.getDescription();
        this.positionReportGroup = builder.getPositionReportGroup();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.groupKeyCodeSet = builder.getGroupKeyCodeSet();
        this.groupKeySet = ModelObjectUtils.<HrGroupKey>buildImmutableCopy(builder.getGroupKeySet());
    }

    @Override
    public String getPmPositionReportGroupId() {
        return this.pmPositionReportGroupId;
    }

    @Override
    public Set<EffectiveKey> getEffectiveKeySet() {
        return this.effectiveKeySet;
    }
    
    // helper method to convert from key-set to  key-list 
    public List<EffectiveKey> getEffectiveKeyList() {
    	if (CollectionUtils.isEmpty(this.effectiveKeySet)) {
            return Collections.emptyList();
        }
        List<EffectiveKey> copy = new ArrayList<EffectiveKey>();
        for (EffectiveKey key : this.effectiveKeySet) {
            copy.add(key);
        }
        return Collections.unmodifiableList(copy);
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getPositionReportGroup() {
        return this.positionReportGroup;
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
     * A builder which can be used to construct {@link PositionReportGroup} instances.  Enforces the constraints of the {@link PositionReportGroupContract}.
     * 
     */
    public final static class Builder implements Serializable, PositionReportGroupContract, ModelBuilder {

		private static final long serialVersionUID = 6845448497862211410L;
		
		private String pmPositionReportGroupId;
        private Set<EffectiveKey.Builder> effectiveKeySet;
        private String description;
        private String positionReportGroup;
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
        
        private Builder(String positionReportGroup) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setPositionReportGroup(positionReportGroup);
        }

        public static Builder create(String positionReportGroup) {

            return new Builder(positionReportGroup);
        }

        public static Builder create(PositionReportGroupContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

            Builder builder = create(contract.getPositionReportGroup());
            builder.setPmPositionReportGroupId(contract.getPmPositionReportGroupId());
            builder.setEffectiveKeySet(ModelObjectUtils.transformSet(contract.getEffectiveKeySet(), toEffectiveKeyBuilder));
            builder.setDescription(contract.getDescription());
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

        public PositionReportGroup build() {
            return new PositionReportGroup(this);
        }

        @Override
        public String getPmPositionReportGroupId() {
            return this.pmPositionReportGroupId;
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
        public String getPositionReportGroup() {
            return this.positionReportGroup;
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

        public void setPmPositionReportGroupId(String pmPositionReportGroupId) {

            this.pmPositionReportGroupId = pmPositionReportGroupId;
        }

        public void setEffectiveKeySet(Set<EffectiveKey.Builder> effectiveKeySet) {

            this.effectiveKeySet = effectiveKeySet;
        }

        public void setDescription(String description) {

            this.description = description;
        }

        public void setPositionReportGroup(String positionReportGroup) {

            if (StringUtils.isWhitespace(positionReportGroup) || (positionReportGroup == null)) {
                throw new IllegalArgumentException("positionReportGroup is blank");
            }
            this.positionReportGroup = positionReportGroup;
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

        final static String ROOT_ELEMENT_NAME = "positionReportGroup";
        final static String TYPE_NAME = "PositionReportGroupType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String PM_POSITION_REPORT_GROUP_ID = "pmPositionReportGroupId";
        final static String EFFECTIVE_KEY_SET = "effectiveKeySet";
        final static String DESCRIPTION = "description";
        final static String POSITION_REPORT_GROUP = "positionReportGroup";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE_SET = "groupKeyCodeSet";
        final static String GROUP_KEY_SET = "groupKeySet";

    }

}