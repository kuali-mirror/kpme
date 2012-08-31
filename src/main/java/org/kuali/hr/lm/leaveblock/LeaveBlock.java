package org.kuali.hr.lm.leaveblock;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class LeaveBlock extends PersistableBusinessObjectBase {

	/**
     *
     */
	private static final long serialVersionUID = 1L;
	// use java sql date for now
	private String lmLeaveBlockId;
	private Date leaveDate;
	private String description;
	private String principalId;
	private String earnCode;
	private String earnCodeId;
	private String scheduleTimeOffId;
	private String accrualCategoryId;
	// private Boolean active;
	private BigDecimal leaveAmount = new BigDecimal("0.0");
	private String applyToYtdUsed;
	private String documentId;
	private String principalIdModified;
	private Timestamp timestamp;
	private Boolean accrualGenerated;
	private Long blockId;
	private String tkAssignmentId;
	private String requestStatus;
	private String leaveBlockType;
	
	private List<LeaveBlockHistory> leaveBlockHistories = new ArrayList<LeaveBlockHistory>();
	private EarnCode earnCodeObj;
	private SystemScheduledTimeOff systemScheduledTimeOffObj;
	private AccrualCategory accrualCategoryObj;

	@Transient
	private String earnCodeString;
	@Transient
	private boolean submit;
	@Transient
	private String reason;
	@Transient
	private Timestamp dateAndTime;

	private Long workArea;
	private Long jobNumber;
	private Long task;

	@Transient
	private String assignmentTitle;
	@Transient
	private Double accrualBalance;
	@Transient
	private String calendarId;

	public static class Builder {

		// required parameters for the constructor
		private final Date leaveDate;
		private final String principalId;
		private final String documentId;
		private final String earnCode;
		private final BigDecimal leaveAmount;

		private String description = null;
		private String applyToYtdUsed = null;
		private String principalIdModified = null;
		private Timestamp timestamp = null;
		private Boolean accrualGenerated = Boolean.FALSE;
		private Long blockId = 0L;
		private String earnCodeId;
		private String scheduleTimeOffId;
		private String accrualCategoryId;
		private String tkAssignmentId;
		private String requestStatus;
		private Long workArea;
		private Long jobNumber;
		private Long task;
		private String leaveBlockType;

		public Builder(DateTime leaveBlockDate, String documentId,
				String principalId, String earnCode, BigDecimal leaveAmount) {
			this.leaveDate = new java.sql.Date(leaveBlockDate.toDate()
					.getTime());
			this.documentId = documentId;
			this.principalId = principalId;
			this.earnCode = earnCode;
			this.leaveAmount = leaveAmount;
		}

		// validations could be done in the builder methods below

		public Builder description(String val) {
			this.description = val;
			return this;
		}

		public Builder applyToYtdUsed(String val) {
			this.applyToYtdUsed = val;
			return this;
		}

		public Builder principalIdModified(String val) {
			this.principalIdModified = val;
			return this;
		}

		public Builder timestamp(Timestamp val) {
			this.timestamp = val;
			return this;
		}

		public Builder accrualGenerated(Boolean val) {
			this.accrualGenerated = val;
			return this;
		}

		public Builder blockId(Long val) {
			this.blockId = val;
			return this;
		}

		public Builder earnCodeId(String val) {
			this.earnCodeId = val;
			return this;
		}

		// TODO: need to hook up the objcets to get the real ids
		public Builder scheduleTimeOffId(String val) {
			this.scheduleTimeOffId = val;
			return this;
		}

		public Builder accrualCategoryId(String val) {
			this.accrualCategoryId = val;
			return this;
		}

		public Builder tkAssignmentId(String val) {
			this.tkAssignmentId = val;
			return this;
		}

		public Builder workArea(Long val) {
			this.workArea = val;
			return this;
		}

		public Builder jobNumber(Long val) {
			this.jobNumber = val;
			return this;
		}

		public Builder task(Long val) {
			this.task = val;
			return this;
		}

		public Builder requestStatus(String val) {
			this.requestStatus = val;
			return this;
		}
		
		public Builder leaveBlockType(String leaveBlockType) {
			this.leaveBlockType = leaveBlockType;
			return this;
		}

		public LeaveBlock build() {
			return new LeaveBlock(this);
		}

	}

	private LeaveBlock(Builder builder) {
		leaveDate = builder.leaveDate;
		description = builder.description;
		principalId = builder.principalId;
		earnCode = builder.earnCode;
		leaveAmount = builder.leaveAmount;
		applyToYtdUsed = builder.applyToYtdUsed;
		documentId = builder.documentId;
		principalIdModified = builder.principalIdModified;
		timestamp = builder.timestamp;
		accrualGenerated = builder.accrualGenerated;
		blockId = builder.blockId;
		earnCodeId = builder.earnCodeId;
		scheduleTimeOffId = builder.scheduleTimeOffId;
		accrualCategoryId = builder.accrualCategoryId;
		tkAssignmentId = builder.tkAssignmentId;
		requestStatus = builder.requestStatus;
		workArea = builder.workArea;
		jobNumber = builder.jobNumber;
		task = builder.task;
		leaveBlockType = builder.leaveBlockType;
		// TODO: need to hook up leaveCodeObj, systemScheduledTimeOffObj,
		// accrualCategoryObj, and ids for individual obj
	}

	public LeaveBlock() {

	}

	public String getAccrualCategoryId() {
		return accrualCategoryId;
	}

	public void setAccrualCategoryId(String accrualCategoryId) {
		this.accrualCategoryId = accrualCategoryId;
	}

	public Boolean getAccrualGenerated() {
		return accrualGenerated;
	}

	public void setAccrualGenerated(Boolean accrualGenerated) {
		this.accrualGenerated = accrualGenerated;
	}

	// public Boolean getActive() {
	// return active;
	// }
	//
	// public void setActive(Boolean active) {
	// this.active = active;
	// }

	public String getApplyToYtdUsed() {
		return applyToYtdUsed;
	}

	public boolean getSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}

	public void setApplyToYtdUsed(String applyToYtdUsed) {
		this.applyToYtdUsed = applyToYtdUsed;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public BigDecimal getLeaveAmount() {
		return leaveAmount;
	}

	public void setLeaveAmount(BigDecimal leaveAmount) {
		this.leaveAmount = leaveAmount;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getLmLeaveBlockId() {
		return lmLeaveBlockId;
	}

	public void setLmLeaveBlockId(String lmLeaveBlockId) {
		this.lmLeaveBlockId = lmLeaveBlockId;
	}

	public String getPrincipalIdModified() {
		return principalIdModified;
	}

	public void setPrincipalIdModified(String principalIdModified) {
		this.principalIdModified = principalIdModified;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getScheduleTimeOffId() {
		return scheduleTimeOffId;
	}

	public void setScheduleTimeOffId(String scheduleTimeOffId) {
		this.scheduleTimeOffId = scheduleTimeOffId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public AccrualCategory getAccrualCategoryObj() {
		return accrualCategoryObj;
	}

	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}

	public SystemScheduledTimeOff getSystemScheduledTimeOffObj() {
		return systemScheduledTimeOffObj;
	}

	public void setSystemScheduledTimeOffObj(
			SystemScheduledTimeOff systemScheduledTimeOffObj) {
		this.systemScheduledTimeOffObj = systemScheduledTimeOffObj;
	}

	public String getTkAssignmentId() {
		return tkAssignmentId;
	}

	public void setTkAssignmentId(String tkAssignmentId) {
		this.tkAssignmentId = tkAssignmentId;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public List<LeaveBlockHistory> getLeaveBlockHistories() {
		return leaveBlockHistories;
	}

	public void setLeaveBlockHistories(
			List<LeaveBlockHistory> leaveBlockHistories) {
		this.leaveBlockHistories = leaveBlockHistories;
	}

	public String getEarnCodeString() {
		try {
			System.out.println("Fetching code >>");
			EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCodeById(this.earnCode);
					
			earnCodeString = earnCode.getDescription();
		} catch (Exception ex) {
			System.out
					.println("error came while fetching leave code String >>>>>>");
		}
		return earnCodeString;
	}

	public void setEarnCodeString(String earnCodeString) {
		this.earnCodeString = earnCodeString;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	public Long getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(Long jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Long getTask() {
		return task;
	}

	public void setTask(Long task) {
		this.task = task;
	}

	public String getAssignmentTitle() {
		StringBuilder b = new StringBuilder();

		if (this.workArea != null) {
			WorkArea wa = TkServiceLocator.getWorkAreaService().getWorkArea(
					this.workArea, TKUtils.getCurrentDate());
			if (wa != null) {
				b.append(wa.getDescription());
			}
			Task task = TkServiceLocator.getTaskService().getTask(
					this.getTask(), this.getLeaveDate());
			if (task != null) {
				// do not display task description if the task is the default
				// one
				// default task is created in getTask() of TaskService
				if (!task.getDescription()
						.equals(TkConstants.TASK_DEFAULT_DESP)) {
					b.append("-" + task.getDescription());
				}
			}
		}
		return b.toString();
	}

	public void setAssignmentTitle(String assignmentTitle) {
		this.assignmentTitle = assignmentTitle;
	}

	public Double getAccrualBalance() {
		if(this.accrualCategoryId != null) {
			AccrualCategory accrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(this.accrualCategoryId);
			if(accrualCategory != null && accrualCategory.getShowOnGrid() != null && StringUtils.equals(accrualCategory.getShowOnGrid(), "Y")){
				accrualBalance = TkServiceLocator.getLeaveBlockService().calculateAccrualbalance(this.timestamp, this.accrualCategoryId, this.principalId);
			}
		}
		return accrualBalance;
	}

	public void setAccrualBalance(Double accrualBalance) {
		this.accrualBalance = accrualBalance;
	}

	public String getCalendarId() {
		PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(this.principalId, TKUtils.getCurrentDate());
		Calendar pcal= null;
		if(principalHRAttributes != null) {
			//pcal = principalHRAttributes.getCalendar() != null ? principalHRAttributes.getCalendar() : principalHRAttributes.getLeaveCalObj() ;
			pcal = principalHRAttributes.getLeaveCalObj() != null ? principalHRAttributes.getLeaveCalObj() : principalHRAttributes.getCalendar();
            if(pcal!= null) {
				CalendarEntries calEntries = TkServiceLocator.getCalendarEntriesService().getCurrentCalendarEntriesByCalendarId(pcal.getHrCalendarId(), this.leaveDate);
				if(calEntries != null) {
					this.calendarId = calEntries.getHrCalendarEntriesId();
				}
			}
		}
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public String getEarnCodeId() {
		return earnCodeId;
	}

	public void setEarnCodeId(String earnCodeId) {
		this.earnCodeId = earnCodeId;
	}

	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}

	public String getLeaveBlockType() {
		return leaveBlockType;
	}

	public void setLeaveBlockType(String leaveBlockType) {
		this.leaveBlockType = leaveBlockType;
	}

    public boolean isEditable() {
        return TkServiceLocator.getPermissionsService().canEditLeaveBlock(this);
    }

    public boolean isDeletable() {
        return TkServiceLocator.getPermissionsService().canDeleteLeaveBlock(this);
    }
}