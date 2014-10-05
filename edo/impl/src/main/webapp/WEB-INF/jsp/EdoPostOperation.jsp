<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <edo:jq_delconfirmtable_init />

    <script type="text/javascript">
        var nidFwd = '${nidFwd}';
        var checklistItemID = ${checklistItemID};

        function do_submit() {
            var sData = $('input', oTable.fnGetNodes()).serialize();
            document.forms["EdoDeleteConfirmForm"].formData.value = sData;
            document.forms["EdoDeleteConfirmForm"].submit();
        }

        function do_cancel() {
            window.location.href = "EdoChecklistItem.do?nid=" + nidFwd;
        }

    </script>

    <div style="width: 100%;">

        <edo:edoTreeNav />
        <div class="content">
            <h3 class="error">WARNING</h3>
            <p class="warning"> You are about to delete the following items from the dossier:</p>
            <form method="post" action="EdoDeleteConfirm.do" name="EdoDeleteConfirmForm">
                <input type="hidden" name="operation" value="confirm"/>
                <input type="hidden" name="formData" value="" />
                <input type="hidden" name="nidFwd" value="${nidFwd}"/>
                <table id="item_list"></table>
                <hr />
            <button type="button" onclick="javascript:do_submit();">Delete File(s)</button>
            <button type="button" onclick="javascript:do_cancel();">Cancel</button>
            </form>
        </div>

        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
