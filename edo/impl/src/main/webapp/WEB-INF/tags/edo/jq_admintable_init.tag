<script lang="text/javascript">
    $(document).ready( function() {
        var oAdminTable = $("#admin_list").dataTable(
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
                        ${memberJSON}
                    ],
                    "aoColumnDefs"  : [
                        {   aTargets: [0],
                            sTitle: "Department",
                            sClass: 'tableCell'
                        },
                        {   aTargets: [1],
                            sTitle: "School",
                            sClass: 'tableCell'
                        },
                        {   aTargets: [2],
                            sTitle: "Campus",
                            sClass: 'tableCell'
                        },
                        {   aTargets: [3],
                            sTitle: "Username",
                            sClass: 'tableCell'
                        },
                        {
                            aTargets: [4],
                            sTitle: "",
                            sClass: 'tableCell',
                            fnCreatedCell: function(nTd, sData, oData, iRow, iCol){
                                var mbrRemove = "<button name='remove' onclick='javascript:remove_member(" + iRow + ");'>Remove</button>";
                                var i = $(mbrRemove);
                                $(nTd).empty();
                                $(nTd).append(i);
                            }
                        }
                    ],
                    "bJQueryUI": true,
                    "iDisplayLength": 25
                }
        );

    });

    function add_member() {
        var member_record = new Array();

        member_record.push(document.getElementById('departmentCode').options[document.getElementById('departmentCode').options.selectedIndex].value);
        member_record.push(document.getElementById('schoolCode').options[document.getElementById('schoolCode').options.selectedIndex].value);
        member_record.push(document.getElementById('campusCode').options[document.getElementById('campusCode').options.selectedIndex].value);
        //member_record.push(document.getElementById('deptTitle').innerHTML);
        //member_record.push(document.getElementById('schoolTitle').innerHTML);
        //member_record.push(document.getElementById('campusTitle').innerHTML);
        member_record.push(document.getElementById('username').value);
        member_record.push("-");

        // check for empty values
        for (var i = 0; i < member_record.length; i++) {
            if (member_record[i].length < 1) {
                member_record[i] = "-";
            }
        }

        var oAdmTable = $('#admin_list').dataTable().fnAddData(member_record);
    }

    function remove_member(row_index) {
        $('#admin_list').dataTable().fnDeleteRow(row_index);
    }

    function get_members() {
        var data = $('#admin_list').dataTable().fnGetData();
        document.forms[0].memberData.value = JSON.stringify(data);
        document.forms[0].methodToCall.value = 'loadAdminData';
        document.forms[0].submit();
    }
</script>
