package org.kuali.hr.time.workarea.web;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class WorkAreaRoleValuesFinder extends KeyValuesBase {
    
    static final List<KeyLabelPair> labels = new ArrayList<KeyLabelPair>(2);
    static {
	labels.add(new KeyLabelPair(TkRoleAssign.TK_APPROVER, "Approver"));
	labels.add(new KeyLabelPair(TkRoleAssign.TK_ORG_ADMIN, "Org Admin"));
    }

    @Override
    public List<KeyLabelPair> getKeyValues() {
	return labels;
    }

}
