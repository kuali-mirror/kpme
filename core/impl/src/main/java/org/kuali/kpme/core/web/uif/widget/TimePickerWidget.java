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
package org.kuali.kpme.core.web.uif.widget;

import org.kuali.rice.krad.datadictionary.parse.BeanTag;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.widget.WidgetBase;

/**
 * Widget that decorates a control to add stepped time selection interface.
 * The underlying timepicker plugin can be configured via the template options map.
 */
@SuppressWarnings("unchecked")
@BeanTag(name="timePickerWidget-bean", parent="KPMEUif-TimePickerWidget")
public class TimePickerWidget extends WidgetBase {
	
	private static final long serialVersionUID = 3435484954814741651L;

	public TimePickerWidget() {
        super();
    }
	
	@Override
    public void performFinalize(View view, Object model, Component parent) {
        super.performFinalize(view, model, parent);
    }

}
