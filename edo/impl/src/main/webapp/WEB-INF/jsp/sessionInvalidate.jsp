<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>

<html>
<head>
    <title>eDossier - Session Expired</title>
</head>

<body>


<div id="headerarea" class="headerarea">
			<span class="left">
			</span>
</div>

<br />
<br />
<div align="center">
    <table width="95%" cellpadding="0" cellspacing="0"
           style="border-style: ridge; border-color: #7d1100; border-width: thin">
        <tr>
            <td style="background-color: #F0F0F0">

                <table width="100%">
                    <tr>
                        <td width="33%" valign="top">
                            <div class="portlet">
                                <div class="header">
                                    <h2>Your session has expired.</h2>
                                </div>

                                <div class="portlet-content">
                                    <hr>
                                    <center>
                                        <h5> For security reasons, your session expires after 60 minutes of inactivity. <BR><BR>
                                           
                                            Please <a href="EdoIndex.do?">click</a> here to access the eDossier system.
                                        </h5>
                                        <c:if test="false">
                                            <hr />
							<pre
                                    style="font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif;"></font></pre>
                                        </c:if></div>
                        </td>
                    </tr>
                </table>
                <br>
                <br>
            </td>
        </tr>
    </table>
</body>
</html>

