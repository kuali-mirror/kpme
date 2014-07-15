/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.edo.candidate;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class EdoCandidateCurrentRankValuesFinder extends KeyValuesBase {

	private static final long serialVersionUID = -8292128951407749052L;
	
	private static final List<KeyValue> EDO_CANDIDATE_CURRENT_RANK_KEY_VALUES = new ArrayList<KeyValue>();
	
	static {
		EDO_CANDIDATE_CURRENT_RANK_KEY_VALUES.add(new ConcreteKeyValue("Assistant Professor", "Assistant Professor"));
		EDO_CANDIDATE_CURRENT_RANK_KEY_VALUES.add(new ConcreteKeyValue("Associate Professor", "Associate Professor"));
		EDO_CANDIDATE_CURRENT_RANK_KEY_VALUES.add(new ConcreteKeyValue("Professor", "Professor"));
	}

	@Override
	public List<KeyValue> getKeyValues() {
		return EDO_CANDIDATE_CURRENT_RANK_KEY_VALUES;
	}
	
}