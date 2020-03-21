<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>部门</title>

</head>
<body>
<jsp:include page="/incloud/header.jsp"/>

<div class="d-flex align-items-stretch">
    <!-- Sidebar Navigation-->
    <jsp:include page="/incloud/sidebar.jsp"/>
    <!-- Sidebar Navigation end-->
    <div class="page-content">
        <div class="page-header">
            <div class="container-fluid">
                <h2 class="h5 no-margin-bottom">全部部门</h2>
            </div>
        </div>
        <section class="no-padding-bottom">

            <div class="list-group subject" id="depts">
                <div class="list-group-item">
                    <a href="#deptDetail1" data-toggle="collapse">研发部 &nbsp;3人</a>
                    <div id="deptDetail1" class="collapse deptDetail">
                        <ul>
                            <li>
                                <a href="#">小东</a>
                            </li>
                            <li>
                                <a href="#">小方</a>
                            </li>
                            <li>
                                <a href="#">小标</a>
                            </li>
                        </ul>
                    </div>
                </div>

            </div>
        </section>

        <jsp:include page="/incloud/footer.jsp"/>
    </div>
</div>
<script>

    $(function () {

        var deptHtml = "";

        $.get("/dept/findAll", function (res) {

            // 遍历每个部门
            for (var i = 0; i < res.length; i++) {
                var userHtml = "";

                var dept = res[i];

                // 遍历每个部门下的所有用户
                for (var user of res[i].userMapList) {


                    userHtml += "                            <li>\n" +
                        "                                <a href=\"javascript:\" onclick='userDetail("+user.id+","+user.isSecret+")'>" + user.realName + "</a>\n" +
                        "                            </li>";


                }

                // 追加到部门里面
                deptHtml += "<div class=\"list-group-item\">\n" +
                    "                    <a href=\"#deptDetail" + i + "\" data-toggle=\"collapse\">" + dept.name + " &nbsp;" + dept.deptCount + "人</a>\n" +
                    "                    <div id=\"deptDetail" + i + "\" class=\"collapse deptDetail\">\n" +
                    userHtml+
                "                    </div>\n" +
                "                </div>";

            }

            $("#depts").html(deptHtml);

        })

    })

    /**
     * 查看详情
     */
    function userDetail(userId, isSecret) {
        if (isSecret == 0) {
            layer.msg('对方设置了私密！');
            return;
        }
        location.href = '/user/userDetail?flag=detail&id=' + userId;

    }

</script>
</body>
</html>