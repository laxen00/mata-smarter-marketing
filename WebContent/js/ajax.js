function refreshNotif(username) {
	setInterval('checkNotif(\''+username+'\')', 30000); // milliseconds
}

function getList(input, username, updateBool) {
	document.getElementById('list-group').innerHTML = '';
	document.getElementById('myModal12').style.display = 'inline';
	
	var page = parseInt(input);
	var searchFromDate = document.getElementById('datepickerfrom').value;	
	var searchToDate = document.getElementById('datepickerto').value;
	var opt;
    
	var selectCategory = document.getElementById('searchCategory');
	var options = selectCategory && selectCategory.options;
	var searchCategory = "";
    var selectedItem = new Array();
	for (var i=0, iLen=options.length; i<iLen; i++) {
	    opt = options[i];

	    if (opt.selected) {
	    	if (opt.text != '') selectedItem.push(opt.text);
	    }
	}
	for (var i=0; i<selectedItem.length; i++) {
		if (i == 0) searchCategory = searchCategory + selectedItem[i];
		else searchCategory = searchCategory + "|" + selectedItem[i];
	}
	
	var selectSource = document.getElementById('searchSource');
	options = selectSource && selectSource.options;
	var searchSource = "";
	selectedItem = [];
	for (var i=0, iLen=options.length; i<iLen; i++) {
	    opt = options[i];

	    if (opt.selected) {
	    	if (opt.text != '') selectedItem.push(opt.text);
	    }
	}
	for (var i=0; i<selectedItem.length; i++) {
		if (i == 0) searchSource = searchSource + selectedItem[i];
		else searchSource = searchSource + "|" + selectedItem[i];
	}
	
	var selectStatus = document.getElementById('searchStatus');
	options = selectStatus && selectStatus.options;
	var searchStatus = "";
	selectedItem = [];
	for (var i=0, iLen=options.length; i<iLen; i++) {
	    opt = options[i];

	    if (opt.selected) {
	    	if (opt.text != '') selectedItem.push(opt.text);
	    }
	}
	for (var i=0; i<selectedItem.length; i++) {
		if (i == 0) searchStatus = searchStatus + selectedItem[i];
		else searchStatus = searchStatus + "|" + selectedItem[i];
	}
	
	var selectSentiment = document.getElementById('searchSentiment');
	options = selectSentiment && selectSentiment.options;
	var searchSentiment = "";
	selectedItem = [];
	for (var i=0, iLen=options.length; i<iLen; i++) {
	    opt = options[i];

	    if (opt.selected) {
	    	if (opt.text != '') selectedItem.push(opt.text);
	    }
	}
	for (var i=0; i<selectedItem.length; i++) {
		if (i == 0) searchSentiment = searchSentiment + selectedItem[i];
		else searchSentiment = searchSentiment + "|" + selectedItem[i];
	}
	
	selectedItem = [];
//	var newArrived = document.getElementById('newArrived').checked;
//	var replied = document.getElementById('replied').checked;
	
	var q = '{'
			+ '"searchFromDate" : "' + searchFromDate + '",'
			+ '"searchToDate" : "' + searchToDate + '",'
			+ '"searchCategory" : "' + searchCategory + '",'
			+ '"searchSource" : "' + searchSource + '",'
			+ '"searchStatus" : "' + searchStatus + '",'
			+ '"searchSentiment" : "' + searchSentiment + '"'
//			+ '"replied" : "' + replied + '"'
			+ '}';
	
	if (updateBool == 'true') {
		if (!searchFromDate == '') {
			document.getElementById('well_from').innerHTML = searchFromDate;
			document.getElementById('well_from').style.display = "inline-block";
		}
		else {
			document.getElementById('well_from').style.display = "none";
		}
		if (!searchToDate == '') {
			document.getElementById('well_to').innerHTML = searchToDate;
			document.getElementById('well_to').style.display = "inline-block";
		}
		else {
			document.getElementById('well_to').style.display = "none";
		}
		if (!searchCategory == '') {
			document.getElementById('well_cat').innerHTML = searchCategory;
			document.getElementById('well_cat').style.display = "inline-block";
		}
		else {
			document.getElementById('well_cat').style.display = "none";
		}
		if (!searchSource == '') {
			document.getElementById('well_src').innerHTML = searchSource;
			document.getElementById('well_src').style.display = "inline-block";
		}
		else {
			document.getElementById('well_src').style.display = "none";
		}
		if (!searchStatus == '') {
			document.getElementById('well_status').innerHTML = searchStatus;
			document.getElementById('well_status').style.display = "inline-block";
		}
		else {
			document.getElementById('well_status').style.display = "none";
		}
		if (!searchSentiment == '') {
			document.getElementById('well_sent').innerHTML = searchSentiment;
			document.getElementById('well_sent').style.display = "inline-block";
		}
		else {
			document.getElementById('well_sent').style.display = "none";
		}
//		if (replied == 'true' || replied == true) {
//			document.getElementById('well_replied').innerHTML = 'Replied';
//			document.getElementById('well_replied').style.display = "inline-block";
//		}
//		else {
//			document.getElementById('well_replied').style.display = "none";
//		}
		updatePageList(page, q, username);
	}
	else {
		loadList(page, q, username);
	}
}

