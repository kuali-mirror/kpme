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
package org.kuali.hr.lm.leaveblock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeaveBlock extends PersistableBusinessObjectBase {

	private static final long serialVersionUID = -8240826812581295376L;
	
	private String lmLeaveBlockId;
	private Date leaveDate;
	private String description;
	private String principalId;
	private String earnCode;
	private String scheduleTimeOffId;
	private String accrualCategory;
	private BigDecimal leaveAmount = new BigDecimal("0.0");
	private String documentId;
	private String principalIdModified;
	private Timestamp timestamp;
	private Boolean accrualGenerated;
	private Long blockId;
	private String requestStatus;
	private String leaveBlockType;
	private String documentStatus;
	
	private List<LeaveBlockHistory> leaveBlockHistories = new ArrayList<LeaveBlockHistory>();
    private String leaveRequestDocumentId;
    
    private Timestamp beginTimestamp;
    private Timestamp endTimestamp;
    
	@Transient
	private boolean submit;
	@Transient
	private String reason;
	@Transient
	private Timestamp dateAndTime;

	private Long workArea;
	private Long jobNumber;
	private Long task;
	private String assignmentKey;
	
	@Transient
	private String assignmentTitle;
	@Transient
	private String calendarId;
	@Transient
	private String planningDescription;

	private String transactionalDocId;

	public String getAccrualCategoryRuleId() {
		AccrualCategory category = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, getLeaveLocalDate());
		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, getLeaveLocalDate());
		AccrualCategoryRule aRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(category, getLeaveLocalDate(), pha.getServiceLocalDate());
		return ObjectUtils.isNull(aRule) ? null : aRule.getLmAccrualCategoryRuleId();
	}
	
	public static class Builder {

		// required parameters for the constructor
		private final Date leaveDate;
		private final String principalId;
		private final String documentId;
		private final String earnCode;
		private final BigDecimal leaveAmount;

		private String description = null;
		private String principalIdModified = null;
		private Timestamp timestamp = null;
		private Boolean accrualGenerated = Boolean.FALSE;
		private Long blockId = 0L;
		private String scheduleTimeOffId;
		private String accrualCategory;
		private String requestStatus;
		private Long workArea;
		private Long jobNumber;
		private Long task;
		private String leaveBlockType;
		
		public Builder(LocalDate leaveDate, String documentId,
				String principalId, String earnCode, BigDecimal leaveAmount) {
			this.leaveDate = leaveDate.toDate();
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

		// TODO: need to hook up the objcets to get the real ids
		public Builder scheduleTimeOffId(String val) {
			this.scheduleTimeOffId = val;
			return this;
		}

		public Builder accrualCategory(String val) {
			this.accrualCategory = val;
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
		documentId = builder.documentId;
		principalIdModified = builder.principalIdModified;
		timestamp = builder.timestamp;
		accrualGenerated = builder.accrualGenerated;
		blockId = builder.blockId;
		scheduleTimeOffId = builder.scheduleTimeOffId;
		accrualCategory = builder.accrualCategory;
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

	public String getAccrualCategory() {
		return accrualCategory;
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}

	public Boolean getAccrualGenerated() {
		return accrualGenerated;
	}

	public void setAccrualGenerated(Boolean accrualGenerated) {
		this.accrualGenerated = accrualGenerated;
	}

	public boolean getSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
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
	
	public LocalDate getLeaveLocalDate() {
		return leaveDate != null ? LocalDate.fromDateFields(leaveDate) : null;
	}
	
	public void setLeaveLocalDate(LocalDate leaveLocalDate) {
		this.leaveDate = leaveLocalDate != null ? leaveLocalDate.toDate() : null;
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

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

    public String getRequestStatusString() {
        String status = LMConstants.REQUEST_STATUS_STRINGS.get(getRequestStatus());
        return status == null ? "usage" : status;
    }

	public List<LeaveBlockHistory> getLeaveBlockHistories() {
		return leaveBlockHistories;
	}

	public void setLeaveBlockHistories(
			List<LeaveBlockHistory> leaveBlockHistories) {
		this.leaveBlockHistories = leaveBlockHistories;
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
					this.workArea, LocalDate.now());
			if (wa != null) {
				b.append(wa.getDescription());
			}
			Task task = TkServiceLocator.getTaskService().getTask(
					this.getTask(), this.getLeaveLocalDate());
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

	public String getCalendarId() {
		PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(this.principalId, LocalDate.now());
		Calendar pcal= null;
		if(principalHRAttributes != null) {
			//pcal = principalHRAttributes.getCalendar() != null ? principalHRAttributes.getCalendar() : principalHRAttributes.getLeaveCalObj() ;
			pcal = principalHRAttributes.getLeaveCalObj() != null ? principalHRAttributes.getLeaveCalObj() : principalHRAttributes.getCalendar();
            if(pcal!= null) {
				CalendarEntry calEntries = TkServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(pcal.getHrCalendarId(), new DateTime(this.leaveDate));
				if(calEntries != null) {
					this.calendarId = calEntries.getHrCalendarEntryId();
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
	
	public String getEarnCodeDescription() {
		String earnCodeDescription = "";
		
		EarnCode earnCodeObj = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, getLeaveLocalDate());
		if (earnCodeObj != null) {
			earnCodeDescription = earnCodeObj.getDescription();
		}
		
		return earnCodeDescription;
	}

	public String getLeaveBlockType() {
		return leaveBlockType;
	}

	public void setLeaveBlockType(String leaveBlockType) {
		this.leaveBlockType = leaveBlockType;
	}

    public boolean isEditable() {
        return TkServiceLocator.getLMPermissionService().canEditLeaveBlock(TKContext.getPrincipalId(), this);
    }

    public boolean isDeletable() {
        return TkServiceLocator.getLMPermissionService().canDeleteLeaveBlock(TKContext.getPrincipalId(), this);
    }
    
    public String getAssignmentKey() {
        if (assignmentKey == null) {
            this.setAssignmentKey(TKUtils.formatAssignmentKey(jobNumber, workArea, task));
        }
        return assignmentKey;
    }
    public void setAssignmentKey(String assignmentDescription) {
        this.assignmentKey = assignmentDescription;
    }

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

    public String getLeaveRequestDocumentId() {
        return leaveRequestDocumentId;
    }

    public void setLeaveRequestDocumentId(String leaveRequestDocumentId) {
        this.leaveRequestDocumentId = leaveRequestDocumentId;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.principalId)
                .append(this.jobNumber)
                .append(this.workArea)
                .append(this.task)
                .append(this.earnCode)
                .append(this.leaveDate)
                .append(this.leaveAmount)
                .append(this.accrualCategory)
                .append(this.earnCode)
                .append(this.description)
                .append(this.leaveBlockType)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        LeaveBlock leaveBlock = (LeaveBlock) obj;
        return new EqualsBuilder()
                .append(principalId, leaveBlock.principalId)
                .append(jobNumber, leaveBlock.jobNumber)
                .append(workArea, leaveBlock.workArea)
                .append(task, leaveBlock.task)
                .append(earnCode, leaveBlock.earnCode)
                .append(leaveDate, leaveBlock.leaveDate)
                .append(leaveAmount, leaveBlock.leaveAmount)
                .append(accrualCategory, leaveBlock.accrualCategory)
                .append(earnCode, leaveBlock.earnCode)
                .append(description, leaveBlock.description)
                .append(leaveBlockType, leaveBlock.leaveBlockType)
                .isEquals();
    }
    
    public String getPlanningDescription() {
    	if(this.getRequestStatus().equals(LMConstants.REQUEST_STATUS.DEFERRED)) {
    		List<LeaveRequestDocument> lrdList = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(this.getLmLeaveBlockId());
    		if(CollectionUtils.isNotEmpty(lrdList)) {
    			for(LeaveRequestDocument lrd : lrdList) {    				
    				DocumentStatus status = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(lrd.getDocumentNumber());
    				if(status != null && DocumentStatus.CANCELED.getCode().equals(status.getCode())) {
	    				String requestDescription = "";
						if(StringUtils.isNotEmpty(this.getDescription())) {
							requestDescription = this.getDescription() + " <br/>";
						}
						String actionDateString = TKUtils.formatDate(lrd.getDocumentHeader().getWorkflowDocument().getDateFinalized().toLocalDate());
						requestDescription += "Approval deferred on " + actionDateString + ". Reason: " + lrd.getDescription();
			    		this.setPlanningDescription(requestDescription);
						return planningDescription;
    				}
    			}
    		}
    	}
    	this.setPlanningDescription(this.getDescription());
    	return planningDescription;
    }

	public void setPlanningDescription(String planningDescription) {
		this.planningDescription = planningDescription;
	}

	public void setTransactionDocId(String documentHeaderId) {
		transactionalDocId = documentHeaderId;		
	}
	
	public String getTransactionalDocId() {
		return transactionalDocId;
	}

	public Timestamp getBeginTimestamp() {
		return beginTimestamp;
	}

	public void setBeginTimestamp(Timestamp beginTimestamp) {
		this.beginTimestamp = beginTimestamp;
	}

	public Timestamp getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(Timestamp endTimestamp) {
		this.endTimestamp = endTimestamp;
	}
}


