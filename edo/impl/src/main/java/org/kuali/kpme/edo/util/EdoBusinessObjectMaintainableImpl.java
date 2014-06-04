package org.kuali.kpme.edo.util;

import org.joda.time.DateTime;
import org.kuali.kpme.edo.EdoBusinessObject;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;

import java.math.BigDecimal;
import java.util.Date;

public abstract class EdoBusinessObjectMaintainableImpl extends KualiMaintainableImpl {
    protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EdoBusinessObjectMaintainableImpl.class);
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void saveBusinessObject() {
        EdoBusinessObject edoObj = (EdoBusinessObject) this.getBusinessObject();

        Date updateDate = DateTime.now().toDate();

        //If this is a new doc.
        if(edoObj.getId()==null) {
            edoObj.setCreateDate(updateDate);
            edoObj.setCreatedBy(GlobalVariables.getUserSession().getPrincipalName());
        }

        edoObj.setLastUpdated(updateDate);
        edoObj.setUpdatedBy(GlobalVariables.getUserSession().getPrincipalName());

        customSaveLogic(edoObj);
        KRADServiceLocator.getBusinessObjectService().save(edoObj);
//        KRADServiceLocatorWeb.getLegacyDataAdapter().save(edoObj);
    }

    public abstract EdoBusinessObject getObjectById(BigDecimal id);
    public void customSaveLogic(EdoBusinessObject edoObj){};
}
