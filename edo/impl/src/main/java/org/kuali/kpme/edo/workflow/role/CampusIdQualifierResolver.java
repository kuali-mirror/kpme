package org.kuali.kpme.edo.workflow.role;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.permission.EDOKimAttributes;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kns.workflow.attribute.QualifierResolverBase;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampusIdQualifierResolver extends QualifierResolverBase {

	@Override
	public List<Map<String, String>> resolve(RouteContext context) {
		Map<String, String> qualifiers = new HashMap<String, String>();
		String documentContent = context.getDocumentContent().getDocContent();
		String campusId = StringUtils.substringBetween(documentContent, "<campusId>", "</campusId>");
		if(StringUtils.isNotBlank(campusId)) {
			qualifiers.put(EDOKimAttributes.EDO_CAMPUS_ID, campusId);
		}
        return Collections.singletonList(qualifiers);
	}
}