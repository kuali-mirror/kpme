package org.kuali.kpme.core.util;

/**
 * Created by mlemons on 5/22/14.
 */
/*
    public static final String  KPME_GROUPKEY_DEFAULT = "kpme.core.groupkey.default";
    String defaultHrKeySetting = ConfigContext.getCurrentContextConfig().getProperty(KPME_GROUPKEY_DEFAULT);
 */

public interface SingleGroupKeyField {
    public void singleGroupKeyCheck();
}