function loadList(page, q, username) {
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function()
	  {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
//		    if (xmlhttp.responseText != 'null') {
			  	var arr = JSON.parse(xmlhttp.responseText);
			  	for (var i=0;i<arr.length; i++) {
			  		var element = '';
			  		var url = '';
			  		if (arr[i].source == 'Twitter' || arr[i].source == 'twitter') {
			  			element = '<i class="fa fa-twitter-square fa-2x"></i>';
			  			url = encodeURIComponent(arr[i].statusurl);
			  		}
			  		else {
			  			element = '<img src="../images/kaskus.png" height="3%" width="3%">';
			  			url = encodeURIComponent(arr[i].posturl);
			  		}
			  		
			  		var entry = '<a href="#" class="list-group-item" data-toggle="modal" data-target="#myModal" onclick="getConversation(\'' + arr[i].id + '\',\''+ url +'\',\''+ arr[i].source +'\',\''+username+'\',\''+arr[i].status+'\')">'
	                    + element + ' ' + arr[i].screenname
	                    + '<span class="pull-right text-muted small"><div class="well well-sm" style="display:inline-flex">'+ arr[i].status +'</div><em> '+ arr[i].postingdatestr +'</em>'
	                    + '</span>'
						+ '<p>'
	                    + arr[i].text_content
	                    + '    </p>'
	                    + '</a>';
			  		
				  	document.getElementById('list-group').innerHTML = document.getElementById('list-group').innerHTML + entry;
			  	}
//		    }
			document.getElementById('refreshList').setAttribute('onclick',"getList("+page+",'"+username+"','true')");
		  	document.getElementById('myModal12').style.display = 'none';
	    }
	  }
	xmlhttp.open("GET","../api/getPage.jsp?page="+page+"&q="+encodeURIComponent(q),true);
	xmlhttp.send();
}

function updatePageList(page, q, username) {

	document.getElementById('list-group').innerHTML = '';
	document.getElementById('myModal12').style.display = 'inline';
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function()
	  {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200) {
		  getStatusTotal();
		  var totalPage = parseInt(xmlhttp.responseText);
		  console.log = ("totalPage: "+ totalPage);
//		  document.getElementById('page-selection').innerHTML = '<script>'
//									+ '$("#page-selection").bootpag({'
//									+ '	total: ' + totalPage + ','
//									+ '	maxVisible: 10'
//									+ '}).on("page", function(event, /* page number here */ num){'
//									+ '	getList(num, ' + username + ',"false");'
//									+ '});'
//									+ '</script>';
		  $('#page-selection').bootpag({
				total: totalPage,
				maxVisible: 10
			}).on("page", function(event, /* page number here */ num){
				getList(num, username,'false');
//				 $("#content").html("Insert content"); // some ajax content loading...
			});
		  loadList(page, q, username);
	    }

	  }
	xmlhttp.open("GET","../api/getTotalPage.jsp?q="+encodeURIComponent(q),true);
	xmlhttp.send();
}

