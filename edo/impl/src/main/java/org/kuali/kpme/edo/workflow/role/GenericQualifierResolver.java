package org.kuali.kpme.edo.workflow.role;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.kpme.edo.permission.EDOKimAttributes;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoPropertyConstants;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kns.workflow.attribute.QualifierResolverBase;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericQualifierResolver extends QualifierResolverBase {

    static final Logger LOG = Logger.getLogger(GenericQualifierResolver.class);

    @Override
    public List<Map<String, String>> resolve(RouteContext context) {
        Map<String, String> qualifiers = new HashMap<String, String>();
        DocumentRouteHeaderValue docHeader = context.getDocument();
        String levelName = docHeader.getCurrentRouteLevelName();

        String documentContent = context.getDocumentContent().getDocContent();

        String workflowId = StringUtils.substringBetween(documentContent, "<workflowId>","</workflowId");

        String qualifier = EdoServiceLocator.getEdoReviewLayerDefinitionService().getLevelQualifierByWorkflowId(workflowId, levelName);

        if (StringUtils.isNotBlank(qualifier)) {
            LOG.info("Level is " + levelName + "; qualifier is " + qualifier);

            if (qualifier.equals(EdoPropertyConstants.EdoReviewLayerDefinitionFields.SCHOOL_ID)) {
                String schoolId = StringUtils.substringBetween(documentContent, "<schoolId>", "</schoolId>");
                if(StringUtils.isNotBlank(schoolId)) {
                    qualifiers.put(EDOKimAttributes.EDO_SCHOOL_ID, schoolId);
                }
            }
            if (qualifier.equals(EdoPropertyConstants.EdoReviewLayerDefinitionFields.DEPARTMENT_ID)) {
                String departmentId = StringUtils.substringBetween(documentContent, "<departmentId>", "</departmentId>");
                if(StringUtils.isNotBlank(departmentId)) {
                    qualifiers.put(EDOKimAttributes.EDO_DEPARTMENT_ID, departmentId);
                }
            }
            if (qualifier.equals(EdoPropertyConstants.EdoReviewLayerDefinitionFields.CAMPUS_ID)) {
                String campusId = StringUtils.substringBetween(documentContent, "<campusId>", "</campusId>");
                if(StringUtils.isNotBlank(campusId)) {
                    qualifiers.put(EDOKimAttributes.EDO_CAMPUS_ID, campusId);
                }
            }
        }
        return Collections.singletonList(qualifiers);
    }

}
