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
package org.kuali.hr.time.roles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

// chen, KPME-938, implement the view link on lookup page
public class TkRoleGroupInquirableImpl extends KualiInquirableImpl {

	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		TkRoleGroup tkRoleGroup = (TkRoleGroup) super.getBusinessObject(fieldValues);
		
		List<Job> jobs = TkServiceLocator.getJobService().getJobs(tkRoleGroup.getPrincipalId(), TKUtils.getCurrentDate());
		List<TkRole> positionRoles = new ArrayList<TkRole>();
		List<TkRole> inactivePositionRoles = new ArrayList<TkRole>();
		Set<String> positionNumbers = new HashSet<String>(); 
		for(Job job : jobs){
			positionNumbers.add(job.getPositionNumber());
		}
		for(String pNo : positionNumbers){
			TkRole positionRole = TkServiceLocator.getTkRoleService().getRolesByPosition(pNo);
			if(positionRole != null)
				positionRoles.add(positionRole);
			TkRole inactivePositionRole = TkServiceLocator.getTkRoleService().getInactiveRolesByPosition(pNo);
			if(inactivePositionRole != null)
				inactivePositionRoles.add(inactivePositionRole);
		}
		tkRoleGroup.setInactivePositionRoles(inactivePositionRoles);
		tkRoleGroup.setPositionRoles(positionRoles);
		
		List<TkRole> tkRoles = TkServiceLocator.getTkRoleService().getRoles(tkRoleGroup.getPrincipalId(), TKUtils.getCurrentDate());
		List<TkRole> tkInActiveRoles = TkServiceLocator.getTkRoleService().getInactiveRoles(tkRoleGroup.getPrincipalId(), TKUtils.getCurrentDate());
		Iterator<TkRole> itr = tkRoles.iterator();
		// remove position roles from the active/inactive roles lists
		while (itr.hasNext()) {
			TkRole tkRole = (TkRole) itr.next();
			if(tkRoleGroup.getPositionRoles()!=null && tkRoleGroup.getPositionRoles().contains(tkRole)){
				itr.remove();
			}
		}
		itr = tkInActiveRoles.iterator();
		while (itr.hasNext()) {
			TkRole tkRole = (TkRole) itr.next();
			if(tkRoleGroup.getPositionRoles()!=null && tkRoleGroup.getPositionRoles().contains(tkRole)){
				itr.remove();
			}
		}
		tkRoleGroup.setRoles(tkRoles);
		tkRoleGroup.setInactiveRoles(tkInActiveRoles);

		return tkRoleGroup;
	}
}
