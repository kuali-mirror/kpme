<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<%@ attribute name="addLink" %>
<%@ attribute name="markall" %>
<%@ attribute name="formName" %>
<%@ attribute name="formOperation" %>
<c:set var="Const" value="${EdoConstants}" scope="request"/>

<script lang="text/javascript">
    var marked = ${itemTracker};

    $(document).ready( function() {
        var oTable = $("#item_list").dataTable(
            {
                "oTableTools": {
                    "aButtons": [
                        {
                            "sExtends":    "collection",
                            "sButtonText": "Bulk Actions",
                            "aButtons":    [
                                {
                                    "sExtends": "text",
                                    "sButtonText": "Download PDF Aggregate",
                                    "fnClick": function (nButton, oConfig, oFlash) {
                                       openPDF('foo');
                                    }
                                }
                            ]
                        }
                    ]
                },
                "sScrollX": "100%",
                "sScrollXInner": "500%",
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
                        sTitle: "",
                        <c:if test="${selectedCandidate.getDossierStatus().equals('OPEN') || (selectedCandidate.getDossierStatus().equals('SUBMITTED') && itemName.equals(EdoConstants.EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME))}">
                        bVisible: true,
                        </c:if>
                        fnCreatedCell: function(nTd, sData, oData, iRow, iCol){
                            var itemID = oData[1];
                            var itemName = oData[4];
                            var menuid = "actmenu" + itemID;
                            var topid  = "acttopmenu" + itemID;
                            var copyactid = "actcopymenu" + itemID;
                            var selstring = '<select id="' + menuid + '"><option id="' + topid + '" value="noop">Action</option>' +
                                    '<option id="' + copyactid + '" value="copy">Copy Link</option>' +
                                    '</select>&nbsp;&nbsp;&nbsp;';
                            var actmenu = $(selstring);
                            actmenu.on('change', function(event) {
                                var op = event.target.options[event.target.selectedIndex].value;
                                switch (op) {
                                    case 'copy':
                                        openCopy(itemID);
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
                    // only show the checkbox if the dossier is OPEN (for now)
                    <c:if test="${selectedCandidate.getDossierStatus().equals('OPEN') || (selectedCandidate.getDossierStatus().equals('SUBMITTED') && itemName.equals(EdoConstants.EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME))}">
                    {   aTargets: [15],
                        sTitle: "Bulk Actions&nbsp;&nbsp;<input type='checkbox' id='select_all'>",
                        mData: function ( source, type, val ) {
                            var itemID = source[1];
                            var selectFlag = '';
                            var itemIndex = $.inArray(Number(itemID), marked);
                            if (itemIndex != -1) {
                                selectFlag = 'checked';
                            }
                            var chboxtxt = "<input type='checkbox' id='cbitem" + itemID + "' value='" + itemID + "' name='itemID' " + selectFlag + " />";
                            return chboxtxt;
                        },
                        sClass: "center-cell"
                    },
                    </c:if>
                    {   aTargets: [14], sTitle: "Dossier ID", bVisible: false  },
                    {   aTargets: [16], sTitle: "Review Layer Description", bVisible: false  },
                    {   aTargets: [17], sTitle: "Review Level", bVisible: false  }
                ],
                "bJQueryUI": true,
                "bSort": false,
                "iDisplayLength": 50
            }
        );

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

    function copytext(linktext) {
        eli = document.getElementById("copylink");
        eli.innerHTML = "${requestPath}EdoDownloadFile.do?itemID=" + linktext;
        return false;
    }

    function openCopy(linktext) {
        copytext(linktext);
        $( "#dialog-copy").dialog( 'open' );
        return false;
    }

    function openPDF(linktext) {
        return false;
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
