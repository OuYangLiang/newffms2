<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
<head>
<link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
<link href="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.css' />" rel="stylesheet">
<link href="<c:url value='/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css' />" rel="stylesheet">
</head>

<body>
  <section class="content-header">
    <h1>
      消费<small>新建</small>
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

    <c:url value='/consumption/confirmAdd' var='url' />
    <spring:form id="form" method="post" action="${url}" class="form-horizontal" modelAttribute="cpnForm" autocomplete="off">
      <div class="row">
        <div class="col-sm-12">
          <div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">消费概况</h3>
            </div>

            <div class="box-body">
              <div class="row" id="errorArea" style="display: none">
                <div class="col-md-1"></div>
                <div class="col-md-10">
                  <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                      <span class="sr-only">Error:</span>
                    <spring:errors path="*" />
                  </div>
                </div>
              </div>

              <div class="form-group">
                <label for="cpnTypeInput" class="col-xs-4 col-sm-2 control-label">消费方式</label>
                <div class="col-xs-7 col-sm-4">
                  <spring:select path="cpnType" class="form-control" id="cpnTypeInput">
                    <spring:option value="" label="请选择" />
                    <spring:options items="${cpnTypes}" />
                  </spring:select>
                </div>
              </div>

              <div class="form-group">
                <label for="cpnTimeInput" class="col-xs-4 col-sm-2 control-label">消费时间</label>
                <div class="col-xs-7 col-sm-4">
                  <div class='input-group date' id='cpnTimeInputPicker'>
                    <spring:input path="cpnTimeInput" id="cpnTimeInput" class="form-control" data-validation-engine="validate[required]" />
                    <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                    </span>
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
              <div class="box-tools pull-right">
                <button type="button" class="btn btn-default" id="btn-add-item">
                  <i class="glyphicon glyphicon-plus"></i>
                </button>
              </div>
            </div>

            <div class="box-body" id="items">
              <c:forEach var="item" items="${ cpnForm.items }" varStatus="status">
                <div class="col-sm-6" id="item${status.index}">
                  <div class="box box-default">
                    <div class="box-header with-border">
                      <h3 class="box-title" id="item-title${status.index}">消费项${status.index +1}</h3>
                      <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" id="remove-item${status.index}" onClick="javascript:removeItem(${status.index });">
                          <i class="fa fa-times"></i>
                        </button>
                      </div>
                    </div>
                    <div class="box-body">
                      <div class="form-horizontal">
                        <div class="form-group">
                          <div class="col-sm-6">
                            <spring:input class="form-control" placeholder="说明" data-validation-engine="validate[required]" id="itemDesc${status.index }" path="items[${status.index }].itemDesc" maxlength="30" />
                          </div>
                          <div class="col-sm-6">
                            <spring:input class="form-control" placeholder="类别" data-validation-engine="validate[required]" id="categoryDesc${status.index }" path="items[${status.index }].categoryDesc" onClick="javascript:selectCategory(${status.index });" />
                            <input type="hidden" id="categoryOid${status.index }" name="items[${status.index }].categoryOid" value="${item.categoryOid }" />
                          </div>

                          <div class="col-sm-6">
                            <spring:input class="form-control" placeholder="金额" data-validation-engine="validate[required]" id="itemAmount${status.index }" path="items[${status.index }].amount" onBlur="javascript:checkItemAmount(this);" maxlength="11" />
                          </div>

                          <div class="col-sm-6">
                            <spring:select class="form-control" placeholder="消费人" data-validation-engine="validate[required]" id="itemOwner${status.index }" path='items[${status.index }].ownerOid'>
                              <spring:option value="" label="请选择" />
                              <spring:options items="${users}" itemValue="userOid" itemLabel="userName" />
                            </spring:select>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </c:forEach>
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
              <div class="box-tools pull-right">
                <button type="button" class="btn btn-default" id="btn-add-account">
                  <i class="glyphicon glyphicon-plus"></i>
                </button>
              </div>
            </div>

            <div class="box-body" id="accounts">
              <c:forEach var="item" items="${ cpnForm.payments }" varStatus="status">
                <div class="col-sm-6" id="account${status.index}">
                  <div class="box box-default">
                    <div class="box-header with-border">
                      <h3 class="box-title" id="account-title${status.index}" >支付账户${status.index +1}</h3>
                      <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" id="remove-account${status.index}" onClick="javascript:removeAccount(${status.index});">
                          <i class="fa fa-times"></i>
                        </button>
                      </div>
                    </div>
                    <div class="box-body">
                      <div class="form-horizontal">
                        <div class="col-sm-9">
                          <input class="form-control" placeholder="账户" data-validation-engine="validate[required]" id="acntHumanDesc${status.index }" value="${item.acntHumanDesc }" onClick="javascript:selectAccount(${status.index });" />
                          <input type="hidden" id="accountOid${status.index }" name="payments[${status.index }].acntOid" value="${item.acntOid }" />
                        </div>

                        <div class="col-sm-3">
                          <spring:input class="form-control" placeholder="支付金额" data-validation-engine="validate[required]" id="payment${status.index }" path="payments[${status.index }].payment" onBlur="javascript:checkAccountAmount(this);" maxlength="11" />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </c:forEach>
            </div>
            <div class="box-footer">
              <div class="pull-right">
                支付总金额: <span id="totalPaymentDisplay">${cpnForm.totalPayment }</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </spring:form>
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
              data-pagination="true" data-side-pagination="client"
              data-page-size="5" data-page-number="1" data-search="true"
              data-search-on-enter-key="true" data-show-toggle="true"
              data-show-columns="true" data-silent-sort="false"
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
  function detailFormatter(index, row, element) {
      var rlt = '<div class="row">';
      $.each(row.subCategories, function(idx, element) {
          rlt = rlt + "<div class=\"col-xs-4 col-sm-3\"><a href=\"javascript:chooseCategory("+ element.categoryOid + ", '" + element.categoryDesc + "')\">" + element.categoryDesc + "</a></div>";
      });
      rlt = rlt + '</div>';
      return rlt;
  }
        
  function chooseCategory(categoryOid, categoryDesc) {
      $( "#categoryDesc" + curItem).val(categoryDesc);
      $( "#categoryOid" + curItem).val(categoryOid);
      $("#categorySelectModal").modal("hide");
  };
	        
  function categoryFormatter(value, row) {
      if (row.isLeaf) {
          return "<a href=\"javascript:chooseCategory("+ row.categoryOid + ", '" + row.categoryDesc + "')\">" + row.categoryDesc + "</a>";
      }
      return value;
  }
        
  function selectCategory(seq) {
      curItem = seq;
      $("#categorySelectModal").modal("show");
  };
	        
  function calculateAmount() {
      var totalAmount = 0;
      for (var i = 0; i < itemCnt; i ++) {
          if ($("#itemAmount" + i).val() != "") {
              totalAmount += parseFloat($("#itemAmount" + i).val());
          }
      }
      $("#totalAmountDisplay").html(parseFloat(totalAmount).toFixed(2));
  };
            
  function checkItemAmount(obj) {
      checkAmount(obj);
      calculateAmount();
  };
            
  function calculateAccountAmount() {
      var totalAmount = 0;
      for (var i = 0; i < accountCnt; i ++) {
          if ($("#payment" + i).val() != "") {
              totalAmount += parseFloat($("#payment" + i).val());
          }
      }
      $("#totalPaymentDisplay").html(parseFloat(totalAmount).toFixed(2));
  };
            
  function checkAccountAmount(obj) {
      checkAmount(obj);
      calculateAccountAmount();
  };
            
  function removeItem(seq){
      if (itemCnt == 1) {
          $ ( "#last-item-dialog" ).dialog( "open" );
          return;
      }
      
      $ ("#item" + seq).remove();
      
      if (seq < (itemCnt - 1)) {
          seq ++;
          while (seq <= (itemCnt - 1)) {
              $ ( "#item-title" + seq).html("消费项" + (seq));
              $ ( "#item-title" + seq).attr("id", "item-title" + (seq-1));
              
              $ ( "#remove-item" + seq).attr("onClick", "javascript:removeItem(" + (seq-1) + ");");
              $ ( "#remove-item" + seq).attr("id", "remove-item" + (seq-1));
              
              $ ( "#itemDesc" + seq).attr("name", "items[" + (seq-1) + "].itemDesc");
              $ ( "#itemDesc" + seq).attr("id", "itemDesc" + (seq-1));
              
              $ ( "#categoryDesc" + seq).attr("name", "items[" + (seq-1) + "].categoryDesc");
              $ ( "#categoryDesc" + seq).attr("onClick", "javascript:selectCategory(" + (seq-1) + ");");
              $ ( "#categoryDesc" + seq).attr("id", "categoryDesc" + (seq-1));
              $ ( "#categoryOid" + seq).attr("name", "items[" + (seq-1) + "].categoryOid");
              $ ( "#categoryOid" + seq).attr("id", "categoryOid" + (seq-1));
              
              $ ( "#itemAmount" + seq).attr("name", "items[" + (seq-1) + "].amount");
              $ ( "#itemAmount" + seq).attr("id", "itemAmount" + (seq-1));
              
              $ ( "#itemOwner" + seq).attr("name", "items[" + (seq-1) + "].ownerOid");
              $ ( "#itemOwner" + seq).attr("id", "itemOwner" + (seq-1));
              
              $ ( "#item" + seq).attr("id", "item" + (seq-1));
              seq++;
          }
      }
      itemCnt --;
  };
             
  function removeAccount(seq){
     if (accountCnt == 1) {
         $ ( "#last-account-dialog" ).dialog( "open" );
         return;
     }
     
     if (arrContain(selectedAccounts, $("#accountOid" + seq).val())) {
         arrRemove(selectedAccounts, $("#accountOid" + seq).val());
     }

     $ ("#account" + seq).remove();

     if (seq < (accountCnt -1) ) {
         seq ++;
         while (seq <= (accountCnt -1)) {
             $ ( "#account" + seq).attr("id", "account" + (seq-1));
             
             $ ( "#account-title" + seq).html("支付账户" + seq);
             $ ( "#account-title" + seq).attr("id", "account-title" + (seq-1));
             
             $ ( "#remove-account" + seq).attr("onClick", "javascript:removeAccount(" + (seq-1) + ");");
             $ ( "#remove-account" + seq).attr("id", "remove-account" + (seq-1));
             
             $ ( "#acntHumanDesc" + seq).attr("onClick", "javascript:selectAccount(" + (seq-1) + ");");
             $ ( "#acntHumanDesc" + seq).attr("id", "acntHumanDesc" + (seq-1));
             
             $ ( "#accountOid" + seq).attr("name", "payments[" + (seq-1) + "].acntOid");
             $ ( "#accountOid" + seq).attr("id", "accountOid" + (seq-1));
             
             $ ( "#payment" + seq).attr("name", "payments[" + (seq-1) + "].payment");
             $ ( "#payment" + seq).attr("id", "payment" + (seq-1));
             seq++;
         }
     }
     accountCnt --;
  };
             
  function accountFormatter(val, row, idx) {
     return "<a href=\"javascript:chooseAccount(" + row.acntOid + ", '" + val + "');\" >" + val + "</a>";
  }
             
  function chooseAccount(acntOid, acntHumanDesc) {
      var curAcntOid = $( "#accountOid" + curAccount).val();
     
      if (curAcntOid == "") {
          if (arrContain(selectedAccounts, acntOid)) {
             alert("该账户已经使用过了，亲。");
             return;
          }
          
          $( "#acntHumanDesc" + curAccount).val(acntHumanDesc);
          $( "#accountOid" + curAccount).val(acntOid);
          $("#AccountSelectModal").modal("hide");
          selectedAccounts.push(acntOid);
      }
      else {
          if ( curAcntOid == acntOid ) {
              $("#AccountSelectModal").modal("hide");
              return;
          }
          
          if (arrContain(selectedAccounts, acntOid)) {
              alert("该账户已经使用过了，亲。");
              return;
          }
          
          $( "#acntHumanDesc" + curAccount).val(acntHumanDesc);
          $( "#accountOid" + curAccount).val(acntOid);
          $("#AccountSelectModal").modal("hide");
          selectedAccounts.push(acntOid);
          arrRemove(selectedAccounts, curAcntOid);
      }
  };
             
  selectAccount = function(seq)
  {
     curAccount = seq;
     $("#AccountSelectModal").modal("show");
  };
        
  $( document ).ready(function() {
      //验证失败后，显示后台错误信息。
      if ('<c:out value="${validation}" />' === 'false') {
      	$("#errorArea").css("display", "");
      }
      	
      $ ("#btn-cancel").click(function(){
          window.location.href = "<c:url value='/consumption/summary?keepSp=Y' />";
      });
          
      $ ("#btn-save").click(function(){
          //提交表单时，使用使用jquery validation engine进行前端基本验证。
          $("#form").validationEngine();
          if ($ ("#form").validationEngine('validate')) {
              $ ("#form").submit();
          }
      });
          
      $('#cpnTimeInputPicker').datetimepicker({
          format: 'YYYY-MM-DD HH:mm',
          dayViewHeaderFormat: 'YYYY-MM'
      });
      	
      //明细
      itemCnt = ${ cpnForm.items.size() };
      var itemTemplate = "<div class=\"col-sm-6\" id=\"item\#{itemSeq}\">" +
      	"<div class=\"box box-default\">" +
      	"<div class=\"box-header with-border\">" +
          "<h3 class=\"box-title\" id=\"item-title\#{itemSeq}\">消费项\#{itemCnt}</h3>" +
          "<div class=\"box-tools pull-right\">" +
          "<button type=\"button\" class=\"btn btn-box-tool\" id=\"remove-item\#{itemSeq}\" onClick=\"javascript:removeItem(\#{itemSeq});\"><i class=\"fa fa-times\"></i></button>" +
          "</div>" +
          "</div>" +
      	"<div class=\"box-body\">" +
      	"<div class=\"form-horizontal\">" +
      	"<div class=\"form-group\">" +
      	"<div class=\"col-sm-6\">" +
      	"<input type=\"text\" class=\"form-control\" placeholder=\"说明\" data-validation-engine=\"validate[required]\" id=\"itemDesc\#{itemSeq}\" name=\"items[\#{itemSeq}].itemDesc\" maxlength=\"30\" />" +
      	"</div>" +
      	"<div class=\"col-sm-6\">" +
      	"<input type=\"text\" class=\"form-control\" placeholder=\"类别\" data-validation-engine=\"validate[required]\" id=\"categoryDesc\#{itemSeq}\" name=\"items[\#{itemSeq}].categoryDesc\" onClick=\"javascript:selectCategory(\#{itemSeq});\" />" +
      	"<input type=\"hidden\" id=\"categoryOid\#{itemSeq}\" name=\"items[\#{itemSeq}].categoryOid\" />" +
      	"</div>" +
      	"<div class=\"col-sm-6\">" +
      	"<input type=\"text\" class=\"form-control\" placeholder=\"金额\" data-validation-engine=\"validate[required]\" id=\"itemAmount\#{itemSeq}\" name=\"items[\#{itemSeq}].amount\" onBlur=\"javascript:checkItemAmount(this);\" maxlength=\"11\" />" +
      	"</div>" +
      	"<div class=\"col-sm-6\">" +
      	"<select class=\"form-control\" placeholder=\"消费人\" data-validation-engine=\"validate[required]\" id=\"itemOwner\#{itemSeq}\" name='items[\#{itemSeq}].ownerOid' >" +
      	"<option value=\"\" label=\"请选择\"/>" +
      	<c:forEach var="user" items="${users}" varStatus="status" >
          "<option value =\"${user.userOid}\">${user.userName}</option>" +
          </c:forEach>
      	"</select>" +
      	"</div>" +
      	"</div>" +
      	"</div>" +
      	"</div>" +
      	"</div>" +
      	"</div>";

      $( "#btn-add-item" ).click(function(){
          itemCnt ++;
          $ ( "#items" ).append(itemTemplate.replace( /#\{itemSeq\}/g, (itemCnt-1) ).replace( /#\{itemCnt\}/g, itemCnt ));
      });
          
      //账户
      selectedAccounts = new Array();//用来保存已经使用过的账户oid。
      <c:forEach var="item" items="${ cpnForm.payments }" varStatus="status" >
      selectedAccounts.push('${item.acntOid}');
      </c:forEach>
        
      accountCnt = ${ cpnForm.payments.size() };
      var accountTemplate = "<div class=\"col-sm-6\" id=\"account\#{accountSeq}\">" +
      	"<div class=\"box box-default\">" +
      	"<div class=\"box-header with-border\">" +
      	"<h3 class=\"box-title\" id=\"account-title\#{accountSeq}\"\" >支付账户\#{accountCnt}</h3>" +
      	"<div class=\"box-tools pull-right\">" +
      	"<button type=\"button\" class=\"btn btn-box-tool\" id=\"remove-account\#{accountSeq}\" onClick=\"javascript:removeAccount(\#{accountSeq});\"><i class=\"fa fa-times\"></i></button>" +
      	"</div>" +
      	"</div>" +
      	"<div class=\"box-body\">" +
      	"<div class=\"form-horizontal\">" +
      	"<div class=\"col-sm-9\">" +
      	"<input class=\"form-control\" placeholder=\"账户\" data-validation-engine=\"validate[required]\" id=\"acntHumanDesc\#{accountSeq}\" onClick=\"javascript:selectAccount(\#{accountSeq});\" />" +
      	"<input type=\"hidden\" id=\"accountOid\#{accountSeq}\" name=\"payments[\#{accountSeq}].acntOid\" />" +
      	"</div>" +
      	"<div class=\"col-sm-3\">" +
      	"<input type=\"text\" class=\"form-control\" placeholder=\"支付金额\" data-validation-engine=\"validate[required]\" id=\"payment\#{accountSeq}\" name=\"payments[\#{accountSeq}].payment\" onBlur=\"javascript:checkAccountAmount(this);\" maxlength=\"11\" />" +
      	"</div>" +
      	"</div>" +
      	"</div>" +
      	"</div>" +
      	"</div>";

      $( "#btn-add-account" ).click(function(){
          accountCnt ++;                 
          $ ( "#accounts" ).append(accountTemplate.replace( /#\{accountSeq\}/g, (accountCnt-1) ).replace( /#\{accountCnt\}/g, accountCnt ) );
      });
  });
  </script>
</body>
</html>
