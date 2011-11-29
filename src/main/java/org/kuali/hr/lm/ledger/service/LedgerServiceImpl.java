package org.kuali.hr.lm.ledger.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.lm.ledger.Ledger;
import org.kuali.hr.lm.ledger.dao.LedgerDao;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class LedgerServiceImpl implements LedgerService {

    private static final Logger LOG = Logger.getLogger(LedgerServiceImpl.class);

    private LedgerDao ledgerDao;

    @Override
    public Ledger getLedger(Long ledgerId) {
        return ledgerDao.getLedger(ledgerId);
    }

    public LedgerDao getLedgerDao() {
        return ledgerDao;
    }

    public void setLedgerDao(LedgerDao ledgerDao) {
        this.ledgerDao = ledgerDao;
    }
    
    public List<Ledger> getLedgersForDocumentId(String documentId){
    	return ledgerDao.getLedgersForDocumentId(documentId);
    }

	@Override
	public List<Ledger> getLedgers(String principalId, Date beginDate,
			Date endDate) {
		return ledgerDao.getLedgers(principalId, beginDate, endDate);
	}
	
	public void saveLedgers(List<Ledger> ledgers){
		for(Ledger ledger : ledgers){
			saveLedger(ledger);
		}
	}
	
	public void saveLedger(Ledger ledger){
		//Existing one becomes inactivated
		ledger.setActive(false);
		ledger.setTimestamp(null);
		ledger.setPrincipalInactivated(TKContext.getPrincipalId());
		KNSServiceLocator.getBusinessObjectService().save(ledger);
		//now save new entry with same data
		ledger.setActive(true);
		ledger.setLmLedgerId(null);
		ledger.setPrincipalActivated(TKContext.getPrincipalId());
		ledger.setPrincipalInactivated(null);
		ledger.setTimestamp(null);
		
		KNSServiceLocator.getBusinessObjectService().save(ledger);
		
	}
    
    
}
