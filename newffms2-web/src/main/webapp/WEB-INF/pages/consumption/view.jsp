<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
<head>
<link
  href="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.css' />" rel="stylesheet">
</head>

<body>
  <section class="content-header">
    <h1>
      消费<small>查看</small>
    </h1>
  </section>

  <section class="content">
    <div class="row">
      <div class="col-sm-12">
        <div style="padding-left: 20px; padding-bottom: 20px;">
          <button type="button" class="btn btn-default" id="btn-back">
            <i class="glyphicon glyphicon-arrow-left"></i>
          </button>

          <c:if test="${!cpnForm.confirmed }">
            <button type="button" class="btn btn-default" id="btn-edit">
              <i class="glyphicon glyphicon-edit"></i>
            </button>

            <button type="button" class="btn btn-default" id="btn-delete" data-toggle="modal" data-target="#deleteModal">
              <i class="glyphicon glyphicon-remove"></i>
            </button>

            <button type="button" class="btn btn-default" id="btn-confirm">
              <i class="glyphicon glyphicon-ok-circle"></i>
            </button>
          </c:if>

          <c:if test="${cpnForm.confirmed }">
            <button type="button" class="btn btn-default" id="btn-rollback">
              <i class="glyphicon glyphicon-remove-circle"></i>
            </button>
          </c:if>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-12">
        <div class="box box-primary">
          <div class="box-header with-border">
            <h3 class="box-title">消费概况</h3>
          </div>

          <div class="box-body">
            <div class="form-horizontal">
              <div class="form-group">
                <label for="cpnTypeInput" class="col-xs-4 col-sm-2 control-label">消费方式</label>
                <div class="col-xs-7 col-sm-4">
                  <div class="form-control" style="BORDER-STYLE: none;" id="cpnTypeInput">${cpnForm.cpnTypeDesc }</div>
                </div>
              </div>

              <div class="form-group">
                <label for="cpnTimeInput" class="col-xs-4 col-sm-2 control-label">消费时间</label>
                <div class="col-xs-7 col-sm-4">
                  <div class="form-control" style="BORDER-STYLE: none;"
                    id="cpnTimeInput">
                    <fmt:formatDate value="${cpnForm.cpnTime}" pattern="yyyy-MM-dd HH:mm" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-12">
        <div class="box box-primary">
          <div class="box-header with-border">
            <h3 class="box-title">消费明细</h3>
          </div>

          <div class="box-body" id="items">
            <div class="container-fluid">
              <table id="data-table" data-toggle="table"
                data-show-toggle="true" data-show-columns="true"
                data-silent-sort="false" data-row-style="rowStyle">
                <thead>
                  <tr>
                    <th>消费说明</th>
                    <th>消费类别</th>
                    <th data-align="right" data-formatter="amtFormatter">金额</th>
                    <th>消费人</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="item" items="${ cpnForm.items }" varStatus="status">
                    <tr>
                      <td>${item.itemDesc}</td>
                      <td>${item.categoryDesc}</td>
                      <td>${item.amount}</td>
                      <td>${item.ownerName}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <div class="box-footer">
            <div class="pull-right">
              消费总金额: <span id="totalAmountDisplay">${cpnForm.totalItemAmount }</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-12">
        <div class="box box-primary">
          <div class="box-header with-border">
            <h3 class="box-title">支付明细</h3>
          </div>

          <div class="box-body" id="accounts">
            <div class="container-fluid">
              <table id="data-table" data-toggle="table"
                data-show-toggle="true" data-show-columns="true"
                data-silent-sort="false" data-row-style="rowStyle">
                <thead>
                  <tr>
                    <th>支付账户</th>
                    <th data-align="right" data-formatter="amtFormatter">金额</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="item" items="${ cpnForm.payments }" varStatus="status">
                    <tr>
                      <td>${item.acntHumanDesc}</td>
                      <td>${item.payment}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <div class="box-footer">
            <div class="pull-right">
              支付总金额: <span id="totalPaymentDisplay">${cpnForm.totalPayment }</span>
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
          <div class="container lead">确定要删除吗?</div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          <button type="button" class="btn btn-danger" id="btn-delete-confirm" data-dismiss="modal">删除</button>
        </div>
      </div>
    </div>
  </div>
  
  <div class="modal fade" id="alertModal" tabindex="-1" role="dialog" aria-labelledby="alertModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id="alertModalLabel">警告</h4>
        </div>
        <div class="modal-body">
          <div class="container lead" id="alertMsg"></div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
  
  function amtFormatter(value) {
      return "¥" + parseFloat(value).toFixed(2);
  }
  
  $( document ).ready(function() {
      $ ("#btn-back").click(function(){
          window.location.href = "<c:url value='/consumption/summary?keepSp=Y' />";
      });
      
      <c:if test="${!cpnForm.confirmed }" >
      $ ("#btn-edit").click(function(){
          window.location.href = "<c:url value='/consumption/initEdit' />?cpnOid=<c:out value='${cpnForm.cpnOid}' />";
      });
  
      $ ("#btn-confirm").click(function(){
          $.ajax({
              cache: false,
              url: "<c:url value='/consumption/confirm' />?cpnOid=<c:out value='${cpnForm.cpnOid}' />",
              type: "POST",
              async: true,
              success: function(data) {
                  if (data.success) {
                      window.location.href = "<c:url value='/consumption/summary?keepSp=Y'/>";
                  } else {
                      $('#alertMsg').text(data.errCode + ", " + data.errMsg);
                      $('#alertModal').modal('show')
                  }
              }
          });
      });
  
      if ($ ("#btn-delete").length > 0) {
          $ ("#btn-delete-confirm").click(function(){
              window.location.href = "<c:url value='/consumption/delete' />?cpnOid=<c:out value='${cpnForm.cpnOid}' />";
          });
      }
      </c:if>

      <c:if test="${cpnForm.confirmed }" >
      $ ("#btn-rollback").click(function(){
          $.ajax({
              cache: false,
              url: "<c:url value='/consumption/rollback' />?cpnOid=<c:out value='${cpnForm.cpnOid}' />",
              type: "POST",
              async: true,
              success: function(data) {
                  if (data.success) {
                      window.location.href = "<c:url value='/consumption/summary?keepSp=Y'/>";
                  } else {
                      $('#alertMsg').text(data.errCode + ", " + data.errMsg);
                      $('#alertModal').modal('show')
                  }
              }
          });
      });
      </c:if>
  });
  </script>
</body>
</html>
