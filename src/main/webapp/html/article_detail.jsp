<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>文章详情</title>
    <style>
        .myList li {
            display: inline-block;
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
                <h2 class="h5 no-margin-bottom">文章详情</h2>
            </div>
        </div>

        <section class="no-padding-bottom">

            <div class="myTitle">
                <h3 class="text-center">${article.title}</h3>
                <c:if test="${isFavorite}">

                    <input type="button" value="取消收藏" onclick="favorite()" class="btn btn-info">
                </c:if>
                <c:if test="${!isFavorite}">

                    <input type="button" value="收藏" onclick="favorite()" class="btn btn-info">
                </c:if>
            </div>

            <div class="myContent">
                <p class="h6"><strong>发布人：</strong>${article.publishRealName}</p>
                <p class="h6"><strong>创建时间：</strong>${article.publishDate}</p>
                <p class="h6"><strong>浏览次数：</strong>${article.browseCount}</p>
                <p class="h6"><strong>收藏次数：</strong>${favoriteCount}</p>
                <textarea style="padding: 2px" disabled="disabled" class="form-control" rows="11">
                    ${article.content}
                </textarea>

            </div>

            <div class="myList">
                <p>您关注的小伙伴等人也收藏了该文章</p>
                <ul>
                    <c:forEach items="${userFavorite}" var="map">
                        <li class="list-group-item">
                            <a href="/user/userDetail?flag=detail&id=${map.userId}">${map.realName}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </section>
        <jsp:include page="/incloud/footer.jsp"/>
    </div>
</div>

<script>
    /**
     * 文章收藏/取消收藏
     */
    function favorite() {
        $.post("/article/favorite", {
            id:${article.id},
            isFavorite:${isFavorite}
        }, function (res) {

            if (res != 500) {

                window.location.reload();
                return ;
            }
            layer.msg('服务器忙');
        })
    }

</script>
</body>
</html>