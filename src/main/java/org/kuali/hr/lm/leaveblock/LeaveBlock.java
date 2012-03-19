package org.kuali.hr.lm.leaveblock;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Transient;

import org.joda.time.DateTime;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;
import org.kuali.rice.kns.service.KNSServiceLocator;

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
    private String leaveCode;
    private String leaveCodeId;
    private String scheduleTimeOffId;
    private String accrualCategoryId;
//    private Boolean active;
    private BigDecimal hours = new BigDecimal("0.0");
    private String applyToYtdUsed;
    private String documentId;
    private String principalIdModified;
    private Timestamp timestamp;
    private Boolean accrualGenerated;
    private Long blockId;
    private String tkAssignmentId;
    private String requestStatus;

    private List<LeaveBlockHistory> leaveBlockHistories = new ArrayList<LeaveBlockHistory>();
    private LeaveCode leaveCodeObj;
    private SystemScheduledTimeOff systemScheduledTimeOffObj;
    private AccrualCategory accrualCategoryObj;
    
    @Transient
    private String leaveCodeString;
    @Transient
    private Boolean submit;
    @Transient
    private String reason;
    @Transient
    private Timestamp dateAndTime;
    

    public static class Builder {

        // required parameters for the constructor
        private final Date leaveDate;
        private final String principalId;
        private final String documentId;
        private final String leaveCode;
        private final BigDecimal hours;


        private String description = null;
        private String applyToYtdUsed = null;
        private String principalIdModified = null;
        private Timestamp timestamp = null;
        private Boolean accrualGenerated = Boolean.FALSE;
        private Long blockId = 0L;
        private String leaveCodeId;
        private String scheduleTimeOffId;
        private String accrualCategoryId;
        private String tkAssignmentId;
        private String requestStatus;


        public Builder(DateTime leaveBlockDate, String documentId, String principalId, String leaveCode, BigDecimal hours) {
            this.leaveDate = new java.sql.Date(leaveBlockDate.toDate().getTime());
            this.documentId = documentId;
            this.principalId = principalId;
            this.leaveCode = leaveCode;
            this.hours = hours;
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

        public Builder leaveCodeId(String val) {
            this.leaveCodeId = val;
            return this;
        }

        //TODO: need to hook up the objcets to get the real ids
        public Builder scheduleTimeOffId(String val) {
            this.scheduleTimeOffId = val;
            return this;
        }

        public Builder accrualCategoryId(String val) {
            this.accrualCategoryId = val;
            return this;
        }

        public Builder tkAssignmentId(String val){
        	this.tkAssignmentId = val;
        	return this;
        }
        
        public Builder requestStatus(String val){
        	this.requestStatus = val;
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
        leaveCode = builder.leaveCode;
        hours = builder.hours;
        applyToYtdUsed = builder.applyToYtdUsed;
        documentId = builder.documentId;
        principalIdModified= builder.principalIdModified;
        timestamp = builder.timestamp;
        accrualGenerated = builder.accrualGenerated;
        blockId = builder.blockId;
        leaveCodeId = builder.leaveCodeId;
        scheduleTimeOffId = builder.scheduleTimeOffId;
        accrualCategoryId = builder.accrualCategoryId;
        tkAssignmentId = builder.tkAssignmentId;
        requestStatus = builder.requestStatus;
        // TODO: need to hook up leaveCodeObj, systemScheduledTimeOffObj, accrualCategoryObj, and ids for individual obj
    }


    public LeaveBlock() {

    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected LinkedHashMap toStringMapper() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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

//    public Boolean getActive() {
//        return active;
//    }
//
//    public void setActive(Boolean active) {
//        this.active = active;
//    }

    public String getApplyToYtdUsed() {
        return applyToYtdUsed;
    }

    public Boolean getSubmit() {
		return submit;
	}


	public void setSubmit(Boolean submit) {
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

    public BigDecimal getHours() {
        return hours;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public String getLeaveCodeId() {
        return leaveCodeId;
    }

    public void setLeaveCodeId(String leaveCodeId) {
        this.leaveCodeId = leaveCodeId;
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

    public LeaveCode getLeaveCodeObj() {
        return leaveCodeObj;
    }

    public void setLeaveCodeObj(LeaveCode leaveCodeObj) {
        this.leaveCodeObj = leaveCodeObj;
    }

    public SystemScheduledTimeOff getSystemScheduledTimeOffObj() {
        return systemScheduledTimeOffObj;
    }

    public void setSystemScheduledTimeOffObj(SystemScheduledTimeOff systemScheduledTimeOffObj) {
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


	public void setLeaveBlockHistories(List<LeaveBlockHistory> leaveBlockHistories) {
		this.leaveBlockHistories = leaveBlockHistories;
	}


	public String getLeaveCodeString() {
		try {
			System.out.println("Fetching code >>");
			LeaveCode leaveCode = TkServiceLocator.getLeaveCodeService().getLeaveCode(this.leaveCodeId);
			leaveCodeString = leaveCode.getDisplayName();
		} catch(Exception ex) {
			System.out.println("error came while fetching leave code String >>>>>>");
		}
		return leaveCodeString;
	}


	public void setLeaveCodeString(String leaveCodeString) {
		this.leaveCodeString = leaveCodeString;
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



}
