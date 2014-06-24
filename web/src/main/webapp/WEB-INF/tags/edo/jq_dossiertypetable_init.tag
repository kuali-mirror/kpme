<%@ attribute name="addLink" %>
<%@ attribute name="selectLink" %>

<script lang="text/javascript">
    $(document).ready( function() {
        $("#dt_list").dataTable(
                {
                    "oTableTools": {
                        "sScrollX": "100%",
                        "bScrollCollapse": true,
                        "sRowSelect": "single",
                        "fnRowSelected" : function( nodes ) {
                            document.location.href = '${selectLink}&op=edit&dtid=' + nodes[0].cells[0].innerText;
                        },
                        "aButtons": [
                            {
                                "sExtends":    "text",
                                "sButtonText": "Add Dossier Type",
                                "fnClick": function ( nButton, oConfig, oFlash ) {
                                    document.location.href = '${addLink}&op=add';
                                }
                            }
                        ]},
                    "sDom": 'T<"fg-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix"lf>t<"fg-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix"ip>',
                    "aaData": [
                        ${dossierTypesJSON}
                    ],
                    "aoColumnDefs": [
                        { aTargets: [0], sTitle: "Dossier Type ID", bVisible: true },
                        { aTargets: [1], sTitle: "Dossier Type Name", bVisible: true },
                        { aTargets: [2], sTitle: "Dossier Type Code", bVisible: true },
                        { aTargets: [3], sTitle: "Last Updated", bVisible: true }
                    ],
                    "bJQueryUI": true
                }
        );
    });
</script>
