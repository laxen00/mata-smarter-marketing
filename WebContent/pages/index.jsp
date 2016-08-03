<%@ page import="
java.util.List,
com.api.main.DataClass,
org.json.JSONArray,
org.json.JSONObject
" %>
<%
	JSONObject user;
	String kaskusUser = "";
	String kaskusPass = "";
	String consumerKey = "";
	String consumerSecret = "";
	String oauthToken = "";
	String oauthSecret = "";
 	System.out.println("Checking for login information...");
	String username = "";
	try {
		username = session.getAttribute("username").toString();
		user = new JSONObject(session.getAttribute("userDetail").toString());
		kaskusUser = user.getString("kaskusUser");
		kaskusPass = user.getString("kaskusPass");
		consumerKey = user.getString("consumerKey");
		consumerSecret = user.getString("consumerSecret");
		oauthToken = user.getString("oauthToken");
		oauthSecret = user.getString("oauthSecret");
	}
	catch (Exception e) {
		username = "";
		user = null;
		kaskusUser = "";
		kaskusPass = "";
		consumerKey = "";
		consumerSecret = "";
		oauthToken = "";
		oauthSecret = "";
		System.out.println("No login information found, redirecting to login page...");
		response.sendRedirect("login.jsp");
	}
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Mata Smarter Marketing Portal</title>

    <!-- Bootstrap Core CSS -->
<!--     <link rel="icon" type="image/png" href="../images/ticon.png" /> -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="../bower_components/bootstrap/dist/css/spinner.css" rel="stylesheet">
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="../dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../bower_components/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<!--script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.9.0.min.js"></script-->
	<!--script src="//code.jquery.com/jquery-1.10.2.js"></script-->
	<script src="../bower_components/jquery/dist/jquery.min.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<script type="text/javascript" src="../bower_components/momentjs/moment.js"></script>
 	<script src="../bower_components/bootpage/bootpage.js"></script>
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.1/summernote.css" rel="stylesheet">
 	<script src="../bower_components/bootstrap/dist/js/wysiwyg.js"></script>
	<!--script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script-->
	<!--script type="text/javascript" src="../bower_components/datetimepicker/datetimepicker.js"></script-->
	<script type="text/javascript">
    $(function() {
      $("#add").click(function() {
		  var emailname = document.getElementById("email_name").value;
          div = document.createElement('div');
          $(div).addClass("alert alert-info alert-dismissable").css({display:"inline-block"}).css({width:"40%"}).css({height:"10px"}).css({'margin-bottom':"0px"}).css({'margin-right':"4px"}).html("<div style='margin-top:-10px'> <button type='button' class='close' data-dismiss='alert' aria-hidden='true' style='z-index:10'>Ã?</button><a style='margin:0px; position:fixed; width:29%; overflow:hidden'>" + emailname + "</a></div>");
          $("#container-nael").append(div);
        });
    });
</script>
<script type="text/javascript">
    $(function() {
      $("#addtwit").click(function() {
          div = document.createElement('div');
          $(div).addClass("").css({'margin-bottom':"5px"}).html("<label>Consumer Key</label><input id='ck' class='form-control' required placeholder='Consumer Key' style='width:70%; display:inline-block; margin:0px 0px 3px 49px'><label>Consumer Secret</label><input id='cs' type='email' class='form-control' required placeholder='Consumer Secret' style='width:70%; display:inline-block; margin:0px 0px 3px 32px'><label>Access Token</label><input id='at' type='email' class='form-control' required placeholder='Access Token' style='width:70%; display:inline-block; margin:0px 0px 3px 52px'><label>Access Token Secret</label><input id='ats' type='email' class='form-control' required placeholder='Access Token Secret' style='width:70%; display:inline-block; margin:0px 0px 3px 5px'>");
          $("#container-nael1").append(div);
        });
    });
</script>
<script>
	function checkInput() {
		var x = document.getElementById("btn-input1").innerHTML;
		if (x=='') {
			document.getElementById("sendButton").setAttribute("disabled","");
		}
		else {
			document.getElementById("sendButton").removeAttribute("disabled");
		}
	}
	function clearSelected(){
		document.getElementById('datepickerfrom').value = '';
		document.getElementById('datepickerto').value = '';
	    var elements = document.getElementById("searchSource").options;

	    for(var i = 0; i < elements.length; i++){
	      elements[i].selected = false;
	    }
	    
	    elements = document.getElementById("searchStatus").options;

	    for(var i = 0; i < elements.length; i++){
	      elements[i].selected = false;
	    }
	    
	    elements = document.getElementById("searchCategory").options;

	    for(var i = 0; i < elements.length; i++){
	      elements[i].selected = false;
	    }
		
		 elements = document.getElementById("searchSentiment").options;

	    for(var i = 0; i < elements.length; i++){
	      elements[i].selected = false;
	    }
	  }
</script>
<script type="text/javascript">
            // @param filename The name of the file WITHOUT ending
            function playSound(filename){   
                document.getElementById("sound").innerHTML='<audio autoplay="autoplay"><source src="../sound/' + filename + '.mp3" type="audio/mpeg" /><source src="../sound/' + filename + '.ogg" type="audio/ogg" /><embed hidden="true" autostart="true" loop="false" src="../sound/' + filename +'.mp3" /></audio>';
            }
