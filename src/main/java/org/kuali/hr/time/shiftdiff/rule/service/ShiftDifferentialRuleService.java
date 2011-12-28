package org.kuali.hr.time.shiftdiff.rule.service;

import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

import java.sql.Date;
import java.util.List;

public interface ShiftDifferentialRuleService {
	/**
	 * Save or Update List of ShiftDifferentialRule objects
	 * @param shiftDifferentialRules
	 */
	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules);
	/**
	 * Save or Update a ShiftDifferentialRule object
	 * @param shiftDifferentialRule
	 */
	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule);
	/**
	 * Fetch a ShiftDifferentialRule object for a given id
	 * @param tkShiftDifferentialRuleId
	 * @return
	 */
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
	public List<ShiftDifferentialRule> getShiftDifferentalRules(String location, String hrSalGroup, String payGrade, String pyCalendarGroup, Date asOfDate);
	/**
	 * Process a given TkTimeBlockAggregate with appropriate shift differential rules
	 * @param timesheetDocument
	 * @param aggregate
	 */
	public void processShiftDifferentialRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate);

}
