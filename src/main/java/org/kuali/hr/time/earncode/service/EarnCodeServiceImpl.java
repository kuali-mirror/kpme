package org.kuali.hr.time.earncode.service;

import java.util.List;

import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncode.dao.EarnCodeDao;

public class EarnCodeServiceImpl implements EarnCodeService {

	private EarnCodeDao earnCodeDao;

	public void setEarnCodeDao(EarnCodeDao earnCodeDao) {
		this.earnCodeDao = earnCodeDao;
	}

	public EarnCode getEarnCodeById(Long earnCodeId) {
		return earnCodeDao.getEarnCodeById(earnCodeId);
	}
}
