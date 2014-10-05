<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<%@ attribute name="actionPath" required="true"%>

<div id="addfile" style="font-size: 12px; text-align: center; background-color: #cccccc; padding-bottom: 15px;">
    <form name="manualupload" action="${actionPath}" method="post" enctype="multipart/form-data">
        <input type="hidden" id="methodToCall" name="methodToCall" value="postFile" />
        <input type="hidden" id="nidFwd" name="nidFwd" value="" />
        <input type="hidden" id="checklistItemID" name="checklistItemID" value="" />
        <input type="file" name="uploadFile" id="uploadFile" size="60"><button onclick="document.manualupload.submit();" name="submit" id="submit" value="upload">Upload file</button>
    </form>
    <script>
        document.forms["manualupload"].elements["nidFwd"].value = nidFwd;
        document.forms["manualupload"].elements["checklistItemID"].value = checklistItemID;
    </script>
</div>
