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
package org.kuali.kpme.core.util;

import org.kuali.rice.krad.lookup.LookupInputField;

/**
 * Created by mlemons on 5/21/14.
 */
public class GroupKeyLookupInputField extends LookupInputField  implements SingleGroupKeyField {
    String singleGroupKey = null;
    public void singleGroupKeyCheck()
    {
        if (TKUtils.singleGroupKeyExists())
        {
            singleGroupKey = TKUtils.getSingleGroupKey();
        }
    }


    public GroupKeyLookupInputField() {
        super();

        singleGroupKeyCheck();
    }

    public boolean isReadOnly() {
        if (TKUtils.singleGroupKeyExists())
        {
            return true;
        }

        return super.isReadOnly();
    }

    public String getForcedValue() {
        if (TKUtils.singleGroupKeyExists())
        {
            return singleGroupKey;
        }

        return super.getForcedValue();
    }

    public boolean isAddHiddenWhenReadOnly() {
        if (TKUtils.singleGroupKeyExists())
        {
            return true;
        }

        return super.isAddHiddenWhenReadOnly();
    }

    public Object getDefaultValue() {
        if (TKUtils.singleGroupKeyExists())
        {
            return singleGroupKey;
        }

        return super.getDefaultValue();
    }
}
