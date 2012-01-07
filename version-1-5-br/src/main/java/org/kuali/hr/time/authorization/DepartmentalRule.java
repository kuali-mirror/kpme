package org.kuali.hr.time.authorization;

/**
 * A contract designating the DepartmentalRule status of the implementing
 * class.
 *
 * See:
 * https://wiki.kuali.org/display/KPME/Role+Security+Grid
 */
public interface DepartmentalRule {

    /**
     * Returns the String representing the Department that this object belongs
     * to.
     * @return A String representation of the department.
     */
    public String getDept();

    /**
     * Returns the Long representing the WorkArea that this object belongs to.
     * @return A Long representing the WorkArea.
     */
    public Long getWorkArea();
}
