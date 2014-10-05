package org.kuali.kpme.edo.vote.type;

import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

public class EdoVoteTypeValuesFinder extends KeyValuesBase {

    static final List<KeyValue> labels = new ArrayList<KeyValue>(EdoConstants.VOTE_TYPES.size());
    static {
        for (String voteType : EdoConstants.VOTE_TYPES) {
            labels.add(new ConcreteKeyValue(voteType, voteType));
        }
    }

    @Override
    public List<KeyValue> getKeyValues() {
        return labels;
    }
}
