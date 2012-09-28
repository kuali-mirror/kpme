/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TagSupport {

    public Map<String, String> getDocumentStatus() {
        return TkConstants.DOCUMENT_STATUS;
    }

    private List<String> ipAddresses = new LinkedList<String>();

    public List<String> getIpAddresses() {
        ipAddresses.add("127.0.0.1");
        ipAddresses.add("129.79.23.203");
        ipAddresses.add("129.79.23.59");
        ipAddresses.add("129.79.23.123");

        return ipAddresses;
    }
}
