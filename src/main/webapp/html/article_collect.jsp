<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>我的收藏</title>

</head>
<body>
<jsp:include page="/incloud/header.jsp" />

<div class="d-flex align-items-stretch">
    <!-- Sidebar Navigation-->
    <jsp:include page="/incloud/sidebar.jsp" />
    <!-- Sidebar Navigation end-->
    <div class="page-content">
        <div class="page-header">
            <div class="container-fluid">
                <h2 class="h5 no-margin-bottom">我收藏的文章</h2>
            </div>
        </div>

        <section class="no-padding-bottom">

            <div class="list-group myList">
                <!--搜索文章的条件-->
                <div class="myTitle">
                    <form class="form-inline" action="/article/favoritePage" method="get">
                        <div class="form-group">
                            <label for="inlineFormInput" class="sr-only">Name</label>
                            <input type="hidden" value="1" name="currPage">
                            <input id="inlineFormInput" value="${title}" type="text" name="title" placeholder="按标题名字查找" class="mr-sm-3 form-control">
                        </div>
                        <div class="form-group">
                            <input type="submit" value="查询" class="btn btn-primary">
                        </div>
                    </form>

                    <input id = "addArt" type="submit" value="发布文章" class="btn btn-primary">

                </div>

                <ul>
                    <c:forEach items="${pageData.data}" var="article">
                        <li class="list-group-item">

                            <a href="/article/articleDetail?id=${article.id}">${article.title}</a>
                            <p class="h6"><strong>作者：</strong>${article.publishRealName}</p>
                            <p class="h6"><strong>时间：</strong>${article.publishDate}</p>
                            <p style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis">${article.content}</p>
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
                            <li><a href="/article/favoritePage?currPage=${i.count}&title=${title}">${i.count}</a></li>
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

        <jsp:include page="/incloud/footer.jsp" />
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

        window.location.href = "/article/favoritePage?favoritePage=${pageData.currPage - 1}&title=${title}"
    }

    /**
     * 下一页
     */
    function next() {
        if (${pageData.currPage+1>pageData.totalPage}) {

            layer.msg('已经到底啦！');
            return;

        }

        window.location.href = "/article/favoritePage?favoritePage=${pageData.currPage + 1}&title=${title}"
    }
</script>
</body>
</html>