</script>
<script>
	$('#closeModal').click(function(){
	
	    $('#linkModal').removeClass('in');
	});
</script>
<script type="text/javascript" src="../js/ajax.js"></script>
</head>

<body>
	<%
		if (kaskusUser.equalsIgnoreCase("N/A")
		||	kaskusPass.equalsIgnoreCase("N/A")
		||	consumerKey.equalsIgnoreCase("N/A")
		||	consumerSecret.equalsIgnoreCase("N/A")
		||	oauthToken.equalsIgnoreCase("N/A")
		||	oauthSecret.equalsIgnoreCase("N/A")) {
	%>
	<script>alert('Please review your Kaskus and Twitter settings');</script>
	<%
		}
	%>
    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0px;  background: #45484d; /* Old browsers */
  background: -moz-linear-gradient(top,  #45484d 0%, #000000 100%); /* FF3.6+ */
  background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#45484d), color-stop(100%,#000000)); /* Chrome,Safari4+ */
  background: -webkit-linear-gradient(top,  #45484d 0%,#000000 100%); /* Chrome10+,Safari5.1+ */
  background: -o-linear-gradient(top,  #45484d 0%,#000000 100%); /* Opera 11.10+ */
  background: -ms-linear-gradient(top,  #45484d 0%,#000000 100%); /* IE10+ */
  background: linear-gradient(to bottom,  #45484d 0%,#000000 100%); /* W3C */
  filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#45484d', endColorstr='#000000',GradientType=0 ); /* IE6-9 */
  border-bottom: 3px solid #286CD1;">
            <div class="navbar-header">
                <a class="navbar-brand" href="index.jsp" style="color:#E1E1D2">
                	Mata Analytics Smarter Marketing
                </a>
            </div>
            <!-- /.navbar-header -->
			
            <ul class="nav navbar-top-links navbar-right">
           
           		 <li class="dropdown" style="margin-right:-10px">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" style="color:#E1E1D2; ">
                        <i class="fa fa-bell fa-fw"></i><span id="notifCount">0</span>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul id="notifs" class="dropdown-menu dropdown-alerts"></ul>

                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" style="color:#E1E1D2; ">
                        <% out.print(username); %><i class="fa fa-user fa-fw" style="margin-left:5px"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                   <ul class="dropdown-menu dropdown-user">
<!--                         <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a> -->
<!--                         </li> -->
                        <li><a href="#" data-toggle="modal" data-target="#usersettings"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="../api/processLogout.jsp"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
              
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <!--div class="navbar-default sidebar" role="navigation">
                
                
            </div-->
            <!-- /.navbar-static-side -->
        </nav>
        <!-- baru -->
        
		
		<div class="tab-content">
		  <div id="home" class="tab-pane fade active in" style="">
			<div id="page-wrapper1" style="margin: 0 0 0 0; padding:0 30px; position:inherit; background-color:transparent;">
            <div class="row" style="margin-top:5px">
                
            </div>
            <!-- /.row -->
            <div class="row" style="display:none">
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-comments fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">26</div>
                                    <div>New Comments!</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-tasks fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">12</div>
                                    <div>New Tasks!</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-yellow">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-shopping-cart fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">124</div>
                                    <div>New Orders!</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-red">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-support fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">13</div>
                                    <div>Support Tickets!</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer" style="padding-bottom: 5px;padding-top: 5px;">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <!-- /.row -->
            <div class="row" style="">
				<!--baru-->
				
				<div class="col-lg-3" style="height: 550px">
                    <!-- /.panel -->
                    <div class="chat-panel panel panel-default" style="height: 551px;margin-bottom: 0px;">
                        <div class="panel-heading">
                            
                            <i class="fa fa-filter fa-fw"></i>Filter
                            <div class="btn-group pull-right">
                                <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                    Action  <i class="fa fa-chevron-down"></i>
                                </button>
                                <ul class="dropdown-menu slidedown">
                                    <li>
                                        <a href="#" onclick="clearSelected();">
                                            <i class="fa fa-refresh fa-fw"></i> Clear
                                        </a>
                                    </li>
                                    
                                    <!-- li>
                                        <a href="#" data-toggle="modal" data-target="#myModal">
                                            <i class="fa fa-mail-forward fa-fw"></i> Config CC history
                                        </a>
                                    </li-->
                                    
                                </ul>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body" style="height:458px">
						
						 
                               
                                   <ul class="chat">
									
									
									<div class="form-group" style="margin-bottom:1px">
									<div id="well_from" class="well well-sm" style="display:none">
									</div>
									<div id="well_to" class="well well-sm" style="display:none">
									</div>
									<div id="well_cat" class="well well-sm" style="display:none">
									</div>
									<div id="well_sent" class="well well-sm" style="display:none">
									</div>
									<div id="well_src" class="well well-sm" style="display:none">
									</div>
									<div id="well_status" class="well well-sm" style="display:none">
									</div>
									<div id="well_newArrived" class="well well-sm" style="display:none">
									</div>
<!-- 									<div id="well_replied" class="well well-sm" style="display:none"> -->
<!-- 									</div> -->
									<div class="row" style="margin-left:0px;">
									<label><u>Date</u></label>
									</div>
									<table style="width:100%">
									<tr>
									<td style="width:17%; padding-bottom:5px">
									<label>From</label>
									</td>
									<td style="padding-bottom:5px">
									<input type="text" id="datepickerfrom" style="width:100%">
									<script>
									  $(function() {
										$( "#datepickerfrom" ).datepicker({
										  changeMonth: true,
										  changeYear: true
										});
									  });
									  </script>
									  </td>
									  </tr>
									  <tr>
									  <td style="padding-bottom:5px">
									  <label>To</label>
									  </td>
									<td style="padding-bottom:5px">
									<input type="text" id="datepickerto" style="width:100%">
									<script>
									  $(function() {
										$( "#datepickerto" ).datepicker({
										  changeMonth: true,
										  changeYear: true
										});
									  });
									  </script>
									  </td>
									  </tr>
									</table>
									
                                    <div class="form-group" style="display:none">
                                            <label><u>Category</u></label>
                                            <select id ="searchCategory" multiple class="form-control">
                                                <option>New Car Any Brand</option>
                                                <option>New Car Any Toyota</option>
                                                <option>New Car Toyota Model</option>
                                                <option>New Car Toyota and Other Brand</option>
                                                <option>Dealer</option>
                                                <option>Spare Parts</option>
                                                <option>Service</option>
                                            </select>
                                        </div>
									<div class="form-group">
                                            <label><u>Sentiment</u></label>
                                            <select id ="searchSentiment" multiple class="form-control" style="height:60px">
                                                <option>Positive</option>
                                                <option>Negative</option>
                                            </select>
                                        </div>
                                
                                    <div class="form-group">
                                            <label><u>Source</u></label>
                                            <select id="searchSource" multiple class="form-control" style="height:60px">
                                                <option>Twitter</option>
                                                
                                            </select>
                                        </div>
										 <div class="form-group">
                                            <label><u>Status</u></label>
                                            <select id ="searchStatus" multiple class="form-control" style="height:90px; overflow-x:scroll;">
                                                <option id="tNRY" title="Not Responded Yet" value="Not Responded Yet">Not Responded Yet</option>
                                                <option id="tNR" title="No Need To Reply" value="No Need To Reply">No Need To Reply</option>
                                                <option id="tO" title="Ongoing" value="Ongoing">Ongoing</option>
                                                <option id="tCC" title="Closed Communication" value="Closed Communication">Closed Communication</option>
                                                <option id="tCCP" title="Closed Communication By Phone" value="Closed Communication By Phone">Closed Communication By Phone</option>
<!--                                                 <option id="tSD" value="Shared To Dealer">Shared To Dealer</option> -->
<!--                                                 <option id="tCDB" value="Closed Dealer Communication (Buy)">Closed Dealer Communication (Buy)</option> -->
<!--                                                 <option id="tCDNB" value="Closed Dealer Communication (Not Buy)">Closed Dealer Communication (Not Buy)</option> -->
                                                <option id="tSAD" title="Saved As Draft" value="Saved As Draft">Saved As Draft</option>
                                            </select>
                                        </div>
										 

                               
                                
                              
                            </ul>
                                
                               
                                    
                                
                            
                            
                        </div>
                        <!-- /.panel-body -->
                        <div class="panel-footer" style="padding-top: 5px;height: 49px;padding-bottom: 0px;">
							<fieldset>
							<div class="form-group">
							
							<div class="col-lg-12" style="padding:0px; margin-bottom:-15px; margin-top:5px;height: 30px;">
                           <div class="form-group" style="margin-bottom: 0px;">
								<span class="input-group-btn">
                                    <button type="button" onclick="getList(1,'<% out.print(username); %>', 'true')" class="btn btn-primary btn-sm btn-block" id="btn-chat">
                                        Submit
                                    </button>
                                </span>
                           </div>
							</div>
							<!--div class="col-lg-2" style="padding:3px 0 0 10px">
								
								<label class="checkbox-inline">
                                  <input type="checkbox">CC
                                </label>
								
                            </div-->
							</div>
							
							</fieldset>
                        </div>
                        <!-- /.panel-footer -->
                    </div>
                    <!-- /.panel .chat-panel -->
					
                </div>
				<!--baruuu-->
				<div class="col-lg-9" style="height: 551px">
                    <!-- /.panel -->
                    <%
//                     	int totalPage = DataClass.getTotalPage("");
//                     	JSONArray arr = DataClass.getList(1);
                    %>
                    <div class="chat-panel panel panel-default" style="height: 550px;margin-bottom: 0px;">
                        <div class="panel-heading">
                            <i class="fa fa-comments fa-fw"></i>
                            List
                            <div class="btn-group pull-right">
                                <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                    Action  <i class="fa fa-chevron-down"></i>
                                </button>
                                <ul class="dropdown-menu slidedown">
                                    <li>
                                        <a href="#" id="refreshList" onclick="getList(1,'<%out.print(username);%>','true')">
                                            <i class="fa fa-refresh fa-fw"></i> Refresh
                                        </a>
                                    </li>
									
                                   
                                    
                                </ul>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body" style="height:458px">
						
						 
                               
                                  <div class="list-group" id="list-group">
<%--                                <% --%>

<%--                                %> --%>

<%--                                     <% out.print(element); %> <% out.print(obj.get("screenname")); %> --%>
<%--                                     <span class="pull-right text-muted small"><em><% out.print(obj.get("postingdate")); %></em> --%>

<%--                                             <% out.print(obj.get("text_content")); %> --%>

<%--                                 <% } %> --%>
                            </div>
                                
                               
                                    
                                
                            
                            
                        </div>
                        <!-- /.panel-body -->
                        <!-- /.panel-footer -->
						<div class="panel-footer" style="padding-top: 5px;padding-bottom: 0px;height: 49px;">
							<fieldset>
							 <div id="content"></div>
							 <div style="text-align:right">
								<div id="page-selection" style="margin: -19px 0px -25px 0px;height: 63px;"></div>
								<script>
									// init bootpag
// 									$('#page-selection').bootpag({
<%-- 										total: <% out.print(totalPage); %>, --%>
// 										maxVisible: 10
// 									}).on("page", function(event, /* page number here */ num){
<%-- 										getList(num, '<% out.print(username); %>','false'); --%>
// // 										 $("#content").html("Insert content"); // some ajax content loading...
// 									});
								</script>
							</div>
							</fieldset>
                        </div>
                    </div>
                    <!-- /.panel .chat-panel -->
					
                </div>
                <div class="col-lg-12" style="display:none">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Dashboards
                            <div class="pull-right" style="display:none">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                        Actions
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu pull-right" role="menu">
                                        <li><a href="#">Action</a>
                                        </li>
                                        <li><a href="#">Another action</a>
                                        </li>
                                        <li><a href="#">Something else here</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li><a href="#">Separated link</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <!--div id="morris-area-chart"></div-->
							<img src="../images/picture2.png" alt="dashboards" style="max-width:100%; max-height:auto;">
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                    <div class="panel panel-default" style="display:none">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Bar Chart Example
                            <div class="pull-right">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                        Actions
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu pull-right" role="menu">
                                        <li><a href="#">Action</a>
                                        </li>
                                        <li><a href="#">Another action</a>
                                        </li>
                                        <li><a href="#">Something else here</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li><a href="#">Separated link</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-4">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-hover table-striped">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Date</th>
                                                    <th>Time</th>
                                                    <th>Amount</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>3326</td>
                                                    <td>10/21/2013</td>
                                                    <td>3:29 PM</td>
                                                    <td>$321.33</td>
                                                </tr>
                                                <tr>
                                                    <td>3325</td>
                                                    <td>10/21/2013</td>
                                                    <td>3:20 PM</td>
                                                    <td>$234.34</td>
                                                </tr>
                                                <tr>
                                                    <td>3324</td>
                                                    <td>10/21/2013</td>
                                                    <td>3:03 PM</td>
                                                    <td>$724.17</td>
                                                </tr>
                                                <tr>
                                                    <td>3323</td>
                                                    <td>10/21/2013</td>
                                                    <td>3:00 PM</td>
                                                    <td>$23.71</td>
                                                </tr>
                                                <tr>
                                                    <td>3322</td>
                                                    <td>10/21/2013</td>
                                                    <td>2:49 PM</td>
                                                    <td>$8345.23</td>
                                                </tr>
                                                <tr>
                                                    <td>3321</td>
                                                    <td>10/21/2013</td>
                                                    <td>2:23 PM</td>
                                                    <td>$245.12</td>
                                                </tr>
                                                <tr>
                                                    <td>3320</td>
                                                    <td>10/21/2013</td>
                                                    <td>2:15 PM</td>
                                                    <td>$5663.54</td>
                                                </tr>
                                                <tr>
                                                    <td>3319</td>
                                                    <td>10/21/2013</td>
                                                    <td>2:13 PM</td>
                                                    <td>$943.45</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- /.table-responsive -->
                                </div>
                                <!-- /.col-lg-4 (nested) -->
                                <div class="col-lg-8">
                                    <div id="morris-bar-chart"></div>
                                </div>
                                <!-- /.col-lg-8 (nested) -->
                            </div>
                            <!-- /.row -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                    <div class="panel panel-default" style="display:none">
                        <div class="panel-heading">
                            <i class="fa fa-clock-o fa-fw"></i> Responsive Timeline
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <ul class="timeline">
                                <li>
                                    <div class="timeline-badge"><i class="fa fa-check"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">Lorem ipsum dolor</h4>
                                            <p><small class="text-muted"><i class="fa fa-clock-o"></i> 11 hours ago via Twitter</small>
                                            </p>
                                        </div>
                                        <div class="timeline-body">
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Libero laboriosam dolor perspiciatis omnis exercitationem. Beatae, officia pariatur? Est cum veniam excepturi. Maiores praesentium, porro voluptas suscipit facere rem dicta, debitis.</p>
                                        </div>
                                    </div>
                                </li>
                                <li class="timeline-inverted">
                                    <div class="timeline-badge warning"><i class="fa fa-credit-card"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">Lorem ipsum dolor</h4>
                                        </div>
                                        <div class="timeline-body">
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Autem dolorem quibusdam, tenetur commodi provident cumque magni voluptatem libero, quis rerum. Fugiat esse debitis optio, tempore. Animi officiis alias, officia repellendus.</p>
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Laudantium maiores odit qui est tempora eos, nostrum provident explicabo dignissimos debitis vel! Adipisci eius voluptates, ad aut recusandae minus eaque facere.</p>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="timeline-badge danger"><i class="fa fa-bomb"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">Lorem ipsum dolor</h4>
                                        </div>
                                        <div class="timeline-body">
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus numquam facilis enim eaque, tenetur nam id qui vel velit similique nihil iure molestias aliquam, voluptatem totam quaerat, magni commodi quisquam.</p>
                                        </div>
                                    </div>
                                </li>
                                <li class="timeline-inverted">
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">Lorem ipsum dolor</h4>
                                        </div>
                                        <div class="timeline-body">
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptates est quaerat asperiores sapiente, eligendi, nihil. Itaque quos, alias sapiente rerum quas odit! Aperiam officiis quidem delectus libero, omnis ut debitis!</p>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="timeline-badge info"><i class="fa fa-save"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">Lorem ipsum dolor</h4>
                                        </div>
                                        <div class="timeline-body">
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nobis minus modi quam ipsum alias at est molestiae excepturi delectus nesciunt, quibusdam debitis amet, beatae consequuntur impedit nulla qui! Laborum, atque.</p>
                                            <hr>
                                            <div class="btn-group">
                                                <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">
                                                    <i class="fa fa-gear"></i>  <span class="caret"></span>
                                                </button>
                                                <ul class="dropdown-menu" role="menu">
                                                    <li><a href="#">Action</a>
                                                    </li>
                                                    <li><a href="#">Another action</a>
                                                    </li>
                                                    <li><a href="#">Something else here</a>
                                                    </li>
                                                    <li class="divider"></li>
                                                    <li><a href="#">Separated link</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">Lorem ipsum dolor</h4>
                                        </div>
                                        <div class="timeline-body">
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sequi fuga odio quibusdam. Iure expedita, incidunt unde quis nam! Quod, quisquam. Officia quam qui adipisci quas consequuntur nostrum sequi. Consequuntur, commodi.</p>
                                        </div>
                                    </div>
                                </li>
                                <li class="timeline-inverted">
                                    <div class="timeline-badge success"><i class="fa fa-graduation-cap"></i>
                                    </div>
                                    <div class="timeline-panel">
                                        <div class="timeline-heading">
                                            <h4 class="timeline-title">Lorem ipsum dolor</h4>
                                        </div>
                                        <div class="timeline-body">
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Deserunt obcaecati, quaerat tempore officia voluptas debitis consectetur culpa amet, accusamus dolorum fugiat, animi dicta aperiam, enim incidunt quisquam maxime neque eaque.</p>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-8 -->
                
                <!-- /.col-lg-4 -->
            </div>
			
                    <!-- /.panel -->
                    <%
// 	                    String tweetUrl = "https://twitter.com/ice1217/status/692197182150148096";
// 	            		JSONArray conv = DataClass.getConversationJSON(tweetUrl);
                    %>
                   <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header" style="height:57px">
                            <ul class="nav nav-tabs navbar-left">
		  <li class="active"><a onclick="changeSend(0)" data-toggle="tab" href="#conversation"><i class="fa fa-comments fa-fw"></i>
                            Conversation</a></li>
		  <li><a onclick="changeSend(1)" id="privateChat" name="privateChat" data-toggle="tab" href="#dm1">Direct Message</a></li>
		</ul>
                            <div class="btn-group pull-right">
                                <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                    Action  <i class="fa fa-chevron-down"></i>
                                </button>
                                <ul class="dropdown-menu slidedown">
                                    <li>
                                        <a href="#" id="convRefresh">
                                            <i class="fa fa-refresh fa-fw"></i> Refresh
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#" id="saveDraft" onclick="saveDraft()">
                                            <i class="fa fa-save fa-fw"></i> Save to drafts
                                        </a>
                                    </li>
									<li>
                                        <a href="#" id="status_0_action" onclick="changeStatus()">
                                            <i id="status_0_text" class=""></i> Not Responded Yet
                                        </a>
                                    </li>
									<li>
                                        <a href="#" id="status_1_action" onclick="changeStatus()">
                                            <i id="status_1_text" class=""></i> No Need To Reply
                                        </a>
                                    </li>
									<li>
                                        <a href="#" id="status_2_action" onclick="changeStatus()">
                                            <i id="status_2_text" class=""></i> Ongoing
                                        </a>
                                    </li>
									<li>
                                        <a href="#" id="status_3_action" onclick="changeStatus()">
                                            <i id="status_3_text" class=""></i> Closed Communication
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#" id="status_4_action" onclick="changeStatus()">
                                            <i id="status_4_text" class=""></i> Closed Communication By Phone
                                        </a>
                                    </li>
									<li>
                                        <a href="#" id="status_5_action" onclick="changeStatus()">
                                            <i id="status_5_text" class=""></i> Shared To Dealer
                                        </a>
                                    </li>
									<li>
                                        <a href="#" id="status_6_action" onclick="changeStatus()">
                                            <i id="status_6_text" class=""></i> Closed Dealer Communication (Buy)
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#" id="status_7_action" onclick="changeStatus()">
                                            <i id="status_7_text" class=""></i> Closed Dealer Communication (Not Buy)
                                        </a>
                                    </li>
                                    <!-- li>
                                        <a href="#" data-toggle="modal" data-target="#myModal">
                                            <i class="fa fa-mail-forward fa-fw"></i> Config CC history
                                        </a>
                                    </li-->
                                    
                                </ul>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="modal-body" id="convPopup" style="height:350px; overflow-x:auto">
						<div class="tab-content">
						 <div id="conversation" class="tab-pane fade in active">
<!--                                <p><i class="fa fa-twitter-square fa-2x"></i> <b>Twitter</b></p> -->
<!--                                    <ul class="chat"> -->
                                <%
//                                 	for (int i=0;i<conv.length();i++) {
//                                 		JSONObject convObj = conv.getJSONObject(i);
//                                 		String liClass = "";
//                                 		String spanClass = "";
//                                 		String imgSrc = convObj.getString("profileImageUrl");
//                                 		if (i % 2 == 0) {
//                                 			liClass = "left clearfix";
//                                 			spanClass = "chat-img pull-left";
//                                 		}
//                                 		else {
                                			
//                                 			liClass = "right clearfix";
//                                 			spanClass = "chat-img pull-right";
//                                 		}
                                %>
<%--                                 <li class="<% out.print(liClass); %>"> --%>
<%--                                     <span class="<% out.print(spanClass); %>"> --%>
<%--                                         <img src="<% out.print(imgSrc); %>" alt="User Avatar" class="img-circle" /> --%>
<!--                                     </span> -->
<!--                                     <div class="chat-body clearfix"> -->
<!--                                         <div class="header"> -->
<%--                                             <strong class="primary-font"><% convObj.get("screenname"); %></strong> --%>
<!--                                         </div> -->
<!--                                         <p> -->
<%--                                             <% out.print(convObj.get("text")); %> --%>
<!--                                         </p> -->
<!--                                     </div> -->
<!--                                 </li> -->
                                <%
//                                 	}
                                %>
<!--                                 <li class="right clearfix"> -->
<!--                                     <span class="chat-img pull-right"> -->
<!--                                         <img src="../images/logo.png" alt="User Avatar" class="img-circle" /> -->
<!--                                     </span> -->
<!--                                     <div class="chat-body clearfix"> -->
<!--                                         <div class="header"> -->
<!--                                             <small class=" text-muted"> -->
<!--                                                 <i class="fa fa-clock-o fa-fw"></i> 13 mins ago</small> -->
<!--                                             <strong class="pull-right primary-font">Toyota</strong> -->
<!--                                         </div> -->
<!--                                         <p> -->
<!--                                             Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare dolor, quis ullamcorper ligula sodales. -->
<!--                                         </p> -->
<!--                                     </div> -->
<!--                                 </li>            -->
<!--                             </ul> -->
                                
                               
                                    
                                
                            
                            </div>
							<div id="dm1" class="tab-pane fade">
							</div>
							</div>
                        </div>
                        <!-- /.panel-body -->
                         <div class="modal-footer">
							<fieldset>
							<div class="form-group">
							<div class="col-lg-12" style="padding:0px">
                            <div class="input-group" style="width:100%; text-align:left">
                                <div id="inputw" style="display:none">
								<input id="btn-input" type="text" class="form-control input-sm" placeholder="Type your message here..." oninput="checkInput()" style="text-align:left; " />
								<script>
									$(document).ready(function() {
										  $('#btn-input').summernote({
											minHeight:50
										  });
									   });
								</script>
								</div>
								<input id="mediaPath" name="mediaPath" type="hidden" value="" />
								<input id="mediaName" name="mediaName" type="hidden" value="" />
								<input id="btn-input2" type="text" class="form-control input-sm" placeholder="Type your message here..." oninput="checkInput()" style="text-align:left; display:inline" />
								
                                <!--span class="input-group-btn">
                                    <button class="btn btn-warning btn-sm" id="btn-chat">
                                        Send
                                    </button>
                                </span-->
							</div>
							</div>
							
							
							<div class="col-lg-12" style="padding:0px; margin-bottom:-15px; margin-top:5px">
                           <div class="form-group" id="convButtons">
								 <span class="input-group-btn">
									<div style="float:left">
									<button id="imageinput" type="button" class="btn btn-default btn-sm" id="btn-img" data-toggle="modal" data-target="#inputimage">
                                    <i class="fa fa-image fa-fw"></i>
                                    </button>
									</div>
                                    <button id="sendButton" type="button" class="btn btn-primary btn-sm" id="btn-chat" disabled>
                                        Send
                                    </button> 
									<button type="button" class="btn btn-sm" data-dismiss="modal">Close</button>
                                </span> 
                           </div>
							</div>
							<!--div class="col-lg-2" style="padding:3px 0 0 10px">
								
								<label class="checkbox-inline">
                                  <input type="checkbox">CC
                                </label>
								
                            </div-->
							</div>
							
							</fieldset>
                        </div>
                        <!-- /.panel-footer -->
                    </div></div></div>
                    <!-- /.panel .chat-panel -->
					
             
            <!-- /.row -->
			<div id="myModal1" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Config CC</h4>
      </div>
      <div class="modal-body">
	  
	  <div class="form-group" >
		<form data-toggle="validator" role="form">
        <div class="form-group" >
		<input id="email_name" type="email" class="form-control" required placeholder="email name" data-error="invalid email format" style="width:70%; margin-right:15px; display:inline-block">
		
		
		
			<button id="add" type="button" class="btn btn-info" style="display:inline-block; margin-bottom:3px">Add</button>
		
		<div id="container-nael">
		<div class="alert alert-info alert-dismissable" style="display:inline-block; width:40%; height:10px; margin-bottom:0px">
                               <div style="margin-top:-10px"> <button type="button" class="close" data-dismiss="alert" aria-hidden="true" style="z-index:10">Ã?</button>
                                <a style="margin:0px; position:fixed; width:29%; overflow:hidden">fendy@mataprima.com</a></div>
                            </div>
							<div class="alert alert-info alert-dismissable" style="display:inline-block; width:40%; height:10px; margin-bottom:0px">
                                <div style="margin-top:-10px"> <button type="button" class="close" data-dismiss="alert" aria-hidden="true" style="z-index:10">Ã?</button>
                                <a style="margin:0px; position:fixed; width:29%; overflow:hidden">davidnatanaelwattimena@mataprima.com</a></div>
                            </div>
		</div>
		</div>
		</form>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Ok</button>
      </div>
    </div>

  </div>
</div>
<div id="usersettings" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header" style="height:82px">
	  <h4 class="modal-title" style="text-align:center">User settings</h4>
	  <ul class="nav nav-tabs navbar-left">
		  <li class="active"><a data-toggle="tab" href="#twittertab" onclick="changeValidate(0)"><i class="fa fa-twitter-square fa-fw"></i>
                            Twitter</a></li>
		  <li><a id="privateChat" name="privateChatName" data-toggle="tab" href="#kaskustab" onclick="changeValidate(1)"><i class="fa fa-group fa-fw"></i>Kaskus</a></li>
		   <li><a data-toggle="tab" href="#facebooktab" onclick="changeValidate(2)"><i class="fa fa-facebook-square fa-fw"></i>
                            Facebook</a></li>
		</ul>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        
      </div>
      <div class="modal-body" style="height:200px; overflow:auto">
	  <div class="tab-content">
		<div id="twittertab" class="tab-pane fade in active">
	  <div class="form-group" >
		<form data-toggle="validator" role="form">
        <div class="form-group" >
		<div id="container-nael1">
		<div style="margin-bottom:5px">
		<label>Consumer Key</label>
		<input id="ck" type="email" value="<% out.print(consumerKey); %>" class="form-control" required placeholder="Consumer Key" style="width:70%; display:inline-block; margin:0px 0px 3px 45px">
		<label>Consumer Secret</label>
		<input id="cs" type="email" value="<% out.print(consumerSecret); %>" class="form-control" required placeholder="Consumer Secret" style="width:70%; display:inline-block; margin:0px 0px 3px 28px"><br>
		<label>Access Token</label>
		<input id="at" type="email" value="<% out.print(oauthToken); %>" class="form-control" required placeholder="Access Token" style="width:70%; display:inline-block; margin:0px 0px 3px 48px"><br>
		<label>Access Token Secret</label>
		<input id="ats" type="email" value="<% out.print(oauthSecret); %>" class="form-control" required placeholder="Access Token Secret" style="width:70%; display:inline-block; margin:0px 0px 3px 1px"><br>
		</div>
			
		
		</div>
		<button id="addtwit" type="button" class="btn btn-info" style="display:none; margin-bottom:3px">Add</button>
		</form>
		</div>
      </div>
	  
	  </div>
	  <div id="facebooktab" class="tab-pane fade">
	  <div class="form-group" >
		<form data-toggle="validator" role="form">
        <div class="form-group" >
		<div id="container-nael1">
		<div style="margin-bottom:5px">
		<label>Consumer Key</label>
		<input id="ck" type="email" value="<% out.print(consumerKey); %>" class="form-control" required placeholder="Consumer Key" style="width:70%; display:inline-block; margin:0px 0px 3px 45px">
		<label>Consumer Secret</label>
		<input id="cs" type="email" value="<% out.print(consumerSecret); %>" class="form-control" required placeholder="Consumer Secret" style="width:70%; display:inline-block; margin:0px 0px 3px 28px"><br>
		<label>Access Token</label>
		<input id="at" type="email" value="<% out.print(oauthToken); %>" class="form-control" required placeholder="Access Token" style="width:70%; display:inline-block; margin:0px 0px 3px 48px"><br>
		<label>Access Token Secret</label>
		<input id="ats" type="email" value="<% out.print(oauthSecret); %>" class="form-control" required placeholder="Access Token Secret" style="width:70%; display:inline-block; margin:0px 0px 3px 1px"><br>
		</div>
			
		
		</div>
		<button id="addtwit" type="button" class="btn btn-info" style="display:none; margin-bottom:3px">Add</button>
		</form>
		</div>
      </div>
	  
	  </div>
	  <div id="kaskustab" class="tab-pane fade">
	  <div class="form-group">
		<label>User Name</label>
		<input id="un"  type="email" value="<% out.print(kaskusUser); %>" class="form-control" required placeholder="User Name" style="width:70%; display:block">
		<label>Password</label>
		<input id="pw" type="password" value="<% out.print(kaskusPass); %>" class="form-control" required placeholder="Password" style="width:70%; display:block">
	  </div>	  
	  </div>
	  </div>
	  </div>
      <div class="modal-footer" style="text-align:left">
        <button type="button" id="validateButton" onclick="validateSettings(0)" class="btn btn-info" data-dismiss="modal" style="bottom:0">Validate</button><span><i id="validateCheck" class="" style="margin-left:3px"></i></span>
        <div style="float:right">
		<button type="button" onclick="setSettings()" class="btn btn-info" data-dismiss="modal" style="bottom:0">Save</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" style="bottom:0">Ok</button>
		</div>
	  </div>
    </div>

  </div>
</div>
		<div class="modal fade in" id="myModal12" tabindex="-1" role="dialog" style="display:none; background-color: rgba(26, 26, 26, 0.8);"  >
         <div class="loader">
			<div class="circle one"></div>
			<div class="circle two"></div>
			<div class="circle three"></div>
		 </div>
        </div>
		 <div class="modal fade" id="inputimage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
<!--                                 <form id="imgform" name="imgform" action="../api/uploadMedia.jsp" method="post"> -->
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                                        </div>
                                        <div class="modal-body">
                                           <input id="imgselect" name="imgselect" type="file">
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                            <button id="imgsubmit" type="button" onclick="uploadMedia()" name="imgsubmit" class="btn btn-primary">Upload</button>
                                        </div>
                                    </div>
<!--                                  </form> -->
                                    <!-- /.modal-content -->
                                </div>
                                <!-- /.modal-dialog -->
                            </div>
        </div>
		  </div>
		  <div id="menu1" class="tab-pane fade" style="position:relative; width:100%; height:550px">
			
					<iframe src="http://dev.summit.com.sg:81/ibmcognos/cgi-bin/cognos.cgi?b_action=icd&pathinfo=%2Fmain&src=%2Fibmcognos%2Fcgi-bin%2Fcognos.cgi%2Ficd%2Ffeeds%2Fcm%2Fid%2Fi922DEB99C560428FA9188D63574B0C76%3Fentry%3D&ui.gateway=%2Fibmcognos%2Fcgi-bin%2Fcognos.cgi&ui.tool=DashboardConsole&ui.action=edit&ui.backURL=%2Fibmcognos%2Fcgi-bin%2Fcognos.cgi%3Fb_action%3Dxts.run%26m%3Dportal%2Fcc.xts%26m_folder%3DiD59F128E684F4B749BD84772BBA57533&ui.errURL=%2Fibmcognos%2Fcgi-bin%2Fcognos.cgi%3Fb_action%3Dxts.run%26m%3Dportal%2Fcc.xts%26m_folder%3DiD59F128E684F4B749BD84772BBA57533&ui.encoding=UTF-8" style="border:none; position:absolute; top:-42px; height:623px" height="550px" width="100%" ></iframe>
				
                <!-- /.col-lg-12 -->
            
				<!-- test -->
			</div>
			<footer>
				<div style="display:block;position:fixed; width:100%; height:25px;bottom:0; background: #45484d; /* Old browsers */
background: -moz-linear-gradient(top,  #45484d 0%, #000000 100%); /* FF3.6+ */
background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#45484d), color-stop(100%,#000000)); /* Chrome,Safari4+ */
background: -webkit-linear-gradient(top,  #45484d 0%,#000000 100%); /* Chrome10+,Safari5.1+ */
background: -o-linear-gradient(top,  #45484d 0%,#000000 100%); /* Opera 11.10+ */
background: -ms-linear-gradient(top,  #45484d 0%,#000000 100%); /* IE10+ */
background: linear-gradient(to bottom,  #45484d 0%,#000000 100%); /* W3C */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#45484d', endColorstr='#000000',GradientType=0 ); /* IE6-9 */ border-top: 2px solid #286CD1;z-index:3 ">
		
			<a href="http://www.mataprima.com" style="margin:5px 5px 0 0; font-size:12px;float:right; color:#e5e5e5" target="_blank">Copyright © 2016 PT. Mata Prima Universal</a>
		</div>
			</footer>
		 </div>
        <!-- tab-content -->
        <!-- /#page-wrapper -->
	
    </div>
    <!-- /#wrapper -->

    <!-- jQuery >
    <script src="../bower_components/jquery/dist/jquery.min.js"></script-->

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="../bower_components/raphael/raphael-min.js"></script>
    <script src="../bower_components/morrisjs/Fmorris.min.js"></script>
    <script src="../js/morris-data.js"></script>

    <!-- Custom Theme JavaScript -->
<!--     <div id="test111"></div> -->
	<div id="sound"></div>
    <script src="../dist/js/sb-admin-2.js"></script>
    <script src="../js/uploadMedia.js"></script>
	<script>getList(1,'<% out.print(username); %>','true');</script>
	<script>checkNotif('<% out.print(username); %>');</script>
	<script>refreshNotif('<% out.print(username); %>');</script>
	
</body>

</html>
