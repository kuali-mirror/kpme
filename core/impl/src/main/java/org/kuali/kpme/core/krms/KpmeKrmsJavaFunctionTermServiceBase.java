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
