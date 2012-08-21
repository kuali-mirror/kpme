package org.kuali.hr.time.shiftdiff.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface ShiftDifferentialRuleService {
	/**
	 * Save or Update List of ShiftDifferentialRule objects
	 * @param shiftDifferentialRules
	 */
    @CacheEvict(value={ShiftDifferentialRule.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules);
	/**
	 * Save or Update a ShiftDifferentialRule object
	 * @param shiftDifferentialRule
	 */
    @CacheEvict(value={ShiftDifferentialRule.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule);
	/**
	 * Fetch a ShiftDifferentialRule object for a given id
	 * @param tkShiftDifferentialRuleId
	 * @return
	 */
    @Cacheable(value= ShiftDifferentialRule.CACHE_NAME, key="'tkShiftDifferentialRuleId=' + #p0")
	public ShiftDifferentialRule getShiftDifferentialRule(String tkShiftDifferentialRuleId);
	/**
	 * Fetch a given ShiftDifferentialRule based on criteria passed in
	 * @param location
	 * @param hrSalGroup
	 * @param payGrade
	 * @param pyCalendarGroup
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= ShiftDifferentialRule.CACHE_NAME,
            key="'location=' + #p0" +
                "+ '|' + 'hrSalGroup=' + #p1" +
                "+ '|' + 'payGrade=' + #p2" +
                "+ '|' + 'pyCalendarGroup=' + #p3" +
                "+ '|' + 'asOfDate=' + #p4")
	public List<ShiftDifferentialRule> getShiftDifferentalRules(String location,
            String hrSalGroup, String payGrade, String pyCalendarGroup, Date asOfDate);
	/**
	 * Process a given TkTimeBlockAggregate with appropriate shift differential rules
	 * @param timesheetDocument
	 * @param aggregate
	 */
	public void processShiftDifferentialRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate);

}