function getConversation(id, url, type, username, status) {
	document.getElementById('convRefresh').setAttribute('onclick','getConversation("' + id + '","' + url + '","' + type + '","' + username + '","'+status+'")');
	document.getElementById('saveDraft').setAttribute('onclick','saveDraft("'+id+'","'+type+'")');
	document.getElementById('mediaPath').innerHTML = '';
	document.getElementById('mediaName').innerHTML = '';	
		for (var x = 0; x <= 6; x++) {
  			document.getElementById('status_'+x+'_action').setAttribute("onclick","changeStatus('"+x+"','"+id+"','"+url+"','"+type+"','"+username+"')");
  			var statusList = document.getElementById('status_'+x+'_text').innerHTML.trim();
  			if (statusList == status) {
//  				document.getElementById('status_'+x+'_text').className = "fa fa-share fa-fw";
  			}
		}
		if (type == 'Twitter' || type == 'twitter') {
			document.getElementById('privateChat').innerHTML = 'Direct Message';
//			document.getElementById('btn-input').style.display = 'none';
//			document.getElementById('btn-input1').style.display = 'none';
			document.getElementById('inputw').style.display = 'none';
			document.getElementById('btn-input2').style.display = 'inline';
			document.getElementById('imageinput').style.display = 'inline';
		}
		else {
			document.getElementById('privateChat').innerHTML = 'Private Message';
//			document.getElementById('btn-input').style.display = 'inline';
//			document.getElementById('btn-input1').style.display = 'inline';
			document.getElementById('inputw').style.display = 'inline';
			document.getElementById('btn-input2').style.display = 'none';
			document.getElementById('imageinput').style.display = 'none';
		}
	document.getElementById('conversation').innerHTML = '';
//	document.getElementById('sendButton').setAttribute('onclick','sendReply()');
//	document.getElementById('convButtons').innerHTML = '<span class="input-group-btn">'
//        	+ '<button type="button" onclick="sendReply(\'' + id + '\',\''+ encodeURIComponent(url) +'\',\''+ type +'\',\''+username+'\')" class="btn btn-primary btn-sm" id="btn-chat">'
//    		+ 'Send'
//    		+ '</button>';
	document.getElementById('myModal12').style.display = 'inline';
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function()
	  {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  	var arr = JSON.parse(xmlhttp.responseText);
		  	document.getElementById('myModal12').style.display = 'none';
	  		if (type == 'Twitter' || type == 'twitter') element = '<p><i class="fa fa-twitter-square fa-2x"></i> <b>Twitter</b></p>';
	  		else element = '<p><img src="../images/kaskus.png" height="3%" width="3%"> <b>Kaskus</b></p>';
		  	
	  		var start = element
            + '<ul class="chat" style="list-style-type:none;">';
	  		var end = '</ul>';
//			+ '<div class="modal-footer">'
//			+				'<fieldset>'
//			+				'<div class="form-group">'
//			+				'<div class="col-lg-12" style="padding:0px">'
//            +                '<div class="input-group" style="width:100%">'
//            +                '    <input id="btn-input1" type="text" class="form-control input-sm" placeholder="Type your message here..." oninput="checkInput()" />'
//            +                ''
//			+				'</div>'
//			+				'</div>'
//			+				''
//			+				''	
//			+				'<div class="col-lg-12" style="padding:0px; margin-bottom:-15px; margin-top:5px">'
//            +              '<div class="form-group" id="convButtons">'
//			+				'	 <span class="input-group-btn">'
//            +               '        <button id="sendButton" type="button" class="btn btn-primary btn-sm" id="btn-chat" disabled>'
//            +                '            Send'
//            +                '        </button>' 
//			+			'			<button type="button" class="btn btn-sm" data-dismiss="modal">Close</button>'
//            +            '        </span>'
//            +            '   </div>'
//			+			'	</div>'
//			+			''
//			+			'	</div>'
//			+			''	
//			+			'	</fieldset>'
//            +           '</div>';

	  		document.getElementById('conversation').innerHTML = document.getElementById('conversation').innerHTML + start;
	  		
		  	for (var i=0;i<arr.length; i++) {
		  		var element = '';
		  		var liCLass = '';
		  		var spanClass = '';
		  		var margin = '';
		  		if (i % 2 == 0) {
        			liClass = "left clearfix";
        			spanClass = "chat-img pull-left";
        			margin = 'style="margin-left: 60px"';
        		}
        		else {
        			
        			liClass = "right clearfix";
        			spanClass = "chat-img pull-right";
        			margin = 'style="text-align: right;margin-right: 60px"';
        		}
		  		
		  		var start = '<li class="'+ liClass +'" style="list-style-type:none;">'
                + '<span class="'+ spanClass +'">'
                + '<img src="'+ arr[i].profileImageUrl +'" alt="User Avatar" class="img-circle" height="50px" width="50px"/>'
                + '</span>'
                + '<div class="chat-body clearfix" ' + margin + '>'
                + '<div class="header">'
                + '<strong class="primary-font">' + arr[i].screenname +'</strong>'
                + '</div>'
                + '<p>'
                + arr[i].text
                + '</p>'
				+ '<p>';
		  		
		  		var end = '</p>'
                + '</div>'
                + '</li>';
		  		
		  		if (arr[i].isRetweeted == "true" || arr[i].isRetweeted == true) {
		  			retweetBtnClass = "btn btn-success btn-xs";
		  		}
		  		else {
		  			retweetBtnClass = "btn btn-default btn-xs";
		  		}
		  		var retweetButton = '<button onclick="sendRetweet(\'' + encodeURIComponent(arr[i].postUrl) + '\',\'' + id + '\',\'' + encodeURIComponent(url) + '\',\'' + type + '\',\'' + username + '\',\''+status+'\')" type="button" class="' + retweetBtnClass + '"><i class="fa fa-retweet"></i></button>';
		  		
		  		if (type == 'Twitter' || type == 'twitter') var entry = start + retweetButton + end;
		  		else entry = start + end;
		  		
		  		document.getElementById('conversation').innerHTML = document.getElementById('conversation').innerHTML + entry;
//		  		document.getElementById('refreshList').click();
		  	}
		  	document.getElementById('conversation').innerHTML = document.getElementById('conversation').innerHTML + end;
		  	for (var j=arr.length-1;j>=0;j--) {
		  		if (arr[j].notThisUser == "true" || arr[j].notThisUser == "true") {
		  			document.getElementById('sendButton').setAttribute('onclick','sendReply(\'' + id + '\',\''+ encodeURIComponent(arr[j].postUrl) +'\',\''+ type +'\',\''+username+'\',\''+status+'\')');
		  			break;
		  		}
		  	}
//		  		document.getElementById('conversation').innerHTML = document.getElementById('conversation').innerHTML + end;
//		  		document.getElementById('sendDM').setAttribute('onclick','sendDM(\'' + id + '\',\''+ encodeURIComponent(arr[j].postUrl) +'\',\''+ type +'\',\''+username+'\',\''+status+'\')');
		  		getDM(id, url, type, username, status);
		  		getDraft(id, type);
	    }
	  }
	xmlhttp.open("GET","../api/getConversation.jsp?url="+encodeURIComponent(url)+"&type="+type+"&id="+id,true);
	xmlhttp.send();
}

