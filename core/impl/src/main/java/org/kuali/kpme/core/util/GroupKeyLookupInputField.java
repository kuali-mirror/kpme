package org.kuali.kpme.core.util;

/**
 * Created by mlemons on 5/21/14.
 */
public class GroupKeyLookupInputField extends org.kuali.rice.krad.uif.field.LookupInputField  implements SingleGroupKeyField {
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

    public String getDefaultValue() {
        if (TKUtils.singleGroupKeyExists())
        {
            return singleGroupKey;
        }

        return super.getDefaultValue();
    }
}
