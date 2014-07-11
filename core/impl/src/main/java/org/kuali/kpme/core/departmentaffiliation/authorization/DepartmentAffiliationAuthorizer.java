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
