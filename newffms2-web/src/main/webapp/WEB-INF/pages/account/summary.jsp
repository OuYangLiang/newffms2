<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
<body>
  <section class="content-header">
    <h1>
      账户<small></small>
    </h1>
  </section>

  <section class="content">
    <div class="row">
      <div class="col-sm-12">
        <div style="padding-left: 20px; padding-bottom: 20px;">
          <button type="button" class="btn btn-default" id="btn-add">
            <i class="glyphicon glyphicon-plus"></i>
          </button>
          
          <button type="button" class="btn btn-default" id="btn-display-active" style="display:none">
            <i class="fa fa-search-minus"></i>
          </button>
          
          <button type="button" class="btn btn-default" id="btn-display-all">
            <i class="fa fa-search-plus"></i>
          </button>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-xs-10 col-xs-offset-1">
        <div id="myCarousel" class="carousel slide" wrap="false">
          <ol class="carousel-indicators" id="carouselSlide">
          </ol>
          <div class="carousel-inner" id="carouselInner"></div>
          <a class="left carousel-control" href="#myCarousel"
            role="button" data-slide="prev"> <span
            class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </a> <a class="right carousel-control" href="#myCarousel"
            role="button" data-slide="next"> <span
            class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </a>
        </div>
      </div>
    </div>
  </section>

  <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
  <script src="<c:url value='/AdminLTE2/js/app.min.js' />" charset="utf-8"></script>

  <script>
  $( document ).ready(function() {
	  function amtFormatter(value) {
          return "¥" + parseFloat(value).toFixed(2);
      }
	  
      var slideTemplate = "<li data-target=\"#myCarousel\" data-slide-to=\"\#{slideIdx}\"></li>";
      var innerTemplate = "<div class=\"item \#{activeness}\"><div class=\"box box-primary\"><div class=\"box-body box-profile\"><img class=\"profile-user-img img-responsive img-circle\" src=\"<c:url value='/resources/img/\#{icon}' />\" alt=\"User profile picture\"/><h3 class=\"profile-username text-center\">\#{username}</h3><p class=\"text-muted text-center\">\#{userRemark}</p></div><div class=\"box-footer\"><div class=\"row\"><div class=\"col-sm-4 border-right\"><div class=\"description-block\"> <h5 class=\"description-header\">\#{numOfAccount}</h5> <span class=\"description-text\">账户数</span></div></div><div class=\"col-sm-4 border-right\"><div class=\"description-block\"><h5 class=\"description-header\">\#{totalBalance}</h5><span class=\"description-text\">总余额</span></div></div><div class=\"col-sm-4\"><div class=\"description-block\"><h5 class=\"description-header\">\#{totalDept}</h5><span class=\"description-text\">欠额</span></div></div></div></div><div class=\"box-footer\"><div class=\"row\" id=\"\#{cardListId}\"></div></div></div></div>";
      var cardTemplate = "<div class=\"col-md-4 col-sm-6 col-xs-12\"><div class=\"info-box bg-blue\"><span class=\"info-box-icon\"><i class=\"fa fa-credit-card\"></i></span><div class=\"info-box-content\"><span class=\"info-box-text\">\#{cardType}&nbsp;&nbsp;<a href=\"<c:url value='/account/view' />?acntOid=\#{itemOid}\" >More Info</a></span><span class=\"info-box-text\">\#{cardBalance}</span><div class=\"progress\"><div class=\"progress-bar\" style=\"width: 100%\"></div></div><span class=\"progress-description\">\#{itemInfo}</span></div></div></div>";

      function refresh(url) {
    	  $ ("#carouselSlide").empty();
          $ ("#carouselInner").empty();
    	  $.ajax({
              cache: false,
              url: url,
              type: "POST",
              async: true,
              success: function(data) {
                  $.each(data, function(idx, obj) {
                      $("#carouselSlide").append(slideTemplate.replace( /#\{slideIdx\}/g, idx ));

                      $("#carouselInner").append(innerTemplate.replace( /#\{icon\}/g, obj.user.icon )
                            .replace( /#\{username\}/g, obj.user.userName )
                            .replace( /#\{userRemark\}/g, obj.user.remarks )
                            .replace( /#\{numOfAccount\}/g, obj.numOfAccount )
                            .replace( /#\{totalBalance\}/g, amtFormatter(obj.totalBalance) )
                            .replace( /#\{totalDept\}/g, amtFormatter(obj.totalDept) )
                            .replace( /#\{cardListId\}/g, "cardList-" + obj.user.userOid )
                            .replace( /#\{activeness\}/g, 0 == idx ? "active" : "" )
                      );

                      if (obj.accounts) {
                          $.each(obj.accounts, function(idx, item) {
                              $ ( "#cardList-" + obj.user.userOid ).append(
                                   cardTemplate.replace( /#\{cardType\}/g, item.acntTypeDesc )
                                  .replace( /#\{cardBalance\}/g, "余额: " + amtFormatter(item.balance) + ("Creditcard" === item.acntType ? " 欠款: " + amtFormatter(item.debt) : "") )
                                  .replace( /#\{itemOid\}/g, item.acntOid )
                                  .replace( /#\{itemInfo\}/g, item.acntDesc )
                              );
                          })
                      }
                  })
              }
          });
      }
      
      refresh("<c:url value='/account/alaxGetAllAccountsByUser?includeDisabled=false' />");

      $ ("#btn-add").click(function(){
          window.location.href = "<c:url value='/account/initAdd' />";
      });
      
      $ ("#btn-display-active").click(function(){
          refresh("<c:url value='/account/alaxGetAllAccountsByUser?includeDisabled=false' />");
          $("#btn-display-active").attr("style", "display:none");
          $("#btn-display-all").attr("style", "display:''");
      });
      
      $ ("#btn-display-all").click(function(){
    	  refresh("<c:url value='/account/alaxGetAllAccountsByUser?includeDisabled=true' />");
    	  $("#btn-display-active").attr("style", "display:''");
    	  $("#btn-display-all").attr("style", "display:none");
      });
      
      
  });
  </script>
</body>
</html>
