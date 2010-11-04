package org.kuali.hr.time.paytype;

import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

/**
 * 
 * @author crivera
 *
 */
public class PrincipalPayType extends PersistableBusinessObjectBase {

	private static final long serialVersionUID = 1L;

	private Long principalPayTypeId;
	
	private String principalId;
	
	private String hrPayType;
	
	private PayType payType;
	
	/*
	 * (non-Javadoc)
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		return map;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setHrPayType(String hrPayType) {
		this.hrPayType = hrPayType;
	}

	public String getHrPayType() {
		return hrPayType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPrincipalPayTypeId(Long principalPayTypeId) {
		this.principalPayTypeId = principalPayTypeId;
	}

	public Long getPrincipalPayTypeId() {
		return principalPayTypeId;
	}

}
