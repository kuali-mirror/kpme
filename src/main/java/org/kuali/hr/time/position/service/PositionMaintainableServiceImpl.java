package org.kuali.hr.time.position.service;

import java.sql.Timestamp;

import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class PositionMaintainableServiceImpl extends KualiMaintainableImpl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		Position position = (Position)this.getBusinessObject();
		if(position.getHrPositionId()!=null && position.isActive()){
			Position oldPosition = TkServiceLocator.getPositionService().getPosition(position.getHrPositionId());
			if(position.getEffectiveDate().equals(oldPosition.getEffectiveDate())){
				position.setTimestamp(null);
			} else{
				if(oldPosition!=null){
					oldPosition.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldPosition.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(System.currentTimeMillis())));
					oldPosition.setEffectiveDate(position.getEffectiveDate());
					KNSServiceLocator.getBusinessObjectService().save(oldPosition);
				}
				position.setTimestamp(new Timestamp(System.currentTimeMillis()));
				position.setHrPositionId(null);
			}
		}
		
		KNSServiceLocator.getBusinessObjectService().save(position);
	}

}
