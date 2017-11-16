<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <link href="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.css' />" rel="stylesheet">
    </head>

    <body>
        <section class="content-header">
            <h1>
                类别<small>查看</small>
            </h1>
        </section>
        
        <section class="content">
            <div class="row">
                <div class="col-sm-12">
                    <div style="padding-left: 20px; padding-bottom: 20px;">
                        <button type="button" class="btn btn-default" id="btn-back">
                            <i class="glyphicon glyphicon-arrow-left"></i>
                        </button>
                    
                        <button type="button" class="btn btn-default" id="btn-edit">
                            <i class="glyphicon glyphicon-edit"></i>
                        </button>
                        
                        <c:if test="${ removable && catForm.isLeaf }" >
                            <button type="button" class="btn btn-default" id="btn-delete" data-toggle="modal" data-target="#deleteModal">
                                <i class="glyphicon glyphicon-remove"></i>
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
                
            <div class="row">
                <div class="col-sm-12">
                    <div class="box box-primary">
                        <div class="box-body">
                            <div class="form-horizontal">
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
                                
                                <div class="form-group">
                                    <label for="categoryLevelInput" class="col-xs-4 col-sm-2 control-label">类别层次</label>
                                    <div class="col-xs-7 col-sm-4">
                                        <div class="form-control" style="BORDER-STYLE: none;" id="categoryLevelInput">${catForm.categoryLevel + 1 }</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    
        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="deleteModalLabel">警告</h4>
                    </div>
                    <div class="modal-body">
                        <div class="container lead">
                            确定要删除吗?
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-danger" id="btn-delete-confirm" data-dismiss="modal">删除</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/AdminLTE2/js/app.min.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
                $ ("#btn-back").click(function(){
                    window.location.href = "<c:url value='/category/summary?keepSp=Y' />";
                });
                
                $ ("#btn-edit").click(function(){
                    window.location.href = "<c:url value='/category/initEdit' />?categoryOid=<c:out value='${catForm.categoryOid}' />";
                });
                
                
                if ($ ("#btn-delete").length > 0) {
                    $ ("#btn-delete-confirm").click(function(){
                        window.location.href = "<c:url value='/category/delete' />?categoryOid=<c:out value='${catForm.categoryOid}' />";
                    });
                }
                
            });
        </script>
    </body>
</html>
