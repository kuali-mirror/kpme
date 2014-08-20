package org.kuali.kpme.edo.checklist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.edo.api.checklist.EdoChecklist;
import org.kuali.kpme.edo.api.checklist.EdoChecklistContract;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinitionBo;
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
		private static final String DOSSIER_TYPE_CD = "dossierTypeCode";
		private static final String DEPT_ID = "departmentId";
		private static final String ORG_CD = "organizationCode";
	}
	
	private String edoChecklistId;
    private String dossierTypeCode;
    private String departmentId;
    private String organizationCode;
    private String description;
    
    private List<EdoChecklistSectionBo> checklistSections = new ArrayList<EdoChecklistSectionBo> ();

    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
    		.add(KeyFields.GROUP_KEY_CODE)
    		.add(KeyFields.DOSSIER_TYPE_CD)
    		.add(KeyFields.DEPT_ID)
    		.add(KeyFields.ORG_CD)
			.build();
	
    @Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.GROUP_KEY_CODE, this.getGroupKeyCode())
				.put(KeyFields.DOSSIER_TYPE_CD, this.getDossierTypeCode())
				.put(KeyFields.DEPT_ID, this.getDepartmentId())
				.put(KeyFields.ORG_CD, this.getOrganizationCode())
				.build();
	}
    
	@Override
	public String getId() {
		return getEdoChecklistId();
	}

	@Override
	public void setId(String checklistId) {
		setEdoChecklistId(checklistId);
	}
	
	@Override
	protected String getUniqueKey() {
		return this.getEdoChecklistId();
	}

	public String getEdoChecklistId() {
		return edoChecklistId;
	}

	public void setEdoChecklistId(String edoChecklistId) {
		this.edoChecklistId = edoChecklistId;
	}

	public String getDossierTypeCode() {
		return dossierTypeCode;
	}

	public void setDossierTypeCode(String dossierTypeCode) {
		this.dossierTypeCode = dossierTypeCode;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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

	public List<EdoChecklistSectionBo> getChecklistSections() {
		return checklistSections;
	}

	public void setChecklistSections(List<EdoChecklistSectionBo> checklistSections) {
		this.checklistSections = checklistSections;
	}

	public static EdoChecklistBo from(EdoChecklist im) {
        if (im == null) {
            return null;
        }
        EdoChecklistBo eclt = new EdoChecklistBo();
        eclt.setEdoChecklistId(im.getEdoChecklistId());
        eclt.setDossierTypeCode(im.getDossierTypeCode());
        eclt.setDepartmentId(im.getDepartmentId());
        eclt.setOrganizationCode(im.getOrganizationCode());
        eclt.setDescription(im.getDescription());
        eclt.setGroupKeyCode(im.getGroupKeyCode());        
        eclt.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));
        if (CollectionUtils.isEmpty(im.getChecklistSections())) {
            eclt.setChecklistSections(Collections.<EdoChecklistSectionBo>emptyList());
        } else {
            eclt.setChecklistSections(ModelObjectUtils.transform(im.getChecklistSections(), EdoChecklistSectionBo.toBo));
        }

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