function getDM(id, url, type, username, status) {
	document.getElementById('dm1').innerHTML = '';
//	document.getElementById('sendDM').setAttribute('onclick','sendDM(\'' + id + '\',\''+ encodeURIComponent(arr[j].postUrl) +'\',\''+ type +'\',\''+username+'\',\''+status+'\')');
//	document.getElementById('convButtons').innerHTML = '<span class="input-group-btn">'
//        	+ '<button type="button" onclick="sendReply(\'' + id + '\',\''+ encodeURIComponent(url) +'\',\''+ type +'\',\''+username+'\')" class="btn btn-primary btn-sm" id="btn-chat">'
//    		+ 'Send'
//    		+ '</button>';
	document.getElementById('myModal12').style.display = 'inline';
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function()
	  {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  	var arr = JSON.parse(xmlhttp.responseText);
		  	document.getElementById('myModal12').style.display = 'none';

	  		if (type == 'Twitter' || type == 'twitter') element = '<p><i class="fa fa-twitter-square fa-2x"></i> <b>Twitter</b></p>';
	  		else element = '<p><img src="../images/kaskus.png" height="3%" width="3%"> <b>Kaskus</b></p>';
		  	
	  		var start = element
            + '<ul class="chat" style="list-style-type:none;">';
	  		var end = '</ul>';
//			+ '<div class="modal-footer">'
//			+				'<fieldset>'
//			+				'<div class="form-group">'
//			+				'<div class="col-lg-12" style="padding:0px">'
//            +                '<div class="input-group" style="width:100%">'
//            +                '    <input id="btn-input1" type="text" class="form-control input-sm" placeholder="Type your message here..." oninput="checkInput()" />'
//            +                ''
//			+				'</div>'
//			+				'</div>'
//			+				''
//			+				''	
//			+				'<div class="col-lg-12" style="padding:0px; margin-bottom:-15px; margin-top:5px">'
//            +              '<div class="form-group" id="convButtons">'
//			+				'	 <span class="input-group-btn">'
//            +               '        <button id="sendDM" type="button" class="btn btn-primary btn-sm" id="btn-chat" disabled>'
//            +                '            Send'
//            +                '        </button>' 
//			+			'			<button type="button" class="btn btn-sm" data-dismiss="modal">Close</button>'
//            +            '        </span>'
//            +            '   </div>'
//			+			'	</div>'
//			+			''
//			+			'	</div>'
//			+			''	
//			+			'	</fieldset>'
//            +           '</div>';

	  		document.getElementById('dm1').innerHTML = document.getElementById('dm1').innerHTML + start;
	  		
		  	for (var i=0;i<arr.length; i++) {
		  		var element = '';
		  		var liCLass = '';
		  		var spanClass = '';
		  		var margin = '';
		  		if (i % 2 == 0) {
        			liClass = "left clearfix";
        			spanClass = "chat-img pull-left";
        			margin = 'style="margin-left: 60px"';
        		}
        		else {
        			
        			liClass = "right clearfix";
        			spanClass = "chat-img pull-right";
        			margin = 'style="text-align: right;margin-right: 60px"';
        		}
		  		
		  		var start = '<div style="text-align:center"><p><i>This user is not following you</i></p></div>' 
				+'<li class="'+ liClass +'" style="list-style-type:none;">'
                + '<span class="'+ spanClass +'">'
                + '<img src="'+ arr[i].profileImageUrl +'" alt="User Avatar" class="img-circle" height="50px" width="50px"/>'
                + '</span>'
                + '<div class="chat-body clearfix" ' + margin + '>'
                + '<div class="header">'
                + '<strong class="primary-font">' + arr[i].screenname +'</strong>'
                + '</div>'
                + '<p>'
                + arr[i].text
                + '</p>'
				+ '<p>';
		  		
		  		var end = '</p>'
                + '</div>'
                + '</li>';
		  		
//		  		if (arr[i].isRetweeted == "true" || arr[i].isRetweeted == true) {
//		  			retweetBtnClass = "btn btn-success btn-xs";
//		  		}
//		  		else {
//		  			retweetBtnClass = "btn btn-default btn-xs";
//		  		}
//		  		var retweetButton = '<button onclick="sendRetweet(\'' + encodeURIComponent(arr[i].postUrl) + '\',\'' + id + '\',\'' + encodeURIComponent(url) + '\',\'' + type + '\',\'' + username + '\',\''+status+'\')" type="button" class="' + retweetBtnClass + '"><i class="fa fa-retweet"></i></button>';
//		  		
//		  		if (type == 'Twitter' || type == 'twitter') var entry = start + retweetButton + end;
//		  		else entry = start + end;
		  		entry = start + end;
		  		
		  		document.getElementById('dm1').innerHTML = document.getElementById('dm1').innerHTML + entry;
		  	}
		  	
//		  	for (var j=arr.length-1;j>=0;j--) {
//		  		if (arr[j].notThisUser == "true" || arr[j].notThisUser == "true") {
//		  			document.getElementById('sendButton').setAttribute('onclick','sendReply(\'' + id + '\',\''+ encodeURIComponent(arr[j].postUrl) +'\',\''+ type +'\',\''+username+'\',\''+status+'\')');
//		  			break;
//		  		}
//		  	}
		  		document.getElementById('dm1').innerHTML = document.getElementById('dm1').innerHTML + end;
		  		if (type == 'Twitter' || type == 'twitter') checkFollowing(url);
	    }
	  }
	xmlhttp.open("GET","../api/getDM.jsp?url="+encodeURIComponent(url)+"&type="+type+"&id="+id,true);
	xmlhttp.send();
}

