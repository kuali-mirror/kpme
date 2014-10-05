package org.kuali.kpme.edo.item.type;

import java.text.ParseException;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.edo.api.item.type.EdoItemType;
import org.kuali.kpme.edo.api.item.type.EdoItemTypeContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class EdoItemTypeBo extends HrBusinessObject implements EdoItemTypeContract {

	private static final long serialVersionUID = 4600890413203389445L;
	
	static class KeyFields {
		private static final String EDO_ITEM_TYPE = "itemTypeName";
	}
	
	private String edoItemTypeId;
    private String itemTypeName;
    private String itemTypeDescription;
    private String itemTypeInstructions;
    private boolean itemTypeExtAvailable;
    
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			.add(KeyFields.EDO_ITEM_TYPE)
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
		return  getEdoItemTypeId();
	}

	@Override
	public void setId(String edoItemTypeId) {
		setEdoItemTypeId(edoItemTypeId);
	}

	@Override
	protected String getUniqueKey() {
		return getEdoItemTypeId();
	}
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.EDO_ITEM_TYPE, this.getItemTypeName())
				.build();
	}

    public void EdoItemType() throws ParseException {
        this.setEdoItemTypeId(null);
        this.setItemTypeName(null);
        this.setItemTypeDescription(null);
        this.setItemTypeExtAvailable(false);
        this.setItemTypeInstructions(null);
    }

    public String getEdoItemTypeId() {
        return edoItemTypeId;
    }

    public void setEdoItemTypeId(String edoItemTypeId) {
        this.edoItemTypeId = edoItemTypeId;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getItemTypeDescription() {
        return itemTypeDescription;
    }

    public void setItemTypeDescription(String itemTypeDescription) {
        this.itemTypeDescription = itemTypeDescription;
    }

    public String getItemTypeInstructions() {
        return itemTypeInstructions;
    }

    public void setItemTypeInstructions(String itemTypeInstructions) {
        this.itemTypeInstructions = itemTypeInstructions;
    }

    public boolean isItemTypeExtAvailable() {
        return itemTypeExtAvailable;
    }

    public void setItemTypeExtAvailable(boolean itemTypeExtAvailable) {
        this.itemTypeExtAvailable = itemTypeExtAvailable;
    }
    
    public static EdoItemTypeBo from(EdoItemType im) {
        if (im == null) {
            return null;
        }
        EdoItemTypeBo eit = new EdoItemTypeBo();
        eit.setEdoItemTypeId(im.getEdoItemTypeId());
        eit.setItemTypeName(im.getItemTypeName());
        eit.setItemTypeDescription(im.getItemTypeDescription());
        eit.setItemTypeInstructions(im.getItemTypeInstructions());
        eit.setItemTypeExtAvailable(im.isItemTypeExtAvailable());
     
        // finally copy over the common fields into phra from im
        copyCommonFields(eit, im);
     
        return eit;
    } 
    
    public static EdoItemType to(EdoItemTypeBo bo) {
        if (bo == null) {
            return null;
        }
        return EdoItemType.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoItemTypeBo, EdoItemType> toImmutable = new ModelObjectUtils.Transformer<EdoItemTypeBo, EdoItemType>() {
        public EdoItemType transform(EdoItemTypeBo input) {
            return EdoItemTypeBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoItemType, EdoItemTypeBo> toBo = new ModelObjectUtils.Transformer<EdoItemType, EdoItemTypeBo>() {
        public EdoItemTypeBo transform(EdoItemType input) {
            return EdoItemTypeBo.from(input);
        };
    };

}
