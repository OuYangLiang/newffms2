<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
<head>
</head>

<body>
  <section class="content-header">
    <h1>
      消费<small>图表</small>
    </h1>
  </section>

  <section class="content">
    <div class="row" style="padding-left: 20px; padding-bottom: 20px;">
      <button type="button" class="btn btn-primary btn-flat" id="btn-previous">上一月</button>
      <button type="button" class="btn btn-primary btn-flat" id="btn-next">下一月</button>
      <button type="button" class="btn btn-primary btn-flat" id="btn-mode-toggle">按年查询</button>
      <a class="btn btn-primary btn-flat" data-toggle="modal" data-target="#categoryExcludingModal">排除类别</a>
    </div>

    <div class="row">
      <div class="col-lg-4">
        <div class="box box-primary">
          <div class="box-header with-border">
            <h3 class="box-title">成员消费情况</h3>
          </div>

          <div class="box-body">
            <div id="chartForUserAmtConsumption"></div>
          </div>
        </div>
      </div>

      <div class="col-lg-4">
        <div class="box box-primary">
          <div class="box-header with-border">
            <h3 class="box-title">成员消费比例</h3>
          </div>

          <div class="box-body">
            <div id="chartForUserRatioConsumption"></div>
          </div>
        </div>
      </div>

      <div class="col-lg-4">
        <div class="box box-primary">
          <div class="box-header with-border">
            <h3 class="box-title">类别消费比例</h3>
          </div>

          <div class="box-body">
            <div id="chartForCategoryRatioConsumption"></div>
          </div>
        </div>
      </div>

      <div class="col-lg-12">
        <div class="box box-primary">
          <div class="box-header with-border">
            <h3 class="box-title">消费明细报表</h3>
          </div>

          <div class="box-body">
            <div id="chartForDetailConsumption"></div>
          </div>
        </div>
      </div>

    </div>

  </section>

  <div class="modal fade" id="categoryExcludingModal" tabindex="-1" role="dialog" aria-labelledby="categoryExcludingModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id="categoryExcludingModalLabel">请沟选排除项</h4>
        </div>
        <div class="modal-body">
          <div class="row">
            <c:forEach var="item" items="${ rootCategories }" varStatus="status">
              <div class="col-xs-6 col-md-3">
                <input type="checkbox" id="category${status.index }" value="${item.categoryOid }" onclick="javascript:doQuery();">
                  <c:out value="${ item.categoryDesc }" />
                </input>
              </div>
            </c:forEach>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary btn-flat" data-dismiss="modal">关闭</button>
        </div>
      </div>
    </div>
  </div>

  <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/AdminLTE2/js/app.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/js/highcharts.js' />" charset="utf-8"></script>
  <script src="<c:url value='/js/drilldown.js' />" charset="utf-8"></script>
  <script src="<c:url value='/js/moment.js' />" charset="utf-8"></script>

  <script>
  $( document ).ready(function() {
      var options4UserAmtConsumption = {
          chart : {
              type : "column"
          },
          title : {
              text : 'Title'
          },
          xAxis : {
              type : 'category'
          },
          tooltip : {
              "pointFormat" : "{series.name}: <b>{point.y:,.2f}</b>"
          },
          yAxis : {
              title : {
                  text : ""
              }
          },
          plotOptions : {
              series : {
                  borderWidth : 0,
                  dataLabels : {
                      enabled : false,
                      "format" : "{point.y:,.2f}"
                  }
              }
          },
          dataLabels : {
              "enabled" : true,
              "format" : "<b>{point.name}</b>: {point.y:,.2f}"
          },
          series : [],
          drilldown : {
              series : []
          }
      };
      
      var options4UserRatioConsumption = {
          title: {
              text: 'title'
          },
          tooltip: {
              pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
          },
          plotOptions: {
              pie: {
                  allowPointSelect: false,
                  showInLegend: true,
                  //cursor: 'pointer',
                  dataLabels: {
                      enabled: true,
                      format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                  }
              }
          },
          series: [],
          drilldown: {
              series:[]
          }
      };
      
      var options4CategoryRatioConsumption = {
          title: {
              text: 'title'
          },
          tooltip: {
              pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
          },
          plotOptions: {
              pie: {
                  allowPointSelect: false,
                  showInLegend: true,
                  dataLabels: {
                      enabled: true,
                      format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                  }
              }
          },
          series: [],
          drilldown: {series:[]}
      };
      
      var options4DetailConsumption = {
          chart: {
              type: "column"
          },
          title: {
              text: 'Title'
          },
          xAxis: {
              type:'category'
          },
          tooltip: {
              "pointFormat": "{series.name}: <b>{point.y:,.2f}</b>"
          },
          yAxis: {
              title: {
                  text: ""
              }
          },
          plotOptions: {
              series: {
                  borderWidth: 0,
                  dataLabels: {
                      enabled: false,
                      "format": "{point.y:,.2f}"
                  }
              }
          },
          dataLabels: {
              "enabled": true,
              "format": "<b>{point.name}</b>: {point.y:,.2f}"
          },
          series: [],
          drilldown: {series:[]}
      };

      var refreshUserAmtConsumption = function(data) {
          options4UserAmtConsumption.series = data.series;
          options4UserAmtConsumption.title.text = data.title;
          $('#chartForUserAmtConsumption').highcharts(options4UserAmtConsumption);
      };

      var refreshUserRatioConsumption = function(data) {
          options4UserRatioConsumption.series = data.series;
          options4UserRatioConsumption.title.text = data.title;
          $('#chartForUserRatioConsumption').highcharts(options4UserRatioConsumption);
      };
      
      var refreshCategoryRatioConsumption = function(data) {
          options4CategoryRatioConsumption.series = data.series;
          options4CategoryRatioConsumption.drilldown = {};
          options4CategoryRatioConsumption.drilldown.series = data.drilldown;
          options4CategoryRatioConsumption.title.text = data.title;
          $('#chartForCategoryRatioConsumption').highcharts(options4CategoryRatioConsumption);
      };
      
      var refreshDetailConsumption = function(data) {
          options4DetailConsumption.series = data.series;
          options4DetailConsumption.drilldown = {};
          options4DetailConsumption.drilldown.series = data.drilldown;
          options4DetailConsumption.title.text = data.title;
          $('#chartForDetailConsumption').highcharts(options4DetailConsumption);
      };
      
      mode = "monthly";
      selectYear = parseInt(moment().format("YYYY"));
      selectMonth = parseInt(moment().format("MM"));

      doQuery = function() {
          var selectedCategories = [];
          var i = 0;

          while ($("#category" + i).length > 0) {
              if ($("#category" + i).prop("checked")) {
                  selectedCategories.push($( "#category" + i).val());
              }
              i++;
          }

          var queryStr = "?mode=" + mode + "&year="  + selectYear + "&month=" + selectMonth;

          if (selectedCategories.length != 0) {
              queryStr = queryStr + "&excludeCategories=" + selectedCategories.join("|");
          }

          $.ajax({
              cache : false,
              url : '<c:url value="/report/queryUserAmtConsumption" />' + queryStr,
              type : "POST",
              async : true,
              success : function(data) {
                  refreshUserAmtConsumption(data);
              }
          });

          $.ajax({
              cache : false,
              url : '<c:url value="/report/queryUserRatioConsumption" />' + queryStr,
              type : "POST",
              async : true,
              success : function(data) {
                  refreshUserRatioConsumption(data);
              }
          });
          
          $.ajax({
              cache : false,
              url : '<c:url value="/report/queryCategoryRatioConsumption" />' + queryStr,
              type : "POST",
              async : true,
              success : function(data) {
                  refreshCategoryRatioConsumption(data);
              }
          });
          
          $.ajax({
              cache : false,
              url : '<c:url value="/report/queryDetailConsumption" />' + queryStr,
              type : "POST",
              async : true,
              success : function(data) {
                  refreshDetailConsumption(data);
              }
          });
      };

      doQuery();

      $("#btn-mode-toggle").click(function() {
          if (mode == "monthly") {
              $("#btn-previous").html("上一年");
              $("#btn-next").html("下一年");
              $("#btn-mode-toggle").html("按月查询");
              mode = "annually";
          } else if (mode == "annually") {
              $("#btn-previous").html("上一月");
              $("#btn-next").html("下一月");
              $("#btn-mode-toggle").html("按年查询");
              mode = "monthly";
          }

          selectYear = parseInt(moment().format("YYYY"));
          selectMonth = parseInt(moment().format("MM"));
          doQuery();
      });

      $("#btn-previous").click(function() {
          if (mode == "monthly") {
              selectMonth--;
              if (selectMonth == 0) {
                  selectMonth = 12;
                  selectYear--;
              }
          } else if (mode == "annually") {
              selectYear--;
          }

          doQuery();
      });

      $("#btn-next").click(function() {
          if (mode == "monthly") {
              selectMonth++;
              if (selectMonth == 13) {
                  selectMonth = 1;
                  selectYear++;
              }
          } else if (mode == "annually") {
              selectYear++;
          }

          doQuery();
      });
  });
  </script>
</body>
</html>
