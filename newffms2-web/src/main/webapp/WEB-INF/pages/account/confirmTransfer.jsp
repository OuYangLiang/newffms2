<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
<head>

</head>

<body>
  <section class="content-header">
    <h1>
      账户<small>转账确认</small>
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
              <div class="row" id="errorArea" style="display: none">
                <div class="col-md-1"></div>
                <div class="col-md-10">
                  <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only">Error:</span>
                    ${errCode },&nbsp;${errMsg }
                  </div>
                </div>
              </div>

              <div class="form-group">
                <label for=srcAccountInput class="col-xs-4 col-sm-2 control-label">账户所有人</label>
                <div class="col-xs-7 col-sm-4">
                  <div class="form-control" style="BORDER-STYLE: none;" id="srcAccountInput">${acntForm.acntHumanDesc }</div>
                </div>
              </div>

              <div class="form-group">
                <label for=targetAccountInput class="col-xs-4 col-sm-2 control-label">目标账户</label>
                <div class="col-xs-7 col-sm-4">
                  <div class="form-control" style="BORDER-STYLE: none;" id="targetAccountInput">${acntForm.target.acntHumanDesc }</div>
                </div>
              </div>

              <div class="form-group">
                <label for=paymentInput class="col-xs-4 col-sm-2 control-label">转账金额</label>
                <div class="col-xs-7 col-sm-4">
                  <div class="form-control" style="BORDER-STYLE: none;" id="paymentInput">${acntForm.payment }</div>
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
      if ('<c:out value="${validation}" />' === 'false') {
          $("#errorArea").css("display", "");
      }

      $ ("#btn-cancel").click(function(){
          window.location.href = "<c:url value='/account/initTransfer' />";
      });

      $ ("#btn-save").click(function(){
          window.location.href = "<c:url value='/account/saveTransfer' />";
      });
    });
  </script>
</body>
</html>
