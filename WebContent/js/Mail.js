var app = angular.module("app", []);
app.controller("MailController", function($scope,$http) {
	var data = sessionStorage.getItem("customer");
	if(data == null){
		$http.get("http://localhost:8080/BooksForAll/GetCustomerBySession?")
		.then(function (success){
			sessionStorage.setItem('customer', JSON.stringify(success.data));
	},function(error){
		window.location="index.html";
	});
		 
	}
	$scope.user = JSON.parse(data);
	
	$http.get("http://localhost:8080/BooksForAll/GetAllMSGS?")
	.then(function (success){
		$scope.msgs = success.data;
},function(error){
	
});
	//Gets the number of unread messages to display the icon in the nav bar.
	$http.post("http://localhost:8080/BooksForAll/GetUnreadMSGS?")
	.then(function (success){
		$scope.mailCount = success.data.counter;
	},function(error){
		$scope.mailCount = 0;
	});	
	//click on message sends to the server that the message was read.
	$scope.readMSG = function(msg) {
		if(msg.readStatus ==0){
		var data= {
				ID : msg.ID
		  };
			var request = $.ajax({
				  url: "http://localhost:8080/BooksForAll/ReadMSG",
				  type: "POST",
				  data: JSON.stringify(data),
				  contentType: "application/json"
				});

			request.done(function(msg) {
				});
		}
		
	}
	//signs out from the site.
	$scope.Signout = function() {
		$http.get("http://localhost:8080/BooksForAll/Logout?")
		.then(function (success){
			sessionStorage.clear();
	},function(error){
		
	});
		window.location = "index.html";
	}
});