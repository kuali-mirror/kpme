<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<hr:page>
	<hr:search searchCriteria="universityId,department"
		resultColumns="universityId,primaryFirstName,primaryLastName"
	/>
</hr:page>