function sendReply(id, url, type, username, status) {
	document.getElementById('conversation').innerHTML = "";
	document.getElementById('myModal12').style.display = 'inline';
	var mediaPath = document.getElementById('mediaPath').value;
	var text = '';
	if (type == 'Twitter' || type == 'twitter') text = document.getElementById("btn-input2").value;
	else text = document.getElementById("btn-input1").innerHTML;
//	console.log(text);
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  	document.getElementById('conversation').innerHTML = '';
		    url = decodeURIComponent(url);
		  	setTimeout(getConversation2(id, url, type, username, status), 1000);
//		  	document.getElementById('myModal12').style.display = 'none';
			
	    }
	}
//	xmlhttp.open("GET","../api/sendReply.jsp?url="+url+"&type="+type+"&username="+username+"&id="+id+"&text="+text,true);
//	xmlhttp.send();
	xmlhttp.open("POST","../api/sendReply.jsp",true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("url="+url+"&type="+type+"&username="+username+"&id="+id+"&text="+text+"&mediaPath="+mediaPath);
}

function sendDM(id, url, type, username, status) {
	document.getElementById('conversation').innerHTML = "";
	document.getElementById('myModal12').style.display = 'inline';
	var text = '';
	if (type == 'Twitter' || type == 'twitter') text = document.getElementById("btn-input2").value;
	else text = document.getElementById("btn-input1").innerHTML;
//	console.log(text);
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {	
		  	if (xmlhttp.responseText == false || xmlhttp.responseText == 'false') {
		  		alert("Error in sending DM; is the user currently following you?");
		  	}
		  	document.getElementById('conversation').innerHTML = '';
		    url = decodeURIComponent(url);
		  	setTimeout(getConversation(id, url, type, username, status), 1000);
//		  	document.getElementById('myModal12').style.display = 'none';
			
	    }
	}
//	xmlhttp.open("GET","../api/sendReply.jsp?url="+url+"&type="+type+"&username="+username+"&id="+id+"&text="+text,true);
//	xmlhttp.send();
	xmlhttp.open("POST","../api/sendDM.jsp",true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("url="+url+"&type="+type+"&username="+username+"&id="+id+"&text="+text);
}

function sendRetweet(postUrl, id, url, type, username, status) {
	document.getElementById('myModal12').style.display = 'inline';
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  document.getElementById('conversation').innerHTML = '';
		  url = decodeURIComponent(url);
		  setTimeout(getConversation(id, url, type, username, status), 1000);
	    }
	}
	xmlhttp.open("POST","../api/sendRetweet.jsp",true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("postUrl="+postUrl);
}

function changeStatus(num, id, url, type, username) {
	document.getElementById('myModal12').style.display = 'inline';
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  document.getElementById('conversation').innerHTML = '';
		  url = decodeURIComponent(url);
		  setTimeout(getConversation2(id, url, type, username, status), 1000);
	    }
	}
//	xmlhttp.open("POST","../api/sendRetweet.jsp",true);
//	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//	xmlhttp.send("postUrl="+postUrl);
	xmlhttp.open("GET","../api/changeStatus.jsp?num="+num+"&id="+id+"&username="+username,true);
	xmlhttp.send();
}

