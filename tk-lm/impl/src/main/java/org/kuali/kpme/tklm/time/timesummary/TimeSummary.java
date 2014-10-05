/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.time.timesummary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONValue;
import org.kuali.kpme.tklm.api.time.timesummary.TimeSummaryContract;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;

public class TimeSummary implements Serializable, TimeSummaryContract {

	private static final long serialVersionUID = -1292273625423289154L;
	private Map<Integer,String> timeSummaryHeader;
	private BigDecimal grandTotal= BigDecimal.ZERO;
	private List<String> summaryHeader = new ArrayList<String>();
	private List<EarnGroupSection> sections = new ArrayList<EarnGroupSection>();
	private Map<String, List<EarnGroupSection>> weeklySections = new LinkedHashMap<String, List<EarnGroupSection>>();
	private List<LeaveSummaryRow> maxedLeaveRows = new ArrayList<LeaveSummaryRow>();
	private List<BigDecimal> workedHours = new ArrayList<BigDecimal>();
	private Map<String, BigDecimal> weekTotalMap = new LinkedHashMap<String, BigDecimal>();
	private Map<String, BigDecimal> flsaWeekTotalMap = new LinkedHashMap<String, BigDecimal>();
	private Map<String, Map<Integer, BigDecimal>> weeklyWorkedHours = new LinkedHashMap<String, Map<Integer, BigDecimal>>();
	private Map<String, Map<Integer, Boolean>> weeklyClockLogs = new LinkedHashMap<String, Map<Integer, Boolean>>();
	private Map<String, String> weekDates = new LinkedHashMap<String, String>();
    private Map<String, Integer> weekDateToCalendarDayInt = new HashMap<>();

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}
	public List<String> getSummaryHeader() {
		return summaryHeader;
	}
	public void setSummaryHeader(List<String> summaryHeader) {
		this.summaryHeader = summaryHeader;
	}
	public List<EarnGroupSection> getSections() {
		return sections;
	}
	public void setSections(List<EarnGroupSection> sections) {
		this.sections = sections;
	}
	public List<BigDecimal> getWorkedHours() {
		return workedHours;
	}
	public void setWorkedHours(List<BigDecimal> workedHours) {
		this.workedHours = workedHours;
	}

	public String toJsonString() {
        List<Map<String, Object>> earnGroupSections = new ArrayList<Map<String, Object>>();

        //Lets add a fake EarnGroupSection for worked hours!!!
        Map<String, Object> wkHours = new HashMap<String, Object>();
        wkHours.put("totals", getWorkedHours());
        wkHours.put("earnGroup", "Worked Hours");
        wkHours.put("earnCodeSections", new HashMap<String, Object>());

        for (EarnGroupSection earnGroupSection : this.sections) {
            List<Map<String, Object>> earnCodeSections = new ArrayList<Map<String, Object>>();
            Map<String, Object> egs = new TreeMap<String, Object>();
            egs.put("earnGroup", earnGroupSection.getEarnGroup());
            egs.put("totals", earnGroupSection.getTotals());
            for (EarnCodeSection earnCodeSection : earnGroupSection.getEarnCodeSections()) {
                Map<String, Object> ecs = new TreeMap<String, Object>();

                ecs.put("earnCode", earnCodeSection.getEarnCode());
                ecs.put("desc", earnCodeSection.getDescription());
                ecs.put("totals", earnCodeSection.getTotals());
                ecs.put("isAmountEarnCode", earnCodeSection.getIsAmountEarnCode());
                ecs.put("assignmentSize", earnCodeSection.getAssignmentsRows().size() + 1);
                ecs.put("earnGroup", earnGroupSection.getEarnGroup());
                ecs.put("totals", earnGroupSection.getTotals());
                
                List<Map<String, Object>> assignmentRows = new ArrayList<Map<String, Object>>();
                for (AssignmentRow assignmentRow : earnCodeSection.getAssignmentsRows()) {
                    Map<String, Object> ar = new TreeMap<String, Object>();

                    ar.put("descr", assignmentRow.getDescr());
                    ar.put("assignmentKey", assignmentRow.getAssignmentKey());
                    ar.put("cssClass", assignmentRow.getCssClass());
     	 	 	 	ar.put("earnCode", earnCodeSection.getEarnCode());
                    
                    List<Map<String, Object>> assignmentColumns = new ArrayList<Map<String, Object>>();
                    for (AssignmentColumn assignmentColumn : assignmentRow.getAssignmentColumns().values()) {
                    	Map<String, Object> ac = new TreeMap<String, Object>();

                    	ac.put("cssClass", assignmentColumn.getCssClass());
                    	ac.put("amount", assignmentColumn.getAmount());
                    	ac.put("total", assignmentColumn.getTotal());
                    	ac.put("isWeeklyTotal", assignmentColumn.isWeeklyTotal());

                    	assignmentColumns.add(ac);
                    }
                    ar.put("assignmentColumns", assignmentColumns);

                    assignmentRows.add(ar);
                }
                
                ecs.put("assignmentRows", assignmentRows);
                
                List<Map<String, Object>> weekTotalRows = new java.util.LinkedList<Map<String, Object>>();
                for(String key : this.weekTotalMap.keySet()) {
                	Map<String, Object> wt = new HashMap<String, Object>();
                	wt.put("weekName", key);
                	wt.put("weekTotal", weekTotalMap.get(key));
                	weekTotalRows.add(wt);
                }
                
                ecs.put("weekTotals", weekTotalRows);
                
                List<Map<String, Object>> flsaWeekTotalRows = new java.util.LinkedList<Map<String, Object>>();
                for(String key : this.flsaWeekTotalMap.keySet()) {
                	Map<String, Object> wt = new HashMap<String, Object>();
                	wt.put("weekName", key);
                	wt.put("weekTotal", flsaWeekTotalMap.get(key));
                	flsaWeekTotalRows.add(wt);
                }
                
                ecs.put("flsaWeekTotals", flsaWeekTotalRows);

                earnCodeSections.add(ecs);
            }
            egs.put("earnCodeSections", earnCodeSections);
            earnGroupSections.add(egs);
        }
        earnGroupSections.add(wkHours);
        return JSONValue.toJSONString(earnGroupSections);
    }
	
	public List<LeaveSummaryRow> getMaxedLeaveRows() {
		return maxedLeaveRows;
	}
	
	public void setMaxedLeaveRows(List<LeaveSummaryRow> maxedLeaveRows) {
		this.maxedLeaveRows = maxedLeaveRows;
	}
	/**
	 * @return the weekTotalMap
	 */
	public Map<String, BigDecimal> getWeekTotalMap() {
		return weekTotalMap;
	}
	/**
	 * @param weekTotalMap the weekTotalMap to set
	 */
	public void setWeekTotalMap(Map<String, BigDecimal> weekTotalMap) {
		this.weekTotalMap = weekTotalMap;
	}
	
	public Map<String, BigDecimal> getFlsaWeekTotalMap() {
		return flsaWeekTotalMap;
	}
	
	public void setFlsaWeekTotalMap(Map<String, BigDecimal> flsaWeekTotalMap) {
		this.flsaWeekTotalMap = flsaWeekTotalMap;
	}
	
	public Map<String, String> getWeekDates() {
		return weekDates;
	}
	public void setWeekDates(Map<String, String> weekDates) {
		this.weekDates = weekDates;
	}
	public Map<String, List<EarnGroupSection>> getWeeklySections() {
		return weeklySections;
	}
	
	public void setWeeklySections(Map<String, List<EarnGroupSection>> weeklySections) {
		this.weeklySections = weeklySections;
	}
	
	public Map<String, Map<Integer, BigDecimal>> getWeeklyWorkedHours() {
		return weeklyWorkedHours;
	}
	
	public void setWeeklyWorkedHours(
			Map<String, Map<Integer, BigDecimal>> weeklyWorkedHours) {
		this.weeklyWorkedHours = weeklyWorkedHours;
	}
	
	public Map<Integer, String> getTimeSummaryHeader() {
		return timeSummaryHeader;
	}
	
	public void setTimeSummaryHeader(Map<Integer, String> timeSummaryHeader) {
		this.timeSummaryHeader = timeSummaryHeader;
	}

    public Map<String, Integer> getWeekDateToCalendarDayInt() {
        return weekDateToCalendarDayInt;
    }

    public void setWeekDateToCalendarDayInt(Map<String, Integer> weekDateToCalendarDayInt) {
        this.weekDateToCalendarDayInt = weekDateToCalendarDayInt;
    }
	public Map<String, Map<Integer, Boolean>> getWeeklyClockLogs() {
		return weeklyClockLogs;
	}
	public void setWeeklyClockLogs(
			Map<String, Map<Integer, Boolean>> weeklyClockLogs) {
		this.weeklyClockLogs = weeklyClockLogs;
	}
    
}
