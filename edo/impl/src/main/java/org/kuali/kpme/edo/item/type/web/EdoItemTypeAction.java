package org.kuali.kpme.edo.item.type.web;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.api.item.type.EdoItemType;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.item.type.EdoItemTypeBo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
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
        EdoItemType.Builder itemTypeObj = EdoItemType.Builder.create();
        String itemTypeListJSON = "";

        // prepare the item type list
        List<EdoItemType> itemTypeList = ((EdoItemTypeForm)form).getItemTypeList();

        for (EdoItemType itemType : itemTypeList) {
            itemTypeListJSON = itemTypeListJSON.concat(EdoServiceLocator.getEdoItemTypeService().getEdoItemTypeJSONString(itemType) + ",");
            															
        }
        request.setAttribute("itemTypesJSON", itemTypeListJSON);

        itemTypeObj.setEdoItemTypeID(((EdoItemTypeForm) form).getEdoItemTypeID());
        itemTypeObj.setItemTypeExtAvailable( ((EdoItemTypeForm) form).isItemTypeExtAvailability() );
        itemTypeObj.setItemTypeDescription(((EdoItemTypeForm) form).getItemTypeDescription());
        itemTypeObj.setItemTypeName(((EdoItemTypeForm) form).getItemTypeName());
        itemTypeObj.setItemTypeInstructions(((EdoItemTypeForm) form).getItemTypeInstructions());
        itemTypeObj.setUserPrincipalId(((EdoItemTypeForm) form).getUpdatedBy()+"");
        itemTypeObj.setCreateTime(DateTime.parse(((EdoItemTypeForm) form).getLastUpdated()));

        if (itemTypeObj.getEdoItemTypeID() != null) {
            if (itemTypeObj.getEdoItemTypeID().equals("0")) {
                itemTypeObj.setEdoItemTypeID(null);
            } 
            // update the last-updated field
            // itemTypeObj.setLastUpdated( new Date() );
            // and save it to the database
            EdoServiceLocator.getEdoItemTypeService().saveOrUpdate(itemTypeObj.build());
        }

        //prepare the add form, if necessary

        if (request.getParameter("itid") != null) {
            itemTypeID = request.getParameter("itid").toString();
            
            // TODO Make sure the code below works with EdoItemType
            // itemTypeObj = ((EdoItemTypeForm)form).getItemType(BigDecimal.valueOf(Integer.parseInt(itemTypeID)));
            EdoItemType edoItemType = ((EdoItemTypeForm)form).getItemType(itemTypeID);

            if (itemTypeObj != null) {
                // ((EdoItemTypeForm) form).lastUpdated = itemTypeObj.getLastUpdated();
                ((EdoItemTypeForm) form).itemTypeExtAvailable = edoItemType.isItemTypeExtAvailable();
                ((EdoItemTypeForm) form).itemTypeDescription = edoItemType.getItemTypeDescription();
                ((EdoItemTypeForm) form).itemTypeName =  edoItemType.getItemTypeName();
                ((EdoItemTypeForm) form).edoItemTypeID = edoItemType.getEdoItemTypeID();
                // TODO Make sure the code below works right - For example, EdoItemType only has user principal id, so
                // setting it to updatedBy and createdBy may not work correctly.  Same for lastUpdated and createDate.
                ((EdoItemTypeForm) form).lastUpdated =  DateFormat.getDateInstance(DateFormat.SHORT).format(edoItemType.getCreateTime());
                ((EdoItemTypeForm) form).updatedBy =  new BigDecimal(edoItemType.getUserPrincipalId());
                ((EdoItemTypeForm) form).createDate =  DateFormat.getDateInstance(DateFormat.SHORT).format(edoItemType.getCreateTime());
                ((EdoItemTypeForm) form).createdBy =  new BigDecimal(edoItemType.getUserPrincipalId());
            }
        }


        return super.execute(mapping, form, request, response);
    }
}