function getConversation2(id, url, type, username, status) {
	document.getElementById('convRefresh').setAttribute('onclick','getConversation("' + id + '","' + url + '","' + type + '","' + username + '","'+status+'")');
	document.getElementById('saveDraft').setAttribute('onclick','saveDraft("'+id+'","'+type+'")');
	document.getElementById('mediaPath').innerHTML = '';
	document.getElementById('mediaName').innerHTML = '';	
	for (var x = 0; x <= 6; x++) {
  			document.getElementById('status_'+x+'_action').setAttribute("onclick","changeStatus('"+x+"','"+id+"','"+url+"','"+type+"','"+username+"')");
  			var statusList = document.getElementById('status_'+x+'_text').innerHTML.trim();
  			if (statusList == status) {
//  				document.getElementById('status_'+x+'_text').className = "fa fa-share fa-fw";
  			}
		}
	if (type == 'Twitter' || type == 'twitter') {
		document.getElementById('privateChat').innerHTML = 'Direct Message';
//		document.getElementById('btn-input').style.display = 'none';
//		document.getElementById('btn-input1').style.display = 'none';
		document.getElementById('inputw').style.display = 'none';
		document.getElementById('btn-input2').style.display = 'inline';
		document.getElementById('imageinput').style.display = 'inline';
	}
	else {
		document.getElementById('privateChat').innerHTML = 'Private Message';
//		document.getElementById('btn-input').style.display = 'inline';
//		document.getElementById('btn-input1').style.display = 'inline';
		document.getElementById('inputw').style.display = 'inline';
		document.getElementById('btn-input2').style.display = 'none';
		document.getElementById('imageinput').style.display = 'none';
	}
	document.getElementById('conversation').innerHTML = '';
	document.getElementById('sendButton').setAttribute('onclick','sendReply()');
//	document.getElementById('convButtons').innerHTML = '<span class="input-group-btn">'
//        	+ '<button type="button" onclick="sendReply(\'' + id + '\',\''+ encodeURIComponent(url) +'\',\''+ type +'\',\''+username+'\')" class="btn btn-primary btn-sm" id="btn-chat">'
//    		+ 'Send'
//    		+ '</button>';
	document.getElementById('myModal12').style.display = 'inline';
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function()
	  {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  	var arr = JSON.parse(xmlhttp.responseText);
//		  	document.getElementById('myModal12').style.display = 'none';
	  		if (type == 'Twitter' || type == 'twitter') element = '<p><i class="fa fa-twitter-square fa-2x"></i> <b>Twitter</b></p>';
	  		else element = '<p><img src="../images/kaskus.png" height="3%" width="3%"> <b>Kaskus</b></p>';
		  	
	  		var start = element
            + '<ul class="chat" style="list-style-type:none;">';
	  		var end = '</ul>'

	  		document.getElementById('conversation').innerHTML = document.getElementById('conversation').innerHTML + start;
	  		
		  	for (var i=0;i<arr.length; i++) {
		  		var element = '';
		  		var liCLass = '';
		  		var spanClass = '';
		  		var margin = '';
		  		if (i % 2 == 0) {
        			liClass = "left clearfix";
        			spanClass = "chat-img pull-left";
        			margin = 'style="margin-left: 60px"';
        		}
        		else {
        			
        			liClass = "right clearfix";
        			spanClass = "chat-img pull-right";
        			margin = 'style="text-align: right;margin-right: 60px"';
        		}
		  		
		  		var start = '<li class="'+ liClass +'" style="list-style-type:none;">'
                + '<span class="'+ spanClass +'">'
                + '<img src="'+ arr[i].profileImageUrl +'" alt="User Avatar" class="img-circle" height="50px" width="50px"/>'
                + '</span>'
                + '<div class="chat-body clearfix" ' + margin + '>'
                + '<div class="header">'
                + '<strong class="primary-font">' + arr[i].screenname +'</strong>'
                + '</div>'
                + '<p>'
                + arr[i].text
                + '</p>'
				+ '<p>';
		  		
		  		var end = '</p>'
                + '</div>'
                + '</li>';
		  		
		  		if (arr[i].isRetweeted == "true" || arr[i].isRetweeted == true) {
		  			retweetBtnClass = "btn btn-success btn-xs";
		  		}
		  		else {
		  			retweetBtnClass = "btn btn-default btn-xs";
		  		}
		  		var retweetButton = '<button onclick="sendRetweet(\'' + encodeURIComponent(arr[i].postUrl) + '\',\'' + id + '\',\'' + encodeURIComponent(url) + '\',\'' + type + '\',\'' + username + '\',\''+status+'\')" type="button" class="' + retweetBtnClass + '"><i class="fa fa-retweet"></i></button>';
		  		
		  		if (type == 'Twitter' || type == 'twitter') var entry = start + retweetButton + end;
		  		else entry = start + end;
		  		
		  		document.getElementById('conversation').innerHTML = document.getElementById('conversation').innerHTML + entry;
		  		document.getElementById('refreshList').click();
		  	}
		  	
		  	for (var j=arr.length-1;j>=0;j--) {
		  		if (arr[j].notThisUser == "true" || arr[j].notThisUser == "true") {
		  			document.getElementById('sendButton').setAttribute('onclick','sendReply(\'' + id + '\',\''+ encodeURIComponent(arr[j].postUrl) +'\',\''+ type +'\',\''+username+'\',\''+status+'\')');
		  			break;
		  		}
		  	}
		  		document.getElementById('conversation').innerHTML = document.getElementById('conversation').innerHTML + end;
//		  		getPM(id, url, type, username, status);
		  		getDraft(id, type);
		  		document.getElementById('refreshList').click();
	    }
	  }
	xmlhttp.open("GET","../api/getConversation.jsp?url="+encodeURIComponent(url)+"&type="+type+"&id="+id,true);
	xmlhttp.send();
}

