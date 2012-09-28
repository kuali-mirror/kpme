/**
 * Copyright 2004-2012 The Kuali Foundation
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
