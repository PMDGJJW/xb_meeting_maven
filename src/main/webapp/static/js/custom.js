/* your js go here */
$(function () {



/*    $("#avatar").click(function () {
        console.log("你点击了头像");
        window.location.href = '../html/user_look.jsp'
    });*/

    //file表单选中文件时,让file表单的val展示到showname这个展示框
    $('#myFile').change(function () {
        $('#showname').val($(this).val())
    });

    //用户列表，查看详情，通过class名字userDetail来找
/*    $(".userDetail").click(function () {
        console.log("你点击了用户详情");
        window.location.href = '../html/user_detail.jsp'
    });*/

    //发布会议跳转链接
    $("#addMeet").click(function () {
        window.location.href = '../html/meeting_add.jsp'
    });

    //添加文章跳转链接
    $("#addArt").click(function () {
        window.location.href = '../html/article_add.jsp'
    });


});

