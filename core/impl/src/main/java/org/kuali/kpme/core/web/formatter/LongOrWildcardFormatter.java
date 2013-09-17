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
package org.kuali.kpme.core.web.formatter;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.core.web.format.FormatException;
import org.kuali.rice.core.web.format.Formatter;


public class LongOrWildcardFormatter extends Formatter {
    protected Object convertToObject(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        if (StringUtils.equals("%", string)) {
            return -1L;
        }
        try {
            return new Long(string);
        }
        catch (NumberFormatException e) {
            throw new FormatException("parsing", RiceKeyConstants.ERROR_LONG, string, e);
        }
    }

    @Override
    public Object format(Object value) {
        if (value != null && value instanceof Long) {
            Long val = (Long)value;
            if (val == -1L) {
                return "%";
            }
        }
        return (value == null ? null : value.toString());
    }
}
