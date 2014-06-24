<%@ attribute name="addLink" %>
<%@ attribute name="selectLink" %>

<script lang="text/javascript">
    $(document).ready( function() {
        $("#it_list").dataTable(
                {
                    "oTableTools": {
                        "sScrollX": "100%",
                        "bScrollCollapse": true,
                        "sRowSelect": "single",
                        "fnRowSelected" : function( nodes ) {
                            document.location.href = '${selectLink}&op=edit&itid=' + nodes[0].cells[0].innerText;
                        },
                        "aButtons": [
                            {
                                "sExtends":    "text",
                                "sButtonText": "Add Item Type",
                                "fnClick": function ( nButton, oConfig, oFlash ) {
                                    document.location.href = '${addLink}&op=add';
                                }
                            }
                        ]},
                    "sDom": 'T<"fg-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix"lf>t<"fg-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix"ip>',
                    "aaData": [
                        ${itemTypesJSON}
                    ],
                    "aoColumnDefs": [
                        { aTargets: [0], sTitle: "Item Type ID", bVisible: true },
                        { aTargets: [1], sTitle: "Item Type Name", bVisible: true },
                        { aTargets: [2], sTitle: "Description", bVisible: true },
                        { aTargets: [3], sTitle: "Instructions", bVisible: false },
                        { aTargets: [4], sTitle: "Externally Available", bVisible: true },
                        { aTargets: [5], sTitle: "Create Date", bVisible: false },
                        { aTargets: [6], sTitle: "Created By", bVisible: false },
                        { aTargets: [7], sTitle: "Last Updated", bVisible: true },
                        { aTargets: [8], sTitle: "Updated By", bVisible: false }
                    ],
                    "bJQueryUI": true
                }
        );
    });
</script>
