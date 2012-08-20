package org.kuali.hr.lookup;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.lookup.KualiLookupableImpl;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.Properties;


public class KPMELookupableImpl extends KualiLookupableImpl {

    /**
     * @see org.kuali.rice.kns.lookup.Lookupable#getCreateNewUrl()
     */
    @Override
    public String getCreateNewUrl() {
        String url = "";

        if (getLookupableHelperService().allowsMaintenanceNewOrCopyAction()) {
            Properties parameters = new Properties();
            parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
            parameters.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, this.businessObjectClass.getName());
            if (StringUtils.isNotBlank(getReturnLocation())) {
                parameters.put(KRADConstants.RETURN_LOCATION_PARAMETER, getReturnLocation());
            }
            url = UrlFactory.parameterizeUrl(KRADConstants.MAINTENANCE_ACTION, parameters);
            url = "<a title=\"Create a new record\" href=\"" + url + "\"><img src=\"images/tinybutton-createnew.gif\" alt=\"create new\" width=\"70\" height=\"15\"/></a>";
        }

        return url;
    }
}