function changeSend(x) {
	var action = document.getElementById("sendButton").getAttribute("onclick");
	if (x == 0 || x == '0') {
		action = action.replace("sendDM","sendReply");
		document.getElementById("sendButton").setAttribute("onclick",action);
	}
	else {
		action = action.replace("sendReply","sendDM");
		document.getElementById("sendButton").setAttribute("onclick",action);
	}
}

function checkFollowing(url) {
//	document.getElementById('myModal12').style.display = 'inline';
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  if (xmlhttp.responseText == 0 || xmlhttp.responseText == '0') {
//			  document.getElementById("btn-input1").value = xmlhttp.responseText;
			  document.getElementById('dm1').innerHTML = '';
			  var element = '<p><i class="fa fa-twitter-square fa-2x"></i> <b>Twitter</b></p>';
			  	
		  		var start = element
	          + '<ul class="chat" style="list-style-type:none;">';
		  		var end = '</ul>';
		  		
		  		document.getElementById('dm1').innerHTML = document.getElementById('dm1').innerHTML + start
				+'<div style="text-align:center"><p><i>This user is not following you</i></p></div>'
				+ end;
		  }
	    }
	}
//	xmlhttp.open("POST","../api/sendRetweet.jsp",true);
//	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//	xmlhttp.send("postUrl="+postUrl);
	xmlhttp.open("GET","../api/checkFollowing.jsp?postUrl="+url,true);
	xmlhttp.send();
}

function setSettings() {
	document.getElementById('myModal12').style.display = 'inline';
	var kaskusUser = document.getElementById('un').value;
	var kaskusPass = document.getElementById('pw').value;
	var consumerKey = document.getElementById('ck').value;
	var consumerSecret = document.getElementById('cs').value;
	var oauthToken = document.getElementById('at').value;
	var oauthSecret = document.getElementById('ats').value;
	var q = '{'
		+ '"kaskusUser" : "' + kaskusUser + '",'
		+ '"kaskusPass" : "' + kaskusPass + '",'
		+ '"consumerKey" : "' + consumerKey + '",'
		+ '"consumerSecret" : "' + consumerSecret + '",'
		+ '"oauthToken" : "' + oauthToken + '",'
		+ '"oauthSecret" : "' + oauthSecret + '"'
		+ '}';
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  document.getElementById('myModal12').style.display = 'none';
	    }
	}
	xmlhttp.open("POST","../api/setSettings.jsp",true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("q="+q);
//	xmlhttp.open("GET","../api/changeStatus.jsp?num="+num+"&id="+id+"&username="+username,true);
//	xmlhttp.send();
}

function getDraft(id, type) {
//	document.getElementById('myModal12').style.display = 'inline';
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  if (type == 'Twitter' || type == 'twitter') document.getElementById("btn-input2").value = xmlhttp.responseText;
		  else document.getElementById("btn-input1").innerHTML = xmlhttp.responseText;
	    }
	}
//	xmlhttp.open("POST","../api/sendRetweet.jsp",true);
//	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//	xmlhttp.send("postUrl="+postUrl);
	xmlhttp.open("GET","../api/getDraft.jsp?id="+id,true);
	xmlhttp.send();
}

function saveDraft(id, type) {
	document.getElementById('myModal12').style.display = 'inline';
	var message = '';
	if (type == 'Twitter' || type == 'twitter') message = document.getElementById('btn-input1').innerHTML;
	else message = document.getElementById('btn-input2').value;
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		 document.getElementById('myModal12').style.display = 'none';
	    }
	}
	xmlhttp.open("POST","../api/saveDraft.jsp",true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("id="+id+"&message="+message);
//	xmlhttp.open("GET","../api/checkFollowing.jsp?postUrl="+url,true);
//	xmlhttp.send();
}

