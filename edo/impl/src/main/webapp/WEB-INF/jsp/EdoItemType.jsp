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
            <edo:jq_itemtypetable_init selectLink="EdoItemType.do?nid=gadm_4&tabId=gadmin" addLink="EdoItemType.do?tabId=gadmin&nid=gadm_4&itid=0" />

            <div id="it_list_block" style="display: ${list_block_mode};">
                <table id="it_list"></table>
            </div>

            <div id="it_edit" style="display: ${edit_block_mode};">
                <h3>${edit_block_title} Item Type</h3>
                <html:form action="/EdoItemType.do?tabId=gadmin&nid=gadm_4" method="post" >
                <html:hidden property="itemTypeID" value="${param.itid}" />
                <html:hidden property="lastUpdated" />
                <html:hidden property="createDate" />
                <table border="0">
                    <tr><td>Item Type Name</td><td><html:text property="itemTypeName" /></td></tr>
                    <tr><td>Item Type Description</td><td><html:textarea property="itemTypeDescription" /></td></tr>
                    <tr><td>Item Type Instructions</td><td><html:textarea property="itemTypeInstructions" /></td></tr>
                    <tr><td>External Availability</td><td><html:checkbox property="itemTypeExtAvailability" /></td></tr>
                    <tr><td>Last Updated</td><td><bean:write name="EdoItemTypeForm" property="lastUpdated" /></td></tr>
                </table>
                <html:submit value="${edit_block_title} Item Type" />
            </html:form>

        </div>
        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
