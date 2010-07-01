<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search searchCriteria="universityId,lastName,firstName"
		resultColumns="universityId,primaryFirstName,primaryMiddleName,primaryLastName"/>
</hr:page>