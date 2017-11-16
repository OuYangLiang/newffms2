<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
    
    </head>

    <body>
        <section class="content-header">
            <h1>
                类别<small>修改确认</small>
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
                            <i class="glyphicon glyphicon-arrow-left"></i>
                        </button>
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="col-sm-12">
                    <div class="box box-primary">
                        <div class="box-body">
                            <div class="form-horizontal">
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
                                        <div class="form-control" style="BORDER-STYLE: none;" id="categoryDescInput">${catForm.categoryDesc }</div>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="monthlyBudgetInput" class="col-xs-4 col-sm-2 control-label">月度预算</label>
                                    <div class="col-xs-7 col-sm-4">
                                        <div class="form-control" style="BORDER-STYLE: none;" id="monthlyBudgetInput">${catForm.monthlyBudget }</div>
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/AdminLTE2/js/app.min.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
                $ ("#btn-cancel").click(function(){
                    window.location.href = "<c:url value='/category/initEdit?back=true' />";
                });
                
                $ ("#btn-save").click(function(){
                    window.location.href = "<c:url value='/category/saveEdit' />";
                });
            });
        </script>
    </body>
</html>
