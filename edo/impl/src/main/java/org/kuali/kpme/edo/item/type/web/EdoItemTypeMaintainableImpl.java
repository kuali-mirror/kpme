package org.kuali.kpme.edo.item.type.web;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.edo.item.type.EdoItemTypeBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;

public class EdoItemTypeMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return EdoItemTypeBo.from(EdoServiceLocator.getEdoItemTypeService().getItemType(id));
	}
}
