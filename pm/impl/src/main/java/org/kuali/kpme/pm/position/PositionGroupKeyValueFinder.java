package org.kuali.kpme.pm.position;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.KPMEConstants;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.util.InputFieldDefaultValueFinder;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.service.role.KPMERoleService;
import org.kuali.kpme.core.util.GroupKeyInputField;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.valuefinder.ValueFinder;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mlemons on 7/18/14.
 */

public class PositionGroupKeyValueFinder extends org.kuali.kpme.core.util.GroupKeyValueFinder {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        String principalId = GlobalVariables.getUserSession().getPrincipalId();

        KPMERoleService rs = HrServiceLocator.getKPMERoleService();

        List<Map<String, String>> roles = new ArrayList<Map<String, String>>();

        Map<String, String> role1 = new HashMap<String, String>();
        role1.put((String)"NameSpace" , KPMENamespace.KPME_HR.getNamespaceCode());
        role1.put((String)"Role", KPMERole.DEPARTMENT_APPROVER.getRoleName());

        Map<String, String> role2 = new HashMap<String, String>();
        role2.put((String)"NameSpace", KPMENamespace.KPME_HR.getNamespaceCode());
        role2.put((String)"Role", KPMERole.HR_DEPARTMENT_ADMINISTRATOR.getRoleName());

        roles.add(role1);
        roles.add(role2);

        for (Map<String, String> role : roles)
        {
            List<RoleMember> rms = rs.getRoleMembers(role.get("NameSpace"), role.get("Role"), new HashMap<String, String>(), DateTime.now(), true);
            if (!rms.isEmpty())
            {
                for (RoleMember rm: rms)
                {
                    if ( ( rm.getType() == MemberType.PRINCIPAL) && (StringUtils.equals(rm.getMemberId(), principalId)) )
                    {
                        if (rm.getAttributes().containsKey(KPMEConstants.CommonElements.GROUP_KEY_CODE))
                        {
                            List<KeyValue> keyValues = new ArrayList<KeyValue>();
                            keyValues.add(new ConcreteKeyValue(rm.getAttributes().get(KPMEConstants.CommonElements.GROUP_KEY_CODE), rm.getAttributes().get(KPMEConstants.CommonElements.GROUP_KEY_CODE) ));
                            return keyValues;
                        }
                    }
                }
            }
        }

        return super.getKeyValues(model);
    }
}
