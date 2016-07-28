var form = document.getElementById('imgform');
var fileSelect = document.getElementById('imgselect');
var uploadButton = document.getElementById('imgsubmit');

function uploadMedia() {
	document.getElementById('myModal12').style.display = 'inline';
	var files = fileSelect.files;
	var formData = new FormData();
	for (var i = 0; i < files.length; i++) {
		var file = files[i];
		formData.append('toupload', file, file.name);
	}
	var xhr = new XMLHttpRequest();
	
	// Open the connection.
	xhr.open('POST', '../api/uploadMedia.jsp', true);
	
	// Set up a handler for when the request finishes.
	xhr.onload = function () {
		if (xhr.status === 200) {
			// File(s) uploaded.
			var a = xhr.responseText.split("\\");
			document.getElementById('mediaPath').value = xhr.responseText;
			document.getElementById('mediaName').innerHTML = a[a.length-1];
			document.getElementById('myModal12').style.display = 'none';
		} else {
			alert('An error occurred while uploading, please check for errors and try again.');
			document.getElementById('myModal12').style.display = 'none';
		}
	};
	
	// Send the Data.
	xhr.send(formData);
}

//uploadButton.onclick = function(event) {
//	event.preventDefault();
//
//	// Update button text.
//	// uploadButton.innerHTML = 'Uploading...';
//
//	// The rest of the code will go here...
//	var files = fileSelect.files;
//	var formData = new FormData();
//	for (var i = 0; i < files.length; i++) {
//		var file = files[i];
//		formData.append('toupload', file, file.name);
//	}
//	var xhr = new XMLHttpRequest();
//	
//	// Open the connection.
//	xhr.open('POST', '../api/uploadMedia.jsp', true);
//	
//	// Set up a handler for when the request finishes.
//	xhr.onload = function () {
//		if (xhr.status === 200) {
//			// File(s) uploaded.
//			var a = xhr.responseText.split("\\");
//			document.getElementById('mediaPath').value = xhr.responseText;
//			document.getElementById('mediaName').innerHTML = a[a.length-1];
//		} else {
//			alert('An error occurred while uploading, please check for errors and try again.');
//		}
//	};
//	
//	// Send the Data.
//	xhr.send(formData);
//}

//form.onsubmit = function(event) {
//	event.preventDefault();
//
//	// Update button text.
//	// uploadButton.innerHTML = 'Uploading...';
//
//	// The rest of the code will go here...
//	var files = fileSelect.files;
//	var formData = new FormData();
//	for (var i = 0; i < files.length; i++) {
//		var file = files[i];
//		formData.append('toupload', file, file.name);
//	}
//	var xhr = new XMLHttpRequest();
//	
//	// Open the connection.
//	xhr.open('POST', '../api/uploadMedia.jsp', true);
//	
//	// Set up a handler for when the request finishes.
//	xhr.onload = function () {
//		if (xhr.status === 200) {
//			// File(s) uploaded.
//			var a = xhr.responseText.split("\\");
//			document.getElementById('mediaPath').value = xhr.responseText;
//			document.getElementById('mediaName').innerHTML = a[a.length-1];
//		} else {
//			alert('An error occurred while uploading, please check for errors and try again.');
//		}
//	};
//	
//	// Send the Data.
//	xhr.send(formData);
//}