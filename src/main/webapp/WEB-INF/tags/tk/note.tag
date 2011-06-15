<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="width" required="false" %>

<div id="note">
    <h2><a href="#">Note</a></h2>

    <div style="text-align: center;">
        <table width="100%" cellpadding="0" cellspacing="0" class="null">
            <tr>
                <td width="100%">
                    <iframe src="${Form.workflowUrl}Note.do?docId=${Form.timesheetDocument.documentId}&attachmentTarget=_blank"
                            height="200" width="100%" scrolling="auto"></iframe>
                </td>
            </tr>
        </table>
    </div>
</div>