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
