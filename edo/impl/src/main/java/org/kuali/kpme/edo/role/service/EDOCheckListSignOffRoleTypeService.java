package org.kuali.kpme.edo.role.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.kns.kim.role.RoleTypeServiceBase;
@WebService(name = "edoCheckListSignOffRoleTypeService", targetNamespace = "EDO")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)


public class EDOCheckListSignOffRoleTypeService extends RoleTypeServiceBase {

}
