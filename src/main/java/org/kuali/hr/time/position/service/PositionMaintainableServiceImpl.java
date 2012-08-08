package org.kuali.hr.time.position.service;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class PositionMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Override
    public HrBusinessObject getObjectById(String id) {
        return TkServiceLocator.getPositionService().getPosition(id);
    }

    @Override
    public void saveBusinessObject() {
        Position position = (Position) this.getBusinessObject();
        //String nextUniqueNumber = TkServiceLocator.getPositionService().getNextUniquePositionNumber();
        //position.setPositionNumber(nextUniqueNumber);

        position = KRADServiceLocator.getBusinessObjectService().save(position);
        System.out.print(position);
    }
}
