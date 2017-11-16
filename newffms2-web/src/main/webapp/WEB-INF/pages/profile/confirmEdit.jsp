<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <body>
        <div class="button-area">
            <button id="btn-save">确认</button>
            <button id="btn-cancel">返回</button>
        </div>
        
        <div class="content-header ui-widget-header">
            个人信息<span style="font-size: 80%;"> - 修改确认</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">姓名</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${userForm.userName }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">姓别</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${userForm.gender.desc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">手机</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${userForm.phone }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">邮箱</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${userForm.email }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">用户名</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${userForm.loginId }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            </div>
            
        </div>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	$ ("#btn-cancel").click(function(){
                    window.location.href = "<c:url value='/profile/initEdit?back=true' />";
                });
                
                $ ("#btn-save").click(function(){
                    window.location.href = "<c:url value='/profile/saveEdit' />";
                });
                
                $ (".button-area button").button();
            });
        </script>
    
    </body>
</html>