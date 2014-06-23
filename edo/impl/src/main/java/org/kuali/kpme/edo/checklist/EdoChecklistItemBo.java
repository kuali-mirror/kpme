package org.kuali.kpme.edo.checklist;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItemContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 9:54 AM
 */
public class EdoChecklistItemBo extends HrBusinessObject implements EdoChecklistItemContract {

	private static final long serialVersionUID = -3737008004782110416L;
	
	static class KeyFields {
		private static final String EDO_CHECKLIST_ITEM = "checklistItemName";
	}
	
	private String edoChecklistItemID;
    private String edoChecklistSectionID;
    private String checklistItemName;
    private String itemDescription;
    private boolean required;
    private int checklistItemOrdinal;
    
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			.add(KeyFields.EDO_CHECKLIST_ITEM)
			.build();

    @Override
	public boolean isActive() {
		return super.isActive();
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);
	}

	@Override
	public String getObjectId() {
		return super.getObjectId();
	}

	@Override
	public Long getVersionNumber() {
		return super.getVersionNumber();
	}
	
	@Override
	public String getId() {
		return  getEdoChecklistItemID();
	}

	@Override
	public void setId(String edoChecklistItemID) {
		setEdoChecklistItemID(edoChecklistItemID);
	}

	@Override
	protected String getUniqueKey() {
		return getEdoChecklistItemID();
	}
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.EDO_CHECKLIST_ITEM, this.getChecklistItemName())
				.build();
	}

    public String getEdoChecklistItemID() {
        return edoChecklistItemID;
    }

    public void setEdoChecklistItemID(String edoChecklistItemID) {
        this.edoChecklistItemID = edoChecklistItemID;
    }

    public String getEdoChecklistSectionID() {
        return edoChecklistSectionID;
    }

    public void setEdoChecklistSectionID(String edoChecklistSectionID) {
        this.edoChecklistSectionID = edoChecklistSectionID;
    }

    public String getChecklistItemName() {
        return checklistItemName;
    }

    public void setChecklistItemName(String checklistItemName) {
        this.checklistItemName = checklistItemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getChecklistItemOrdinal() {
        return checklistItemOrdinal;
    }

    public void setChecklistItemOrdinal(int checklistItemOrdinal) {
        this.checklistItemOrdinal = checklistItemOrdinal;
    }
    
    public static EdoChecklistItemBo from(EdoChecklistItem im) {
        if (im == null) {
            return null;
        }
        EdoChecklistItemBo ecli = new EdoChecklistItemBo();
        ecli.setEdoChecklistItemID(im.getEdoChecklistItemID());
        ecli.setEdoChecklistSectionID(im.getEdoChecklistSectionID());
        ecli.setChecklistItemName(im.getChecklistItemName());
        ecli.setItemDescription(im.getItemDescription());
        ecli.setChecklistItemOrdinal(im.getChecklistItemOrdinal());

        // finally copy over the common fields into phra from im
        copyCommonFields(ecli, im);
     
        return ecli;
    } 
    
    public static EdoChecklistItem to(EdoChecklistItemBo bo) {
        if (bo == null) {
            return null;
        }
        return EdoChecklistItem.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoChecklistItemBo, EdoChecklistItem> toImmutable = new ModelObjectUtils.Transformer<EdoChecklistItemBo, EdoChecklistItem>() {
        public EdoChecklistItem transform(EdoChecklistItemBo input) {
            return EdoChecklistItemBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoChecklistItem, EdoChecklistItemBo> toBo = new ModelObjectUtils.Transformer<EdoChecklistItem, EdoChecklistItemBo>() {
        public EdoChecklistItemBo transform(EdoChecklistItem input) {
            return EdoChecklistItemBo.from(input);
        };
    };
}
