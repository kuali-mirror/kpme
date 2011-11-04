package org.kuali.hr.time.mobile.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;

@WebService(name = "TkMobileService", targetNamespace = "http://service.mobile.time.hr.kuali.org/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TkMobileService {
    public String getClockEntryInfo(@WebParam(name = "principalId") String principalId);

    public HashMap<String, List<String>> addClockAction(String principalId, String assignmentKey, String clockAction);
}
