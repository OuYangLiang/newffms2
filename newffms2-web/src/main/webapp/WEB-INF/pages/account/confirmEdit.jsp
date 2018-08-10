<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
    
    </head>

    <body>
        <section class="content-header">
            <h1>
                账户<small>修改确认</small>
            </h1>
        </section>
        
        <section class="content">
            <div class="row">
                <div class="col-sm-12">
                    <div style="padding-left: 20px; padding-bottom: 20px;">
		                <button type="button" class="btn btn-primary btn-flat" id="btn-save">
		                    <i class="glyphicon glyphicon-ok"></i>
		                </button>
		            
		                <button type="button" class="btn btn-primary btn-flat" id="btn-cancel">
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
			                    <div class="form-group">
			                        <label for="userNameInput" class="col-xs-4 col-sm-2 control-label">账户所有人</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <div class="form-control" style="BORDER-STYLE: none;" id="userNameInput">${acntForm.owner.userName }</div>
			                        </div>
			                    </div>
			                    
			                    <div class="form-group">
			                        <label for="acntTypeInput" class="col-xs-4 col-sm-2 control-label">账户类型</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <div class="form-control" style="BORDER-STYLE: none;" id=acntTypeInput>${acntForm.acntType.desc }</div>
			                        </div>
			                    </div>
			                    
			                    <div class="form-group">
			                        <label for="acntDescInput" class="col-xs-4 col-sm-2 control-label">描述</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <div class="form-control" style="BORDER-STYLE: none;" id="acntDescInput">${acntForm.acntDesc }</div>
			                        </div>
			                    </div>
			                    
			                    <div class="form-group">
			                        <label for="balanceInput" class="col-xs-4 col-sm-2 control-label">初始可用额度</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <div class="form-control" style="BORDER-STYLE: none;" id="balanceInput">${acntForm.balance }</div>
			                        </div>
			                    </div>
			                    
			                    <c:if test="${acntForm.acntType == \"Creditcard\" }" >
			                    <div class="form-group" id="quota">
			                        <label for="quotaInput" class="col-xs-4 col-sm-2 control-label">限定额度</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <div class="form-control" style="BORDER-STYLE: none;" id="quotaInput">${acntForm.quota }</div>
			                        </div>
			                    </div>
			                    
			                    <div class="form-group" id="debt">
			                        <label for="debtInput" class="col-xs-4 col-sm-2 control-label">初始欠款额度</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <div class="form-control" style="BORDER-STYLE: none;" id="debtInput">${acntForm.debt }</div>
			                        </div>
			                    </div>
			                    </c:if>
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
                    window.location.href = "<c:url value='/account/initEdit?back=true' />";
                });
                
            	$ ("#btn-save").click(function(){
                    window.location.href = "<c:url value='/account/saveEdit' />";
                });
            });
        </script>
    </body>
</html>
