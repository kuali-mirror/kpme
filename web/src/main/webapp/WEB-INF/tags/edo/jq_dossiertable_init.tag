<%@ attribute name="addLink" %>
<%@ attribute name="selectLink" %>

<script lang="text/javascript">
    $(document).ready( function() {
        var oDossierTable = $("#dossier_list").dataTable(
                {
                    "oTableTools": {
                        "sScrollX": "100%",
                        "bScrollCollapse": true,
                        "sRowSelect": "single",
                        "aButtons": [
                            {
                                "sExtends":    "collection",
                                "sButtonText": "Export",
                                "aButtons":    [ "csv", "xls", "pdf" ]
                            }
                        ],
                        "sSwfPath": "js/jquery/plugins/TableTools/media/swf/copy_csv_xls.swf"
                    },
                    "sDom": 'T<"fg-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix"lf>t<"fg-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix"ip>',
                    "aaData": [
                        ${dossierJSON}
                    ],
                    "aoColumnDefs"  : [
                        {  // put check box in the "Marked" column
                            aTargets: [0],    // Column number which needs to be modified
                            sTitle: "",
                            bVisible: true,
                            bSortable: false,
                            fnRender: function (o, v) {   // o, v contains the object and value for the column
                                var candidateID = o.aData[0];
                                var dossierID   = o.aData[1];
                                var linkString  = "document.location.href='${selectLink}&cid=" + candidateID + "&dossier=" + dossierID + "';";
                                return "<button onclick=" + linkString + ">View Dossier</button>";
                            }
                        },
                        {   aTargets: [1], sTitle: "Dossier ID", bVisible: false  },
                        {   aTargets: [2], sTitle: "Last Name", bVisible: true  },
                        {   aTargets: [3], sTitle: "First Name", bVisible: true  },
                        {   aTargets: [4], sTitle: "Username", bVisible: true  },
                        {   aTargets: [5], sTitle: "Current Rank", bVisible: true  },
                        {   aTargets: [6], sTitle: "Rank Sought", bVisible: true  },
                        {   aTargets: [7], sTitle: "Department", bVisible: true  },
                        {   aTargets: [8], sTitle: "Campus", bVisible: true  },
                        {   aTargets: [9], sTitle: "School", bVisible: true  },
                        {   aTargets: [10], sTitle: "Year", bVisible: true  },
                        {   aTargets: [11], sTitle: "Due Date", bVisible: true  },
                        {   aTargets: [12], sTitle: "Dossier Status", bVisible: true  }
                    ],
                    "bJQueryUI": true,
                    "iDisplayLength": 20
                }
        );
    });
</script>
