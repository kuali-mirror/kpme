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
        <script type="text/javascript">
            var nidFwd = '${nidFwd}';
        </script>

        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
