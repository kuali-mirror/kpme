package org.kuali.kpme.edo.role.service;

import java.util.Collections;
import java.util.List;

import org.kuali.kpme.edo.permission.EDOKimAttributes;
import org.kuali.rice.kns.kim.role.RoleTypeServiceBase;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "edoCommitteeChairDelegateRoleTypeService", targetNamespace = "EDO")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public class EDOCommitteeChairDelegateRoleTypeService extends RoleTypeServiceBase {
	@Override
	public List<String> getQualifiersForExactMatch() {
		return Collections.singletonList(EDOKimAttributes.ROLE_CHAIR_DELEGATE_ID);
	}
	
	
}
