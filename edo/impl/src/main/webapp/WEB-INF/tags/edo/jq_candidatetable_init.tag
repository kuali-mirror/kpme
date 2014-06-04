<%@ attribute name="addLink" %>
<%@ attribute name="selectLink" %>

<script lang="text/javascript">
    $(document).ready( function() {
        var oCandidateTable = $("#candidate_list").dataTable(
                {
                    "oTableTools": {
                        "sScrollX": "100%",
                        "bScrollCollapse": true,
                        "sRowSelect": "single",
                        "aButtons": [
                            {
                                "sExtends":    "collection",
                                "sButtonText": "Export",
                                "aButtons":    [ "csv", "xls" ]
                            }
                        ],
                        "sSwfPath": "js/jquery/plugins/TableTools/media/swf/copy_csv_xls.swf"
                    },
                    "sDom": 'T<"fg-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix"lf>t<"fg-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix"ip>',
                    "aaData": [
                        ${candidateJSON}
                    ],
                    "aoColumnDefs"  : [
                        {  // put check box in the "Marked" column
                            aTargets: [0],
                            sTitle: "",
                            bSortable: false,
                            bVisible: true,
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
                        {   aTargets: [6], sTitle: "Rank Sought", bVisible: false  },
                        {   aTargets: [7], sTitle: "Department", bVisible: true  },
                        {   aTargets: [8], sTitle: "Campus", bVisible: true  },
                        {   aTargets: [9], sTitle: "School", bVisible: true  },
                        {   aTargets: [10], sTitle: "Year", bVisible: true  },
                        {   aTargets: [11], sTitle: "Dossier", bVisible: false  },
                        {   aTargets: [12], sTitle: "Status", bVisible: true  }
                    ],
                    "bJQueryUI": true,
                    "iDisplayLength": 25
                }
        );
    });
</script>
