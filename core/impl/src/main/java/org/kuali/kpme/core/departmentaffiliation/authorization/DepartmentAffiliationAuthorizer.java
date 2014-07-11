package org.kuali.kpme.core.departmentaffiliation.authorization;

import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.authorization.KPMEMaintenanceDocumentViewAuthorizer;
import org.kuali.rice.kim.api.identity.Person;

/**
 * Created by mlemons on 7/10/14.
 */
public class DepartmentAffiliationAuthorizer  extends KPMEMaintenanceDocumentViewAuthorizer {

    @Override
    public boolean canMaintain(Object dataObject, Person user) {
        return super.canMaintain(dataObject, user);
    }

    @Override
    public boolean canCreate(Class boClass, Person user) {
        return super.canCreate(boClass, user);
    }
}
