<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>查看用户</title>
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
                <h2 class="h5 no-margin-bottom">用户列表</h2>
            </div>
        </div>
        <section class="no-padding-bottom">
            <div class="title">
                <form class="form-inline" action="/user/findPage" method="get">
                    <div class="form-group">
                        <input type="hidden" name="currPage" value="1">
                        <label for="inlineFormInput" class="sr-only">Name</label>
                        <input id="inlineFormInput" type="text" placeholder="按名字查找" name="realName" value="${realName}"
                               class="mr-sm-3 form-control">
                    </div>
                    <div class="form-group">
                        <input type="submit" value="查询" class="btn btn-primary">
                    </div>
                </form>

            </div>
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>年龄</th>
                        <th>简介</th>
                        <th>操作</th>
                        <th>加关注</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageData.data}" var="user">
                        <tr>
                            <th scope="row">${user.id}</th>
                            <td>${user.realName}</td>
                            <td>${user.gender==0?'男':'女'}</td>
                            <td>${user.age}</td>
                            <td>${user.desc}</td>
                            <td>
                                <input type="button" onclick="userDetail(${user.id},${user.isSecret})" value="详细信息"
                                       class="btn-xs btn-primary userDetail" >
                            </td>
                            <td>
                                <input type="checkbox" ${focus.contains(user.id)? 'checked':''}

                                       class="checkbox-template"
                                       onclick="addFocus(event,${user.id})">
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <nav class="text-center" aria-label="Page navigation">
                    <ul class="pagination">
                        <li>
                            <a href="javascript:" onclick="pre()" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <c:forEach begin="1" end="${pageData.totalPage}" varStatus="i">
                            <li><a href="/user/findPage?currPage=${i.count}&realName=${realName}">${i.count}</a></li>
                        </c:forEach>
                        <li>
                            <a href="javascript:" onclick="next()" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </section>

        <jsp:include page="/incloud/footer.jsp"/>
    </div>
</div>

<!-- JavaScript files-->


<script>

    /*    $(".table").find("input[type='checkbox']").on("click",function () {
            if($(this).prop("checked")){
                layer.msg("关注成功")
            }else{
                layer.msg("取关成功")

            }

        })*/


    function addFocus(e,userId) {

        if (userId ==${loginUser.id}) {
            layer.msg('不能关注自己！');
            e.preventDefault();     //取消默认事件
            return ;
        }
        $.post("/user/focus", {
            "userId": userId,
        }, function (res) {
            if (res == 0) {
                layer.msg("取关成功")
            } else {

                layer.msg("关注成功")
            }

        })
    }

    /**
     * 上一页
     */
    function pre() {
        if (${pageData.currPage-1<=0}) {

            layer.msg('已经到顶啦！');
            return;

        }

        window.location.href = "/user/findPage?currPage=${pageData.currPage - 1}&realName=${realName}"
    }

    /**
     * 下一页
     */
    function next() {
        if (${pageData.currPage+1>pageData.totalPage}) {

            layer.msg('已经到底啦！');
            return;

        }

        window.location.href = "/user/findPage?currPage=${pageData.currPage + 1}&realName=${realName}"
    }


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