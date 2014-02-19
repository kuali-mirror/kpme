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
package org.kuali.kpme.core;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.core.web.format.FormatException;

import java.beans.PropertyEditorSupport;
import java.io.Serializable;
import java.sql.Time;

public class SqlTimeEditor extends PropertyEditorSupport implements Serializable {

    private static final DateTimeFormatter sdFormat = DateTimeFormat.forPattern("hh:mm aa");
    @Override
    public String getAsText() {
        if (this.getValue() != null && this.getValue() instanceof Time) {
            Time time = (Time) this.getValue();
            return sdFormat.print(new LocalTime(time)).toString();
        }
        return null;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Object o = null;

        try {
            o = new Time(LocalTime.parse(text, sdFormat).toDateTimeToday().getMillis());
        } catch (Exception e) {
            throw new FormatException("parsing", "error.invalidTime", text, e);
        }

        this.setValue(o);
    }
}
