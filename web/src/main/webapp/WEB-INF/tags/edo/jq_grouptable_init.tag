<%@ attribute name="addLink" %>
<%@ attribute name="selectLink" %>

<script lang="text/javascript">
    $(document).ready( function() {
        var oGroupTable = $("#group_list").dataTable(
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
                        ${groupJSON}
                    ],
                    "aoColumnDefs"  : [
                        {   aTargets: [0],
                            sTitle: "Group Name",
                            sClass: 'tableCell'
                        },
                        {
                            aTargets: [1],
                            sTitle: "Review Level Description",
                            sClass: 'tableCell'
                        }
                    ],
                    "bJQueryUI": true,
                    "iDisplayLength": 25
                }
        );

        if ( $("#groupMembers").val() ) {
            $("#groupNameTitle").html("for ${groupNameTitle}<br>");
            $("#memberButtonDisplay").show();
        }

    });

    function loadmembers(grp) {

        $("#groupNameTitle").html("for " + grp + "<br>");
        $("#groupNameButton").html(grp);
        $("#addToGroupName").val(grp);
        $.ajax({
            type: "GET",
            url: 'EdoAdminGroups.do?methodToCall=getGroupMembers&name=' + grp,
            dataType: "text",
            success: function(data, status) {
                data.replace(/^\s+/g, '');
                $("#groupMembers").val(data);
            },
            error : function(data, status) {
                console.log("failed");
                console.log(status);
            }
        });
        $("#memberButtonDisplay").show();
    }
</script>
