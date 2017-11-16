<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <title>AdminLTE 2 | Documentation</title>
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"/>
        <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

        <!-- Bootstrap -->
        <link href="<c:url value='/bootstrap-3.3.5-dist/css/bootstrap.min.css' />" rel="stylesheet">
        <link href="<c:url value='/font-awesome-4.6.3/css/font-awesome.min.css' />" rel="stylesheet">
        <!-- <link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet"> -->
        <link href="<c:url value='/AdminLTE2/css/AdminLTE.min.css' />" rel="stylesheet">
        <link href="<c:url value='/AdminLTE2/css/skins/skin-blue.min.css' />" rel="stylesheet">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
            <![endif]-->
        <decorator:head />
    </head>

    <body class="hold-transition skin-blue">
		<div class="wrapper">
			<header class="main-header">
				<a href="#" class="logo"> <span class="logo-mini"><b>A</b>LT</span>
					<span class="logo-lg">家庭管理系统</span>
				</a>
	
				<nav class="navbar navbar-static-top" role="navigation">
					<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
					    <span class="sr-only">Toggle navigation</span>
					</a>
					
					<div class="navbar-custom-menu">
	                    <ul class="nav navbar-nav">
	                        <li class="dropdown user user-menu">
				                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
				                    <img src="<c:url value='/resources/img/${SESSION_USER_KEY.icon }' />" class="user-image" alt="<c:out value='${SESSION_USER_KEY.userName }' />">
				                    <span class="hidden-xs"><c:out value='${SESSION_USER_KEY.userName }' /></span>
				                </a>
				                <ul class="dropdown-menu">
				                    <li class="user-header">
				                        <img src="<c:url value='/resources/img/${SESSION_USER_KEY.icon }' />" class="img-circle" alt="<c:out value='${SESSION_USER_KEY.userName }' />">
				                        <p><c:out value='${SESSION_USER_KEY.remarks }' /></p>
				                    </li>
				                    <li class="user-footer">
					                    <div class="pull-right">
					                        <a href="<c:url value='/profile/initEdit' />" class="btn btn-default ">Profile</a>
					                    </div>
				                    </li>
				                </ul>
				            </li>
					                        
	                        <li class="dropdown messages-menu">
	                            <a href="<c:url value='/j_spring_security_logout' />" >
	                                <i class="fa fa-power-off">退出</i>
	                            </a>
	                        </li>
	                    </ul>
	                </div>
				</nav>
			</header>
			
			<aside class="main-sidebar">
                <section class="sidebar">
                    <div class="user-panel">
						<div class="pull-left image">
						    <img src="<c:url value='/resources/img/developper.jpg' />" class="img-circle" alt="作者：欧阳亮">
						</div>
						
                        <div class="pull-left info">
                            <p>By: 欧阳亮</p>
                            苏宁：高级架构师
                        </div>
                    </div>

					<ul class="sidebar-menu">
						<li class="header">菜单</li>
						<c:forEach var="item" items="${ SESSION_MENU_KEY }" varStatus="status" >
                            <li><a href="<c:url value='${item.moduleLink }' />"><i class="fa fa-circle "></i> <span>${item.moduleDesc }</span></a></li>
                        </c:forEach>
					</ul>
                </section>
            </aside>
            
            <div class="content-wrapper">
                <decorator:body />
            </div>

			<footer class="main-footer">
				<!-- To the right -->
				<div class="pull-right hidden-xs"></div>
				<!-- Default to the left -->
				<strong>Copyright &copy; <a href="#">欧阳亮</a></strong>
			</footer>
	   </div>
    </body>
</html>
