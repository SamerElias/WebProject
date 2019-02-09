var app = angular.module("app", []);
app.controller("ADMINrespondController", function($scope,$http) {
	var dataa = sessionStorage.getItem("msg");
	if(dataa == null){
		window.location="homepage.html";
	}
	$scope.msg = JSON.parse(dataa);
	//Send the reply to update the message in DB and shows modal if successfully updated.
	$scope.SubmitReply = function() {
		var data = {
        		ID : $scope.msg.ID,
        		content : $scope.message	
        };
		$http.post("http://localhost:8080/BooksForAll/SendReply",data)
		.then(function (success){
			$("#myModal").modal();
		},function(error){
			
		});
	}
	//Gets the number of unread messages to display the in the nav bar.
	$http.post("http://localhost:8080/BooksForAll/GetUnreadMSGS?")
	.then(function (success){
		$scope.mailCount = success.data.counter;
	},function(error){
		$scope.mailCount = 0;
	});
	
	//after closing the modal send the admin back to helpMe page.
	$scope.Done = function() {
		window.location = "ADMINhelpMe.html";
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