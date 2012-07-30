package org.kuali.hr.lm.earncodesec;


public enum EarnCodeType {
    TIME("T"),
    LEAVE("L"),
    BOTH("B");

    public final String code;

    private EarnCodeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static EarnCodeType fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (EarnCodeType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Failed to locate the EarnCodeType with the given code: " + code);
    }
}
