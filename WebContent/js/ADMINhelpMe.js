var app = angular.module("app", []);
app.controller("ADMINhelpMeController", function($scope,$http) {

	//gets all the messages from DB.
	$http.get("http://localhost:8080/BooksForAll/GetAllMSGS?")
	.then(function (success){
		$scope.msgs = success.data;
	},function(error){
	
	});
	//Clicking on view profile sends you the profile page of user.
	$scope.ChooseCustomer = function(username) {
		$http({
			url:"http://localhost:8080/BooksForAll/GetCustomer?",
			method: "GET",
			params: {username: username} 
		})
		.then(function (success){
			sessionStorage.setItem('ChoosenCustomer', JSON.stringify(success.data));
			window.location = "ADMINprofile.html";
		},function(error){
		});
	}
	
	//Signs out from the web.
	$scope.Signout = function() {
		$http.get("http://localhost:8080/BooksForAll/Logout?")
		.then(function (success){
			sessionStorage.clear();
	},function(error){
		
	});
		window.location = "index.html";
	}
	
	//Gets the number of unread messages to display the icon in the nav bar.
	$http.post("http://localhost:8080/BooksForAll/GetUnreadMSGS?")
	.then(function (success){
		$scope.mailCount = success.data.counter;
	},function(error){
		$scope.mailCount = 0;
	});
	
	//Clicking on respond button sends you to respond page.
	$scope.ChooseToReply = function(msg) {
		sessionStorage.setItem('msg', JSON.stringify(msg));
		window.location = "ADMINrespond.html";
	};
	
	//click on message sends to the server that the message was read.
	$scope.readMSG = function(id) {
		var data= {
				ID : id
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
	
	
});