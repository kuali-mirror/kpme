package org.kuali.kpme.pm.pstnqlfrtype;

import org.kuali.kpme.core.bo.HrBusinessObject;

public class PstnQlfrType extends HrBusinessObject {
	private static final long serialVersionUID = 1L;

	private String pmPstnQlfrTypeId;
	private String code;
	private String type;
	private String descr;
	private String typeValue;
//	private PriorityQueue<String> selectValues;
	private String selectValues;
	
	@Override
	public String getId() {
		return this.getPmPstnQlfrTypeId();
	}

	@Override
	public void setId(String id) {
		this.setPmPstnQlfrTypeId(id);
	}

	@Override
	protected String getUniqueKey() {
		return this.getCode() + "_" + this.getType();
	}

	public String getPmPstnQlfrTypeId() {
		return pmPstnQlfrTypeId;
	}

	public void setPmPstnQlfrTypeId(String pmPstnQlfrTypeId) {
		this.pmPstnQlfrTypeId = pmPstnQlfrTypeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSelectValues() {
		return selectValues;
	}

	public void setSelectValues(String selectValues) {
		this.selectValues = selectValues;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	
}
