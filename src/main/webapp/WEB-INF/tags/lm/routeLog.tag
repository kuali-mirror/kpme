<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<div id="routeLog" class="ui-accordion ui-widget ui-helper-reset ui-accordion-icons" role="tablist">
    <h2 class="ui-accordion-header ui-helper-reset ui-state-default ui-corner-all" role="tab" aria-expanded="false" aria-selected="false" tabindex="0">
    	<span class="ui-icon ui-icon-triangle-1-e"></span>
    	<a href="javascript:toggle('routeDiv');">Route Log</a>
    </h2>

    <div id="routeDiv" 
    	style="text-align: center; display:none;"
    	class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
        <table width="100%" cellpadding="0" cellspacing="0" class="null">
            <tr>
                <td width="100%">
                    <iframe src="${Form.workflowUrl}/RouteLog.do?documentId=${Form.leaveCalendarDocument.documentId}"
                            height="200" width="100%" scrolling="auto"></iframe>
                </td>
            </tr>
        </table>
    </div>
</div>