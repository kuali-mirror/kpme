<script lang="text/javascript">
    $(document).ready( function() {
        var oMemberTable = $("#member_list").dataTable(
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
                            sTitle: "Review Level Number",
                            bVisible: false,
                            sClass: 'tableCell'
                        },
                        {   aTargets: [4],
                            sTitle: "Review Level",
                            sClass: 'tableCell'
                        },
                        {   aTargets: [5],
                            sTitle: "Tenure/Promotion Value",
                            sClass: 'tableCell',
                            bVisible: false
                        },
                        {   aTargets: [6],
                            sTitle: "Tenure/Promotion",
                            sClass: 'tableCell'
                        },
                        {   aTargets: [7],
                            sTitle: "Chair Value",
                            sClass: 'tableCell',
                            bVisible: false
                        },
                        {   aTargets: [8],
                            sTitle: "Chair?",
                            sClass: 'tableCell'
                        },
                        {   aTargets: [9],
                            sTitle: "Username",
                            sClass: 'tableCell'
                        },
                        {
                            aTargets: [10],
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

        member_record.push(document.getElementById('deptTitle').innerHTML);
        member_record.push(document.getElementById('schoolTitle').innerHTML);
        member_record.push(document.getElementById('campusTitle').innerHTML);
        member_record.push(document.getElementById('rl_list').options[document.getElementById('rl_list').options.selectedIndex].value);
        member_record.push(document.getElementById('rl_list').options[document.getElementById('rl_list').options.selectedIndex].text);
        member_record.push(document.getElementById('tnp').options[document.getElementById('tnp').options.selectedIndex].value);
        member_record.push(document.getElementById('tnp').options[document.getElementById('tnp').options.selectedIndex].text);
        member_record.push(document.getElementById('chair').options[document.getElementById('chair').options.selectedIndex].value);
        member_record.push(document.getElementById('chair').options[document.getElementById('chair').options.selectedIndex].text);
        member_record.push(document.getElementById('username').value);
        member_record.push("-");

        var oMbrTable = $('#member_list').dataTable().fnAddData(member_record);
    }

    function remove_member(row_index) {
        $('#member_list').dataTable().fnDeleteRow(row_index);
    }

    function get_members() {
        var data = $('#member_list').dataTable().fnGetData();
        document.forms[0].memberData.value = JSON.stringify(data);
        document.forms[0].methodToCall.value = 'loadMemberData';
        document.forms[0].submit();
    }
</script>
