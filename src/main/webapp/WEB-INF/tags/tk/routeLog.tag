<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<div id="routeLog">
    <h2><a href="#">Route Log</a></h2>

    <div style="text-align: center;">
        <table width="100%" cellpadding="0" cellspacing="0" class="null">
            <tr>
                <td width="100%">
                    <iframe src="${Form.workflowUrl}RouteLog.do?routeHeaderId=${Form.timesheetDocument.documentId}"
                            height="200" width="100%" scrolling="auto"></iframe>
                </td>
            </tr>
        </table>
    </div>
</div>