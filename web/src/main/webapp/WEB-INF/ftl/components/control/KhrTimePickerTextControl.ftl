<#--

    Copyright 2004-2014 The Kuali Foundation

    Licensed under the Educational Community License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.opensource.org/licenses/ecl2.php

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
<#--
Create Standard HTML Text Input then decorates with Timepicker plugin
-->

<#macro kpme_uif_timePickerTextControl control field>

    <#include '/krad/WEB-INF/ftl/components/control/text.ftl' parse=true />
    <@uif_text control=control field=field />

    <#if control.currentTimeButtonEnabled>
        <@krad.script value="addTimePickerToTextFieldWithCurrentTimeButton('${control.id}', ${control.timePickerWidget.templateOptionsJSString}, '${control.id}_setCurrentTimeButton');" />
        <button type="button" id="${control.id}_setCurrentTimeButton">Set current time</button>
    <#else>
        <@krad.script value="addTimePickerToTextField('${control.id}', ${control.timePickerWidget.templateOptionsJSString});" />
    </#if>


</#macro>