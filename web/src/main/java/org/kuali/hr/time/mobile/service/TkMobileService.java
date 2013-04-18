/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.mobile.service;

import org.kuali.rice.core.api.util.jaxb.MultiValuedStringMapAdapter;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@WebService(name = "TkMobileService", targetNamespace = "http://service.mobile.time.hr.kuali.org/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TkMobileService {
    public String getClockEntryInfo(@WebParam(name = "principalId") String principalId);

    @XmlJavaTypeAdapter(MultiValuedStringMapAdapter.class)
    public Map<String, List<String>> addClockAction(@WebParam(name = "principalId") String principalId, @WebParam(name = "assignmentKey") String assignmentKey, @WebParam(name = "clockAction") String clockAction);
}
