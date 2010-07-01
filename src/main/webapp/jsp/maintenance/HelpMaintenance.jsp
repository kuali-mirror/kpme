<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:listMaintenance nestingPath="help" columns="helpType,resourceKey,text" tabFooter="true" >
	   <hr:listMaintenance nestingPath="helpDetail" columns="detailType,order,text" childList="true" style="horizontal" />
    </hr:listMaintenance>
</hr:page>