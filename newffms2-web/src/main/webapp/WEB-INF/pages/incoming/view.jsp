<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
<head>
</head>

<body>
  <section class="content-header">
    <h1>
      收入<small>查看</small>
    </h1>
  </section>

  <section class="content">
    <div class="row">
      <div class="col-sm-12">
        <div style="padding-left: 20px; padding-bottom: 20px;">
          <button type="button" class="btn btn-default" id="btn-back">
            <i class="glyphicon glyphicon-arrow-left"></i>
          </button>

          <c:if test="${!incomingForm.confirmed }">
            <button type="button" class="btn btn-default" id="btn-edit">
              <i class="glyphicon glyphicon-edit"></i>
            </button>

            <button type="button" class="btn btn-default"
              id="btn-delete" data-toggle="modal"
              data-target="#deleteModal">
              <i class="glyphicon glyphicon-remove"></i>
            </button>

            <button type="button" class="btn btn-default"
              id="btn-confirm">
              <i class="glyphicon glyphicon-ok-circle"></i>
            </button>
          </c:if>

          <c:if test="${incomingForm.confirmed }">
            <button type="button" class="btn btn-default"
              id="btn-rollback">
              <i class="glyphicon glyphicon-remove-circle"></i>
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
                <label for="userNameInput" class="col-xs-4 col-sm-2 control-label">收入人</label>
                <div class="col-xs-7 col-sm-4">
                  <div class="form-control" style="BORDER-STYLE: none;" id="userNameInput">${incomingForm.owner.userName }</div>
                </div>
              </div>

              <div class="form-group">
                <label for="incomingTypeInput" class="col-xs-4 col-sm-2 control-label">收入类型</label>
                <div class="col-xs-7 col-sm-4">
                  <div class="form-control" style="BORDER-STYLE: none;" id=incomingTypeInput>${incomingForm.incomingTypeDesc }</div>
                </div>
              </div>

              <div class="form-group">
                <label for="incomingDescInput" class="col-xs-4 col-sm-2 control-label">说明</label>
                <div class="col-xs-7 col-sm-4">
                  <div class="form-control" style="BORDER-STYLE: none;" id="incomingDescInput">${incomingForm.incomingDesc }</div>
                </div>
              </div>

              <div class="form-group">
                <label for="incomingDateInput" class="col-xs-4 col-sm-2 control-label">日期</label>
                <div class="col-xs-7 col-sm-4">
                  <div class="form-control" style="BORDER-STYLE: none;" id="incomingDateInput">
                    <fmt:formatDate value='${incomingForm.incomingDate }' pattern="yyyy-MM-dd" />
                  </div>
                </div>
              </div>

              <div class="form-group">
                <label for="amountInput" class="col-xs-4 col-sm-2 control-label">金额</label>
                <div class="col-xs-7 col-sm-4">
                  <div class="form-control" style="BORDER-STYLE: none;" id="amountInput">${incomingForm.amount }</div>
                </div>
              </div>

              <div class="form-group">
                <label for="targetAccountInput" class="col-xs-4 col-sm-2 control-label">目标账户</label>
                <div class="col-xs-7 col-sm-4">
                  <div class="form-control" style="BORDER-STYLE: none;" id="targetAccountInput">${incomingForm.targetAccount.acntHumanDesc }</div>
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
          <div class="container lead">确定要删除吗?</div>
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
          window.location.href = "<c:url value='/incoming/summary?keepSp=Y' />";
      });
      
      <c:if test="${!incomingForm.confirmed }" >
      $ ("#btn-edit").click(function(){
          window.location.href = "<c:url value='/incoming/initEdit' />?incomingOid=<c:out value='${incomingForm.incomingOid}' />";
      });

      if ($ ("#btn-delete").length > 0) {
          $ ("#btn-delete-confirm").click(function(){
              window.location.href = "<c:url value='/incoming/delete' />?incomingOid=<c:out value='${incomingForm.incomingOid}' />";
          });
      }

      $ ("#btn-confirm").click(function(){
          window.location.href = "<c:url value='/incoming/confirm' />?incomingOid=<c:out value='${incomingForm.incomingOid}' />";
      });
      </c:if>

      <c:if test="${incomingForm.confirmed }" >
      $ ("#btn-rollback").click(function(){
          window.location.href = "<c:url value='/incoming/rollback' />?incomingOid=<c:out value='${incomingForm.incomingOid}' />";
      });
      </c:if>
  });
  </script>
</body>
</html>
