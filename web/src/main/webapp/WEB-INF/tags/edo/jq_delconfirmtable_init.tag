<script lang="text/javascript">
    var oTable;

    $(document).ready( function() {
        oTable = $("#item_list").dataTable(
            {
                "sDom": '<"fg-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix">t<"fg-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix">',
                "aaData": ${itemlistJSON},
                "aoColumns": [
                    { "sTitle": "Item ID", "bVisible": true },
                    { "sTitle": "File Name", "sClass": "bold-content" },
                    { "sTitle": "Checklist Item Category" },
                    { "sTitle": "Checklist Section" },
                    { "sTitle": "Mark", "bVisible": false }
                    ],
                "aoColumnDefs": [
                    {  // put check box in the "Marked" column
                        aTargets: [0],    // Column number which needs to be modified
                        fnRender: function (o, v) {   // o, v contains the object and value for the column
                            var itemID = o.aData[0];
                            return itemID + "<input type='hidden' value='" + itemID + "' name='itemID'/>";
                        },
                        sClass: 'tableCell'    // Optional - class to be applied to this table cell
                    },

                ],
                "bJQueryUI": true,
                "iDisplayLength": 50
            }
        )
    });
</script>
