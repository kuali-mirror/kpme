package org.kuali.kpme.core.util;

import org.kuali.rice.kns.datadictionary.FieldDefinition;

/**
 * Created by mlemons on 5/22/14.
 */

public class GroupKeyFieldDefinition extends  FieldDefinition  implements SingleGroupKeyField {
    String singleGroupKey = null;
    public void singleGroupKeyCheck()
    {
        if (TKUtils.singleGroupKeyExists())
        {
            singleGroupKey = TKUtils.getSingleGroupKey();
        }
    }

    public GroupKeyFieldDefinition()
    {
        super();

        singleGroupKeyCheck();
    }

    public String getDefaultValue()
    {
        if (TKUtils.singleGroupKeyExists())
        {
            return singleGroupKey;
        }
        return super.getDefaultValue();
    }

    public boolean isReadOnly()
    {
        if (TKUtils.singleGroupKeyExists())
        {
            return true;
        }
        return super.isReadOnly();
    }
}