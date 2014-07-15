<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<%@ attribute name="addLink" %>
<%@ attribute name="markall" %>
<%@ attribute name="formName" %>
<%@ attribute name="formOperation" %>
<c:set var="Form" value="${EdoForm}" scope="request"/>

<script lang="text/javascript">
var marked = ${itemTracker};
var editExternalLevels = new Array();
editExternalLevels = ${Form.hasUploadExternalLetterByDept} ;

$(document).ready( function() {
    var oTable = $("#item_list").dataTable(
            {
                "oTableTools": {
                    "aButtons": [
                    ]
                },
                "sScrollX": "100%",
                "sScrollXInner": "500%",
                "sDom": 'T<"fg-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix"l>t<"fg-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix"ip>',
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
                    {   aTargets: [13],
                        sTitle: "Review Layer Description",
                        bVisible: false,
                        mData: function (src, type, data) {
                            return src[16];
                        },
                        sClass: "table-cell"
                    },
                    {
                        aTargets: [14],    // Column number which needs to be modified
                        sTitle: "",
                        bVisible: true,
                        fnCreatedCell: function(nTd, sData, oData, iRow, iCol){
                            var itemID = oData[1];
                            var itemName = oData[4];
                            var itemLevel = oData[17];
                            if (editExternalLevels) {
                                var menuid = "actmenu" + itemID;
                                var topid  = "acttopmenu" + itemID;
                                var delactid = "actdelmenu" + itemID;
                                var replactid = "actreplmenu" + itemID;
                                var selstring = '<select id="' + menuid + '"><option id="' + topid + '" value="noop">Action</option>' +
                                        '<option id="' + delactid + '" value="del">Delete</option>' +
                                        '<option id="' + replactid + '" value="repl">Replace</option>' +
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
                                        default:
                                            // do nothing
                                            return false;
                                    }
                                    // reset the drop down to the default, to capture/trigger any new selections/changes
                                    event.target.selectedIndex = 0;
                                });
                                $(nTd).empty();
                                $(nTd).prepend(actmenu);
                            } else {
                                $(nTd).empty();
                            }
                        },
                        sClass: "center-cell"    // Optional - class to be applied to this table cell
                    } //,
                    //{   aTargets: [15],
                    //    sTitle: "Bulk Actions&nbsp;&nbsp;<input type='checkbox' id='select_all'>",
                    //    mData: function ( source, type, val ) {
                    //        var itemID = source[1];
                    //        var chboxtxt = "<input type='checkbox' id='cbitem" + itemID + "' value='" + itemID + "' name='itemID' />";
                    //        return chboxtxt;
                    //    },
                    //    sClass: "center-cell"
                    //} ,
                    //{   aTargets: [14], sTitle: "Dossier ID", bVisible: false  },
                    //{   aTargets: [15], sTitle: "Review Layer Definition ID", bVisible: false  },
                    //{   aTargets: [17], sTitle: "Review Level", bVisible: false  }
                ],
                "bJQueryUI": true,
                "bSort": false,
                "iDisplayLength": 10
            }
    );

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
                            "name": "EdoExternalLetterForm",
                            "action": "EdoExternalLetter.do",
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

function openDelItem(itemID, itemName) {
    el = document.getElementById("delItemName");
    el.innerHTML = itemName;
    eli = document.getElementById("delitemid");
    eli.innerHTML = itemID;
    $( "#dialog-delitem").dialog( 'open' );
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
        "name": "EdoSolicitedLetterForm",
        "action": "EdoSolicitedLetter.do",
        "enctype": "multipart/form-data",
        "html": sFields,
        "onSubmit": "return validateRepl(this, 'uploadFile');",
        "method": "post"
    });
    frmReplFile.appendTo(replFormDiv);

    $( "#dialog-replace").dialog( 'open' );
    return false;
}

</script>

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
