package org.kuali.kpme.edo.checklist;

import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.edo.api.checklist.EdoChecklist;
import org.kuali.kpme.edo.api.checklist.EdoChecklistContract;
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
public class EdoChecklistBo extends HrKeyedBusinessObject implements EdoChecklistContract {

	private static final long serialVersionUID = -3737008004782110416L;
	
	static class KeyFields {
		private static final String GROUP_KEY_CODE = "groupKeyCode";
	}
	
	private String edoChecklistID;
    private String dossierTypeCode;
    private String departmentID;
    private String organizationCode;
    private String description;
  

    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			//.add(KeyFields.GROUP_KEY_CODE)
			.build();
	
    @Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				//.put(KeyFields.GROUP_KEY_CODE, this.getGroupKeyCode())
				.build();
	}
    
	@Override
	public String getId() {
		return getEdoChecklistID();
	}

	@Override
	public void setId(String checklistID) {
		setEdoChecklistID(checklistID);
	}
	
	@Override
	protected String getUniqueKey() {
		return this.getEdoChecklistID();
	}

	public String getEdoChecklistID() {
		return edoChecklistID;
	}

	public void setEdoChecklistID(String edoChecklistID) {
		this.edoChecklistID = edoChecklistID;
	}

	public String getDossierTypeCode() {
		return dossierTypeCode;
	}

	public void setDossierTypeCode(String dossierTypeCode) {
		this.dossierTypeCode = dossierTypeCode;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static EdoChecklistBo from(EdoChecklist im) {
        if (im == null) {
            return null;
        }
        EdoChecklistBo eclt = new EdoChecklistBo();
        eclt.setEdoChecklistID(im.getEdoChecklistID());
        eclt.setDossierTypeCode(im.getDossierTypeCode());
        eclt.setDepartmentID(im.getDepartmentID());
        eclt.setOrganizationCode(im.getOrganizationCode());
        eclt.setDescription(im.getDescription());
        eclt.setGroupKeyCode(im.getGroupKeyCode());        
        eclt.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));

        // finally copy over the common fields into phra from im
        copyCommonFields(eclt, im);
     
        return eclt;
    } 
    
    public static EdoChecklist to(EdoChecklistBo bo) {
        if (bo == null) {
            return null;
        }
        return EdoChecklist.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoChecklistBo, EdoChecklist> toImmutable = new ModelObjectUtils.Transformer<EdoChecklistBo, EdoChecklist>() {
        public EdoChecklist transform(EdoChecklistBo input) {
            return EdoChecklistBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoChecklist, EdoChecklistBo> toBo = new ModelObjectUtils.Transformer<EdoChecklist, EdoChecklistBo>() {
        public EdoChecklistBo transform(EdoChecklist input) {
            return EdoChecklistBo.from(input);
        };
    };

}
