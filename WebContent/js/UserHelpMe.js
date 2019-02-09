var app = angular.module("app", []);
app.controller("UserHelpMeController", function($scope,$http) {
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
	//signs out from the site.
	$scope.Signout = function() {
		$http.get("http://localhost:8080/BooksForAll/Logout?")
		.then(function (success){
			sessionStorage.clear();
	},function(error){
		
	});
		window.location = "index.html";
	}
	//submits the message.
	$scope.SubmitMSG = function() {
		var data = {
         		subject : $scope.subject,
         		content : $scope.message
         		
         };
		$http.post("http://localhost:8080/BooksForAll/AddMSG",data)
		.then(function (success){
			$("#myModal").modal();
		},function(error){
			
		});
	}
	
	$scope.Done = function() {
		window.location = "Mail.html";
	}
	$http.post("http://localhost:8080/BooksForAll/GetUnreadMSGS?")
	.then(function (success){
		$scope.mailCount = success.data.counter;
	},function(error){
		$scope.mailCount = 0;
	});
	
});