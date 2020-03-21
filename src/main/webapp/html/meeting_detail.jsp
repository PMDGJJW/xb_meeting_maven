<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>会议详情</title>
    <style>

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
                <h2 class="h5 no-margin-bottom">会议详情</h2>
            </div>
        </div>

        <section class="no-padding-bottom">

            <div class="myTitle">
                <h3 class="text-center">${meeting.title}</h3>


                <c:if test="${flag}">
                    <input type="submit" value="取消会议" onclick="joinMeeting()"
                           class="btn btn-danger">

                </c:if>
                <c:if test="${!flag}">
                    <input type="submit" value="参加会议" onclick="joinMeeting()"
                           class="btn btn-info">

                </c:if>


            </div>

            <div class="myContent">
                <p class="h6"><strong>部门：</strong>研发部</p>
                <p class="h6"><strong>应到：</strong>${joinCount}<span>人</span></p>
                <p class="h6"><strong>实到：</strong>${realCount}<span>人</span></p>
                <p class="h6"><strong>未到：</strong>${noCount}<span>人</span></p>
                <textarea style="padding: 2px" disabled="disabled" class="form-control" rows="11">
                    ${meeting.content}

                </textarea>


            </div>

            <div class="text-right myList">
                <p>开始时间：${meeting.startTime}</p>
                <p>日期：${meeting.publishDate}</p>
            </div>
        </section>

        <jsp:include page="/incloud/footer.jsp"/>
    </div>
</div>


</body>

<script>

    /**
     * 参加/取消 会议
     */
    function joinMeeting() {


        if(${meeting.status==1}){
            layer.msg('不可操作!会议正在进行中!')
            return;
        }


        if(${meeting.status==2}){
            layer.msg('不可操作!已经结束!')
            return;
        }

        window.location.href = "/meeting/joinMeeting?id=${meeting.id}";

    }


</script>
</html>