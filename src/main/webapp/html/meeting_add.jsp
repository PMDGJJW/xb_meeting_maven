<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>发布会议</title>
    <style>
        /*这个是这页面特有的，需要单独写出来*/
        .myTitle > input {
            float: right;
            margin-left: 15px;
        }

    </style>
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
                <h2 class="h5 no-margin-bottom">发布会议</h2>
            </div>
        </div>

        <section class="no-padding-bottom">

            <div class="block-body">
                <form action="/meeting/add" method="post">
                    <div class="form-group">
                        <label class="form-control-label">标题</label>
                        <input type="text" placeholder="会议标题" class="form-control" name="title" required>
                    </div>
                    <!--选择部门-->
                    <div class="form-group">
                        <label class="form-control-label">部门：</label>
                        <select class="selectpicker" data-live-search="true" name="deptId" id="depts" required>

                        </select>

                        <%--提交的部门名称--%>
                        <input type="hidden" value="${deptMap.name}" name="deptName" id="deptName" >

                        <label class="form-control-label">抄送人：</label>
                        <select class="selectpicker" data-live-search="true"
                                title="请先选择部门"
                                multiple name="makeUser" id="users" required>
                        </select>
                    </div>
                    <!--开始时间-->
                    <div class="form-group">
                        <label class="form-control-label">开始时间</label>

                        <input type="text" class="form-control" id="startTime" name="startTime"
                               data-date-format="yyyy-mm-dd hh:ii:ss" required>
                        <script>
							<%--  初始化日期选择框 --%>
                            $(function () {
                                $('#startTime').datetimepicker();
                            })
                        </script>

                    </div>
                    <!--结束时间-->
                    <div class="form-group">
                        <label class="form-control-label">结束时间</label>

                        <input type="text" class="form-control" id="endTime" name="endTime" data-date-format="yyyy-mm-dd hh:ii:ss" required>
                        <script>
                            $(function () {
                                $('#endTime').datetimepicker();
                            })
                        </script>

                    </div>
                    <div class="form-group">
                        <label class="form-control-label">会议内容</label>
                        <textarea class="form-control" rows="5" name="content" required></textarea>
                    </div>
                    <div class="text-center form-group">
                        <input type="submit" value="发布" class="btn btn-primary">
                        <input type="reset" value="清空" class="btn btn-info">
                    </div>
                </form>
            </div>
        </section>
        <jsp:include page="/incloud/footer.jsp"/>
    </div>
</div>

<script>

    $(function () {
        $("#depts").change(function () {

            // 设置部门名称
            $("#deptName").val($(this).find("option:selected").html());

            $.get("/dept/findUserByDeptId?id="+$(this).val(),function (res) {

                var userHtml="";
                for(var user of res){

                    userHtml+="<option value=\""+user.id+"\">"+user.realName+"</option>"

                }
                $("#users").html(userHtml);
                // 刷新页面UI
                $('#users').selectpicker('refresh');
            })
        })


        $.get("/dept/findAll",function (res) {

            var deptHtml="<option value=\"\">---请选择---</option>";
            for(var dept of res){

                deptHtml+="<option value=\""+dept.id+"\">"+dept.name+"</option>";
            }
            $('#depts').html(deptHtml);

            // 刷新页面UI
            $('#depts').selectpicker('refresh');
        })

    })

</script>
</body>
</html>