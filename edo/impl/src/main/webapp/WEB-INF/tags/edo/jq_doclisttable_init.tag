<%@ tag import="org.kuali.kpme.edo.util.EdoConstants" %>
<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<%@ attribute name="addLink" %>
<%@ attribute name="markall" %>
<%@ attribute name="formName" %>
<%@ attribute name="formOperation" %>
<c:set var="Form" value="${EdoForm}" scope="request" />

<c:set var="dossierStatusOpen" value="<%= EdoConstants.DOSSIER_STATUS.OPEN %>" />
<c:set var="dossierStatusPending" value="<%= EdoConstants.DOSSIER_STATUS.PENDING %>" />
<c:set var="dossierStatusSubmitted" value="<%= EdoConstants.DOSSIER_STATUS.SUBMITTED %>" />
<c:set var="suppCategoryName" value="<%= EdoConstants.EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME %>" />
<c:set var="dossierStatusReconsider" value="<%= EdoConstants.DOSSIER_STATUS.RECONSIDERATION %>" />

<script lang="text/javascript">
    var marked = ${itemTracker};

    $(document).ready( function() {
        /*
         * Insert a 'details' column to the table
         */
        var nCloneTh = document.createElement( 'th' );
        var nCloneTd = document.createElement( 'td' );
        nCloneTd.innerHTML = '<img src="js/jquery/plugins/TableTools/media/images/details_open.png">';
        nCloneTd.className = "center";

        $('#item_list thead tr').each( function () {
            this.insertAfter( nCloneTh, this.childNodes[0] );
        } );

        $('#item_list tbody tr').each( function () {
            this.insertAfter(  nCloneTd.cloneNode( true ), this.childNodes[0] );
        } );

        var oTable = $("#item_list").dataTable(
            {
                "oTableTools": {
                    "aButtons": [
                        {
                            "sExtends":    "collection",
                            "sButtonText": "Bulk Actions",
                            "aButtons":    [
                            <c:if test="${EdoForm.useEditDossierFunc && (selectedCandidate.getDossierStatus().equals(dossierStatusOpen) || selectedCandidate.getDossierStatus().equals(dossierStatusPending) || (selectedCandidate.getDossierStatus().equals(dossierStatusSubmitted) && itemName.equals(suppCategoryName)) )}">
                                {
                                    "sExtends": "text",
                                    "sButtonText": "Delete Marked",
                                    "fnClick": function (nButton, oConfig, oFlash) {
                                       openConfirm();
                                    }
                                },
                            </c:if>
                                {
                                    "sExtends": "text",
                                    "sButtonText": "Show Marked",
                                    "fnClick": function (nButton, oConfig, oFlash) {
                                        var nid = "${nidFwd}";
                                        if (marked.length < 1) {
                                            openMarked();
                                        } else {
                                            var checkboxNodes = $('input[name="itemID"]', oTable.fnGetNodes()).serialize();
                                            document.location.href = "EdoMarkedItems.do?nid=" + nid;
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                },
                //"sScrollX": "100%",
                //"sScrollXInner": "110%",
                "sDom": 'T<"fg-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix"lf>t<"fg-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix"ip>',
                "aaData": [
                    ${itemlistJSON}
                ],
                "aoColumnDefs"  : [
                    {   aTargets: [0], sTitle: "Row Index", bVisible: false  },
                    {   aTargets: [1], sTitle: "Item ID", sClass: "hide-column"  },
                    {   aTargets: [2], sTitle: "Item Type", bVisible: false  },
                    {   aTargets: [3], sTitle: "Item Type ID", bVisible: false  },
                    {  // add link for download to file name
                        aTargets: [4],
                        sTitle: "File Name",
                        fnCreatedCell: function(nTd, sData, oData, iRow, iCol){
                            var itemID = oData[1];
                            var itemName = oData[4];
                            var nid = "${nidFwd}";
                            //var nid = getUrlVars()["nid"];
                            var itemLink = "EdoDownloadFile.do?itemID=" + itemID + "&nid=" + nid;
                            var itemHREF = "<a href='" + itemLink + "' target='dlwin'>" + itemName + "</a>";
                            var i = $(itemHREF);
                            $(nTd).empty();
                            $(nTd).append(i);
                        },
                        sClass: 'tableCell'
                    },
                    {   aTargets: [5], sTitle: "File Location", bVisible: false  },
                    {   aTargets: [6], sTitle: "Uploaded By", bVisible: false  },
                    {   aTargets: [7], sTitle: "Checklist Item ID", bVisible: false  },
                    {   aTargets: [8], sTitle: "Checklist Section ID", bVisible: false  },
                    {   aTargets: [9], sTitle: "Layer Level", bVisible: false  },
                    {   aTargets: [10], sTitle: "Externally Available", bVisible: false  },
                    {   aTargets: [11], sTitle: "Date Uploaded", bVisible: true  },
                    {   aTargets: [12], sTitle: "Candidate Username", bVisible: false  },
                    {
                        aTargets: [13],    // Column number which needs to be modified
                        //sTitle: "Actions&nbsp;&nbsp;<input type='checkbox' id='select_all'>",
                        sTitle: "",
                        <c:if test="${selectedCandidate.getDossierStatus().equals(dossierStatusOpen) || selectedCandidate.getDossierStatus().equals(dossierStatusPending) || (selectedCandidate.getDossierStatus().equals(dossierStatusSubmitted) && itemName.equals(suppCategoryName))}">
                        bVisible: true,
                        </c:if>
                        fnCreatedCell: function(nTd, sData, oData, iRow, iCol){
                            var itemID = oData[1];
                            var itemName = oData[4];
                            var itemDescription = oData[19];
                            var menuid = "actmenu" + itemID;
                            var topid  = "acttopmenu" + itemID;
                            var delactid = "actdelmenu" + itemID;
                            var replactid = "actreplmenu" + itemID;
                            var copyactid = "actcopymenu" + itemID;
                            var renactid = "actrenmenu" + itemID;
                            var nid = "${nidFwd}";
                            // if this is a pending supplemental item, show the del action
                            var delString = '';
                            if (Number(oData[18]) == 1) {
                                delString = '<option id="' + delactid + '" value="del">Delete</option>';
                            }
                            var selstring = '<select id="' + menuid + '"><option id="' + topid + '" value="noop">Action</option>' +
                                    <c:if test="${EdoForm.useEditDossierFunc && (selectedCandidate.getDossierStatus().equals(dossierStatusOpen) || selectedCandidate.getDossierStatus().equals(dossierStatusPending) )}">
                                        '<option id="' + delactid + '" value="del">Delete</option>' +
                                        '<option id="' + replactid + '" value="repl">Replace</option>' +
                                        '<option id="' + renactid + '" value = "rename">Edit Info</option>' +
                                    </c:if>
                                    <c:if test="${EdoForm.useEditDossierFunc && (selectedCandidate.getDossierStatus().equals(dossierStatusSubmitted) && itemName.equals(suppCategoryName))}">
                                        delString +
                                        '<option id="' + replactid + '" value="repl">Replace</option>' +
                                    </c:if>
                                    '<option id="' + copyactid + '" value="copy">Copy Link</option>' +
                                    '</select>&nbsp;&nbsp;&nbsp;';
                            var actmenu = $(selstring);
                            actmenu.on('change', function(event) {
                                var op = event.target.options[event.target.selectedIndex].value;
                                switch (op) {
                                    case 'del':
                                        openDelItem(itemID, itemName);
                                        break;
                                    case 'repl':
                                        openReplace(itemID, itemName);
                                        break;
                                    case 'rename':
                                        openRename(itemID, itemName, itemDescription);
                                        break;
                                    case 'copy':
                                        openCopy(itemID, nid);
                                        break;
                                    default:
                                        // do nothing
                                        return false;
                                }
                                // reset the drop down to the default, to capture/trigger any new selections/changes
                                event.target.selectedIndex = 0;
                            });
                            $(nTd).empty();
                            $(nTd).prepend(actmenu);
                        },
                        sClass: "center-cell"    // Optional - class to be applied to this table cell
                    },
                    // only show the checkbox if the dossier is OPEN or PENDING (for now)
                    {   aTargets: [15],
                        sTitle: "Bulk Actions&nbsp;&nbsp;<input type='checkbox' id='select_all'>",
                        mData: function ( source, type, val ) {
                            var itemID = source[1];
                            var selectFlag = '';
                            var itemIndex = $.inArray(Number(itemID), marked);
                            if (itemIndex != -1) {
                                selectFlag = 'checked';
                            }
                            var chboxtxt = "<input type='checkbox' onclick='storeMarkedItem(this);' id='cbitem" + itemID + "' value='" + itemID + "' name='itemID' " + selectFlag + " />";
                            return chboxtxt;
                        },
                        sClass: "center-cell"
                    },
                    {   aTargets: [14], sTitle: "Dossier ID", bVisible: false  },
                    {   aTargets: [16], sTitle: "Review Layer Description", bVisible: false  },
                    {   aTargets: [17], sTitle: "Review Level", bVisible: false  },
                    {   aTargets: [18], sTitle: "", bVisible: false },
                    {   aTargets: [19], sTitle: "Description", bVisible: false, sDefaultContent: "No description provided" },
                    {   aTargets: [20], bSortable: false, sDefaultContent: '<img src="js/jquery/plugins/TableTools/media/images/details_open.png">' }
                ],
                "bJQueryUI": true,
                "bSort": false,
                "iDisplayLength": 50
            }
        );

        $('#item_list tbody td img').on('click', function () {
            var nTr = $(this).parents('tr')[0];
            if ( oTable.fnIsOpen(nTr) )
            {
                /* This row is already open - close it */
                this.src = "js/jquery/plugins/TableTools/media/images/details_open.png";
                oTable.fnClose( nTr );
            }
            else
            {
                /* Open this row */
                this.src = "js/jquery/plugins/TableTools/media/images/details_close.png";
                oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
            }
        } );

        <!-- disable table sorting on Supplemental Items -->
        <c:if test="${!itemName.equals(suppCategoryName) && !(isDossierSubmitted)}">
        var oldIndex;
        var newIndex;
        var $table = $("#item_list tbody");
        var item_indexes = [];

        $table.sortable({
            cursor: "move",
            start:function(event, ui){
                // 0 based array, add one
                oldIndex = ui.item.prevAll().length + 1;
            },

            update: function(event, ui) {
                //var nid = getUrlVars()["nid"];
                var nid = "${nidFwd}";
                // 0 based array, add one
                newIndex = ui.item.prevAll().length + 1;
                // alert('Start: ' + old_index + ' End: ' + new_index);
                // var id = ui.item.context.children[0].innerHTML;
                params = $.map($table.find('.hide-column'), function(el,i) {
                    return 'item_' + el.innerHTML + '=' + (i+1);
                }).join('&');

                $.ajax({
                    type: "GET",
                    url: 'EdoChecklistItem.do?methodToCall=updateIndex&' + params + "&nid=" + nid ,
                    dataType: "json",
                    success: function(data, status) {
                        $datatable = oTable.dataTable( { bRetrieve:true } );
                        $datatable.fnClearTable();
                        $datatable.fnAddData(data); //the data part of your json message
                        oTable.fnDraw();    // ok, this was not adequate to redraw the table highlight bars
                    },
                    error : function(data, status) {
                        console.log("failed");
                        console.log(status);
                    }
                });
            }
            // end of drag
        });
        </c:if>

        $(function() {
            $( "#dialog-delete" ).dialog({
                height: 280,
                width: 450,
                modal: true,
                autoOpen: false,
                buttons: [
                    { text: "Delete Files",
                        click: function() {
                            var sData = $('input[name="itemID"]', oTable.fnGetNodes()).serialize();
                            var sFields = "<input type='hidden' name='methodToCall' value='deleteConfirm'/>" +
                                          "<input type='hidden' name='nidFwd' value='${nidFwd}'/>" +
                                          "<input type='hidden' name='formData' value='" + sData + "'/>";
                            var frmTblAction = $('<form>', {
                                "id": "frmtblactions",
                                "name": "EdoChecklistItemForm",
                                "action": "EdoChecklistItem.do",
                                "html": sFields,
                                "method": "post"
                            });
                            if (sData != "") {
                                frmTblAction.appendTo(document.body);
                                frmTblAction.submit();
                            } else {
                                alert("You have not selected any files for the Delete action.")
                            }
                            $(this).dialog("close")}
                    },
                    { text: "Cancel",
                        click: function() {
                            $(this).dialog("close")}
                    }
                ],
                draggable: false,
                resizable: false
            });
        });

        $(function() {
            $( "#dialog-delitem" ).dialog({
                height: 280,
                width: 550,
                modal: true,
                autoOpen: false,
                buttons: [
                    { text: "Delete File",
                        click: function() {
                            var itemID = document.getElementById("delitemid").innerHTML;
                            var sData = "itemID=" + itemID;
                            var sFields = "<input type='hidden' name='methodToCall' value='deleteConfirm'/>" +
                                    "<input type='hidden' name='nidFwd' value='${nidFwd}'/>" +
                                    "<input type='hidden' name='formData' value='" + sData + "'/>";
                            var frmDelFile = $('<form>', {
                                "id": "frmdelfile",
                                "name": "EdoChecklistItemForm",
                                "action": "EdoChecklistItem.do",
                                "html": sFields,
                                "method": "post"
                            });
                            frmDelFile.appendTo(document.body);
                            frmDelFile.submit();
                            $(this).dialog("close")}
                    },
                    { text: "Cancel",
                        click: function() {
                            $(this).dialog("close")}
                    }
                ],
                draggable: false,
                resizable: false
            });
        });

        $(function() {
            $( "#dialog-copy" ).dialog({
                height: 325,
                width: 550,
                modal: true,
                autoOpen: false,
                buttons: [
                    { text: "Done",
                        click: function() {
                            $(this).dialog("close")}
                    }
                ],
                draggable: false,
                resizable: false
            });
        });

        $(function() {
            $( "#dialog-replace" ).dialog({
                height: 280,
                width: 550,
                modal: true,
                autoOpen: false,
                buttons: [
                    { text: "Cancel",
                        click: function() {
                            $(this).dialog("close")}
                    }
                ],
                draggable: false,
                resizable: false
            });
        });

        $(function() {
            $( "#dialog-rename" ).dialog({
                height: 500,
                width: 550,
                modal: true,
                autoOpen: false,
                buttons: [
                    { text: "Cancel",
                        click: function() {
                            $(this).dialog("close")}
                    }
                ],
                draggable: false,
                resizable: false
            });
        });

        $(function() {
            $( "#dialog-marked" ).dialog({
                height: 280,
                width: 550,
                modal: true,
                autoOpen: false,
                buttons: [
                    { text: "Cancel",
                        click: function() {
                            $(this).dialog("close")}
                    }
                ],
                draggable: false,
                resizable: false
            });
        });

        // register a change function for the "select all" checkbox on the Actions column
        $('#select_all').change(function() {
            var oTableCheckboxes = $('#item_list input[name=itemID]');
            if ( $(this).prop("checked")) {
                oTableCheckboxes.prop("checked", true);
            } else {
                oTableCheckboxes.prop("checked", false);
            }
        });

    });

    function getUrlVars()
    {
        var vars = [], hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for(var i = 0; i < hashes.length; i++)
        {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    }
    function storeMarkedItem(cb) {
        var nid = "${nidFwd}";
        var op = "store";

        if (!cb.checked) {
            op = "remove";
        }

        $.ajax({
            type: "GET",
            url: 'EdoChecklistItem.do?methodToCall=storeMarkedItemID&op=' + op + '&itemID=' + cb.value + "&nid=" + nid ,
            success: function(data, status) {
                //alert("successful ajax call to storeMarkedItemID");
            },
            error : function(data, status) {
                console.log("failed");
                console.log(status);
            }
        });
        if (op == "store") {
            marked.push(cb.value);
        }
        if (op == "remove") {
            var i = marked.indexOf(cb.value);
            marked.splice(i, 1);
        }
    }

    function copytext(linktext, nid) {
        eli = document.getElementById("copylink");
        eli.innerHTML = "${requestPath}EdoDownloadFile.do?itemID=" + linktext + "&nid=" + nid;
        return false;
    }

    function openConfirm() {
        $( "#dialog-delete").dialog( 'open' );
        return false;
    }

    function openDelItem(itemID, itemName) {
        el = document.getElementById("delItemName");
        el.innerHTML = itemName;
        eli = document.getElementById("delitemid");
        eli.innerHTML = itemID;
        $( "#dialog-delitem").dialog( 'open' );
        return false;
    }

    function openCopy(linktext, nid) {
        copytext(linktext, nid);
        $( "#dialog-copy").dialog( 'open' );
        return false;
    }

    function openReplace(itemID, itemName) {
        // clear the form from the dialog
        var replFormDiv = $('[id=replform]');
        var cliID = checklistItemID;
        var sFields = "<input type='hidden' name='methodToCall' value='postFile'/>" +
                "<input type='hidden' name='nidFwd' value='${nidFwd}'/>" +
                "<input type='hidden' name='checklistItemID' value='" + cliID + "'/>" +
                "<input type='hidden' name='itemID' value='" + itemID + "'/>" +
                "<input type='hidden' name='repl' value='true'/>" +
                "<input type='file' name='uploadFile' size='60'/><br/><br/>" +
                "<input type='submit' value='Upload File'/>";

        replFormDiv.empty();
        el = document.getElementById("replItemName");
        el.innerHTML = itemName;

        var frmReplFile = $('<form>', {
            "id": "frmreplfile",
            "name": "EdoChecklistItemForm",
            "action": "EdoChecklistItem.do",
            "enctype": "multipart/form-data",
            "html": sFields,
            "onSubmit": "return validateRepl(this, 'uploadFile');",
            "method": "post"
        });
        frmReplFile.appendTo(replFormDiv);

        $( "#dialog-replace").dialog( 'open' );
        return false;
    }

    function openRename(itemID, itemName, itemDescription) {
        // clear the form from the dialog
        var renFormDiv = $('[id=renameform]');
        var cliID = checklistItemID;
        var sFields = "<input type='hidden' name='methodToCall' value='renameFile'/>" +
                "<input type='hidden' name='nidFwd' value='${nidFwd}'/>" +
                "<input type='hidden' name='checklistItemID' value='" + cliID + "'/>" +
                "<input type='hidden' name='itemID' value='" + itemID + "'/>" +
                "<input type='hidden' name='rename' value='true'/>" +
                "New Name <input type='text' name='newFilename' size='60' value='" + itemName + "' /><br/><br/>" +
                "File Description<br><textarea name='fileDescription' rows=15 cols=50>" + itemDescription + "</textarea><br/><br/>" +
                "<input type='submit' value='Update File Info'/>";

        renFormDiv.empty();
        el = document.getElementById("renItemName");
        el.innerHTML = itemName;

        var frmRenameFile = $('<form>', {
            "id": "frmrenfile",
            "name": "EdoChecklistItemForm",
            "action": "EdoChecklistItem.do",
            "html": sFields,
            "method": "post"
        });
        frmRenameFile.appendTo(renFormDiv);

        $( "#dialog-rename").dialog( 'open' );
        return false;
    }

    function openMarked() {
        $( "#dialog-marked").dialog( 'open' );
        return false;
    }

    function fnFormatDetails ( oTable, nTr )
    {
        var aData = oTable.fnGetData( nTr );
        var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;" class="file-detail">';
        sOut += '<tr><td>Uploaded by:</td><td>' + aData[6] + '</td></tr>';
        sOut += '<tr><td>File description:</td><td>'+ aData[19] + '</td></tr>';
        sOut += '</table>';

        return sOut;
    }

</script>

<div id="dialog-copy">
    <div>
        <h3>Copy Document Link</h3>
        <p>You may use the link below to refer to this document outside of eDossier.</p>
        <p>Highlight the link address below and press your copy command to copy this link to your local clipboard:</p>
        <textarea id="copylink" rows="5" cols="120" readonly="true"></textarea>
    </div>
</div>

<div id="dialog-delete" title="Confirm Delete Files">
    <h3>WARNING</h3>
    <p>You are about to delete the marked files from the dossier.</p>
    <p>If this is correct, press the "Delete Files" button.  Press the "Cancel" button to stop the delete operation.</p>
</div>

<div id="dialog-delitem" title="Confirm Delete File">
    <h3>WARNING</h3>
    <p>You are about to delete the file, <strong><span id="delItemName"></span></strong>, from the dossier.</p>
    <p>If this is correct, press the "Delete File" button.  Press the "Cancel" button to stop the delete operation.</p>
    <div style="display: none;" id="delitemid"></div>
</div>

<div id="dialog-replace">
    <div>
        <h3>Replace File</h3>
        <p>This will replace the file, <strong><span id="replItemName"></span></strong>.</p>
        <script>
            function validateRepl(frm, fld)
            {
                if (frm.elements[fld].value == null || frm.elements[fld].value == "") {
                    alert("You must select a file with which to replace the current file.");
                    return false;
                }
                return true;
            }
        </script>

        <div id="replform"></div>
    </div>
</div>

<div id="dialog-rename" title="Rename File">
    <h3>WARNING</h3>
    <p>You are about to rename the file, <strong><span id="renItemName"></span></strong>, in the dossier.</p>
    <p>If this is correct, provide a new name and press the "Rename File" button.  Press the "Cancel" button to stop the rename operation.</p>
    <div style="display: none;" id="renitemid"></div>
    <div id="renameform"></div>
</div>

<div id="dialog-marked" title="No Files Marked">
    <h3>WARNING</h3>
    <p>You have not marked any files from the dossier.</p>
</div>
