<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <body>
        <div class="content-header ui-widget-header">
            信息
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="ui-widget">
                    <div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
                        <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                        出错啦，请把错误码<strong>[<c:out value="${tickNo }" />]</strong>发送给管理员。</p>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
