<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <div style="width: 100%;">

    <edo:edoTreeNav />
    <div class="content">
        <c:if test="${!empty ErrorPropertyList}">
            <div class="error">
                <kul:errors keyMatch="error" errorTitle="Errors:" />
                <br />
            </div>
        </c:if>

        <edo:edoDossierHeader/>

    </div>
        <br style="clear: both;" />
    </div>
  
    <c:if test="${debugPoint != null}">
        <script>alert("${debugPoint}");</script>
    </c:if>
    <edo:edoFooter/>
</edo:edoPageLayout>

