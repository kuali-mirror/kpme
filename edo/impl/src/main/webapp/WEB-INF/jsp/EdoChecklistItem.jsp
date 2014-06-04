<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Const" value="${EdoConstants}" scope="request"/>

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <script type="text/javascript">
        var nidFwd = '${nidFwd}';
        var checklistItemID = ${checklistItemID};
        var uploadURL = "EdoChecklistItem.do?methodToCall=postFile";
    </script>

    <c:if test="${!empty itemName}">
        <edo:jq_doclisttable_init formName="EdoChecklistItemForm" formOperation="delete"/>
    </c:if>

    <div style="width: 100%;">

    <edo:edoTreeNav />
    <div class="content">
        <div id="content_hdr">

            <c:if test="${!empty ErrorPropertyList}">
                <div class="error">
                    <kul:errors keyMatch="error" errorTitle="Errors:" />
                    <br />
                </div>
            </c:if>

        <c:if test="${!empty itemName}">
            <div style="padding-bottom: 25px;">
            <h3>${itemName}</h3>

            ${itemDescription}
            </div>

            <c:if test="${!empty itemcount}">
                <edo:edoSectionSummary></edo:edoSectionSummary>
            </c:if>
            <c:if test="${empty itemcount}">
                <div id="content_tbl">
                    <table id="item_list"></table>
                    <!-- disable table sorting on Supplemental Items -->
                    <c:if test="${!itemName.equals(Const.EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME)}">
                    To sort this list, click and drag the item to its new position.
                    </c:if>
                    <br>
                </div>
                <div id="content_dz">
                    <edo:edoDropZone itemName="${itemName}" />
                </div>
            </c:if>
        </c:if>
    </div>

        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
