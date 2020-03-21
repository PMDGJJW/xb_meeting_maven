<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>添加文章</title>

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
                <h2 class="h5 no-margin-bottom">添加文章</h2>
            </div>
        </div>

        <section class="no-padding-bottom">

            <div class="block-body">
                <form action="/article/add" method="post">
                    <div class="form-group">
                        <label class="form-control-label">标题</label>
                        <input type="text" placeholder="文章标题" class="form-control" name="title" required>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label">文章内容</label>
                        <textarea class="form-control" rows="5" name="content" required></textarea>
                    </div>
                    <div class="text-center form-group">
                        <input type="submit" value="发布" class="btn btn-primary">
                    </div>
                </form>
            </div>

        </section>

        <jsp:include page="/incloud/footer.jsp" />
    </div>
</div>

</body>
</html>