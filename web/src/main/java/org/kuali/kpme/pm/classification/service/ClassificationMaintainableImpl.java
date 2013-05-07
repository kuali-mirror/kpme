package org.kuali.kpme.pm.classification.service;

import java.util.Collection;
import java.util.List;

import org.kuali.rice.krad.maintenance.MaintainableImpl;

public class ClassificationMaintainableImpl extends MaintainableImpl {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void addLine(Collection<Object> collection, Object addLine, boolean insertFirst) {
        if (insertFirst && (collection instanceof List)) {
            ((List) collection).add(0, addLine);
        } else {
            collection.add(addLine);
        }
    }

}
