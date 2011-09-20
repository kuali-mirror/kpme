package org.kuali.hr.time.position.service;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class PositionMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Override
    public HrBusinessObject getObjectById(Long id) {
        return TkServiceLocator.getPositionService().getPosition(id);
    }

    @Override
    public void saveBusinessObject() {
        Position position = (Position) this.getBusinessObject();
        String nextUniqueNumber = TkServiceLocator.getPositionService().getNextUniquePositionNumber();
        position.setPositionNumber(nextUniqueNumber);

        KNSServiceLocator.getBusinessObjectService().save(position);

        TkServiceLocator.getPositionService().updatePositionNumber(nextUniqueNumber);
    }
}
