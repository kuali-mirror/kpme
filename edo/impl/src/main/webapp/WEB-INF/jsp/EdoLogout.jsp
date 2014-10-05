<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<html>
<head>
    <link href="kr/css/kuali.css" rel="stylesheet" type="text/css" />
</head>
<title> eDossier - Session Expired</title>
<body>

<div id="headerarea" class="headerarea">

</div>

<br />
<br />

<div align="center">
    <table width="95%" cellpadding="0" cellspacing="0"
           style="border-style: ridge; border-color: #7d1100; border-width: thin">
        <tr>
            <td style="background-color: #7d110c">

                <table width="100%">
                    <tr>
                        <td width="33%" valign="top">
                            <div class="portlet">
                                <div class="header" style="color: #ffffff;">
                                    <h2>You Have Been Logged Out</h2>
                                </div>

                                <div class="portlet-content" style="color: #ffffff;">
                                    <hr>
                                    <center>
                                        <h5> You have been logged out of the application and your session expired.<br>
                                             For security reasons, to complete this logout process, please close your browser.
                                        </h5>
                                        <h5>
                                            You may also logout of the Central Authentication Service, by clicking this button:<br><br>
                                            <a href="https://cas.iu.edu/cas/logout">
                                                <img src="images/cas-buttons-logout.gif" /></a>
                                        </h5>
                                        <c:if test="${not empty stacktrace }">
                                            <hr />
					                        <pre style="font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif;">${stacktrace}</pre>
                                        </c:if>
                                    </center>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
                <br>
                <br>
            </td>
        </tr>
    </table>
</div>
</body>

</html>
