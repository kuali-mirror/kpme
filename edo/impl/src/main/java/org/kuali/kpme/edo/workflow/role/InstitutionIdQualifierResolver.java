package org.kuali.kpme.edo.workflow.role;

import org.kuali.kpme.edo.permission.EDOKimAttributes;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kns.workflow.attribute.QualifierResolverBase;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstitutionIdQualifierResolver extends QualifierResolverBase {

	@Override
	public List<Map<String, String>> resolve(RouteContext context) {
		Map<String, String> qualifiers = new HashMap<String, String>();
		qualifiers.put(EDOKimAttributes.EDO_INSTITUTION_ID, "IU");
        return Collections.singletonList(qualifiers);
	}

}