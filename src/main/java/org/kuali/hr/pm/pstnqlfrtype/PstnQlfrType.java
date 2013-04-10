package org.kuali.hr.pm.pstnqlfrtype;

import org.kuali.hr.time.HrBusinessObject;

public class PstnQlfrType extends HrBusinessObject {
	private static final long serialVersionUID = 1L;

	private String pmPstnQlfrTypeId;
	private String code;
	private String type;
	private String desc;
	private String value;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSelectValues() {
		return selectValues;
	}

	public void setSelectValues(String selectValues) {
		this.selectValues = selectValues;
	}

	
}
