<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>
    <div style="width: 100%;">

        <div class="content">
            <c:if test="${!empty ErrorPropertyList}">
                <div class="error">
                    <kul:errors keyMatch="error" errorTitle="Errors:" />
                    <br />
                </div>
            </c:if>
            <br /><br/>
            <button onclick="window.close();">Close this download window</button>
        </div>
        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
