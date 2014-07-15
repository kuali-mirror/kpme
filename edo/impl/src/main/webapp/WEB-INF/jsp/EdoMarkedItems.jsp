<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Const" value="${EdoConstants}" scope="request"/>

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <script type="text/javascript">
        var nidFwd = '${nidFwd}';
        var checklistItemID = ${checklistItemID};
    </script>

    <edo:jq_markeditemtable_init formName="EdoMarkedItemListForm" formOperation="download"/>

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
                <div style="padding-bottom: 25px;">
                    <h3>Marked Items</h3>

                        This is a list of all items marked, from across all categories.
                </div>
            </div>
            <table id="item_list"></table>
        </div>

        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
