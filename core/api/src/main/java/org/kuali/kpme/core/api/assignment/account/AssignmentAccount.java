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
package org.kuali.kpme.core.api.assignment.account;

import java.io.Serializable;
import java.math.BigDecimal;
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

@XmlRootElement(name = AssignmentAccount.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = AssignmentAccount.Constants.TYPE_NAME, propOrder = {
        AssignmentAccount.Elements.ACCOUNT_NBR,
        AssignmentAccount.Elements.SUB_ACCT_NBR,
        AssignmentAccount.Elements.PERCENT,
        AssignmentAccount.Elements.TK_ASSIGN_ACCT_ID,
        AssignmentAccount.Elements.TK_ASSIGNMENT_ID,
        AssignmentAccount.Elements.EARN_CODE,
        AssignmentAccount.Elements.FIN_COA_CD,
        AssignmentAccount.Elements.FIN_OBJECT_CD,
        AssignmentAccount.Elements.FIN_SUB_OBJ_CD,
        AssignmentAccount.Elements.PROJECT_CD,
        AssignmentAccount.Elements.ORG_REF_ID,
        AssignmentAccount.Elements.ID,
        AssignmentAccount.Elements.ACTIVE,
        AssignmentAccount.Elements.USER_PRINCIPAL_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class AssignmentAccount
        extends AbstractDataTransferObject
        implements AssignmentAccountContract
{

    @XmlElement(name = Elements.ACCOUNT_NBR, required = false)
    private final String accountNbr;
    @XmlElement(name = Elements.SUB_ACCT_NBR, required = false)
    private final String subAcctNbr;
    @XmlElement(name = Elements.PERCENT, required = false)
    private final BigDecimal percent;
    @XmlElement(name = Elements.TK_ASSIGN_ACCT_ID, required = false)
    private final String tkAssignAcctId;
    @XmlElement(name = Elements.TK_ASSIGNMENT_ID, required = false)
    private final String tkAssignmentId;
    @XmlElement(name = Elements.EARN_CODE, required = false)
    private final String earnCode;
    @XmlElement(name = Elements.FIN_COA_CD, required = false)
    private final String finCoaCd;
    @XmlElement(name = Elements.FIN_OBJECT_CD, required = false)
    private final String finObjectCd;
    @XmlElement(name = Elements.FIN_SUB_OBJ_CD, required = false)
    private final String finSubObjCd;
    @XmlElement(name = Elements.PROJECT_CD, required = false)
    private final String projectCd;
    @XmlElement(name = Elements.ORG_REF_ID, required = false)
    private final String orgRefId;
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
    private AssignmentAccount() {
        this.accountNbr = null;
        this.subAcctNbr = null;
        this.percent = null;
        this.tkAssignAcctId = null;
        this.tkAssignmentId = null;
        this.earnCode = null;
        this.finCoaCd = null;
        this.finObjectCd = null;
        this.finSubObjCd = null;
        this.projectCd = null;
        this.orgRefId = null;
        this.versionNumber = null;
        this.objectId = null;
        this.id = null;
        this.active = false;
        this.userPrincipalId = null;
    }

    private AssignmentAccount(Builder builder) {
        this.accountNbr = builder.getAccountNbr();
        this.subAcctNbr = builder.getSubAcctNbr();
        this.percent = builder.getPercent();
        this.tkAssignAcctId = builder.getTkAssignAcctId();
        this.tkAssignmentId = builder.getTkAssignmentId();
        this.earnCode = builder.getEarnCode();
        this.finCoaCd = builder.getFinCoaCd();
        this.finObjectCd = builder.getFinObjectCd();
        this.finSubObjCd = builder.getFinSubObjCd();
        this.projectCd = builder.getProjectCd();
        this.orgRefId = builder.getOrgRefId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.id = builder.getId();
        this.active = builder.isActive();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getAccountNbr() {
        return this.accountNbr;
    }

    @Override
    public String getSubAcctNbr() {
        return this.subAcctNbr;
    }

    @Override
    public BigDecimal getPercent() {
        return this.percent;
    }

    @Override
    public String getTkAssignAcctId() {
        return this.tkAssignAcctId;
    }

    @Override
    public String getTkAssignmentId() {
        return this.tkAssignmentId;
    }

    @Override
    public String getEarnCode() {
        return this.earnCode;
    }

    @Override
    public String getFinCoaCd() {
        return this.finCoaCd;
    }

    @Override
    public String getFinObjectCd() {
        return this.finObjectCd;
    }

    @Override
    public String getFinSubObjCd() {
        return this.finSubObjCd;
    }

    @Override
    public String getProjectCd() {
        return this.projectCd;
    }

    @Override
    public String getOrgRefId() {
        return this.orgRefId;
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
     * A builder which can be used to construct {@link AssignmentAccount} instances.  Enforces the constraints of the {@link AssignmentAccountContract}.
     *
     */
    public final static class Builder
            implements Serializable, AssignmentAccountContract, ModelBuilder
    {

        private String accountNbr;
        private String subAcctNbr;
        private BigDecimal percent;
        private String tkAssignAcctId;
        private String tkAssignmentId;
        private String earnCode;
        private String finCoaCd;
        private String finObjectCd;
        private String finSubObjCd;
        private String projectCd;
        private String orgRefId;
        private Long versionNumber;
        private String objectId;
        private String id;
        private boolean active;
        private String userPrincipalId;

        private Builder() { }

        public static Builder create() {
            return new Builder();
        }

        public static Builder create(AssignmentAccountContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create();
            builder.setAccountNbr(contract.getAccountNbr());
            builder.setSubAcctNbr(contract.getSubAcctNbr());
            builder.setPercent(contract.getPercent());
            builder.setTkAssignAcctId(contract.getTkAssignAcctId());
            builder.setTkAssignmentId(contract.getTkAssignmentId());
            builder.setEarnCode(contract.getEarnCode());
            builder.setFinCoaCd(contract.getFinCoaCd());
            builder.setFinObjectCd(contract.getFinObjectCd());
            builder.setFinSubObjCd(contract.getFinSubObjCd());
            builder.setProjectCd(contract.getProjectCd());
            builder.setOrgRefId(contract.getOrgRefId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setId(contract.getId());
            builder.setActive(contract.isActive());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public AssignmentAccount build() {
            return new AssignmentAccount(this);
        }

        @Override
        public String getAccountNbr() {
            return this.accountNbr;
        }

        @Override
        public String getSubAcctNbr() {
            return this.subAcctNbr;
        }

        @Override
        public BigDecimal getPercent() {
            return this.percent;
        }

        @Override
        public String getTkAssignAcctId() {
            return this.tkAssignAcctId;
        }

        @Override
        public String getTkAssignmentId() {
            return this.tkAssignmentId;
        }

        @Override
        public String getEarnCode() {
            return this.earnCode;
        }

        @Override
        public String getFinCoaCd() {
            return this.finCoaCd;
        }

        @Override
        public String getFinObjectCd() {
            return this.finObjectCd;
        }

        @Override
        public String getFinSubObjCd() {
            return this.finSubObjCd;
        }

        @Override
        public String getProjectCd() {
            return this.projectCd;
        }

        @Override
        public String getOrgRefId() {
            return this.orgRefId;
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

        public void setAccountNbr(String accountNbr) {
            this.accountNbr = accountNbr;
        }

        public void setSubAcctNbr(String subAcctNbr) {
            this.subAcctNbr = subAcctNbr;
        }

        public void setPercent(BigDecimal percent) {
            this.percent = percent;
        }

        public void setTkAssignAcctId(String tkAssignAcctId) {
            this.tkAssignAcctId = tkAssignAcctId;
        }

        public void setTkAssignmentId(String tkAssignmentId) {
            this.tkAssignmentId = tkAssignmentId;
        }

        public void setEarnCode(String earnCode) {
            this.earnCode = earnCode;
        }

        public void setFinCoaCd(String finCoaCd) {
            this.finCoaCd = finCoaCd;
        }

        public void setFinObjectCd(String finObjectCd) {
            this.finObjectCd = finObjectCd;
        }

        public void setFinSubObjCd(String finSubObjCd) {
            this.finSubObjCd = finSubObjCd;
        }

        public void setProjectCd(String projectCd) {
            this.projectCd = projectCd;
        }

        public void setOrgRefId(String orgRefId) {
            this.orgRefId = orgRefId;
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

        final static String ROOT_ELEMENT_NAME = "assignmentAccount";
        final static String TYPE_NAME = "AssignmentAccountType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String ACCOUNT_NBR = "accountNbr";
        final static String SUB_ACCT_NBR = "subAcctNbr";
        final static String PERCENT = "percent";
        final static String TK_ASSIGN_ACCT_ID = "tkAssignAcctId";
        final static String TK_ASSIGNMENT_ID = "tkAssignmentId";
        final static String EARN_CODE = "earnCode";
        final static String FIN_COA_CD = "finCoaCd";
        final static String FIN_OBJECT_CD = "finObjectCd";
        final static String FIN_SUB_OBJ_CD = "finSubObjCd";
        final static String PROJECT_CD = "projectCd";
        final static String ORG_REF_ID = "orgRefId";
        final static String ID = "id";
        final static String ACTIVE = "active";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}