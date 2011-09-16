package org.kuali.hr.time.util;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TagSupport {

    private static final Logger LOG = Logger.getLogger(TagSupport.class);
    private List<String> ipAddresses = new LinkedList<String>();

    public List<String> getIpAddresses() {
        ipAddresses.add("127.0.0.1");
        ipAddresses.add("129.79.23.203");
        ipAddresses.add("129.79.23.59");
        ipAddresses.add("129.79.23.123");

        return ipAddresses;
    }

    public Map<String, String> getDocumentStatus() {
        return TkConstants.DOCUMENT_STATUS;
    }
}