function getStatusTotal() {
//	document.getElementById('myModal12').style.display = 'inline';
	var message = document.getElementById('btn-input1').innerHTML;
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  var obj = JSON.parse(xmlhttp.responseText);
		  document.getElementById('tNRY').innerHTML = 'Not Responded Yet(' + obj.tNRY + ')';
		  document.getElementById('tNR').innerHTML = 'No Need To Reply(' +obj.tNR + ')';
		  document.getElementById('tO').innerHTML = 'Ongoing(' + obj.tO + ')';
		  document.getElementById('tCC').innerHTML = 'Closed Communication(' + obj.tCC + ')';
		  document.getElementById('tCCP').innerHTML = 'Closed Communication By Phone(' + obj.tCCP + ')';
		  document.getElementById('tSD').innerHTML = 'Shared To Dealer(' + obj.tSD + ')';
		  document.getElementById('tCDB').innerHTML = 'Closed Dealer Communication (Buy)(' + obj.tCDB + ')';
		  document.getElementById('tCDNB').innerHTML = 'Closed Dealer Communication (Not Buy)(' + obj.tCDNB + ')';
		  document.getElementById('tSAD').innerHTML = 'Saved As Draft(' + obj.tSAD + ')';
//		 document.getElementById('myModal12').style.display = 'none';
	    }
	}
//	xmlhttp.open("POST","../api/saveDraft.jsp",true);
//	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//	xmlhttp.send("id="+id+"&message="+message);
	xmlhttp.open("GET","../api/getStatusTotal.jsp?",true);
	xmlhttp.send();
}

function checkNotif(username) {
//	document.getElementById('myModal12').style.display = 'inline';
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
//		  document.getElementById('test111').innerHTML = xmlhttp.responseText;
		  var arr = JSON.parse(xmlhttp.responseText);
		  if (arr.length == 0) {
			  document.getElementById('notifCount').innerHTML = arr.length;
			  document.getElementById('notifs').innerHTML = 'No New Notifications';
		  }
		  if (arr.length > 0) {
			  var currentNotif = parseInt(document.getElementById('notifCount').innerHTML);
			  if (currentNotif != arr.length) playSound('dings');
			  document.getElementById('notifCount').innerHTML = arr.length;
			  document.getElementById('notifs').innerHTML = '';
			  for (var i=0; i<arr.length;i++) {
			  
			  var element = '';
			  var url = '';
			  if (arr[i].source == 'Twitter' || arr[i].source == 'twitter') {
				  element = '<i class="fa fa-twitter-square fa-2x"></i> ' + arr[i].screenname;
				  url = encodeURIComponent(arr[i].statusurl);
			  }
			  else {
				  element = '<img src="../images/kaskus.png" height="9%" width="9%">' + arr[i].screenname;
				  url = encodeURIComponent(arr[i].posturl);
			  }
			  
			  var start = '<li>';
			  var divider = '<li class="divider"></li>';
			  var end = '</li>';
			  
			  var entry = '<a href="#" data-toggle="modal" data-target="#myModal" onclick="getConversation(\'' + arr[i].id + '\',\''+ url +'\',\''+ arr[i].source +'\',\''+username+'\',\''+arr[i].status+'\')">' 
		            + '    <div>'
		            + element
		            + '        <span class="pull-right text-muted small">' + arr[i].postingdatestr + '</span>'
		            + '    </div>'
		            + '    <div style="display:block;height:40px;overflow:hidden">' + arr[i].text_content + '</div>'
					+ '	   '
		            + '</a>';
			  
			  document.getElementById('notifs').innerHTML = document.getElementById('notifs').innerHTML + start + entry + end;
			  if (i != (arr.length-1)) document.getElementById('notifs').innerHTML = document.getElementById('notifs').innerHTML + divider;
		  }
	    }
		  
//		 document.getElementById('myModal12').style.display = 'none';
	    }
	}
//	xmlhttp.open("POST","../api/saveDraft.jsp",true);
//	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//	xmlhttp.send("id="+id+"&message="+message);
	xmlhttp.open("GET","../api/checkNotif.jsp?",true);
	xmlhttp.send();
}

function changeValidate(x) {
	document.getElementById("validateButton").setAttribute("onclick","validateSettings("+x+")");
	document.getElementById("validateCheck").className = "";
}

function validateSettings(x) {
	var obj = "";
	document.getElementById('myModal12').style.display = 'inline';
	if (x == 0) {
		var ck = document.getElementById('ck').value;
		var cs = document.getElementById('cs').value;
		var at = document.getElementById('at').value;
		var ats = document.getElementById('ats').value;
		obj = 	'{' +
		'"consumerKey":"' + ck + '",' +
		'"consumerSecret":"' + cs + '",' +
		'"oauthToken":"' + at + '",' +
		'"oauthSecret":"' + ats +  '"' +
		'}';
	}
	else {
		var un = document.getElementById('un').value;
		var pw = document.getElementById('pw').value;
		obj = 	'{' +
		'"kaskusUser":"' + un + '",' +
		'"kaskusPass":"' + pw + '"' +
		'}';
	}
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function() {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  document.getElementById('myModal12').style.display = 'none';
		  var validate = parseInt(xmlhttp.responseText);
		  if (validate == 1) {
			  document.getElementById("validateCheck").className = "fa fa-check fw";
		  }
		  else {
			  document.getElementById("validateCheck").className = "fa fa-times fw";
		  }
	    }
	}
	xmlhttp.open("POST","../api/validateSettings.jsp",true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("obj="+obj+"&type="+x);
}