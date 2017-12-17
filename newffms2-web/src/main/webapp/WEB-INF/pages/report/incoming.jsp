<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
<head>
</head>

<body>
  <section class="content-header">
    <h1>
      收入<small>图表</small>
    </h1>
  </section>

  <section class="content">
    <div class="row" style="padding-left: 20px; padding-bottom: 20px;">
      <div class="col-xs-6 col-md-2">
        <button type="button" class="btn btn-default btn-block" id="btn-previous">上一年</button>
      </div>

      <div class="col-xs-6 col-md-2">
        <button type="button" class="btn btn-default btn-block" id="btn-next">下一年</button>
      </div>
    </div>

    <div class="row">
      <div class="col-lg-6">
        <div class="box box-primary">
          <div class="box-body">
            <div id="chartForTotalIncoming"></div>
          </div>
        </div>
      </div>

      <div class="col-lg-6">
        <div class="box box-primary">
          <div class="box-body">
            <div id="chartForTotalIncomingByType"></div>
          </div>
        </div>
      </div>

      <div class="col-lg-12">
        <div class="box box-primary">
          <div class="box-body">
            <div id="container2"></div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/AdminLTE2/js/app.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/js/highcharts.src.js' />" charset="utf-8"></script>
  <script src="<c:url value='/js/drilldown.src.js' />" charset="utf-8"></script>
  <script src="<c:url value='/js/moment.js' />" charset="utf-8"></script>

  <script>
  $( document ).ready(function() {
      var options4TotalIncoming = {
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
      
      var options4TotalIncomingByType = {
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
  	
      var selectYear = parseInt(moment().format("YYYY")); 
  	
      var refreshTotalIncoming = function(data) {
          options4TotalIncoming.series = data.series;
          options4TotalIncoming.title.text = data.title;
          $('#chartForTotalIncoming').highcharts(options4TotalIncoming);
      };
      
      var refreshTotalIncomingByType = function(data) {
          options4TotalIncomingByType.series = data.series;
          options4TotalIncomingByType.title.text = data.title;
          $('#chartForTotalIncomingByType').highcharts(options4TotalIncomingByType);
      };
      
      doQuery = function() {
          var queryStr = "?year=" + selectYear;
          $.ajax({
              cache: false,
              url: '<c:url value="/report/queryTotalIncoming" />' + queryStr,
              type: "POST",
              async: true,
              success: function(data) {
                  refreshTotalIncoming(data);
              }
          });
          
          $.ajax({
              cache: false,
              url: '<c:url value="/report/queryTotalIncomingByType" />' + queryStr,
              type: "POST",
              async: true,
              success: function(data) {
                  refreshTotalIncomingByType(data);
              }
          });
      };
      
      $ ("#btn-query").click(function(){
      	doQuery();
      });
      
      doQuery();
      
      $("#btn-previous").click(function(){
      	selectYear -= 1;
      	doQuery();
      });
      
      $("#btn-next").click(function(){
      	selectYear += 1;
      	doQuery();
      });
  });
  </script>
</body>
</html>
