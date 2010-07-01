
<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search searchCriteria="helpType,resourceKey"
		resultColumns="helpType,resourceKey,text,effectiveDate,effectiveSequence,active"/>
</hr:page>