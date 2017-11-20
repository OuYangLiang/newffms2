<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
<head>
<link
  href="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.css' />" rel="stylesheet">
</head>

<body>
  <section class="content-header">
    <h1>
      账户<small>查看</small>
    </h1>
  </section>

  <section class="content">
    <div class="row">
      <div class="col-sm-12">
        <div style="padding-left: 20px; padding-bottom: 20px;">
          <button type="button" class="btn btn-default" id="btn-back">
            <i class="glyphicon glyphicon-arrow-left"></i>
          </button>

          <c:if test="${acntForm.balance > 0 }">
            <button type="button" class="btn btn-default" id="btn-transfer">
              <i class="glyphicon glyphicon-transfer"></i>
            </button>
          </c:if>

          <button type="button" class="btn btn-default" id="btn-viewDetail">
            <i class="glyphicon glyphicon-list"></i>
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
                  <div class="form-control" style="BORDER-STYLE: none;" id=acntTypeInput>${acntForm.acntTypeDesc }</div>
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

              <c:if test="${acntForm.acntType == \"Creditcard\" }">
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

    <div class="row" id="detailArea" style="display: none;">
      <div class="col-sm-12">
        <div class="box box-primary">
          <div class="box-body">
            <table id="data-table" data-toggle="table"
              data-pagination="true" data-side-pagination="server"
              data-sort-name="ADT_TIME" data-sort-order="desc"
              data-page-size="10" data-page-number="1"
              data-show-toggle="true" data-show-columns="true"
              data-silent-sort="false" data-row-style="rowStyle">
              <thead>
                <tr>
                  <th data-field="adtDesc">描述</th>
                  <th data-field="adtType">类型</th>
                  <th data-field="chgAmt" data-align="right" data-formatter="amtFormatter">变化量</th>
                  <th data-field="balanceAfter" data-align="right" data-formatter="amtFormatter">变化后余额</th>
                  <th data-field="adtTime">发生时间</th>
                  <th data-field="createTime">操作时间</th>
                </tr>
              </thead>
            </table>
          </div>
        </div>
      </div>
    </div>
  </section>

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
          window.location.href = "<c:url value='/account/summary?keepSp=Y' />";
      });
  	
      $ ("#btn-edit").click(function(){
          window.location.href = "<c:url value='/account/initEdit' />?acntOid=<c:out value='${acntForm.acntOid}' />";
      });
  	
      if ($ ("#btn-transfer").length > 0) {
          $ ("#btn-transfer").click(function(){
              window.location.href = "<c:url value='/account/initTransfer' />?acntOid=<c:out value='${acntForm.acntOid}' />";
          });
      }

      var gridLoaded = false;
      $ ("#btn-viewDetail").click(function(){
          if (gridLoaded) {
              return;
          }
  		
          gridLoaded = true;
          $("#detailArea").attr("style", "display:''");
          $ ("#data-table").bootstrapTable('refresh', {'url': "<c:url value='/account/listOfItemSummary?acntOid=${acntForm.acntOid}' />"});
      });
  });
  </script>
</body>
</html>
