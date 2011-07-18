package org.kuali.hr.location.service;

import java.sql.Timestamp;

import org.kuali.hr.job.Job;
import org.kuali.hr.location.Location;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class LocationMaintainableServiceImpl extends KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		Location location = (Location) this.getBusinessObject();
		
		//Inactivate the old assignment as of the effective date of new assignment
		if(location.getHrLocationId()!=null && location.isActive()){
			Location oldLocation = TkServiceLocator.getLocationService().getLocation(location.getHrLocationId());
			if(location.getEffectiveDate().equals(oldLocation.getEffectiveDate())){
				location.setTimestamp(null);
			} else{
				if(oldLocation!=null){
					oldLocation.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldLocation.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldLocation.setEffectiveDate(location.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldLocation);
				}
				location.setTimestamp(new Timestamp(System.currentTimeMillis()));
				location.setHrLocationId(null);
			}
		}
		
		KNSServiceLocator.getBusinessObjectService().save(location);

	}
}
