<%@include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<script lang="text/javascript">
    var oTable;

    $(document).ready( function() {
        oTable = $("#summary").dataTable(
                {
                    "sDom": '<"fg-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix">t<"fg-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix">',
                    "aaData": [
                        ${itemcountJSON}
                    ],
                    "aoColumnDefs": [
                        { aTargets: [0], sTitle: "Ordering", bVisible: false },
                        { aTargets: [1], sTitle: "Number of documents", bVisible: true, bSortable: false },
                        { aTargets: [2],
                          sTitle: "Category",
                          bVisible: true,
                          bSortable: false,
                          fnCreatedCell: function(nTd, sData, oData, iRow, iCol){
                              var checklistitemID = oData[3];
                              var sectionName = "${sectionName.replaceAll(' |/', '-')}";
                              var nid = sectionName + "_0_" + checklistitemID;
                              var sectionLink = "EdoChecklistItem.do?&nid=" + nid;
                              var sectionHREF = "<a href='" + sectionLink + "'>" + oData[2] + "</a>";
                              var i = $(sectionHREF);
                              $(nTd).empty();
                              $(nTd).append(i);
                            }
                        },
                        { aTargets: [3], sTitle: "Item ID", bVisible: false },
                        { aTargets: [4], sTitle: "Section ID", bVisible: false },
                        { aTargets: [5], sTitle: "Dossier ID", bVisible: false }
                    ],
                    "bJQueryUI": true,
                    "iDisplayLength": 20
                }
        )
    });
</script>

<table id="summary">
</table>
