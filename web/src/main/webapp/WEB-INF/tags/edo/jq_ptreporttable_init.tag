
<script lang="text/javascript">
    $(document).ready( function() {
        var oCandidateTable = $("#pt_report").dataTable(
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
                        ${reportJSON}
                    ],
                    "aoColumnDefs"  : [
                        ${reportHeaders}
                    ],
                    "bJQueryUI": true,
                    "iDisplayLength": 25
                }
        );
    });
</script>
