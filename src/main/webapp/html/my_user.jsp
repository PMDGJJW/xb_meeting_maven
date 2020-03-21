<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>我的用户列表</title>

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
                <h2 class="h5 no-margin-bottom">我的关注列表</h2>
            </div>
        </div>
        <section class="no-padding-bottom">

            <div class="list-group myList">
                <ul>
                    <c:forEach items="${pageData.data}" var="userFocus">
                        <li class="list-group-item">
                            <a href="javascript:" onclick="userDetail(${userFocus.userId},${userFocus.isSecret})">${userFocus.realName}</a>
                            <div style="float: right" class="text-right">
                                <input type="button" onclick="unFocus(${userFocus.userId})" class="btn btn-danger" value="取消关注">
                            </div>
                        </li>
                    </c:forEach>
                </ul>
                <nav class="text-center" aria-label="Page navigation">
                    <ul class="pagination">
                        <li>
                            <a href="javascript:" onclick="pre()" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <c:forEach begin="1" end="${pageData.totalPage}" varStatus="i">
                            <li><a href="/user/findFocus?currPage=${i.count}">${i.count}</a></li>
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

<script>
    /**
     * 上一页
     */
    function pre() {
        if (${pageData.currPage-1<=0}) {

            layer.msg('已经到顶啦！');
            return;

        }

        window.location.href = "/user/findFocus?currPage=${pageData.currPage - 1}"
    }

    /**
     * 下一页
     */
    function next() {
        if (${pageData.currPage+1>pageData.totalPage}) {

            layer.msg('已经到底啦！');
            return;

        }

        window.location.href = "/user/findFocus?currPage=${pageData.currPage + 1}"
    }


    /**
     * 取消关注
     */
    function unFocus(userId) {

        $.post("/user/focus?userId="+userId,function (res) {

            if(res==0){

                window.location.reload();

            }

        })

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