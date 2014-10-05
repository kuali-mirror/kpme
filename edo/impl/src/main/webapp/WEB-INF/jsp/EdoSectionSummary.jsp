<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <script type="text/javascript">
        var nidFwd = '${nidFwd}';
    </script>

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
                    <h3>${sectionName} Summary</h3>

                    <p>The table shows the count of items present in the dossier in each of the subcategories of the ${sectionName} section of the checklist.</p>
                </div>
                <edo:edoSectionSummary/>
            </div>
        </div>

        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
