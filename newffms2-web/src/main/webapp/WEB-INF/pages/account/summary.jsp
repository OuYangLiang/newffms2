<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <head>
        <link href="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.css' />" rel="stylesheet">
    </head>

    <body>
		<section class="content-header">
			<h1>
				账户<small>列表页</small>
			</h1>
		</section>
	
		<section class="content">
            <div class="row">
                <div class="col-sm-12">
                    <div class="box box-primary">
                        <div class="box-body">
                            <div id="search-toolbar" class="btn-group">
							    <button type="button" class="btn btn-default" id="btn-add">
							        <i class="glyphicon glyphicon-plus"></i>
							    </button>
						
							    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#searchModal">
							        <i class="glyphicon glyphicon-search"></i>
							    </button>
						    </div>
							<table id="data-table" data-toggle="table"
							    data-url="<c:url value='/account/listOfSummary' />"
							    data-pagination="true"
							    data-side-pagination="server"
							    data-sort-name="<c:url value='${SESSION_KEY_SEARCH_PARAM_ACCOUNT.sortField}' />"
							    data-sort-order="<c:url value='${SESSION_KEY_SEARCH_PARAM_ACCOUNT.sortDir}' />"
							    data-page-size="<c:url value='${SESSION_KEY_SEARCH_PARAM_ACCOUNT.sizePerPage}' />"
							    data-page-number="<c:url value='${SESSION_KEY_SEARCH_PARAM_ACCOUNT.requestPage}' />"
							    data-show-toggle="true"
							    data-show-columns="true"
							    data-silent-sort="false"
							    data-row-style="rowStyle"
							    data-toolbar="#search-toolbar">
							    <thead>
							        <tr>
							            <th data-field="owner.userName" data-sortable="true" >所有人</th>
							            <th data-field="acntType" data-sortable="true" >账户类型</th>
							            <th data-field="acntDesc">说明</th>
							            <th data-field="balance" data-align="right" data-formatter="amtFormatter">可用额度</th>
							            <th data-formatter="oprFormatter"></th>
							        </tr>
							    </thead>
							</table>
                        </div>
                    </div>
                </div>
            </div>
		</section>
		
		<!-- Modal -->
        <div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="searchModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="searchModalLabel">搜索 / 查询</h4>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid">
                            <form id="search-form" class="form-horizontal">
                                <div class="form-group">
                                    <label for="cpnUserInput" class="col-md-3 control-label">账户所有人</label>
                                    <div class="col-md-4">
                                        <select name="ownerOid" class="form-control" id="cpnUserInput">
                                            <option value ="">全部</option>
                                            <c:forEach var="user" items="${ users }" varStatus="status">
                                                <option value ="${user.userOid}" <c:if test='${user.userOid == SESSION_KEY_SEARCH_PARAM_ACCOUNT.ownerOid }' >selected="selected"</c:if>>${user.userName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="cpnStatusInput" class="col-md-3 control-label">账户类型</label>
                                    <div class="col-md-4">
                                        <select name="acntType" class="form-control" id="cpnStatusInput">
                                            <option value ="">全部</option>
                                            <c:forEach var="item" items="${ acntTypes }" varStatus="status">
                                                <option value="${item.key }" <c:if test='${item.key == SESSION_KEY_SEARCH_PARAM_ACCOUNT.acntType }' >selected="selected"</c:if>>${item.value }</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-default" id="btn-query" data-dismiss="modal">查询</button>
                    </div>
                </div>
            </div>
        </div>

	    <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-table-1.10.1/locale/bootstrap-table-zh-CN.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/AdminLTE2/js/app.min.js' />" charset="utf-8"></script>
        
        <script>
            function rowStyle(row, index) {
                var classes = ['active', 'success', 'info', 'warning', 'danger'];
                
                if (index % 2 === 0 && index / 2 < classes.length) {
                    return {
                        classes: classes[index / 2]
                    };
                }
                return {};
            }
            
            function oprFormatter(val, row, idx) {
                var url = '<c:url value='/account/view' />' + '?acntOid=' + row.acntOid;
                var href = 'javascript:window.location.href="' + url + '"';
                return "<a href='" + href + "'>查看</a>";
            }
            
            function amtFormatter(value) {
                return "¥" + parseFloat(value).toFixed(2);
            }
            
            $( document ).ready(function() {
                $ ("#btn-query").click(function(){
                    $.ajax({
                        cache: false,
                        url: "<c:url value='/account/search' />",
                        type: "POST",
                        async: true,
                        data: $('#search-form').serialize(),
                        success: function() {
                            $ ("#data-table").bootstrapTable('refresh');
                        }
                    });
                });
                
                $ ("#btn-add").click(function(){
                    window.location.href = "<c:url value='/account/initAdd' />";
                });
            });
        </script>
    </body>
</html>
