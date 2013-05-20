package org.kuali.kpme.pm.position;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class PositionQualification extends PersistableBusinessObjectBase {
	private static final long serialVersionUID = 1L;
	
	private String pmQualificationId;
	private String qualificationType;
	private String typeValue;		// for GUI only
	private String qualifier;
	private String qualificationValue;
	private String hrPositionId;
		
	public String getQualificationType() {
		return qualificationType;
	}

	public void setQualificationType(String qualificationType) {
		this.qualificationType = qualificationType;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public String getQualificationValue() {
		return qualificationValue;
	}

	public void setQualificationValue(String qualificationValue) {
		this.qualificationValue = qualificationValue;
	}

	public String getTypeValue() {
		if(StringUtils.isNotEmpty(this.getQualificationType())) {
			PstnQlfrType aTypeObj = PmServiceLocator.getPstnQlfrTypeService().getPstnQlfrTypeById(this.getQualificationType());
			if(aTypeObj != null) {
				return aTypeObj.getTypeValue();
			}
		}
		return "";
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getPmQualificationId() {
		return pmQualificationId;
	}

	public void setPmQualificationId(String pmQualificationId) {
		this.pmQualificationId = pmQualificationId;
	}

	public String getHrPositionId() {
		return hrPositionId;
	}

	public void setHrPositionId(String hrPositionId) {
		this.hrPositionId = hrPositionId;
	}

}
