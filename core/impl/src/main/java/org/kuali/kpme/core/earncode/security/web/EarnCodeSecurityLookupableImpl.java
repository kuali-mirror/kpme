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
package org.kuali.kpme.core.earncode.security.web;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.earncode.security.EarnCodeSecurity;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.earncode.security.EarnCodeSecurityBo;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.lookup.KpmeHrGroupKeyedBusinessObjectLookupableImpl;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.lookup.LookupUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.lookup.LookupForm;

import java.util.*;

//public class EarnCodeSecurityLookupableImpl extends KPMELookupableImpl{
//extends KpmeHrGroupKeyedBusinessObjectLookupableImpl {

//                                                  KpmeHrGroupKeyedBusinessObjectLookupableImpl {
public class EarnCodeSecurityLookupableImpl extends KpmeHrGroupKeyedBusinessObjectLookupableImpl {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EarnCodeSecurityLookupableImpl.class);

    private static final long serialVersionUID = -2027765891252173556L;

    private static final ModelObjectUtils.Transformer<EarnCodeSecurity, EarnCodeSecurityBo> toEarnCodeSecurityBo =
            new ModelObjectUtils.Transformer<EarnCodeSecurity, EarnCodeSecurityBo>() {
                public EarnCodeSecurityBo transform(EarnCodeSecurity input) {
                    return EarnCodeSecurityBo.from(input);
                };
            };


//    protected List<AssignmentBo> filterLookupAssignments(List<AssignmentBo> rawResults, String userPrincipalId) {
    protected List<EarnCodeSecurityBo> filterLookupEarnCodeSecurities(List<EarnCodeSecurityBo> rawResults, String earnCodeType, String userPrincipalId) {
        List<EarnCodeSecurityBo> searchResults = new ArrayList<EarnCodeSecurityBo>();
        for (EarnCodeSecurityBo ecs : rawResults) {
            if (StringUtils.equals(ecs.getEarnCodeType(), earnCodeType) || StringUtils.equals(earnCodeType, "A") || StringUtils.isBlank(earnCodeType)) {
                String department = StringUtils.equals("%", ecs.getDept().trim()) ? "*" : ecs.getDept();
                String grpKeyCode = StringUtils.equals("%", ecs.getGroupKeyCode().trim()) ? "*" : ecs.getGroupKeyCode();

                Map<String, String> roleQualification = new HashMap<String, String>();
                roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, "*"); //userPrincipalId);
                roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
                roleQualification.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), grpKeyCode);

                HrGroupKey gk = HrServiceLocator.getHrGroupKeyService().getHrGroupKey(grpKeyCode, ecs.getEffectiveLocalDate());
                if (gk != null) {
                    roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), gk.getLocationId());
                }


                if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                        KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
                        || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
                        KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
                    ecs.setEarnCodeType(HrConstants.EARN_CODE_SECURITY_TYPE.get(ecs.getEarnCodeType()));
                    searchResults.add(ecs);
                }
            }
        }
        return searchResults;
    }

    @Override
    protected Collection<?> executeSearch(Map<String, String> adjustedSearchCriteria, List<String> wildcardAsLiteralSearchCriteria, boolean bounded, Integer searchResultsLimit) {
        List<EarnCodeSecurityBo> searchResults = new ArrayList<EarnCodeSecurityBo>();
        Collection<EarnCodeSecurityBo> rawSearchResults = (Collection<EarnCodeSecurityBo>)super.executeSearch(adjustedSearchCriteria, wildcardAsLiteralSearchCriteria, bounded, searchResultsLimit);

        if(rawSearchResults != null && !rawSearchResults.isEmpty()) {
            for(EarnCodeSecurityBo ecs : rawSearchResults) {
                ecs.setEarnCodeType(HrConstants.EARN_CODE_SECURITY_TYPE.get(ecs.getEarnCodeType()));
                searchResults.add(ecs);
            }
        }

        return searchResults;
    }


}
