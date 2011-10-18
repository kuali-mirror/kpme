package org.kuali.hr.time.mobile.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


@WebService(name = "tkMobileService", targetNamespace = "http://kpme.kuali.org/wsdl/tkMobileService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TkMobileService {
	public String getClockEntryInfo(String principalId);
	public String addClockAction(String principalId, String assignmentKey, String clockAction);
}
