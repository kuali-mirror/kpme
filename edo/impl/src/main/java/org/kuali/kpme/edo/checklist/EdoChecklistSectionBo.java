package org.kuali.kpme.edo.checklist;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.edo.api.checklist.EdoChecklistSection;
import org.kuali.kpme.edo.api.checklist.EdoChecklistSectionContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.f
 * User: bradleyt
 * Date: 5/22/14
 * Time: 9:54 AM
 */
public class EdoChecklistSectionBo extends PersistableBusinessObjectBase implements EdoChecklistSectionContract {

	static class KeyFields {
		private static final String EDO_CHECKLIST_ID = "edoChecklistId";
		private static final String EDO_CHECKLIST_SECTION_NAME = "checklistSectionName";
	}
	
	private String edoChecklistSectionId;
	private String edoChecklistId;
    private String checklistSectionName;
	private String description;
    private int checklistSectionOrdinal;
    
    private List<EdoChecklistItemBo> checklistItems = new ArrayList<EdoChecklistItemBo> ();
    
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
    		.add(KeyFields.EDO_CHECKLIST_ID)
			.add(KeyFields.EDO_CHECKLIST_SECTION_NAME)
			.build();

    public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.EDO_CHECKLIST_ID, this.getEdoChecklistId())
				.put(KeyFields.EDO_CHECKLIST_SECTION_NAME, this.getChecklistSectionName())
				.build();
    }
    
	public String getId() {
		return  getEdoChecklistSectionId();
	}

	public void setId(String checklistSectionID) {
		setEdoChecklistSectionId(checklistSectionID);
	}

    public String getEdoChecklistSectionId() {
		return edoChecklistSectionId;
	}

	public void setEdoChecklistSectionId(String edoChecklistSectionId) {
		this.edoChecklistSectionId = edoChecklistSectionId;
	}

	public String getEdoChecklistId() {
		return edoChecklistId;
	}

	public void setEdoChecklistId(String edoChecklistId) {
		this.edoChecklistId = edoChecklistId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChecklistSectionName() {
		return checklistSectionName;
	}

	public void setChecklistSectionName(String checklistSectionName) {
		this.checklistSectionName = checklistSectionName;
	}

	public int getChecklistSectionOrdinal() {
		return checklistSectionOrdinal;
	}

	public void setChecklistSectionOrdinal(int checklistSectionOrdinal) {
		this.checklistSectionOrdinal = checklistSectionOrdinal;
	}

	public List<EdoChecklistItemBo> getChecklistItems() {
		return checklistItems;
	}

	public void setChecklistItems(List<EdoChecklistItemBo> checklistItems) {
		this.checklistItems = checklistItems;
	}

	public static EdoChecklistSectionBo from(EdoChecklistSection im) {
        if (im == null) {
            return null;
        }
        EdoChecklistSectionBo ecls = new EdoChecklistSectionBo();
        ecls.setEdoChecklistSectionId(im.getEdoChecklistSectionId());
        ecls.setEdoChecklistId(im.getEdoChecklistId());
        ecls.setDescription(im.getDescription());
        ecls.setChecklistSectionName(im.getChecklistSectionName());
        ecls.setChecklistSectionOrdinal(im.getChecklistSectionOrdinal());
        ecls.setChecklistItems(im.getChecklistItems());
        ecls.setVersionNumber(im.getVersionNumber());
        ecls.setObjectId(im.getObjectId());        
        return ecls;
    } 
    
    public static EdoChecklistSection to(EdoChecklistSectionBo bo) {
        if (bo == null) {
            return null;
        }
        return EdoChecklistSection.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoChecklistSectionBo, EdoChecklistSection> toImmutable = new ModelObjectUtils.Transformer<EdoChecklistSectionBo, EdoChecklistSection>() {
        public EdoChecklistSection transform(EdoChecklistSectionBo input) {
            return EdoChecklistSectionBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoChecklistSection, EdoChecklistSectionBo> toBo = new ModelObjectUtils.Transformer<EdoChecklistSection, EdoChecklistSectionBo>() {
        public EdoChecklistSectionBo transform(EdoChecklistSection input) {
            return EdoChecklistSectionBo.from(input);
        };
    };
}
