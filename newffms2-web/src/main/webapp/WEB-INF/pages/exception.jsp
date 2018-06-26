<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
<body>
    <section class="content">
        <div class="alert alert-danger" role="alert">
            <i class="fa fa-exclamation-triangle fa-2x" aria-hidden="true"></i><br/>
            <p><br/>出错啦，请把错误码<strong>[<c:out value="${tickNo }" />]</strong>发送给管理员。</p>
        </div>
    </section>
    
    <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
    <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
    <script src="<c:url value='/AdminLTE2/js/app.min.js' />" charset="utf-8"></script>
</body>
</html>
