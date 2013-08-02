package org.kuali.kpme.core.krms;


import org.apache.commons.lang.StringUtils;

public abstract class KpmeKrmsJavaFunctionTermServiceBase {
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String[] restrictedElements = { " ", "`", "@", "#", "!", "$", "%", "^", "&", "*", "(", ")", "[", "]", "{",
            "}", "|", "\\", "/", "?", "<", ">", ",", ";", ":", "'", "\"", "`", "+" };

    protected String[] buildArrayFromCommaList(String commaList) {
        String[] newArray = commaList.split(","); // MIT Equity Interests
        if (commaList != null && newArray.length == 0) {
            newArray = new String[] { commaList.trim() };
        }
        return newArray;
    }

    /**
     *
     * This method returns 'true' if 'stringToCheck' does not contain a special character, otherwise returns 'false'.
     *
     * @param stringToCheck
     * @return
     */
    protected String specialCharacterRule(String stringToCheck) {
        for (String element : restrictedElements) {
            if (StringUtils.equalsIgnoreCase(element, stringToCheck)) {
                return FALSE;
            }
        }
        return TRUE;
    }
}
