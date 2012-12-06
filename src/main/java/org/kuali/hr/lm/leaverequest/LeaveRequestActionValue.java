package org.kuali.hr.lm.leaverequest;


public enum LeaveRequestActionValue {
    DEFER("W", "Defer"),
    APPROVE("A", "Approve"),
    DISAPPROVE("D", "Disapprove"),
    NO_ACTION(" ", "No Action");

    public final String code;
    public final String label;

    private LeaveRequestActionValue(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return this.code;
    }

    public String getLabel() {
        return this.label;
    }

    public static LeaveRequestActionValue fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (LeaveRequestActionValue type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Failed to locate the LeaveRequestAction with the given code: " + code);
    }

}
