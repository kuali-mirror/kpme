package org.kuali.hr.lm.ledger.service;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.ledger.Ledger;
import org.kuali.hr.lm.ledger.dao.LedgerDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.service.KNSServiceLocator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    public List<Ledger> getLedgersForDocumentId(String documentId) {
        return ledgerDao.getLedgersForDocumentId(documentId);
    }

    @Override
    public List<Ledger> getLedgers(String principalId, Date beginDate,
                                   Date endDate) {
        return ledgerDao.getLedgers(principalId, beginDate, endDate);
    }

    @Override
    public void saveLedgers(List<Ledger> ledgers) {
        for (Ledger ledger : ledgers) {
            ledgerDao.saveOrUpdate(ledger);
        }
    }

    @Override
    public void deleteLedger(long ledgerId) {
        Ledger ledger = TkServiceLocator.getLedgerService().getLedger(ledgerId);
        ledger.setActive(false);
        ledger.setPrincipalInactivated(TKContext.getTargetPrincipalId());
        ledger.setTimestampInactivaed(TKUtils.getCurrentTimestamp());
        ledgerDao.saveOrUpdate(ledger);
    }

    @Override
    public void saveLedger(Ledger ledger) {
        //Existing one becomes inactivated
        ledger.setActive(false);
        ledger.setTimestamp(new Timestamp(System.currentTimeMillis()));
        ledger.setPrincipalInactivated(TKContext.getPrincipalId());
        KNSServiceLocator.getBusinessObjectService().save(ledger);
        //now save new entry with same data
        ledger.setActive(true);
        ledger.setLedgerId(null);
        ledger.setPrincipalActivated(TKContext.getPrincipalId());
        ledger.setPrincipalInactivated(null);
        ledger.setTimestamp(new Timestamp(System.currentTimeMillis()));

        KNSServiceLocator.getBusinessObjectService().save(ledger);
    }

    @Override
    public void addLedgers(DateTime beginDate, DateTime endDate, LeaveCalendarDocument lcd, String leaveCode, Long leaveCodeId, BigDecimal hours, String description) {
        String docId = lcd.getDocumentId();
        String princpalId = TKContext.getTargetPrincipalId();
        DateTimeZone zone = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();

        DateTime calBeginDateTime = lcd.getCalendarEntry().getBeginLocalDateTime().toDateTime(zone);
        DateTime calEndDateTime = lcd.getCalendarEntry().getEndLocalDateTime().toDateTime(zone);
        Interval calendarInterval = new Interval(calBeginDateTime, calEndDateTime);
        // The leave code format on the form is id_leaveCode. This will save a db trip to grab the leavecode id.

        // To create the correct interval by the given begin and end dates,
        // we need to plus one day on the end date to include the end date
        List<Interval> ledgerIntervals = TKUtils.createDaySpan(beginDate, endDate.plusDays(1), zone);

        List<Ledger> currentledgers = lcd.getLedgers();

        for (Interval ledgerInt : ledgerIntervals) {
            if (calendarInterval.contains(ledgerInt)) {
                Ledger ledger = new Ledger.Builder(new DateTime(ledgerInt.getStartMillis()), docId, princpalId, leaveCode, hours)
                        .description(description)
                        .principalActivated(princpalId)
                        .timestampActivated(TKUtils.getCurrentTimestamp())
                        .leaveCodeId(leaveCodeId)
                        .scheduleTimeOffId(0L)
                        .accrualCategoryId(0L)
                        .build();
                currentledgers.add(ledger);

            }
        }


//        for (Interval dayInt : dayIntervals) {
//            if (dayInt.contains(beginDate) ||
//                    (dayInt.contains(endDate) || dayInt.getEnd().equals(endDate))) {
//
//                // TODO: need to integrate with the leave code, scheduled timeoff, and accrual category objects.
//                Ledger ledger = new Ledger.Builder(new DateTime(dayInt.getStartMillis()), docId, princpalId, leaveCode, hours)
//                        .description(description)
//                        .principalActivated(princpalId)
//                        .timestampActivated(TKUtils.getCurrentTimestamp())
//                        .leaveCodeId(0L)
//                        .scheduleTimeOffId(0L)
//                        .accrualCategoryId(0L)
//                        .build();
//                currentledgers.add(ledger);
//
//            }
//        }

        TkServiceLocator.getLedgerService().saveLedgers(currentledgers);
    }


}
