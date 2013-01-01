/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.time.test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.junit.Assert;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.service.AccrualService;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.service.LeaveBlockService;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timeblock.service.TimeBlockService;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

public class TkTestUtils {

	private static final Logger LOG = Logger.getLogger(TkTestUtils.class);

	public static TimesheetDocument populateBlankTimesheetDocument(Date calDate) {
		try {
			TimesheetDocument timesheet = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKUser.getCurrentTargetPerson().getPrincipalId(),
							TkServiceLocator.getCalendarService().getCurrentCalendarDates(TKUser.getCurrentTargetPerson().getPrincipalId(),
                                    calDate));
			for(TimeBlock timeBlock : timesheet.getTimeBlocks()){
				TkServiceLocator.getTimeBlockService().deleteTimeBlock(timeBlock);
			}

			return timesheet;
		} catch (WorkflowException e) {
			throw new RuntimeException("Problem fetching document");
		}
	}

	public static TimesheetDocument populateTimesheetDocument(Date calDate) {
		try {
			TimesheetDocument timesheet = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKUser.getCurrentTargetPerson().getPrincipalId(),
							TkServiceLocator.getCalendarService().getCurrentCalendarDates(TKUser.getCurrentTargetPerson().getPrincipalId(),
                                    calDate));
			for(TimeBlock timeBlock : timesheet.getTimeBlocks()){
				TkServiceLocator.getTimeBlockService().deleteTimeBlock(timeBlock);
			}

			//refetch clean document
			timesheet = TkServiceLocator.getTimesheetService().openTimesheetDocument(TKUser.getCurrentTargetPerson().getPrincipalId(),
					TkServiceLocator.getCalendarService().getCurrentCalendarDates(TKUser.getCurrentTargetPerson().getPrincipalId(),
                            calDate));
			List<TimeBlock> timeBlocks = new LinkedList<TimeBlock>();
			for(int i = 0;i<5;i++){
				TimeBlock timeBlock = createTimeBlock(timesheet, i+1, 10);
				timeBlocks.add(timeBlock);
				timesheet.setTimeBlocks(timeBlocks);
			}
			
			//Add a TEX accrual earn code
			TimeBlock timeBlock = createTimeBlock(timesheet, 1, 4,"TEX");
			timeBlocks.add(timeBlock);
			timesheet.setTimeBlocks(timeBlocks);
			return timesheet;

		} catch (WorkflowException e) {
			throw new RuntimeException("Problem fetching document");
		}
	}

	//TODO populate this
	public static void createEarnGroup(String earnGroup, List<String> earnCodes, Date asOfDate){

	}

	/**
	 * Helper method to create regular time blocks for use in testing.
	 *
	 * @param start
	 * @param days
	 * @param hours
	 * @param earnCode
	 * @param jobNumber
	 * @param workArea
	 * @return
	 */
	public static List<TimeBlock> createUniformTimeBlocks(DateTime start, int days, BigDecimal hours, String earnCode, Long jobNumber, Long workArea) {
		return TkTestUtils.createUniformTimeBlocks(start, days, hours, earnCode, jobNumber, workArea, null);
	}

	public static List<TimeBlock> createUniformTimeBlocks(DateTime start, int days, BigDecimal hours, String earnCode, Long jobNumber, Long workArea, Long task) {
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();

		for (int i=0; i<days; i++) {
			DateTime ci = start.plusDays(i);
			DateTime co = ci.plusHours(hours.intValue());
			TimeBlock block = TkTestUtils.createDummyTimeBlock(ci, co, hours, earnCode, jobNumber, workArea);
			block.setTask(task);
			blocks.add(block);
		}

		return blocks;
	}

	public static TimeBlock createDummyTimeBlock(DateTime clockIn, DateTime clockOut, BigDecimal hours, String earnCode) {
		return TkTestUtils.createDummyTimeBlock(clockIn, clockOut, hours, earnCode, -1L, -1L);
	}

	public static TimeBlock createDummyTimeBlock(DateTime clockIn, DateTime clockOut, BigDecimal hours, String earnCode, Long jobNumber, Long workArea) {
		TimeBlock block = new TimeBlock();
		Timestamp ci = new Timestamp(clockIn.getMillis());
		Timestamp co = new Timestamp(clockOut.getMillis());
		block.setBeginTimestamp(ci);
		block.setEndTimestamp(co);
		block.setHours(hours);
		block.setEarnCode(earnCode);
		block.setJobNumber(jobNumber);
		block.setWorkArea(workArea);

		TimeHourDetail thd = new TimeHourDetail();
		thd.setHours(hours);
		thd.setEarnCode(earnCode);
		List<TimeHourDetail> timeHourDetails = new ArrayList<TimeHourDetail>();
		timeHourDetails.add(thd);
		block.setTimeHourDetails(timeHourDetails);

		return block;
	}

	public static TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, int dayInPeriod, int numHours){
		return createTimeBlock(timesheetDocument, dayInPeriod, numHours,"RGN");
	}
	public static TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, int dayInPeriod, int numHours, String earnCode){
		TimeBlock timeBlock = new TimeBlock();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTimeInMillis(timesheetDocument.getCalendarEntry().getBeginPeriodDateTime().getTime());
		for(int i = 1; i< dayInPeriod;i++){
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);

		timeBlock.setDocumentId(timesheetDocument.getDocumentId());
		timeBlock.setBeginTimeDisplay(new DateTime(cal.getTimeInMillis()));
		
		timeBlock.setBeginTimestamp(new Timestamp(cal.getTimeInMillis()));
		timeBlock.setBeginTimestampTimezone("EST");
		timeBlock.setEarnCode(earnCode);
		timeBlock.setJobNumber(1L);
		timeBlock.setWorkArea(1234L);
		timeBlock.setTask(1L);
		timeBlock.setHours((new BigDecimal(numHours)).setScale(TkConstants.BIG_DECIMAL_SCALE, TkConstants.BIG_DECIMAL_SCALE_ROUNDING));
		cal.add(Calendar.HOUR, numHours);
		timeBlock.setEndTimestamp(new Timestamp(cal.getTimeInMillis()));
		timeBlock.setEndTimeDisplay(new DateTime(cal.getTimeInMillis()));

		return timeBlock;
	}

	public static List<Job> getJobs(Date calDate){
		return TkServiceLocator.getJobService().getJobs(TKContext.getPrincipalId(), calDate);
	}
	/**
	 *
	 * @param page: current html page
	 * @param criteria: The key is the field name and the value is a string array which contains the field value and the field type which can be chosen from TkTestConstants
	 * @return HtmlPage resultPage
	 * @throws Exception
	 */
	public static HtmlPage fillOutForm(HtmlPage page, Map<String, Object> criteria) throws Exception {
		HtmlForm lookupForm = HtmlUnitUtil.getDefaultForm(page);
		String formFieldPrefix = "";
		HtmlPage resultPage = null;
		HtmlSelect select = null;
		HtmlInput input = null;
		HtmlCheckBoxInput checkBox = null;
		HtmlTextArea textArea = null;


		// Common class of both HtmlInput and HtmlTextArea -- since either of these can appear
		// on a web-form.
		HtmlElement htmlBasicInput = null;

		Set<Map.Entry<String, Object>> entries = criteria.entrySet();
		Iterator<Map.Entry<String, Object>> it = entries.iterator();

		while (it.hasNext()) {
			Map.Entry<String,Object> entry = it.next();

			// if the field type is not <input>
			if(entry.getValue() instanceof String[]) {
				String key = Arrays.asList((String[])entry.getValue()).get(0).toString();
				String value = Arrays.asList((String[])entry.getValue()).get(1).toString();

				// drop-down
				if(key.equals(TkTestConstants.FormElementTypes.DROPDOWN)) {

					try {
						select = (HtmlSelect) lookupForm.getSelectByName(formFieldPrefix  + entry.getKey());
					} catch (Exception e) {
						select = (HtmlSelect) lookupForm.getElementById(formFieldPrefix  + entry.getKey());
					}
                    //  try to get a useful option, other than the typical blank default, by getting the last option
                    //  if the size of the options list is zero, then there is a problem. might as well error here with an array out of bounds.
                    resultPage = (HtmlPage) select.getOption(select.getOptionSize()-1).setSelected(true);
                    HtmlUnitUtil.createTempFile(resultPage);
                }
				// check box
				else if(key.equals(TkTestConstants.FormElementTypes.CHECKBOX)) {
					try {
					  checkBox = page.getHtmlElementById(formFieldPrefix  + entry.getKey());
					}
					catch(Exception e) {
						checkBox = page.getElementByName(formFieldPrefix  + entry.getKey());
					}
					resultPage = (HtmlPage) checkBox.setChecked(Boolean.parseBoolean(value));
				}
				// text area
				else if(key.equals(TkTestConstants.FormElementTypes.TEXTAREA)) {
					try {
					   textArea = page.getHtmlElementById(formFieldPrefix  + entry.getKey());
					} catch (Exception e){
						textArea = page.getElementByName(formFieldPrefix  + entry.getKey());
					}
					textArea.setText(Arrays.asList((String[])entry.getValue()).get(1).toString());
				}
			} else {
				try {
					htmlBasicInput = page.getHtmlElementById(formFieldPrefix + entry.getKey());
					if (htmlBasicInput instanceof HtmlTextArea) {
						textArea = (HtmlTextArea)htmlBasicInput;
						textArea.setText(entry.getValue().toString());
						resultPage = (HtmlPage) textArea.getPage();
					} else if (htmlBasicInput instanceof HtmlInput) {
						input = (HtmlInput)htmlBasicInput;
						resultPage = (HtmlPage) input.setValueAttribute(entry.getValue().toString());
					} else {
						LOG.error("Request to populate a non-input html form field.");
					}
				} catch (Exception e) {
					htmlBasicInput = page.getElementByName(formFieldPrefix + entry.getKey());

					if (htmlBasicInput instanceof HtmlTextArea) {
						textArea = (HtmlTextArea)htmlBasicInput;
						textArea.setText(entry.getValue().toString());
						resultPage = (HtmlPage) textArea.getPage();
					} else if (htmlBasicInput instanceof HtmlInput) {
						input = (HtmlInput)htmlBasicInput;
						resultPage = (HtmlPage) input.setValueAttribute(entry.getValue().toString());
					} else {
						LOG.error("Request to populate a non-input html form field.");
					}
				}
			}
		}
		HtmlUnitUtil.createTempFile(resultPage);
		return resultPage;
	}

	/**
	 *
	 * @param page: current html page //NOTE doesnt seem to work currently for js setting of form variables
	 * @param name: the button name
	 * @return
	 * @throws Exception
	 */
	public static HtmlPage clickButton(HtmlPage page, String name) throws Exception {
		HtmlForm form = HtmlUnitUtil.getDefaultForm(page);
		HtmlSubmitInput input = form.getInputByName(name);
		return (HtmlPage) input.click();
	}
	
	public static HtmlPage clickClockInOrOutButton(HtmlPage page) throws Exception {
		HtmlForm form = HtmlUnitUtil.getDefaultForm(page);
		
		HtmlSubmitInput input = form.getInputByName("clockAction");
		form.getInputByName("methodToCall").setValueAttribute("clockAction");
		return (HtmlPage) input.click();
	}
	
	public static HtmlPage clickLunchInOrOutButton(HtmlPage page, String lunchAction) throws Exception {
		HtmlForm form = HtmlUnitUtil.getDefaultForm(page);
		
		HtmlSubmitInput input = form.getInputByName("clockAction");
		form.getInputByName("methodToCall").setValueAttribute("clockAction");
		form.getInputByName("currentClockAction").setValueAttribute(lunchAction);
		return (HtmlPage) input.click();
	}

	@SuppressWarnings("serial")
	public static void verifyAggregateHourSumsFlatList(String msg, final Map<String,BigDecimal> ecToHoursMap, TkTimeBlockAggregate aggregate) {
		// Initializes sum map to zeros, since we only care about the entires
		// that were passed in.
		Map<String,BigDecimal> ecToSumMap = new HashMap<String,BigDecimal>() {{ for (String ec : ecToHoursMap.keySet()) { put(ec, BigDecimal.ZERO); }}};

		for (TimeBlock bl : aggregate.getFlattenedTimeBlockList())
			for (TimeHourDetail thd : bl.getTimeHourDetails())
				if (ecToSumMap.containsKey(thd.getEarnCode()))
					ecToSumMap.put(thd.getEarnCode(), ecToSumMap.get(thd.getEarnCode()).add(thd.getHours()));

		// Assert that our values are correct.
		for (String key : ecToHoursMap.keySet())
			Assert.assertEquals(
					msg + " >> ("+key+") Wrong number of hours expected: " + ecToHoursMap.get(key) + " found: " + ecToSumMap.get(key) + " :: ",
					0,
					ecToHoursMap.get(key).compareTo(ecToSumMap.get(key)));
	}

	/**
	 * Helper method to verify that the aggregate contains the correct sums as
	 * indicated in the ecToHoursMapping, on a SINGLE given flsaWeek.
	 *
	 * Warning! Contains Assertions, use only with Test Cases.
	 *
	 * @param ecToHoursMap ex: { 'REG' => 40, 'OVT' => 10 }
	 * @param aggregate An aggregate object containing the time blocks
	 * @param flsaWeek 0 indexed start week (pulling from aggregate)
	 */
	@SuppressWarnings("serial")
	public static void verifyAggregateHourSums(String msg, final Map<String,BigDecimal> ecToHoursMap, TkTimeBlockAggregate aggregate, int flsaWeek) {
		// Initializes sum map to zeros, since we only care about the entires
		// that were passed in.
		Map<String,BigDecimal> ecToSumMap = new HashMap<String,BigDecimal>() {{ for (String ec : ecToHoursMap.keySet()) { put(ec, BigDecimal.ZERO); }}};

		List<FlsaWeek> flsaWeeks = aggregate.getFlsaWeeks(DateTimeZone.forID(TkServiceLocator.getTimezoneService().getUserTimezone()));
		Assert.assertTrue(msg + " >> Not enough FLSA weeks to verify aggregate hours, max: " + (flsaWeeks.size() - 1), flsaWeeks.size() > flsaWeek);

		// Build our Sum Map.
		FlsaWeek week = flsaWeeks.get(flsaWeek);
		List<FlsaDay> flsaDays = week.getFlsaDays();
		for (FlsaDay day : flsaDays)
			for (TimeBlock bl : day.getAppliedTimeBlocks())
				for (TimeHourDetail thd : bl.getTimeHourDetails())
					if (ecToSumMap.containsKey(thd.getEarnCode()))
						ecToSumMap.put(thd.getEarnCode(), ecToSumMap.get(thd.getEarnCode()).add(thd.getHours()));

		// Assert that our values are correct.
		for (String key : ecToHoursMap.keySet())
			Assert.assertEquals(
					msg + " >> ("+key+") Wrong number of hours expected: " + ecToHoursMap.get(key) + " found: " + ecToSumMap.get(key) + " :: ",
					0,
					ecToHoursMap.get(key).compareTo(ecToSumMap.get(key)));
	}
	public static void verifyAggregateHourSums(final Map<String,BigDecimal> ecToHoursMap, TkTimeBlockAggregate aggregate, int flsaWeek) {
		TkTestUtils.verifyAggregateHourSums("", ecToHoursMap, aggregate, flsaWeek);
	}


	/**
	 * Helper method to generate time blocks suitable for db persistence in
	 * unit tests.
	 */
	public static List<TimeBlock> createUniformActualTimeBlocks(TimesheetDocument timesheetDocument, Assignment assignment, String earnCode, DateTime start, int days, BigDecimal hours, BigDecimal amount) {
		TimeBlockService service = TkServiceLocator.getTimeBlockService();
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();

		for (int i=0; i<days; i++) {
			DateTime ci = start.plusDays(i);
			DateTime co = ci.plusHours(hours.intValue());
			Timestamp tsin = new Timestamp(ci.getMillis());
			Timestamp tsout = new Timestamp(co.getMillis());

			blocks.addAll(service.buildTimeBlocks(assignment, earnCode, timesheetDocument, tsin, tsout, hours, amount, false, false, TKContext.getPrincipalId()));
		}

		return blocks;
	}

	public static Map<Timestamp, BigDecimal> getDateToHoursMap(TimeBlock timeBlock, TimeHourDetail timeHourDetail) {
		Map<Timestamp, BigDecimal> dateToHoursMap = new HashMap<Timestamp, BigDecimal>();
		DateTime beginTime = new DateTime(timeBlock.getBeginTimestamp());
		DateTime endTime = new DateTime(timeBlock.getEndTimestamp());

		Days d = Days.daysBetween(beginTime, endTime);
		int numberOfDays = d.getDays();
		if (numberOfDays < 1) {
			dateToHoursMap.put(timeBlock.getBeginTimestamp(), timeHourDetail.getHours());
			return dateToHoursMap;
		}
		DateTime currentTime = beginTime;
		for (int i = 0; i < numberOfDays; i++) {
			DateTime nextDayAtMidnight = new DateTime(currentTime.plusDays(1).getMillis());
			nextDayAtMidnight = nextDayAtMidnight.hourOfDay().setCopy(12);
			nextDayAtMidnight = nextDayAtMidnight.minuteOfDay().setCopy(0);
			nextDayAtMidnight = nextDayAtMidnight.secondOfDay().setCopy(0);
			nextDayAtMidnight = nextDayAtMidnight.millisOfSecond().setCopy(0);
			Duration dur = new Duration(currentTime, nextDayAtMidnight);
			long duration = dur.getStandardSeconds();
			BigDecimal hrs = new BigDecimal(duration / 3600, TkConstants.MATH_CONTEXT);
			dateToHoursMap.put(new Timestamp(currentTime.getMillis()), hrs);
			currentTime = nextDayAtMidnight;
		}
		Duration dur = new Duration(currentTime, endTime);
		long duration = dur.getStandardSeconds();
		BigDecimal hrs = new BigDecimal(duration / 3600, TkConstants.MATH_CONTEXT);
		dateToHoursMap.put(new Timestamp(currentTime.getMillis()), hrs);

		return dateToHoursMap;
	}

	public static Date createDate(int month, int day, int year, int hours, int minutes, int seconds){
		DateTime dt = new DateTime(year, month, day, hours, minutes, seconds, 0);
		return new Date(dt.getMillis());
	}


    /**
     * Method to obtain the HREF onclick='' value from the button when
     * the client side typically processes the request.
     * @param button
     */
    public static String getOnClickHref(HtmlElement button) {
        NamedNodeMap attributes = button.getAttributes();
        Node node = attributes.getNamedItem("onclick");

        //location.href='TimesheetSubmit.do?action=R&documentId=2000&methodToCall=approveTimesheet'
        String hrefRaw = node.getNodeValue();
        int sidx = hrefRaw.indexOf("='");

        return hrefRaw.substring(sidx+2, hrefRaw.length() - 1);
    }

}
