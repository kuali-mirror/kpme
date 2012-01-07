package org.kuali.hr.time.mobile.service;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "TkMobileService", targetNamespace = "http://service.mobile.time.hr.kuali.org/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TkMobileService {
    public String getClockEntryInfo(@WebParam(name = "principalId") String principalId);

    public HashMap<String, List<String>> addClockAction(@WebParam(name = "principalId") String principalId, @WebParam(name = "assignmentKey") String assignmentKey, @WebParam(name = "clockAction") String clockAction);
}
