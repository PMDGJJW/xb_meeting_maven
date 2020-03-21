<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<link rel="stylesheet" href=" ${path}/static/bootstrap/css/bootstrap.css">
<%--<link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">--%>
<%--<link rel="stylesheet" href="${path}/static/css/font.css">--%>
<%--<link rel="stylesheet" href="${path}/static/css/style.default.css" id="theme-stylesheet">--%>
<%--<link rel="stylesheet" href="${path}/static/css/custom.css?v=2">--%>
<%--<link rel="stylesheet" href="${path}/static/css/layer.css">--%>
<link rel="stylesheet" href="${path}/static/bootstrap/css/bootstrap-switch.min.css">
<link rel="stylesheet" href="${path}/static/bootstrap/css/bootstrap-select.min.css">
<link rel="stylesheet" href="${path}/static/bootstrap/css/bootstrap-datepicker.min.css">
<link rel="stylesheet" href="${path}/static/bootstrap/css/bootstrap-datetimepicker.css">

<script src="${path}/static/js/jquery-3.4.1.js"></script>
<script src="${path}/static/bootstrap/js/bootstrap-select.min.js"></script>
<script src="${path}/static/bootstrap/js/bootstrap-switch.min.js"></script>
<script src="${path}/static/bootstrap/js/bootstrap-datepicker.min.js"></script>
<script src="${path}/static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="${path}/static/bootstrap/js/bootstrap-collapse.js"></script>
<script src="${path}/static/vendor/popper.js/umd/popper.min.js"> </script>
<script src="${path}/static/vendor/bootstrap/js/bootstrap.min.js"></script>

<%--<script src="/vendor/jquery.cookie/jquery.cookie.js"> </script>--%>
<%--<script src="/vendor/chart.js/Chart.min.js"></script>--%>
<%--<script src="${path}/static/js/layer.js"></script>--%>
<%--<script src="${path}/static/jquery-validation/jquery.validate.min.js"></script>--%>
<%--<script src="/js/charts-home.js?v=11"></script>--%>
<script src="${path}/static/js/front.js"></script>
<script src="${path}/static/js/custom.js?v=1"></script>

<input type="hidden" id="xb" value="${pageContext.request.contextPath}">

