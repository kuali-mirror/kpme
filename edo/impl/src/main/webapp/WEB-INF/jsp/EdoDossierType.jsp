<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<%
    String list_block_mode = "block";
    String edit_block_mode = "none";
    String edit_block_title = "Add";

    if ( (request.getParameter("op") != null) && (request.getParameter("op").contentEquals("edit")) ) {
        list_block_mode = "none";
        edit_block_mode = "block";
        edit_block_title = "Edit";
    }
    if ( (request.getParameter("op") != null) && (request.getParameter("op").contentEquals("add")) ) {
        list_block_mode = "none";
        edit_block_mode = "block";
        edit_block_title = "Add";
    }
    request.setAttribute("list_block_mode", list_block_mode);
    request.setAttribute("edit_block_mode", edit_block_mode);
    request.setAttribute("edit_block_title", edit_block_title);
%>

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <div style="width: 100%;">

    <edo:edoTreeNav />
    <div class="content">
        <edo:jq_dossiertypetable_init selectLink="EdoDossierType.do?nid=gadm_3&tabId=gadmin" addLink="EdoDossierType.do?tabId=gadmin&nid=gadm_3&dtid=0" />

        <div id="dt_list_block" style="display: ${list_block_mode};">
            <table id="dt_list"></table>
        </div>

        <div id="dt_edit" style="display: ${edit_block_mode};">
        <h3>${edit_block_title} Dossier Type</h3>
        <html:form action="/EdoDossierType.do?tabId=gadmin&nid=gadm_3" method="post" >
            <html:hidden property="dossierTypeID" value="${param.dtid}" />
            <html:hidden property="lastUpdated" />
            <table border="0">
                <tr><td>Dossier Type Name</td><td><html:text property="dossierTypeName" /></td></tr>
                <tr><td>Dossier Type Code</td><td><html:text property="dossierTypeCode" /></td></tr>
                <tr><td>Last Updated</td>     <td><bean:write name="EdoDossierTypeForm" property="lastUpdated" /> </td></tr>
            </table>
            <html:submit value="${edit_block_title} Dossier Type" />
        </html:form>
        </div>

    </div>
        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
