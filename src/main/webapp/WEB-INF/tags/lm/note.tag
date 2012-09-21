<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="width" required="false" %>

<div id="note" class="ui-accordion ui-widget ui-helper-reset ui-accordion-icons" role="tablist">
    <h2 class="ui-accordion-header ui-helper-reset ui-state-default ui-corner-all" role="tab" aria-expanded="false" aria-selected="false" tabindex="0">
   	 <span class="ui-icon ui-icon-triangle-1-e"></span>
   	 <a href="javascript:toggle('noteDiv');">Note</a>
    </h2>

    <div style="text-align: center; display:none;" 
    	class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom"
    	id="noteDiv">
        <table width="100%" cellpadding="0" cellspacing="0" class="null">
            <tr>
                <td width="100%">
                    <iframe src="${Form.workflowUrl}/Note.do?docId=${Form.leaveCalendarDocument.documentId}&attachmentTarget=_blank"
                            height="200" width="100%" scrolling="auto"></iframe>
                </td>
            </tr>
        </table>
    </div>
</div>