package org.kuali.hr.time.timeblock.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class TimeBlockLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final String DOC_ID = "documentId";
	static final String DOC_STATUS_ID = "timesheetDocumentHeader.documentStatus";
	static final String BEGIN_DATE_ID = "beginDate";
		
	 @Override
    public List<? extends BusinessObject> getSearchResults(java.util.Map<String, String> fieldValues) {
	 
		 String docStatus = "", beginDateString="";

		 if(fieldValues.containsKey(DOC_STATUS_ID)){
				docStatus = fieldValues.get(DOC_STATUS_ID);
				fieldValues.remove(DOC_STATUS_ID);
			}
		 if(fieldValues.containsKey(BEGIN_DATE_ID)){
			 	beginDateString = fieldValues.get(BEGIN_DATE_ID);
				fieldValues.remove(BEGIN_DATE_ID);
			}
        
        List<? extends BusinessObject> objectList = super.getSearchResults(fieldValues);
      
        if(!objectList.isEmpty()) {
        	Iterator<? extends BusinessObject> itr = objectList.iterator();
			while(itr.hasNext()){
				TimeBlock tb = (TimeBlock)itr.next();
				List<TkRole> tkRoles = TkServiceLocator.getTkRoleService().getRoles(TKContext.getPrincipalId(), TKUtils.getCurrentDate());
				Job job = TkServiceLocator.getJobService().getJob(tb.getUserPrincipalId(), tb.getJobNumber(), TKUtils.getCurrentDate(), false);
				boolean valid = false;
				for (TkRole tkRole : tkRoles) {
					if (StringUtils.equals(tkRole.getRoleName(),
							TkConstants.ROLE_TK_SYS_ADMIN)
							|| (StringUtils.equals(tkRole.getRoleName(), TkConstants.ROLE_TK_GLOBAL_VO))
							|| (StringUtils.equals(tkRole.getRoleName(),
									TkConstants.ROLE_TK_APPROVER) && tb
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
				if(StringUtils.isNotEmpty(docStatus)) {
					if(tb.getTimesheetDocumentHeader() == null) {
						itr.remove();
						continue;
					} else {
						if(tb.getTimesheetDocumentHeader().getDocumentStatus() != null) {
							if(!tb.getTimesheetDocumentHeader().getDocumentStatus().equals(docStatus)){
								itr.remove();
								continue;
							}
						} else {
							itr.remove();
							continue;
						}
					}
				}
								
				if(StringUtils.isNotEmpty(beginDateString)) {
					if(tb.getBeginDate() != null) {
						if(!this.checkDate(tb, tb.getBeginDate(), beginDateString)) {
							itr.remove();
							continue;
						} 
					} else {
						itr.remove();
						continue;
					}
				}				
			}
        }
        return objectList;
	 }
	 
	 public boolean checkDate(TimeBlock tb, Date asOfDate, String dateString) {
		 if(tb.getTimesheetDocumentHeader() == null) {
				return false;
		 }
		try {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date dateFrom;
			Date dateTo;
			String subDateString;
			if(dateString.indexOf("..") == 10) {
				subDateString= dateString.substring(0, 10);
				dateFrom = df.parse(subDateString);
				subDateString= dateString.substring(12, dateString.length());
				dateTo = df.parse(subDateString);
				if(asOfDate != null) {
					if(!( (asOfDate.after(dateFrom) || asOfDate.equals(dateFrom))
							&& (asOfDate.before(dateTo) || asOfDate.equals(dateTo)))) {
						return false;
					}
				} else {
					return false;
				}
			} else{
				subDateString= dateString.substring(2, dateString.length());
				dateTo = df.parse(subDateString);
				if(asOfDate != null) {
					if( (dateString.startsWith(">=") && asOfDate.before(dateTo))
							|| (dateString.startsWith("<=") && asOfDate.after(dateTo))) {
						return false;
					}
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
		}
	  return true;
	 }
	 
	@SuppressWarnings("unchecked")
	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		List<HtmlData> overrideUrls = new ArrayList<HtmlData>();
		for(HtmlData actionUrl : customActionUrls){
			if(!StringUtils.equals(actionUrl.getMethodToCall(), "copy")){
				overrideUrls.add(actionUrl);
			}
		}
		return overrideUrls;
	}
}
