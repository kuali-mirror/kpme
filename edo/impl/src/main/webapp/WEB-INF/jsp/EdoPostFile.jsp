<%--
  Created by IntelliJ IDEA.
  User: bradleyt
  Date: 1/28/13
  Time: 1:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <div style="width: 100%;">

        <edo:edoTreeNav />
        <div class="content">
            <div class="error">
                <kul:errors keyMatch="error" errorTitle="Errors:" />
                <br />
            </div>

        </div>

        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
