package org.kuali.kpme.core.api.mo;


import org.joda.time.DateTime;
import org.joda.time.LocalDate;


public interface Effective {
    /**
     * The localDate format of the effective date of the HrBusinessObject
     *
     * <p>
     * effectiveLocalDate of HrBusinessObject
     * <p>
     *
     * @return effectiveLocalDate of HrBusinessObject
     */
    public LocalDate getEffectiveLocalDate();

    /**
     * The timestamp of when this HrBusinessObject was last created/updated
     *
     * <p>
     * timestamp of HrBusinessObject
     * <p>
     *
     * @return timestamp of HrBusinessObject
     */
    public DateTime getCreateTime();

}
