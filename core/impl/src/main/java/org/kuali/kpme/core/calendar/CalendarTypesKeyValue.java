/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.core.calendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kns.util.KNSGlobalVariables;
import org.kuali.rice.kns.web.struts.form.KualiForm;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;


public class CalendarTypesKeyValue extends KeyValuesBase {

	@SuppressWarnings("unchecked")
	@Override
	public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("Pay", "Pay"));
        keyValues.add(new ConcreteKeyValue("Leave", "Leave"));

        KualiForm form = KNSGlobalVariables.getKualiForm();
        if ((form != null) && (form instanceof LookupForm)) {
            keyValues.add(new ConcreteKeyValue("", "Both"));
        }

        return keyValues;
	}
}
