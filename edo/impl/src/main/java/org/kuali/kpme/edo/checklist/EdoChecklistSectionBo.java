package org.kuali.kpme.edo.checklist;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.edo.api.checklist.EdoChecklistSection;
import org.kuali.kpme.edo.api.checklist.EdoChecklistSectionContract;
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
public class EdoChecklistSectionBo extends HrBusinessObject implements EdoChecklistSectionContract {

	
	static class KeyFields {
		private static final String EDO_CHECKLIST_SECTION = "checklistSectionName";
	}
	
	private String edoChecklistSectionID;
	private String edoChecklistID;
    private String checklistSectionName;
	private String description;
    private int checklistSectionOrdinal;
    
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			.add(KeyFields.EDO_CHECKLIST_SECTION)
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
		return  getEdoChecklistSectionID();
	}

	@Override
	public void setId(String checklistSectionID) {
		setEdoChecklistSectionID(checklistSectionID);
	}

	@Override
	protected String getUniqueKey() {
		return getEdoChecklistSectionID();
	}
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.EDO_CHECKLIST_SECTION, this.getChecklistSectionName())
				.build();
	}

    public String getEdoChecklistSectionID() {
		return edoChecklistSectionID;
	}

	public void setEdoChecklistSectionID(String edoChecklistSectionID) {
		this.edoChecklistSectionID = edoChecklistSectionID;
	}

	public String getEdoChecklistID() {
		return edoChecklistID;
	}

	public void setEdoChecklistID(String edoChecklistID) {
		this.edoChecklistID = edoChecklistID;
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

	public static EdoChecklistSectionBo from(EdoChecklistSection im) {
        if (im == null) {
            return null;
        }
        EdoChecklistSectionBo ecls = new EdoChecklistSectionBo();
        ecls.setEdoChecklistSectionID(im.getEdoChecklistSectionID());
        ecls.setEdoChecklistID(im.getEdoChecklistID());
        ecls.setDescription(im.getDescription());
        ecls.setChecklistSectionName(im.getChecklistSectionName());
        ecls.setChecklistSectionOrdinal(im.getChecklistSectionOrdinal());

        // finally copy over the common fields into phra from im
        copyCommonFields(ecls, im);
     
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
