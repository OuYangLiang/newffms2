<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <head>
        <title>This is the title.</title>
        <link rel="stylesheet" href="<c:url value='/css/consumption.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
    </head>
    
    <body>
        <div class="button-area">
            <button id="btn-save">修改</button>
        </div>
        
        <spring:errors path="errors" />
        
        <c:url value='/profile/confirmEdit' var='url' />
        <spring:form id="form" method="post" action="${url}" modelAttribute="userForm" autocomplete="off">
        <input type="hidden" name="userOid" value="${userForm.userOid }" />
        
        <div id="errorArea" class="ui-widget" style="margin-bottom:5px;display:none">
            <div class="ui-state-error ui-corner-all" style="margin-right: 400px; padding: 5px 30px;" >
                <span class="ui-icon ui-icon-alert" style="float: left; margin-top:5px; "></span>
                <span id="errorMsg" style="font-size: 70%;"><spring:errors path="*" /></span>
            </div>
            
            <div style="clear:both;" ></div>
        </div>
        
        <div class="content-header ui-widget-header">
            个人信息<span style="font-size: 80%;"></span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">姓名</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" path="userName" class="inputbox" maxlength="6" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">姓别</div>
                    
                    <div class="input">
                        <spring:select path="gender" class="selectbox" >
                            <spring:options items="${genders}" />
                        </spring:select>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">手机</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" path="phone" class="inputbox" maxlength="11" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">邮箱</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" path="email" class="inputbox" maxlength="50" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">用户名</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" path="loginId" class="inputbox" maxlength="10" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content" >
                    <div class="label">修改密码</div>
                
                    <div class="input" >
                        <div style="float: left;width: 100px;"><input type="checkbox" name="changePwd" value="true" id="changePwd" class="checkbox" <c:if test="${userForm.changePwd }" >checked="checked"</c:if> /></div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                
                
                <div id="pwdArea" <c:if test="${!userForm.changePwd }" >style="display:none;"</c:if>>
	                <div class="newline-wrapper ui-widget-content">
	                    <div class="label">原密码</div>
	                    
	                    <div class="input">
	                        <input type="password" name="loginPwdOrigin" class="inputbox" value="" />
	                    </div>
	                    
	                    <div style="clear:both;" ></div>
	                </div>
	                
	                <div class="newline-wrapper ui-widget-content">
	                    <div class="label">新密码</div>
	                    
	                    <div class="input">
	                        <input type="password" name="loginPwdNew" class="inputbox" value="" />
	                    </div>
	                    
	                    <div style="clear:both;" ></div>
	                </div>
	                
	                <div class="newline-wrapper ui-widget-content">
	                    <div class="label">密码确认</div>
	                    
	                    <div class="input">
	                        <input type="password" name="loginPwdConfirm" class="inputbox" value="" />
	                    </div>
	                    
	                    <div style="clear:both;" ></div>
	                </div>
                </div>
            
            </div>
            
        </div>
        
        </spring:form>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine-zh_CN.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/common.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
                if ('<c:out value="${validation}" />' === 'false') {
                    $("#errorArea").css("display", "");
                }
                
                $ (".button-area button").button();
                
                $( ".selectbox" ).selectmenu();
                
                $ ("#btn-save").click(function(){
                    //提交表单时，使用使用jquery validation engine进行前端基本验证。
                    $("#form").validationEngine();
                    if ($ ("#form").validationEngine('validate')) {
                        $ ("#form").submit();
                    }
                });
                
                $("#changePwd").click(function(){
                    if ($("#changePwd").prop("checked")) {
                        $ ("#pwdArea").attr("style", "display:''");
                    } else {
                        $ ("#pwdArea").attr("style", "display:none;");
                    }
                });
            });
        </script>
    
    </body>
</html>