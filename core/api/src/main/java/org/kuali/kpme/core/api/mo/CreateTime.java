package org.kuali.kpme.core.api.mo;


import org.joda.time.DateTime;

public interface CreateTime {
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
