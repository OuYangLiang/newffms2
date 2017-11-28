<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
<head>
<link
  href="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.css' />" rel="stylesheet">
<link
  href="<c:url value='/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css' />" rel="stylesheet">
</head>

<body>
  <section class="content-header">
    <h1>
      消费<small>列表页</small>
    </h1>
  </section>

  <section class="content">
    <div class="collapse" id="searchCollapse">
      <div class="row">
        <div class="col-sm-12">
          <div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">搜索 / 查询</h3>
            </div>

            <div class="box-body">
              <form id="search-form" class="form-horizontal">
                <div class="form-group">
                  <label for="cpnTimeFromInput" class="col-sm-2 control-label">起始日期</label>
                  <div class="col-sm-3">
                    <div class='input-group date' id='cpnTimeFromInputPicker'>
                      <input id="cpnTimeFromInput" class="form-control" value="<fmt:formatDate value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.cpnTimeFrom }' pattern="yyyy-MM-dd" />" type="text" name="cpnTimeFrom" />
                      <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                      </span>
                    </div>
                  </div>

                  <label for="cpnTimeToInput" class="col-sm-2 control-label">截止日期</label>
                  <div class="col-sm-3">
                    <div class='input-group date' id='cpnTimeToInputPicker'>
                      <input id="cpnTimeToInput" class="form-control" value="<fmt:formatDate value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.cpnTimeTo }' pattern="yyyy-MM-dd" />" type="text" name="cpnTimeTo" />
                      <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                      </span>
                    </div>
                  </div>
                </div>

                <div class="form-group">
                  <label for="ownerInput" class="col-sm-2 control-label">消费人</label>
                  <div class="col-sm-3">
                    <select name="ownerOid" class="form-control" id="ownerInput">
                      <option value="">全部</option>
                      <c:forEach var="user" items="${ users }" varStatus="status">
                        <option value="${user.userOid}"
                          <c:if test='${user.userOid == SESSION_KEY_SEARCH_PARAM_INCOMING.ownerOid }' >selected="selected"</c:if>>${user.userName}</option>
                      </c:forEach>
                    </select>
                  </div>

                  <label for="confirmedInput" class="col-sm-2 control-label">状态</label>
                  <div class="col-sm-3">
                    <select name="confirmed" class="form-control" id="confirmedInput">
                      <option value="">全部</option>
                      <option value="true" <c:if test='${null != SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.confirmed && SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.confirmed }' >selected="selected"</c:if>>确认</option>
                      <option value="false" <c:if test='${null != SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.confirmed && !SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.confirmed }' >selected="selected"</c:if>>初始</option>
                    </select>
                  </div>
                </div>

                <div class="form-group">
                  <label for="categoryDescInput" class="col-xs-12 col-sm-2 control-label">消费类别</label>
                  <div class="col-sm-3">
                    <div class='input-group'>
                      <input id="categoryDescInput" class="form-control" value="<c:out value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.categoryDesc }' />" readonly="true" type="text" name="categoryDesc" data-toggle="modal" data-target="#categorySelectModal" />
                      <input value="<c:out value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.categoryOid }' />" type="hidden" name="categoryOid" id="categoryOid" />
                      <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar" id="btn-clear-category"></span>
                      </span>
                    </div>
                  </div>

                  <label for="itemDescInput" class="col-sm-2 control-label">描述</label>
                  <div class="col-sm-3">
                    <input id="itemDescInput" class="form-control" value="<c:out value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.itemDesc }' />" type="text" name="itemDesc" />
                  </div>
                </div>

                <div class="row">
                  <div class="col-sm-offset-2 col-sm-3">
                    <button type="button" class="btn btn-default" id="btn-query">查询</button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-12">
        <div class="box box-primary">
          <div class="box-body">
            <div id="search-toolbar" class="btn-group">
              <button type="button" class="btn btn-default" id="btn-add">
                <i class="glyphicon glyphicon-plus"></i>
              </button>

              <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#searchCollapse">
                <i class="glyphicon glyphicon-search"></i>
              </button>
            </div>
            <table id="data-table" data-toggle="table"
              data-url="<c:url value='/consumption/listOfSummary' />"
              data-pagination="true" data-side-pagination="server"
              data-sort-name="<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.paginationParameter.sortField}' />"
              data-sort-order="<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.paginationParameter.sortDir}' />"
              data-page-size="<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.paginationParameter.sizePerPage}' />"
              data-page-number="<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.paginationParameter.requestPage}' />"
              data-show-toggle="true" data-show-columns="true"
              data-silent-sort="false" data-row-style="rowStyle"
              data-toolbar="#search-toolbar">
              <thead>
                <tr>
                  <th data-field="cpnTime" data-sortable="true" data-align="center" data-formatter="dateFormatter">时间</th>
                  <th data-field="itemDesc" data-sortable="true">描述</th>
                  <th data-field="categoryDesc">类别</th>
                  <th data-field="userName">消费人</th>
                  <th data-field="cpnType">消费方式</th>
                  <th data-field="amount" data-align="right" data-formatter="amtFormatter">行金额</th>
                  <th data-field="total" data-align="right" data-formatter="amtFormatter">总金额</th>
                  <th data-field="createBy">登记人</th>
                  <th data-field="confirmed" data-align="center" data-formatter="statusFormatter">状态</th>
                  <th data-formatter="oprFormatter"></th>
                </tr>
              </thead>
            </table>
          </div>
        </div>
      </div>
    </div>
  </section>

  <div class="modal fade" id="categorySelectModal" tabindex="-1" role="dialog" aria-labelledby="#categorySelectModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id="#categorySelectModalLabel">类别选择</h4>
        </div>
        <div class="modal-body">
          <div class="container-fluid">
            <table id="category-table" data-toggle="table"
              data-url="<c:url value='/category/ajaxGetAllCategories' />"
              data-detail-view="true"
              data-detail-formatter="detailFormatter"
              data-show-toggle="false" data-show-columns="false"
              data-silent-sort="false" data-row-style="rowStyle">
              <thead>
                <tr>
                  <th data-field="categoryDesc" data-formatter="categoryFormatter">类别</th>
                </tr>
              </thead>
            </table>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        </div>
      </div>
    </div>
  </div>

  <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/js/jquery.validationEngine.js' />" charset="utf-8"></script>
  <script src="<c:url value='/js/jquery.validationEngine-zh_CN.js' />" charset="utf-8"></script>
  <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/js/moment.js' />" charset="utf-8"></script>
  <script src="<c:url value='/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/bootstrap-table-1.10.1/locale/bootstrap-table-zh-CN.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/AdminLTE2/js/app.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/js/common.js' />" charset="utf-8"></script>

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
      var url = '<c:url value='/consumption/view' />' + '?cpnOid=' + row.cpnOid;
      var href = 'javascript:window.location.href="' + url + '"';
      return "<a href='" + href + "'>查看</a>";
  }
  
  function amtFormatter(value) {
      return "¥" + parseFloat(value).toFixed(2);
  }
  
  function dateFormatter(value) {
      return value.substr(0, 10);
  }
  
  function statusFormatter(value) {
      if (value) {
          return "<span class=\"glyphicon glyphicon-ok-circle\"></span>";
      } else {
          return "<span class=\"glyphicon glyphicon-remove-circle\"></span>";
      }
  }
  
  function categoryFormatter(value, row) {
  	if (row.isLeaf) {
  		return "<a href=\"javascript:chooseCategory("+ row.categoryOid + ", '" + row.categoryDesc + "')\">" + row.categoryDesc + "</a>";
  	}
  	
  	return value;
  }
  
  function chooseCategory(categoryOid, categoryDesc) {
      $( "#categoryDescInput" ).val(categoryDesc);
      $( "#categoryOid" ).val(categoryOid);
      $("#categorySelectModal").modal("hide");
  };
  
  function detailFormatter(index, row, element) {
      var rlt = '<div class="row">';

      $.each(row.subCategories, function(idx, element) {
      	rlt = rlt + "<div class=\"col-xs-4 col-sm-3\"><a href=\"javascript:chooseCategory("+ element.categoryOid + ", '" + element.categoryDesc + "')\">" + element.categoryDesc + "</a></div>";
      });
  	
      rlt = rlt + '</div>';
      return rlt;
  }
  
  $( document ).ready(function() {
  	$('#cpnTimeFromInputPicker').datetimepicker({
          format: 'YYYY-MM-DD',
          showTodayButton: true
      });
  	
  	$('#cpnTimeToInputPicker').datetimepicker({
          format: 'YYYY-MM-DD',
          showTodayButton: true
      });
  	
  	$ ("#btn-clear-category").click(function(){
          $( "#categoryDescInput" ).val('');
          $( "#categoryOid" ).val('');
      });
  	
      $ ("#btn-add").click(function(){
      	window.location.href = "<c:url value='/consumption/initAdd' />";
      });
      
      $ ("#btn-query").click(function(){
          $.ajax({
              cache: false,
              url: "<c:url value='/consumption/search' />",
              type: "POST",
              async: true,
              data: $('#search-form').serialize(),
              success: function() {
                  $ ("#data-table").bootstrapTable('refresh');
              }
          });
      });
  });
  </script>
</body>
</html>