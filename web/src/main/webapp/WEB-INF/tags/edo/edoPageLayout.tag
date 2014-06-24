<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>

<%@ attribute name="tabId" required="false"%>

<c:set var="systemAdmin" value="true" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="cache-control" content="public">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>eDossier</title>
    <edo:edoCSSIncludes />
    <edo:edoJSIncludes />
</head>
<body>

<jsp:doBody />

</body>
</html>
