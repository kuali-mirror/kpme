package org.kuali.kpme.edo.item.type.web;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.item.type.EdoItemType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoItemTypeAction extends EdoAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String itemTypeID = null;
        EdoItemType itemTypeObj = new EdoItemType();
        String itemTypeListJSON = "";

        // prepare the item type list
        List<EdoItemType> itemTypeList = ((EdoItemTypeForm)form).getItemTypeList();

        for ( EdoItemType itemType : itemTypeList ) {
            itemTypeListJSON = itemTypeListJSON.concat(itemType.getEdoItemTypeJSONString() + ",");
        }
        request.setAttribute("itemTypesJSON", itemTypeListJSON);

        itemTypeObj.setItemTypeID(((EdoItemTypeForm) form).getItemTypeID());
        itemTypeObj.setItemTypeExtAvailability( ((EdoItemTypeForm) form).getItemTypeExtAvailability() );
        itemTypeObj.setItemTypeDescription(((EdoItemTypeForm) form).getItemTypeDescription());
        itemTypeObj.setItemTypeName(((EdoItemTypeForm) form).getItemTypeName());
        itemTypeObj.setItemTypeInstructions(((EdoItemTypeForm) form).getItemTypeInstructions());
        itemTypeObj.setCreatedBy(((EdoItemTypeForm) form).getCreatedBy());
        itemTypeObj.setUpdatedBy(((EdoItemTypeForm) form).getUpdatedBy());
        itemTypeObj.setCreateDate(((EdoItemTypeForm) form).getCreateDate());
        itemTypeObj.setLastUpdated(((EdoItemTypeForm) form).getLastUpdated());


        if (itemTypeObj.getItemTypeID() != null) {
            if (itemTypeObj.getItemTypeID().equals(BigDecimal.ZERO)) {
                itemTypeObj.setItemTypeID(null);
            }
            // update the last-updated field
            itemTypeObj.setLastUpdated( new Date() );
            // and save it to the database
            EdoServiceLocator.getEdoItemTypeService().saveOrUpdate(itemTypeObj);
        }

        //prepare the add form, if necessary

        if ( request.getParameter("itid") != null ) {
            itemTypeID = request.getParameter("itid").toString();
            itemTypeObj = ((EdoItemTypeForm)form).getItemType(BigDecimal.valueOf(Integer.parseInt(itemTypeID)));
            if (itemTypeObj != null) {
                // ((EdoItemTypeForm) form).lastUpdated = itemTypeObj.getLastUpdated();
                ((EdoItemTypeForm) form).itemTypeExtAvailability = itemTypeObj.getItemTypeExtAvailability();
                ((EdoItemTypeForm) form).itemTypeDescription = itemTypeObj.getItemTypeDescription();
                ((EdoItemTypeForm) form).itemTypeName =  itemTypeObj.getItemTypeName();
                ((EdoItemTypeForm) form).itemTypeID = itemTypeObj.getItemTypeID();
                ((EdoItemTypeForm) form).lastUpdated =  itemTypeObj.getLastUpdated();
                ((EdoItemTypeForm) form).updatedBy =  itemTypeObj.getUpdatedBy();
                ((EdoItemTypeForm) form).createDate =  itemTypeObj.getCreateDate();
                ((EdoItemTypeForm) form).createdBy =  itemTypeObj.getCreatedBy();
            }
        }


        return super.execute(mapping, form, request, response);
    }
}
