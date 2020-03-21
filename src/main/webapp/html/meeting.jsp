<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>会议系统</title>

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
                <h2 class="h5 no-margin-bottom">会议系统</h2>
            </div>
        </div>

        <section class="no-padding-bottom">

            <div class="list-group ">
                <!--搜索文章的条件-->
                <div class="myTitle">
                    <form class="form-inline" action="/meeting/findPage" method="get" id="searchForm">
                        <div class="form-group">
                            <input type="hidden" value="1" name="currPage">

                            <label for="inlineFormInput" class="sr-only">Name</label>
                            <input id="inlineFormInput" type="text" name="title" value="${title}" placeholder="按会议标题查找"
                                   class="mr-sm-3 form-control">
                        </div>

                        <!--选择状态-->
                        <div class="form-group" style="margin-right: 20px">
                            <select name="status" class="form-control selectpicker" id="status">
                                <option value="">请选择状态</option>
                                <option value="0" ${status==0?'selected':''}>未开始</option>
                                <option value="1" ${status==1?'selected':''}>正在进行</option>
                                <option value="2" ${status==2?'selected':''}>已结束</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="submit" value="查询" class="btn btn-primary mr-sm-3 ">
                        </div>
                    </form>

                    <input id="addMeet" type="submit" value="发布会议" class="btn btn-primary">


                </div>

                <ul class="myList">

                    <c:forEach items="${pageData.data}" var="meeting">
                        <li class="list-group-item">
                            <div style="float: right;">
                                <span><strong>状态：</strong>

                                    <c:if test="${meeting.status==0}">
                                        未开始
                                    </c:if>
                                    <c:if test="${meeting.status==1}">
                                        进行中
                                    </c:if>
                                    <c:if test="${meeting.status==2}">
                                        已结束
                                    </c:if>

                                </span>
                            </div>
                            <a href="/meeting/meetingDetail?id=${meeting.id}">${meeting.title}</a>
                            <p class="h6"><strong>部门：</strong>${meeting.deptName}</p>
                            <p class="h6"><strong>开始时间：</strong>${meeting.startTime}</p>
                            <p style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis">
                                ${meeting.content}</p>
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
                            <li><a href="/meeting/findPage?currPage=${i.count}&title=${title}">${i.count}</a></li>
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

</body>

<script>
    /**
     * 上一页
     */
    function pre() {
        if (${pageData.currPage-1<=0}) {

            layer.msg('已经到顶啦！');
            return;

        }

        window.location.href = "/meeting/findPage?currPage=${pageData.currPage - 1}&title=${title}"
    }

    /**
     * 下一页
     */
    function next() {
        if (${pageData.currPage+1>pageData.totalPage}) {

            layer.msg('已经到底啦！');
            return;

        }

        window.location.href = "/meeting/findPage?currPage=${pageData.currPage + 1}&title=${title}"
    }
</script>

</html>