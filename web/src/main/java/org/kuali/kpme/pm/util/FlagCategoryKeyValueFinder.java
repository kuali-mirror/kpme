package org.kuali.kpme.pm.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.classification.Classification;
import org.kuali.kpme.pm.positionflag.PositionFlag;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class FlagCategoryKeyValueFinder extends UifKeyValuesFinderBase {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public List<KeyValue> getKeyValues() {		
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		List<String> categories = PmServiceLocator.getPositionFlagService().getAllActiveFlagCategories();
		keyValues.add(new ConcreteKeyValue("", "Select category to see flags"));
		if(CollectionUtils.isNotEmpty(categories)) {
			for(String aCategory : categories) {
				keyValues.add(new ConcreteKeyValue(aCategory, aCategory));
			}
		}         
		return keyValues;
	}
	
	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> options = new ArrayList<KeyValue>();
		MaintenanceDocumentForm docForm = (MaintenanceDocumentForm) model; 
		Classification cf = (Classification) docForm.getDocument().getNewMaintainableObject().getDataObject();
		if(cf.getEffectiveDate() != null) {
			List<PositionFlag> flagList = PmServiceLocator.getPositionFlagService().getAllActivePositionFlags(null, null, cf.getEffectiveLocalDate());
			options.add(new ConcreteKeyValue("", "Select category to see flags"));
			if(CollectionUtils.isNotEmpty(flagList)) {
				for(PositionFlag aFlag : flagList) {
					options.add(new ConcreteKeyValue((String) aFlag.getCategory(), (String) aFlag.getCategory()));
				}
			}         
		} else {
			options = this.getKeyValues();
		}
		
        return options;
    }

}
