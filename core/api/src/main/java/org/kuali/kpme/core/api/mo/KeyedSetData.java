package org.kuali.kpme.core.api.mo;

import java.util.Set;

import org.kuali.kpme.core.api.groupkey.HrGroupKeyContract;

public interface KeyedSetData {
	
	Set<String> getGroupKeyCodeSet();    
	Set<? extends HrGroupKeyContract> getGroupKeySet();

}