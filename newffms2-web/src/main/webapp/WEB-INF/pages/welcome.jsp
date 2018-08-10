<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
<body>
    <section class="content-header">
        <h1>
            欢迎您
        </h1>
    </section>
    
    <section class="content">
      <div class="row">
        <div class="col-sm-5">
          <div class="info-box bg-red">
            <span class="info-box-icon"><i class="fa fa-fw fa-exclamation-circle"></i></span>
            <div class="info-box-content">
              <span class="info-box-text">当月消费总额</span>
              <span class="info-box-number" id="consumptionTotal"></span>
              <div class="progress">
                <div class="progress-bar" style="width: 70%"></div>
              </div>
              <span class="progress-description">70% Increase in 30 Days</span>
            </div>
          </div>
        </div>
      </div>
    </section>
    
	<script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
    <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
    <script src="<c:url value='/AdminLTE2/js/app.min.js' />" charset="utf-8"></script>
    <script src="<c:url value='/js/common.js' />" charset="utf-8"></script>
    
    <script>
    $( document ).ready(function() {
      $.ajax({
        cache : false,
        url : '<c:url value="/consumption/monthlyConsumptionTotal" />',
        type : "GET",
        async : true,
        success : function(data) {
        	$("#consumptionTotal").html(amtFormatter(data.data));
        }
      });
    });
    </script>
    
</body>
</html>
