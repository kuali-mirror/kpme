package org.kuali.hr.time.paytype;

import java.util.LinkedHashMap;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.earncode.EarnCode;

public class PayType extends HrBusinessObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long hrPayTypeId;
	private String payType;
	private String descr;
	private String regEarnCode;
    /** Used for lookup */
	private Long tkEarnCodeId;
    private EarnCode regEarnCodeObj;
    private String history;
    private Boolean ovtEarnCode;

    public EarnCode getRegEarnCodeObj() {
        return regEarnCodeObj;
    }

    public void setRegEarnCodeObj(EarnCode regEarnCodeObj) {
        this.regEarnCodeObj = regEarnCodeObj;
    }

    @SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}


	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getRegEarnCode() {
		return regEarnCode;
	}

	public void setRegEarnCode(String regEarnCode) {
		this.regEarnCode = regEarnCode;
	}
	public Long getHrPayTypeId() {
		return hrPayTypeId;
	}


	public void setHrPayTypeId(Long hrPayTypeId) {
		this.hrPayTypeId = hrPayTypeId;
	}



	public Long getTkEarnCodeId() {
		return tkEarnCodeId;
	}

	public void setTkEarnCodeId(Long tkEarnCodeId) {
		this.tkEarnCodeId = tkEarnCodeId;
	}

	@Override
	protected String getUniqueKey() {
		return payType;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public Boolean getOvtEarnCode() {
		return ovtEarnCode;
	}

	public void setOvtEarnCode(Boolean ovtEarnCode) {
		this.ovtEarnCode = ovtEarnCode;
	}

	@Override
	public Long getId() {
		return getHrPayTypeId();
	}

	@Override
	public void setId(Long id) {
		setHrPayTypeId(id);
	}
	
}
