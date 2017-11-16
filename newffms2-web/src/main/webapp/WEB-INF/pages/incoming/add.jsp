<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <link href="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.css' />" rel="stylesheet">
        <link href="<c:url value='/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css' />" rel="stylesheet">
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
    </head>

    <body>
        <section class="content-header">
            <h1>
                收入<small>新建</small>
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
                            <c:url value='/incoming/confirmAdd' var='url' />
			                <spring:form id="form" class="form-horizontal" method="post" action="${url}" modelAttribute="incomingForm" autocomplete="off" >
			                
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
			                
			                    <div class="form-group">
			                        <label for="userNameInput" class="col-xs-4 col-sm-2 control-label">收入人</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <spring:select path="ownerOid" class="form-control" id="userNameInput">
			                                <spring:option value="" label="请选择"/>
			                                <spring:options items="${users}" itemValue="userOid" itemLabel="userName"/>
			                            </spring:select>
			                        </div>
			                    </div>
			                    
			                    <div class="form-group">
			                        <label for="incomingTypeInput" class="col-xs-4 col-sm-2 control-label">收入类型</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <spring:select path="incomingType" class="form-control" id="incomingTypeInput">
			                                <spring:option value="" label="请选择"/>
			                                <spring:options items="${incomingTypes}" />
			                            </spring:select>
			                        </div>
			                    </div>
			                    
			                    <div class="form-group">
			                        <label for="incomingDescInput" class="col-xs-4 col-sm-2 control-label">说明</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <spring:input data-validation-engine="validate[required]" path="incomingDesc" class="form-control" maxlength="30" id="incomingDescInput"/>
			                        </div>
			                    </div>
			                    
			                    <div class="form-group">
			                        <label for="incomingDateInput" class="col-xs-4 col-sm-2 control-label">日期</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <div class='input-group date' id='incomingDatePicker'>
			                                <spring:input data-validation-engine="validate[required]" path="incomingDate" class="form-control" id="incomingDateInput"/>
			                                <span class="input-group-addon">
			                                    <span class="glyphicon glyphicon-calendar"></span>
			                                </span>
			                            </div>
			                        </div>
			                    </div>
			                    
			                    <div class="form-group">
			                        <label for="amountInput" class="col-xs-4 col-sm-2 control-label">金额</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <spring:input path="amount" class="form-control" onBlur="javascript:checkAmount(this);" maxlength="11" id="amountInput"/>
			                        </div>
			                    </div>
			                    
			                    <div class="form-group" id="debt">
			                        <label for="targetAccountInput" class="col-xs-4 col-sm-2 control-label">目标账户</label>
			                        <div class="col-xs-7 col-sm-4">
			                            <input data-validation-engine="validate[required]" id="targetAccountInput" value="<c:out value='${incomingForm.targetAccount.acntHumanDesc }' />" class="form-control" readonly="true" data-toggle="modal" data-target="#AccountSelectModal" />
			                            <input type="hidden" id="targetAcntOid" name="targetAccount.acntOid" value="<c:out value='${incomingForm.targetAccount.acntOid }' />" />
			                        </div>
			                    </div>
			                    
			                </spring:form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        
        <div class="modal fade" id="AccountSelectModal" tabindex="-1" role="dialog" aria-labelledby="AccountSelectModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="AccountSelectModalLabel">选择目标账户</h4>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid">
                            <table id="data-table" data-toggle="table"
                                data-url="<c:url value='/account/ajaxGetAllAccounts' />"
                                data-pagination="true"
                                data-side-pagination="client"
                                data-page-size="5"
                                data-page-number="1"
                                data-search="true"
                                data-search-on-enter-key="true"
                                data-show-toggle="true"
                                data-show-columns="true"
                                data-silent-sort="false"
                                data-row-style="rowStyle">
                                <thead>
                                    <tr>
                                        <th data-field="owner.userName">所有人</th>
                                        <th data-field="acntTypeDesc">账户类型</th>
                                        <th data-field="acntHumanDesc" data-formatter="accountFormatter">描述</th>
                                        <th data-field="balance">可用余额</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-table-1.10.1/locale/bootstrap-table-zh-CN.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/moment.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine-zh_CN.js' />" charset="utf-8"></script>
        <script src="<c:url value='/AdminLTE2/js/app.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/common.js' />" charset="utf-8"></script>
        
        <script>
	        chooseAccount = function(acntOid, acntHumanDesc)
	        {
	            $( "#targetAccountInput").val(acntHumanDesc);
	            $( "#targetAcntOid").val(acntOid);
	            $("#AccountSelectModal").modal("hide");
	        };
	    
	        function accountFormatter(val, row, idx) {
	            return "<a href=\"javascript:chooseAccount(" + row.acntOid + ", '" + val + "');\" >" + val + "</a>";
	        }
        
            $( document ).ready(function() {
                
                if ('<c:out value="${validation}" />' === 'false') {
                    $("#errorArea").css("display", "");
                }
                
                $ ("#btn-cancel").click(function(){
                    window.location.href = "<c:url value='/incoming/summary?keepSp=Y' />";
                });
                
                $ ("#btn-save").click(function(){
                    //提交表单时，使用使用jquery validation engine进行前端基本验证。
                    $("#form").validationEngine();
                    if ($ ("#form").validationEngine('validate')) {
                        $ ("#form").submit();
                    }
                });
                
                $('#incomingDatePicker').datetimepicker({
                    format: 'YYYY-MM-DD',
                    showTodayButton: true
                });
                
            });
        </script>
    </body>
</html>
