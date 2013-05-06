package org.kuali.kpme.pm.classification;


import org.kuali.kpme.core.bo.HrBusinessObject;

public class ClassificationQualification extends HrBusinessObject {
	private static final long serialVersionUID = 1L;
	
	private String pmClassificationQualificationId;
	private String qualificationType;
	private String qualifier;
	private String qualificationValue;
	private String pmPositionClassId;
	private String displayOrder;
	
	@Override
	public String getId() {
		return this.getPmClassificationQualificationId();
	}

	@Override
	public void setId(String id) {
		this.setPmClassificationQualificationId(id);
	}

	@Override
	protected String getUniqueKey() {
		return this.getQualificationType() + "_" + this.getQualificationValue() + "_" + this.getPmPositionClassId();
	}
	
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

	public String getPmPositionClassId() {
		return pmPositionClassId;
	}

	public void setPmPositionClassId(String pmPositionClassId) {
		this.pmPositionClassId = pmPositionClassId;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getPmClassificationQualificationId() {
		return pmClassificationQualificationId;
	}

	public void setPmClassificationQualificationId(
			String pmClassificationQualificationId) {
		this.pmClassificationQualificationId = pmClassificationQualificationId;
	}

}
