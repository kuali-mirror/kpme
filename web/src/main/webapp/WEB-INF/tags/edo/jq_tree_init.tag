<%@ attribute name="div_id" required="true" %>
<%@ attribute name="initially_select" %>

<script type="text/javascript">
    $(function () {
        $("#${div_id}")
            .bind("loaded.jstree", function (event, data) {
                $(".treenav").show();
            })
            .jstree({
                "plugins" : ["html_data","ui","cookies","themeroller"],
                "ui"      : { "select_limit" : 1 },
                "cookies" : { save_selected: false },
                "core"    : { "initially_open" : [ "#${initially_select}" ] }
            });

        setTimeout( function() { $("#${div_id}").jstree("set_focus"); }, 500);

        $("#${div_id}").bind("select_node.jstree", function(event, data) {
            // the jstree ui plugin intercepts the click and prevents the anchor from acting on it,
            // so we have to follow the href manually
            var href = data.rslt.obj.children("a").attr("href") + "&nid=" + data.rslt.obj.attr("id");
            if ($("#${div_id}").jstree("is_closed", "#" + data.rslt.obj[0].id)) {
                $("#${div_id}").jstree("toggle_node", "#" + data.rslt.obj[0].id);
            }
            document.location.href = href;
        })
    });

</script>
