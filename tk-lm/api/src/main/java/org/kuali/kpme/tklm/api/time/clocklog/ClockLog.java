package org.kuali.kpme.tklm.api.time.clocklog;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Collection;

@XmlRootElement(name = ClockLog.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = ClockLog.Constants.TYPE_NAME, propOrder = {
        ClockLog.Elements.TK_CLOCK_LOG_ID,
        ClockLog.Elements.TASK,
        ClockLog.Elements.CLOCKED_BY_MISSED_PUNCH,
        ClockLog.Elements.UNAPPROVED_I_P,
        ClockLog.Elements.ASSIGNMENT_DESCRIPTION_KEY,
        ClockLog.Elements.DEPT,
        ClockLog.Elements.CLOCK_DATE_TIME,
        ClockLog.Elements.DOCUMENT_ID,
        ClockLog.Elements.PRINCIPAL_ID,
        ClockLog.Elements.JOB_NUMBER,
        ClockLog.Elements.CLOCK_ACTION,
        ClockLog.Elements.IP_ADDRESS,
        ClockLog.Elements.USER_PRINCIPAL_ID,
        ClockLog.Elements.CREATE_TIME,
        ClockLog.Elements.CLOCK_TIMESTAMP_TIMEZONE,
        ClockLog.Elements.NEXT_VALID_CLOCK_ACTION,
        ClockLog.Elements.JOB,
        //ClockLog.Elements.WORK_AREA_OBJ,
        //ClockLog.Elements.TASK_OBJ,
        ClockLog.Elements.WORK_AREA,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class ClockLog
        extends AbstractDataTransferObject
        implements ClockLogContract
{

    private static final long serialVersionUID = 7754198231707731234L;
    @XmlElement(name = Elements.TK_CLOCK_LOG_ID, required = false)
    private final String tkClockLogId;
    @XmlElement(name = Elements.TASK, required = false)
    private final Long task;
    @XmlElement(name = Elements.CLOCKED_BY_MISSED_PUNCH, required = false)
    private final boolean clockedByMissedPunch;
    @XmlElement(name = Elements.UNAPPROVED_I_P, required = false)
    private final boolean unapprovedIP;
    @XmlElement(name = Elements.ASSIGNMENT_DESCRIPTION_KEY, required = false)
    private final String assignmentDescriptionKey;
    @XmlElement(name = Elements.DEPT, required = false)
    private final String dept;
    @XmlElement(name = Elements.CLOCK_DATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime clockDateTime;
    @XmlElement(name = Elements.DOCUMENT_ID, required = false)
    private final String documentId;
    @XmlElement(name = Elements.PRINCIPAL_ID, required = false)
    private final String principalId;
    @XmlElement(name = Elements.JOB_NUMBER, required = false)
    private final Long jobNumber;
    @XmlElement(name = Elements.CLOCK_ACTION, required = false)
    private final String clockAction;
    @XmlElement(name = Elements.IP_ADDRESS, required = false)
    private final String ipAddress;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime createTime;
    @XmlElement(name = Elements.CLOCK_TIMESTAMP_TIMEZONE, required = false)
    private final String clockTimestampTimezone;
    @XmlElement(name = Elements.NEXT_VALID_CLOCK_ACTION, required = false)
    private final String nextValidClockAction;
    @XmlElement(name = Elements.JOB, required = false)
    private final Job job;
    //@XmlElement(name = Elements.WORK_AREA_OBJ, required = false)
    //private final WorkArea workAreaObj;
    //@XmlElement(name = Elements.TASK_OBJ, required = false)
    //private final Task taskObj;
    @XmlElement(name = Elements.WORK_AREA, required = false)
    private final Long workArea;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private ClockLog() {
        this.tkClockLogId = null;
        this.task = null;
        this.clockedByMissedPunch = false;
        this.unapprovedIP = false;
        this.assignmentDescriptionKey = null;
        this.dept = null;
        this.clockDateTime = null;
        this.documentId = null;
        this.principalId = null;
        this.jobNumber = null;
        this.clockAction = null;
        this.ipAddress = null;
        this.userPrincipalId = null;
        this.createTime = null;
        this.clockTimestampTimezone = null;
        this.nextValidClockAction = null;
        this.job = null;
        //this.workAreaObj = null;
        //this.taskObj = null;
        this.workArea = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private ClockLog(Builder builder) {
        this.tkClockLogId = builder.getTkClockLogId();
        this.task = builder.getTask();
        this.clockedByMissedPunch = builder.isClockedByMissedPunch();
        this.unapprovedIP = builder.isUnapprovedIP();
        this.assignmentDescriptionKey = builder.getAssignmentDescriptionKey();
        this.dept = builder.getDept();
        this.clockDateTime = builder.getClockDateTime();
        this.documentId = builder.getDocumentId();
        this.principalId = builder.getPrincipalId();
        this.jobNumber = builder.getJobNumber();
        this.clockAction = builder.getClockAction();
        this.ipAddress = builder.getIpAddress();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.createTime = builder.getCreateTime();
        this.clockTimestampTimezone = builder.getClockTimestampTimezone();
        this.nextValidClockAction = builder.getNextValidClockAction();
        this.job = builder.getJob() == null ? null : builder.getJob().build();
        //this.workAreaObj = builder.getWorkAreaObj() == null ? null : builder.getWorkAreaObj().build();
        //this.taskObj = builder.getTaskObj() == null ? null : builder.getTaskObj().build();
        this.workArea = builder.getWorkArea();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public String getTkClockLogId() {
        return this.tkClockLogId;
    }

    @Override
    public Long getTask() {
        return this.task;
    }

    @Override
    public boolean isClockedByMissedPunch() {
        return this.clockedByMissedPunch;
    }

    @Override
    public boolean isUnapprovedIP() {
        return this.unapprovedIP;
    }

    @Override
    public String getAssignmentDescriptionKey() {
        return this.assignmentDescriptionKey;
    }

    @Override
    public String getDept() {
        return this.dept;
    }

    @Override
    public DateTime getClockDateTime() {
        return this.clockDateTime;
    }

    @Override
    public String getDocumentId() {
        return this.documentId;
    }

    @Override
    public String getPrincipalId() {
        return this.principalId;
    }

    @Override
    public Long getJobNumber() {
        return this.jobNumber;
    }

    @Override
    public String getClockAction() {
        return this.clockAction;
    }

    @Override
    public String getIpAddress() {
        return this.ipAddress;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
    }

    @Override
    public String getClockTimestampTimezone() {
        return this.clockTimestampTimezone;
    }

    @Override
    public String getNextValidClockAction() {
        return this.nextValidClockAction;
    }

    @Override
    public Job getJob() {
        return this.job;
    }

    /*@Override
    public WorkArea getWorkAreaObj() {
        return this.workAreaObj;
    }

    @Override
    public Task getTaskObj() {
        return this.taskObj;
    }*/

    @Override
    public Long getWorkArea() {
        return this.workArea;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }


    /**
     * A builder which can be used to construct {@link ClockLog} instances.  Enforces the constraints of the {@link ClockLogContract}.
     *
     */
    public final static class Builder
            implements Serializable, ClockLogContract, ModelBuilder
    {

        private static final long serialVersionUID = -5655909845940279134L;
        private String tkClockLogId;
        private Long task;
        private boolean clockedByMissedPunch;
        private boolean unapprovedIP;
        private String assignmentDescriptionKey;
        private String dept;
        private DateTime clockDateTime;
        private String documentId;
        private String principalId;
        private Long jobNumber;
        private String clockAction;
        private String ipAddress;
        private String userPrincipalId;
        private DateTime createTime;
        private String clockTimestampTimezone;
        private String nextValidClockAction;
        private Job.Builder job;
        //private WorkArea.Builder workAreaObj;
        //private Task.Builder taskObj;
        private Long workArea;
        private Long versionNumber;
        private String objectId;

        private Builder() { }

        public static Builder create() {
            return new Builder();
        }

        public static Builder create(ClockLogContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create();
            builder.setTkClockLogId(contract.getTkClockLogId());
            builder.setTask(contract.getTask());
            builder.setClockedByMissedPunch(contract.isClockedByMissedPunch());
            builder.setUnapprovedIP(contract.isUnapprovedIP());
            builder.setAssignmentDescriptionKey(contract.getAssignmentDescriptionKey());
            builder.setDept(contract.getDept());
            builder.setClockDateTime(contract.getClockDateTime());
            builder.setDocumentId(contract.getDocumentId());
            builder.setPrincipalId(contract.getPrincipalId());
            builder.setJobNumber(contract.getJobNumber());
            builder.setClockAction(contract.getClockAction());
            builder.setIpAddress(contract.getIpAddress());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setCreateTime(contract.getCreateTime());
            builder.setClockTimestampTimezone(contract.getClockTimestampTimezone());
            builder.setNextValidClockAction(contract.getNextValidClockAction());
            builder.setJob(contract.getJob() == null ? null : Job.Builder.create(contract.getJob()));
            //builder.setWorkAreaObj(contract.getWorkAreaObj() == null ? null : WorkArea.Builder.create(contract.getWorkAreaObj()));
            //builder.setTaskObj(contract.getTaskObj() == null ? null : Task.Builder.create(contract.getTaskObj()));
            builder.setWorkArea(contract.getWorkArea());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public ClockLog build() {
            return new ClockLog(this);
        }

        @Override
        public String getTkClockLogId() {
            return this.tkClockLogId;
        }

        @Override
        public Long getTask() {
            return this.task;
        }

        @Override
        public boolean isClockedByMissedPunch() {
            return this.clockedByMissedPunch;
        }

        @Override
        public boolean isUnapprovedIP() {
            return this.unapprovedIP;
        }

        @Override
        public String getAssignmentDescriptionKey() {
            return this.assignmentDescriptionKey;
        }

        @Override
        public String getDept() {
            return this.dept;
        }

        @Override
        public DateTime getClockDateTime() {
            return this.clockDateTime;
        }

        @Override
        public String getDocumentId() {
            return this.documentId;
        }

        @Override
        public String getPrincipalId() {
            return this.principalId;
        }

        @Override
        public Long getJobNumber() {
            return this.jobNumber;
        }

        @Override
        public String getClockAction() {
            return this.clockAction;
        }

        @Override
        public String getIpAddress() {
            return this.ipAddress;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
        }

        @Override
        public String getClockTimestampTimezone() {
            return this.clockTimestampTimezone;
        }

        @Override
        public String getNextValidClockAction() {
            return this.nextValidClockAction;
        }

        @Override
        public Job.Builder getJob() {
            return this.job;
        }

        /*@Override
        public WorkArea.Builder getWorkAreaObj() {
            return this.workAreaObj;
        }

        @Override
        public Task.Builder getTaskObj() {
            return this.taskObj;
        }*/

        @Override
        public Long getWorkArea() {
            return this.workArea;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setTkClockLogId(String tkClockLogId) {
            this.tkClockLogId = tkClockLogId;
        }

        public void setTask(Long task) {
            this.task = task;
        }

        public void setClockedByMissedPunch(boolean clockedByMissedPunch) {
            this.clockedByMissedPunch = clockedByMissedPunch;
        }

        public void setUnapprovedIP(boolean unapprovedIP) {
            this.unapprovedIP = unapprovedIP;
        }

        public void setAssignmentDescriptionKey(String assignmentDescriptionKey) {
            this.assignmentDescriptionKey = assignmentDescriptionKey;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public void setClockDateTime(DateTime clockDateTime) {
            this.clockDateTime = clockDateTime;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        public void setPrincipalId(String principalId) {
            this.principalId = principalId;
        }

        public void setJobNumber(Long jobNumber) {
            this.jobNumber = jobNumber;
        }

        public void setClockAction(String clockAction) {
            this.clockAction = clockAction;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            this.userPrincipalId = userPrincipalId;
        }

        public void setCreateTime(DateTime createTime) {
            this.createTime = createTime;
        }

        public void setClockTimestampTimezone(String clockTimestampTimezone) {
            this.clockTimestampTimezone = clockTimestampTimezone;
        }

        public void setNextValidClockAction(String nextValidClockAction) {
            this.nextValidClockAction = nextValidClockAction;
        }

        public void setJob(Job.Builder job) {
            this.job = job;
        }

        /*public void setWorkAreaObj(WorkArea.Builder workAreaObj) {
            this.workAreaObj = workAreaObj;
        }

        public void setTaskObj(Task.Builder taskObj) {
            this.taskObj = taskObj;
        }*/

        public void setWorkArea(Long workArea) {
            this.workArea = workArea;
        }

        public void setVersionNumber(Long versionNumber) {
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "clockLog";
        final static String TYPE_NAME = "ClockLogType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String TK_CLOCK_LOG_ID = "tkClockLogId";
        final static String TASK = "task";
        final static String CLOCKED_BY_MISSED_PUNCH = "clockedByMissedPunch";
        final static String UNAPPROVED_I_P = "unapprovedIP";
        final static String ASSIGNMENT_DESCRIPTION_KEY = "assignmentDescriptionKey";
        final static String DEPT = "dept";
        final static String CLOCK_DATE_TIME = "clockDateTime";
        final static String DOCUMENT_ID = "documentId";
        final static String PRINCIPAL_ID = "principalId";
        final static String JOB_NUMBER = "jobNumber";
        final static String CLOCK_ACTION = "clockAction";
        final static String IP_ADDRESS = "ipAddress";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String CREATE_TIME = "createTime";
        final static String CLOCK_TIMESTAMP_TIMEZONE = "clockTimestampTimezone";
        final static String NEXT_VALID_CLOCK_ACTION = "nextValidClockAction";
        final static String JOB = "job";
        /*final static String WORK_AREA_OBJ = "workAreaObj";
        final static String TASK_OBJ = "taskObj";*/
        final static String WORK_AREA = "workArea";

    }

}