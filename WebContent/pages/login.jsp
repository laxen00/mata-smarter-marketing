<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Mata Analytics Smarter Marketing</title>

    <!-- Bootstrap Core CSS -->
    <link rel="icon" type="image/png" href="../images/ticon.png" />
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="../bower_components/bootstrap/dist/css/anim.css" rel="stylesheet">
    <link href="../bower_components/bootstrap/dist/css/spinner2.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<script>
		function testaja() {
			var x = document.getElementsByClassName("fade-in");
			x[0].style.display = "inline";
			x[1].style.display = "inline";
			x[2].style.display = "inline";
		}
	</script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.2/modernizr.js"></script>
	<script>
	//paste this code under head tag or in a seperate js file.
		// Wait for window load
		$(window).load(function() {
			// Animate loader off screen
			$(".se-pre-con").fadeOut("slow");;
		});
	</script>
</head>

<body onload="testaja()" style="background: url(../images/silver-light-blue.jpg) no-repeat center center fixed; 
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;">
	<div class="se-pre-con"></div>
    <div class="container">
        <div class="row">
		<div class="col-lg-12 fade-in one" style="background: rgba(100,100,100,0.15); box-shadow: 2px 2px 10px #333333; margin-top:180px; padding:50px 0 50px 0">
            <div class="row">
			<div class="col-md-4 col-md-offset-1 fade-in two">
			<img src="../images/mata_analytics_816w.png" width="350px" style="margin:30px 30px 0 -35px">
			</div>
			<div class="col-md-4 col-md-offset-2 fade-in two">
				<!--img src="../images/toyotalogo.png" width="360px"-->
               
                  
                    <div class="panel-body">
                        <form role="form" action="../api/processLogin.jsp" method="post" >
                            <fieldset>
                            <%
                            	String message = "";
                            	try {
                            		message = session.getAttribute("loginMessage").toString();
                            	}
                            	catch (Exception e) {
                            		message = "";
                            	}
                            %>
                            <% if (!message.equalsIgnoreCase("")) { %><p style="color:#FF0000"><% out.print(message); %></p> <% } %>
                            <% session.removeAttribute("loginMessage"); %>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
								<div class="row">
								<div class="col-md-4"><button type="submit" class="btn btn-default" value="Login">Login</button></div>
                                
								</div>
                                <!-- Change this to a button or input when using this as a form -->
                                
                            </fieldset>
                        </form>
                    </div>
                
            </div>
			
			</div>
			</div>
			
        </div>
    </div>

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

</body>

</html>
