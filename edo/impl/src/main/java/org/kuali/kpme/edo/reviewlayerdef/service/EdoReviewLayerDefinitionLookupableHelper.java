package org.kuali.kpme.edo.reviewlayerdef.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.math.BigDecimal;
import java.util.*;

public class EdoReviewLayerDefinitionLookupableHelper extends KualiLookupableHelperServiceImpl {

    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
        List<HtmlData> customActionUrls = new ArrayList<HtmlData>();
        List<HtmlData> defaultCustomActionUrls = super.getCustomActionUrls(businessObject, pkNames);

        boolean superUser = EdoUser.getCurrentTargetRoles().contains(EdoConstants.ROLE_SUPER_USER);

        for (HtmlData defaultCustomActionUrl : defaultCustomActionUrls){
            if (StringUtils.equals(defaultCustomActionUrl.getMethodToCall(), "edit")) {
                if (superUser) {
                    customActionUrls.add(defaultCustomActionUrl);
                }
            } else {
                customActionUrls.add(defaultCustomActionUrl);
            }
        }

        EdoReviewLayerDefinitionBo edoReviewLayerDefinition = (EdoReviewLayerDefinitionBo) businessObject;
        String reviewLayerDefinitionId = edoReviewLayerDefinition.getEdoReviewLayerDefinitionId();

        Properties params = new Properties();
        params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
        params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
        params.put("reviewLayerDefinitionId", reviewLayerDefinitionId.toString());
        AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
        viewUrl.setDisplayText("view");
        viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
        customActionUrls.add(viewUrl);

        return customActionUrls;
    }

    /*
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        String nodeName = fieldValues.get("nodeName");
        String voteType = fieldValues.get("voteType");
        String reviewLetter = fieldValues.get("reviewLetter");

        return EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions(nodeName, voteType, reviewLetter);
    }*/
}
