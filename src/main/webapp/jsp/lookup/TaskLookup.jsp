<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search searchCriteria="workArea,task,description"
		resultColumns="workArea.workArea,task,workArea.effectiveDate,description"/>
</hr:page>