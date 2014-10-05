<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoForm}" scope="request" />

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <script type="text/javascript">
        var nidFwd = '${nidFwd}';
        var checklistItemID = ${checklistItemID};

        function validateUploadForm() {
            var okToSend = true;

            if (document.EdoForm.uploadFile.value == "") {
                okToSend = okToSend && false;
                alert("You must select a file to upload.");
            }
            return okToSend;
        }
    </script>

    <edo:jq_solicitedltr_init formName="EdoSolicitedLetterForm" />

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
                    <%--<h3>Solicited Letters from Former Students, Co-authors of Collaborative Work, plus Service Letters</h3>

                    Solicited letters include those from former students, co-authors of collaborative work, <em>and/or</em> evaluative letters regarding service
                    --%>
                        <h3>${itemName}</h3>

                            ${itemDescription}

                </div>

            </div>
            <div id="content_tbl">
                <table id="item_list"></table>
                <br>
            </div>
            <div id="content_dz">
                <c:if test="${(not selectedCandidate.getDossierStatus().equals('CLOSED')) && EdoForm.hasUploadExternalLetterByDept}">
                    <div id="ltruploadform">
                        <html:form action="/EdoSolicitedLetter" enctype="multipart/form-data">
                            <html:hidden property="methodToCall" value="postFile"/>
                            <html:hidden property="nidFwd" value="${nidFwd}"/>
                            <html:hidden property="checklistItemID" value="${checklistItemID}"/>
                            <table class="noborders" width="50%">
                                <tbody>
                                <tr>
                                    <td align="right" bgcolor="#eeeeee">File to Upload</td>
                                    <td align="left">
                                        <input type="file" name="uploadFile" id="uploadFile" size="60">

                                        <br>
                                        <button onclick="return validateUploadForm();" name="submit" id="submit" value="upload">Upload file</button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </html:form>
                    </div>
                </c:if>
            </div>
        </div>

        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
