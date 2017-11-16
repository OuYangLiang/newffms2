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
                <div class="col-lg-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">总收入情况</h3>
                        </div>
                        
                        <div class="box-body">
                            <div id="container3" ></div>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">成员收入情况</h3>
                        </div>
                        
                        <div class="box-body">
                            <div id="container" ></div>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">分类收入情况</h3>
                        </div>
                        
                        <div class="box-body">
                            <div id="container2" ></div>
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
            	var options = {
                        chart: {
                            type: "spline"
                        },
                        title: {
                            text: 'Title'
                        },
                        xAxis: {
                            type:'category'
                        },
                        tooltip: {
                            "pointFormat": "{series.name}: <b>¥{point.y:,.2f}</b>"
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
                            "format": "<b>{point.name}</b>:¥{point.y:,.2f}"
                        },
                        series: []
                    };
            	
            	var options2 = {
                        chart: {
                            type: "spline"
                        },
                        title: {
                            text: 'Title'
                        },
                        xAxis: {
                            type:'category'
                        },
                        tooltip: {
                            "pointFormat": "{series.name}: <b>¥{point.y:,.2f}</b>"
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
                            "format": "<b>{point.name}</b>:¥{point.y:,.2f}"
                        },
                        series: []
                    };
            	
            	var options3 = {
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
            	
            	var refresh = function(data) {
                    options.series = data.incomingOfUser.series;
                    options.title.text = data.incomingOfUser.title;
                    $('#container').highcharts(options);
                    
                    options2.series = data.incomingOfType.series;
                    options2.title.text = data.incomingOfType.title;
                    $('#container2').highcharts(options2);
                    
                    options3.series = data.incomingOfAll.series;
                    options3.title.text = data.incomingOfAll.title;
                    $('#container3').highcharts(options3);
                };
                
                doQuery = function() {
                    var queryStr = "?start=" + selectYear + "&end=" + selectYear;
                    
                    $.ajax({
                        cache: false,
                        url: '<c:url value="/report/incomingDataSource" />' + queryStr,
                        type: "POST",
                        async: true,
                        success: function(data) {
                            refresh(data);
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
