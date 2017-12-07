<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
<head>
<link href="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.css' />" rel="stylesheet">
<link href="<c:url value='/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css' />" rel="stylesheet">
</head>

<body>
  <section class="content-header">
    <h1>
      类别<small>列表页</small>
    </h1>
  </section>

  <section class="content">
    <div class="row">
      <div class="col-sm-12">
        <div class="box box-primary">
          <div class="box-body">
            <div id="search-toolbar" class="btn-group">
              <c:if test="${parentOid != null }">
                <button type="button" class="btn btn-default" id="btn-back">
                  <i class="glyphicon glyphicon-arrow-left"></i>
                </button>
              </c:if>

              <button type="button" class="btn btn-default" id="btn-add">
                <i class="glyphicon glyphicon-plus"></i>
              </button>
            </div>
            <table id="category-table" data-toggle="table" data-url="<c:url value='/category/ajaxGetCategoriesByParent' /><c:if test="${parentOid != null }">?parentOid=${parentOid }</c:if>" data-row-style="rowStyle" data-toolbar="#search-toolbar">
              <thead>
                <tr>
                  <th data-field="categoryDesc" data-formatter="descFormatter">类别</th>
                  <th data-field="monthlyBudget" data-align="right" data-formatter="amtFormatter">月度预算</th>
                  <th data-field="categoryLevel" data-align="center" data-formatter="levelFormatter">层级</th>
                  <th data-field="leaf" data-align="center" data-formatter="statusFormatter">叶子节点</th>
                  <th data-formatter="oprFormatter"></th>
                </tr>
              </thead>
            </table>
          </div>
        </div>
      </div>
    </div>
  </section>

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

  function amtFormatter(value) {
      return "¥" + parseFloat(value).toFixed(2);
  }

  function levelFormatter(val) {
  	return (val+1);
  }
	        
  function statusFormatter(value) {
      if (value) {
          return "<span class=\"glyphicon glyphicon-ok-circle\"></span>";
      } else {
          return "<span class=\"glyphicon glyphicon-remove-circle\"></span>";
      }
  }
	        
  function descFormatter(val, row, idx) {
      if (!row.leaf) {
          var url = '<c:url value='/category/summary' />' + '?parentOid=' + row.categoryOid;
          var href = 'javascript:window.location.href="' + url + '"';
          return "<a href='" + href + "'>" + val + "</a>";
      }
      return val;
  }

  function oprFormatter(val, row, idx) {
      var url = '<c:url value='/category/view' />' + '?categoryOid=' + row.categoryOid;
      var href = 'javascript:window.location.href="' + url + '"';
      return "<a href='" + href + "'>查看</a>";
  }
        
  $( document ).ready(function() {
      $ ("#btn-add").click(function(){
          window.location.href = "<c:url value='/category/initAdd' /><c:if test="${parentOid != null }">?parentOid=${parentOid }</c:if>";
      });
  	
      if ($ ("#btn-back").length > 0) {
          $ ("#btn-back").click(function(){
              window.location.href = "<c:url value='/category/summary' /><c:if test="${parentParentOid != null }">?parentOid=${parentParentOid }</c:if>";
          });
      }
  });
  </script>
</body>
</html>