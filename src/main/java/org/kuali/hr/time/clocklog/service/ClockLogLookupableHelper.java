package org.kuali.hr.time.clocklog.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class ClockLogLookupableHelper extends KualiLookupableHelperServiceImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		List<HtmlData> overrideUrls = new ArrayList<HtmlData>();
		
		//Add missed punch to the clock log if it has one
		ClockLog clockLog = (ClockLog)businessObject;
		MissedPunchDocument mpDoc = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(clockLog.getTkClockLogId());
		if(mpDoc != null){
			clockLog.setMissedPunchDocumentId(mpDoc.getDocumentNumber());
		}
		
		for(HtmlData actionUrl : customActionUrls){
			if(!StringUtils.equals(actionUrl.getMethodToCall(), "copy")){
				overrideUrls.add(actionUrl);
			}
		}
		if (TKContext.getUser().getCurrentRoles().isSystemAdmin() || TKContext.getUser().isGlobalViewOnly()) {
			clockLog = (ClockLog) businessObject;
			final String className = this.getBusinessObjectClass().getName();
			final Long tkClockLogId = clockLog.getTkClockLogId();
			HtmlData htmlData = new HtmlData() {

				@Override
				public String constructCompleteHtmlTag() {
					return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
							+ className
							+ "&methodToCall=start&tkClockLogId="
							+ tkClockLogId + "\">view</a>";
				}
			};
			overrideUrls.add(htmlData);
		} else if (overrideUrls.size() != 0) {
			overrideUrls.remove(0);
		}
		return overrideUrls;
	}

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		List<? extends BusinessObject> objectList = super
				.getSearchResults(fieldValues);

		if (!objectList.isEmpty()) {
			Iterator itr = objectList.iterator();
			while (itr.hasNext()) {
				ClockLog cl = (ClockLog) itr.next();
				List<TkRole> tkRoles = TkServiceLocator.getTkRoleService()
						.getRoles(TKContext.getPrincipalId(),
								TKUtils.getCurrentDate());
				Job job = TkServiceLocator.getJobSerivce().getJob(
						cl.getUserPrincipalId(), cl.getJobNumber(),
						TKUtils.getCurrentDate(), false);
				boolean valid = false;
				for (TkRole tkRole : tkRoles) {
					if (StringUtils.equals(tkRole.getRoleName(),
							TkConstants.ROLE_TK_SYS_ADMIN)
							|| (StringUtils.equals(tkRole.getRoleName(),
									TkConstants.ROLE_TK_APPROVER) && cl
									.getWorkArea().equals(tkRole.getWorkArea()))
							|| (StringUtils.equals(tkRole.getRoleName(),
									TkConstants.ROLE_TK_DEPT_ADMIN) && (job != null && (job
									.getDept().equals(tkRole.getDepartment()))))) {
						valid = true;
						break;
					}
					if(StringUtils.equals(tkRole.getRoleName(), TkConstants.ROLE_TK_LOCATION_ADMIN) && job != null && tkRole.getLocationObj()!=null){
						List<Department> departments = TkServiceLocator.getDepartmentService().getDepartmentByLocation(tkRole.getLocationObj().getLocation());
						for(Department department : departments){
							if(StringUtils.equals(job.getDept(), department.getDept())){
								valid = true;
								break;
							}
						}
						if(valid){
							break;
						}
					}
				}
				if (!valid) {
					itr.remove();
					continue;
				}
			}
		}
		return objectList;
	}
	
	
}
