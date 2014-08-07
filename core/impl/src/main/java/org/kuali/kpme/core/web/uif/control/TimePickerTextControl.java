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
package org.kuali.kpme.core.web.uif.control;

import java.util.List;

import org.kuali.kpme.core.web.uif.widget.TimePickerWidget;
import org.kuali.rice.krad.datadictionary.parse.BeanTag;
import org.kuali.rice.krad.datadictionary.parse.BeanTagAttribute;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.control.TextControl;

/**
 * Text control that is decorated with a time picker widget (allowing the control value to be modified using the picker)
 *
 */
@BeanTag(name="timePickerTextControl-bean", parent="KPMEUif-TimePickerTextControl")
public class TimePickerTextControl extends TextControl {
	
	private static final long serialVersionUID = 3028008594161590816L;

	private TimePickerWidget timePickerWidget;
	private boolean currentTimeButtonEnabled;

	public TimePickerTextControl() {
		super();
	}
    
	@BeanTagAttribute(name = "timePickerWidget", type = BeanTagAttribute.AttributeType.SINGLEBEAN)
    public TimePickerWidget getTimePickerWidget() {
        return timePickerWidget;
    }

    public void setTimePickerWidget(TimePickerWidget timePickerWidget) {
        this.timePickerWidget = timePickerWidget;
    }
    
    public boolean isCurrentTimeButtonEnabled() {
        return currentTimeButtonEnabled;
    }

    public void setCurrentTimeButtonEnabled(boolean currentTimeButtonEnabled) {
        this.currentTimeButtonEnabled = currentTimeButtonEnabled;
    }
    
    /**
     * @see org.kuali.rice.krad.uif.component.ComponentBase#getComponentsForLifecycle()
     */
    @Override
    public List<Component> getComponentsForLifecycle() {
        List<Component> components = super.getComponentsForLifecycle();
        components.add(getTimePickerWidget());
        return components;
    }
    
    /**
     * @see org.kuali.rice.krad.uif.component.ComponentBase#copy()
     */
    @Override
    protected <T> void copyProperties(T component) {
        super.copyProperties(component);
        TimePickerTextControl timePickerControlCopy = (TimePickerTextControl) component;
        timePickerControlCopy.setCurrentTimeButtonEnabled(this.currentTimeButtonEnabled);
        if(this.timePickerWidget != null) {
        	timePickerControlCopy.setTimePickerWidget((TimePickerWidget)this.timePickerWidget.copy());
        }
    }
    
}
