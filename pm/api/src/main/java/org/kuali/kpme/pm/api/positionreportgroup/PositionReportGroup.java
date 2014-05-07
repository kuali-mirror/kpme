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
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionReportGroup.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionReportGroup.Constants.TYPE_NAME, propOrder = {
    PositionReportGroup.Elements.POSITION_REPORT_GROUP,
    PositionReportGroup.Elements.DESCRIPTION,
    PositionReportGroup.Elements.PM_POSITION_REPORT_GROUP_ID,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionReportGroup.Elements.ACTIVE,
    PositionReportGroup.Elements.ID,
    PositionReportGroup.Elements.EFFECTIVE_LOCAL_DATE,
    PositionReportGroup.Elements.CREATE_TIME,
    PositionReportGroup.Elements.USER_PRINCIPAL_ID,
    PositionReportGroup.Elements.GROUP_KEY_CODE,
    PositionReportGroup.Elements.GROUP_KEY,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionReportGroup
    extends AbstractDataTransferObject
    implements PositionReportGroupContract
{

    @XmlElement(name = Elements.POSITION_REPORT_GROUP, required = false)
    private final String positionReportGroup;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.PM_POSITION_REPORT_GROUP_ID, required = false)
    private final String pmPositionReportGroupId;
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
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = true)
    private final String groupKeyCode;
    @XmlElement(name = Elements.GROUP_KEY, required = true)
    private final HrGroupKey groupKey;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PositionReportGroup() {
        this.positionReportGroup = null;
        this.description = null;
        this.pmPositionReportGroupId = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
        this.groupKeyCode = null;
        this.groupKey = null;
    }

    private PositionReportGroup(Builder builder) {
        this.positionReportGroup = builder.getPositionReportGroup();
        this.description = builder.getDescription();
        this.pmPositionReportGroupId = builder.getPmPositionReportGroupId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
        this.groupKeyCode = builder.getGroupKeyCode();
    }

    @Override
    public String getPositionReportGroup() {
        return this.positionReportGroup;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getPmPositionReportGroupId() {
        return this.pmPositionReportGroupId;
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
    public HrGroupKey getGroupKey() {
        return this.groupKey;
    }

    @Override
    public String getGroupKeyCode() {
        return this.groupKeyCode;
    }


    /**
     * A builder which can be used to construct {@link PositionReportGroup} instances.  Enforces the constraints of the {@link PositionReportGroupContract}.
     * 
     */
    public final static class Builder
        implements Serializable, PositionReportGroupContract, ModelBuilder
    {

        private String positionReportGroup;
        private String description;
        private String pmPositionReportGroupId;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
        private String groupKeyCode;
        private HrGroupKey.Builder groupKey;

        private Builder(String groupKeyCode, String positionReportGroup) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setGroupKeyCode(groupKeyCode);
        	setPositionReportGroup(positionReportGroup);
        }

        public static Builder create(String groupKeyCode, String positionReportGroup) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(groupKeyCode,positionReportGroup);
        }

        public static Builder create(PositionReportGroupContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create(contract.getGroupKeyCode(),contract.getPositionReportGroup());
            builder.setDescription(contract.getDescription());
            builder.setPmPositionReportGroupId(contract.getPmPositionReportGroupId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));
            return builder;
        }

        public PositionReportGroup build() {
            return new PositionReportGroup(this);
        }

        @Override
        public String getPositionReportGroup() {
            return this.positionReportGroup;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getPmPositionReportGroupId() {
            return this.pmPositionReportGroupId;
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
        public String getGroupKeyCode() {
            return this.groupKeyCode;
        }

        @Override
        public HrGroupKey.Builder getGroupKey() {
            return this.groupKey;
        }

        public void setPositionReportGroup(String positionReportGroup) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
        	if (StringUtils.isWhitespace(positionReportGroup)) {
                throw new IllegalArgumentException("positionReportGroup is blank");
            }
            this.positionReportGroup = positionReportGroup;
        }

        public void setDescription(String description) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.description = description;
        }

        public void setPmPositionReportGroupId(String pmPositionReportGroupId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPositionReportGroupId = pmPositionReportGroupId;
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

        public void setGroupKeyCode(String groupKeyCode) {
            if (StringUtils.isWhitespace(groupKeyCode)) {
                throw new IllegalArgumentException("groupKeyCode is blank");
            }
            this.groupKeyCode = groupKeyCode;
        }

        public void setGroupKey(HrGroupKey.Builder groupKey) {
            this.groupKey = groupKey;
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

        final static String POSITION_REPORT_GROUP = "positionReportGroup";
        final static String DESCRIPTION = "description";
        final static String PM_POSITION_REPORT_GROUP_ID = "pmPositionReportGroupId";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";

    }

}

