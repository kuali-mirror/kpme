package org.kuali.kpme.pm.position;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.pm.PMConstants;
import org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class PositionQualifierValueKeyValueFinder  extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 1L;

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model;
		List<KeyValue> options = new ArrayList<KeyValue>();

		PositionQualification aQualification = (PositionQualification) docForm.getNewCollectionLines().get("document.newMaintainableObject.dataObject.qualificationList");
		if(aQualification != null) {
			String aTypeId = aQualification.getQualificationType();
			PstnQlfrType aTypeObj = PmServiceLocator.getPstnQlfrTypeService().getPstnQlfrTypeById(aTypeId);
			if(aTypeObj != null) {
				if(aTypeObj.getTypeValue().equals(PMConstants.PSTN_QLFR_TYPE_VALUE.SELECT)){
					String[] aCol = aTypeObj.getSelectValues().split(",");
					for(String aString : aCol){
						options.add(new ConcreteKeyValue(aString, aString));
					}
				} else{
					return new ArrayList<KeyValue>();
				}
			}
		}
        return options;
	}
	

}
