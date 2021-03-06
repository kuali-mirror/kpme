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
package org.kuali.kpme.core.api.earncode.group;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.*;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.w3c.dom.Element;
import org.apache.commons.collections.CollectionUtils;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

@XmlRootElement(name = EarnCodeGroup.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EarnCodeGroup.Constants.TYPE_NAME, propOrder = {
    EarnCodeGroup.Elements.EARN_CODE_GROUP,
    EarnCodeGroup.Elements.DESCR,
    EarnCodeGroup.Elements.WARNING_TEXT,
    EarnCodeGroup.Elements.EARN_CODE_GROUPS,
    EarnCodeGroup.Elements.HR_EARN_CODE_GROUP_ID,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EarnCodeGroup.Elements.ACTIVE,
    EarnCodeGroup.Elements.ID,
    EarnCodeGroup.Elements.EFFECTIVE_LOCAL_DATE,
    EarnCodeGroup.Elements.CREATE_TIME,
    EarnCodeGroup.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EarnCodeGroup
    extends AbstractDataTransferObject
    implements EarnCodeGroupContract
{

    @XmlElement(name = Elements.EARN_CODE_GROUP, required = false)
    private final String earnCodeGroup;
    @XmlElement(name = Elements.DESCR, required = false)
    private final String descr;
    @XmlElement(name = Elements.WARNING_TEXT, required = false)
    private final String warningText;
    @XmlElementWrapper(name = Elements.EARN_CODE_GROUPS, required = false)
    @XmlElement(name = Elements.EARN_CODE_GROUP_DEF, required = false)
    private final List<EarnCodeGroupDefinition> earnCodeGroups;
    @XmlElement(name = Elements.HR_EARN_CODE_GROUP_ID, required = false)
    private final String hrEarnCodeGroupId;
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
    private EarnCodeGroup() {
        this.earnCodeGroup = null;
        this.descr = null;
        this.warningText = null;
        this.earnCodeGroups = null;
        this.hrEarnCodeGroupId = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private EarnCodeGroup(Builder builder) {
        this.earnCodeGroup = builder.getEarnCodeGroup();
        this.descr = builder.getDescr();
        this.warningText = builder.getWarningText();
        this.earnCodeGroups = CollectionUtils.isEmpty(builder.getEarnCodeGroups()) ? Collections.<EarnCodeGroupDefinition>emptyList() : ModelObjectUtils.<EarnCodeGroupDefinition>buildImmutableCopy(builder.getEarnCodeGroups());
        this.hrEarnCodeGroupId = builder.getHrEarnCodeGroupId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getEarnCodeGroup() {
        return this.earnCodeGroup;
    }

    @Override
    public String getDescr() {
        return this.descr;
    }

    @Override
    public String getWarningText() {
        return this.warningText;
    }

    @Override
    public List<EarnCodeGroupDefinition> getEarnCodeGroups() {
        return this.earnCodeGroups;
    }

    @Override
    public String getHrEarnCodeGroupId() {
        return this.hrEarnCodeGroupId;
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
     * A builder which can be used to construct {@link EarnCodeGroup} instances.  Enforces the constraints of the {@link EarnCodeGroupContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EarnCodeGroupContract, ModelBuilder
    {

        private String earnCodeGroup;
        private String descr;
        private String warningText;
        private List<EarnCodeGroupDefinition.Builder> earnCodeGroups;
        private String hrEarnCodeGroupId;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private static final ModelObjectUtils.Transformer<EarnCodeGroupDefinitionContract, EarnCodeGroupDefinition.Builder> toEarnCodeGroupDefinitionBuilder =
                new ModelObjectUtils.Transformer<EarnCodeGroupDefinitionContract, EarnCodeGroupDefinition.Builder>() {
                    public EarnCodeGroupDefinition.Builder transform(EarnCodeGroupDefinitionContract input) {
                        return EarnCodeGroupDefinition.Builder.create(input);
                    }
                };
                
        private Builder(String earnCodeGroup) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setEarnCodeGroup(earnCodeGroup);
        }

        public static Builder create(String earnCodeGroup) {

            return new Builder(earnCodeGroup);
        }

        public static Builder create(EarnCodeGroupContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

            Builder builder = create(contract.getEarnCodeGroup());
            builder.setDescr(contract.getDescr());
            builder.setWarningText(contract.getWarningText());
            if (CollectionUtils.isEmpty(contract.getEarnCodeGroups())) {
                builder.setEarnCodeGroups(Collections.<EarnCodeGroupDefinition.Builder>emptyList());
            } else {
                builder.setEarnCodeGroups(ModelObjectUtils.transform(contract.getEarnCodeGroups(), toEarnCodeGroupDefinitionBuilder));
            }
            builder.setHrEarnCodeGroupId(contract.getHrEarnCodeGroupId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public EarnCodeGroup build() {
            return new EarnCodeGroup(this);
        }

        @Override
        public String getEarnCodeGroup() {
            return this.earnCodeGroup;
        }

        @Override
        public String getDescr() {
            return this.descr;
        }

        @Override
        public String getWarningText() {
            return this.warningText;
        }

        @Override
        public List<EarnCodeGroupDefinition.Builder> getEarnCodeGroups() {
            return this.earnCodeGroups;
        }

        @Override
        public String getHrEarnCodeGroupId() {
            return this.hrEarnCodeGroupId;
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

        public void setEarnCodeGroup(String earnCodeGroup) {

            this.earnCodeGroup = earnCodeGroup;
        }

        public void setDescr(String descr) {

            this.descr = descr;
        }

        public void setWarningText(String warningText) {

            this.warningText = warningText;
        }

        public void setEarnCodeGroups(List<EarnCodeGroupDefinition.Builder> earnCodeGroups) {

            this.earnCodeGroups = earnCodeGroups;
        }

        public void setHrEarnCodeGroupId(String hrEarnCodeGroupId) {

            this.hrEarnCodeGroupId = hrEarnCodeGroupId;
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

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "earnCodeGroup";
        final static String TYPE_NAME = "EarnCodeGroupType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String EARN_CODE_GROUP = "earnCodeGroup";
        final static String DESCR = "descr";
        final static String WARNING_TEXT = "warningText";
        final static String EARN_CODE_GROUPS = "earnCodeGroups";
        final static String EARN_CODE_GROUP_DEF = "earnCodeGroupDefinitions";
        final static String HR_EARN_CODE_GROUP_ID = "hrEarnCodeGroupId";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

