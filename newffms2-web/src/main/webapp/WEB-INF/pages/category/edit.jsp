<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
    </head>

    <body>
        <section class="content-header">
            <h1>
                类别<small>修改</small>
            </h1>
        </section>
        
        <section class="content">
            <div class="row">
                <div class="col-sm-12">
                    <div style="padding-left: 20px; padding-bottom: 20px;">
                        <button type="button" class="btn btn-default" id="btn-save">
                            <i class="glyphicon glyphicon-ok"></i>
                        </button>
                    
                        <button type="button" class="btn btn-default" id="btn-cancel">
                            <i class="glyphicon glyphicon-remove"></i>
                        </button>
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="col-sm-12">
                    <div class="box box-primary">
                        <div class="box-body">
                            <c:url value='/category/confirmEdit' var='url' />
                            <spring:form id="form" class="form-horizontal" method="post" action="${url}" modelAttribute="catForm" autocomplete="off" >
                                <input type="hidden" name="categoryOid" value="${catForm.categoryOid}" />
                                <input type="hidden" name="parentOid" value="${catForm.parentOid}" />
                            
                                <div class="row" id="errorArea" style="display:none">
                                    <div class="col-md-1"></div>
                                    <div class="col-md-10">
                                        <div class="alert alert-danger" role="alert" >
                                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                            <span class="sr-only">Error:</span>
                                            <spring:errors path="*" />
                                        </div>
                                    </div>
                                </div>
                                
                                <c:if test="${catForm.parent != null }">
                                    <div class="form-group">
                                        <label for="parentDescInput" class="col-xs-4 col-sm-2 control-label">上级类别</label>
                                        <div class="col-xs-7 col-sm-4">
                                            <div class="form-control" style="BORDER-STYLE: none;" id="parentDescInput">${catForm.parent.categoryDesc }</div>
                                        </div>
                                    </div>
                                </c:if>
                                
                                <div class="form-group">
                                    <label for="categoryDescInput" class="col-xs-4 col-sm-2 control-label">描述</label>
                                    <div class="col-xs-7 col-sm-4">
                                        <spring:input data-validation-engine="validate[required]" path="categoryDesc" class="inputbox" maxlength="10" id="categoryDescInput" />
                                    </div>
                                </div>
                                
                                <c:choose>
                                    <c:when test="${ catForm.isLeaf == true }" >
                                        <div class="form-group">
		                                    <label for="monthlyBudgetInput" class="col-xs-4 col-sm-2 control-label">月度预算</label>
		                                    <div class="col-xs-7 col-sm-4">
		                                        <spring:input data-validation-engine="validate[required]" path="monthlyBudget" class="form-control" onBlur="javascript:checkAmount(this);" maxlength="11" id="monthlyBudgetInput"/>
		                                    </div>
		                                </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="form-group">
		                                    <label for="monthlyBudgetInput" class="col-xs-4 col-sm-2 control-label">月度预算</label>
		                                    <div class="col-xs-7 col-sm-4">
		                                        <div class="form-control" style="BORDER-STYLE: none;" id="monthlyBudgetInput">${catForm.monthlyBudget }</div>
		                                    </div>
		                                </div>
                                    </c:otherwise>
                                </c:choose>
                            
                            </spring:form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine-zh_CN.js' />" charset="utf-8"></script>
        <script src="<c:url value='/AdminLTE2/js/app.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/common.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
                
                if ('<c:out value="${validation}" />' === 'false') {
                    $("#errorArea").css("display", "");
                }
                
                $ ("#btn-cancel").click(function(){
                    window.location.href = "<c:url value='/category/view' />?categoryOid=<c:out value='${catForm.categoryOid}' />";
                });
                
                $ ("#btn-save").click(function(){
                    //提交表单时，使用使用jquery validation engine进行前端基本验证。
                    $("#form").validationEngine();
                    if ($ ("#form").validationEngine('validate')) {
                        $ ("#form").submit();
                    }
                });
                
            });
        </script>
    </body>
</html>
