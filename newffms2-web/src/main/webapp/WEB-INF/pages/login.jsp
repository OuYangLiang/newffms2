<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <header>
        <meta name="description" content="家庭管理系统 - Ver 2">
        <meta name="author" content="欧阳亮">
        <meta charset="UTF-8">
        
        <title>家庭管理系统 - Ver 2</title>
        <link rel="stylesheet" href="<c:url value='/css/login.css' />" type="text/css" />
    </header>
    
    <body>
        <form id="login" method="post" autocomplete="off" action="<c:url value='/j_spring_security_check' />">
            <h1 id="ff-proof" class="ribbon">家庭管理系统&nbsp;&nbsp;</h1>
            <div class="apple">
                <div class="top"><span></span></div>
                <div class="butt"><span></span></div>
                <div class="bite"></div>
            </div>
            <fieldset id="inputs">
                <input id="username" name="j_username" type="text" placeholder="Login Id or Email" />
                <input id="password" name="j_password" type="password" placeholder="Password" />
            </fieldset>
            <fieldset id="actions">
                <input type="submit" id="submit" value="进入"/>
                <p class="option"><a href="#">Forgot Password ?</a></p>
            </fieldset>
        </form>
        
        
        <script>
            function displayError() {
            	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                    alert("Your login attempt was not successful, try again.\n\nReason: " + '<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>')
            	</c:if>
            }

            displayError();
        </script>
    </body>
</html>