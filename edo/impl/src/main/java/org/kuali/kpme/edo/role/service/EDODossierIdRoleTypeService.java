package org.kuali.kpme.edo.role.service;

import org.kuali.kpme.edo.permission.EDOKimAttributes;
import org.kuali.rice.kns.kim.role.RoleTypeServiceBase;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Collections;
import java.util.List;
@WebService(name = "edoDossierIdRoleTypeService", targetNamespace = "EDO")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)


public class EDODossierIdRoleTypeService extends RoleTypeServiceBase {
	@Override
	public List<String> getQualifiersForExactMatch() {
		return Collections.singletonList(EDOKimAttributes.EDO_DOSSIER_ID);
	}

}