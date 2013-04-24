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
package org.kuali.kfs.sys.businessobject.defaultvalue;

import org.kuali.hr.core.position.Position;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.valuefinder.ValueFinder;

public class NextUniquePositionNumberFinder implements ValueFinder {

    @Override
    public String getValue() {
        //making this work without the dumbness of what was done before. Still, this class shouldn't exist.  We don't need
        // a value finder for a sequence value....
        return KRADServiceLocator.getSequenceAccessorService().getNextAvailableSequenceNumber("hr_position_s", Position.class).toString();
        //return TkServiceLocator.getPositionService().getNextUniquePositionNumber();
    }
}
