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

@XmlRootElement(name = EarnCodeGroupDefinition.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EarnCodeGroupDefinition.Constants.TYPE_NAME, propOrder = {
    EarnCodeGroupDefinition.Elements.EARN_CODE,
    EarnCodeGroupDefinition.Elements.HR_EARN_CODE_GROUP_ID,
    EarnCodeGroupDefinition.Elements.HR_EARN_CODE_GROUP_DEF_ID,
    EarnCodeGroupDefinition.Elements.EARN_CODE_DESC,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EarnCodeGroupDefinition.Elements.ID,
    EarnCodeGroupDefinition.Elements.ACTIVE,
    EarnCodeGroupDefinition.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EarnCodeGroupDefinition
    extends AbstractDataTransferObject
    implements EarnCodeGroupDefinitionContract
{

    @XmlElement(name = Elements.EARN_CODE, required = false)
    private final String earnCode;
    @XmlElement(name = Elements.HR_EARN_CODE_GROUP_ID, required = false)
    private final String hrEarnCodeGroupId;
    @XmlElement(name = Elements.HR_EARN_CODE_GROUP_DEF_ID, required = false)
    private final String hrEarnCodeGroupDefId;
    @XmlElement(name = Elements.EARN_CODE_DESC, required = false)
    private final String earnCodeDesc;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EarnCodeGroupDefinition() {
        this.earnCode = null;
        this.hrEarnCodeGroupId = null;
        this.hrEarnCodeGroupDefId = null;
        this.earnCodeDesc = null;
        this.versionNumber = null;
        this.objectId = null;
        this.id = null;
        this.active = false;
        this.userPrincipalId = null;
    }

    private EarnCodeGroupDefinition(Builder builder) {
        this.earnCode = builder.getEarnCode();
        this.hrEarnCodeGroupId = builder.getHrEarnCodeGroupId();
        this.hrEarnCodeGroupDefId = builder.getHrEarnCodeGroupDefId();
        this.earnCodeDesc = builder.getEarnCodeDesc();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.id = builder.getId();
        this.active = builder.isActive();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getEarnCode() {
        return this.earnCode;
    }

    @Override
    public String getHrEarnCodeGroupId() {
        return this.hrEarnCodeGroupId;
    }

    @Override
    public String getHrEarnCodeGroupDefId() {
        return this.hrEarnCodeGroupDefId;
    }

    @Override
    public String getEarnCodeDesc() {
        return this.earnCodeDesc;
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
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }


    /**
     * A builder which can be used to construct {@link EarnCodeGroupDefinition} instances.  Enforces the constraints of the {@link EarnCodeGroupDefinitionContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EarnCodeGroupDefinitionContract, ModelBuilder
    {

        private String earnCode;
        private String hrEarnCodeGroupId;
        private String hrEarnCodeGroupDefId;
        private String earnCodeDesc;
        private Long versionNumber;
        private String objectId;
        private String id;
        private boolean active;
        private String userPrincipalId;

        private Builder(String earnCode) {

        	setEarnCode(earnCode);
        }

        public static Builder create(String earnCode) {

            return new Builder(earnCode);
        }

        public static Builder create(EarnCodeGroupDefinitionContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

            Builder builder = create(contract.getEarnCode());
            builder.setHrEarnCodeGroupId(contract.getHrEarnCodeGroupId());
            builder.setHrEarnCodeGroupDefId(contract.getHrEarnCodeGroupDefId());
            builder.setEarnCodeDesc(contract.getEarnCodeDesc());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setId(contract.getId());
            builder.setActive(contract.isActive());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public EarnCodeGroupDefinition build() {
            return new EarnCodeGroupDefinition(this);
        }

        @Override
        public String getEarnCode() {
            return this.earnCode;
        }

        @Override
        public String getHrEarnCodeGroupId() {
            return this.hrEarnCodeGroupId;
        }

        @Override
        public String getHrEarnCodeGroupDefId() {
            return this.hrEarnCodeGroupDefId;
        }

        @Override
        public String getEarnCodeDesc() {
            return this.earnCodeDesc;
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
        public String getId() {
            return this.id;
        }

        @Override
        public boolean isActive() {
            return this.active;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        public void setEarnCode(String earnCode) {

            this.earnCode = earnCode;
        }

        public void setHrEarnCodeGroupId(String hrEarnCodeGroupId) {

            this.hrEarnCodeGroupId = hrEarnCodeGroupId;
        }

        public void setHrEarnCodeGroupDefId(String hrEarnCodeGroupDefId) {

            this.hrEarnCodeGroupDefId = hrEarnCodeGroupDefId;
        }

        public void setEarnCodeDesc(String earnCodeDesc) {

            this.earnCodeDesc = earnCodeDesc;
        }

        public void setVersionNumber(Long versionNumber) {

            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {

            this.objectId = objectId;
        }

        public void setId(String id) {

            this.id = id;
        }

        public void setActive(boolean active) {

            this.active = active;
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

        final static String ROOT_ELEMENT_NAME = "earnCodeGroupDefinition";
        final static String TYPE_NAME = "EarnCodeGroupDefinitionType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String EARN_CODE = "earnCode";
        final static String HR_EARN_CODE_GROUP_ID = "hrEarnCodeGroupId";
        final static String HR_EARN_CODE_GROUP_DEF_ID = "hrEarnCodeGroupDefId";
        final static String EARN_CODE_DESC = "earnCodeDesc";
        final static String ID = "id";
        final static String ACTIVE = "active";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

