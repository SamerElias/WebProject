var app = angular.module("app", []);
app.controller("ADMINebookPageController", function($scope,$http) {
	var data = sessionStorage.getItem("ChoosenBook");
	if(data == null){
		window.location="ADMINhomepage.html";
	}
	$scope.choosenBook = JSON.parse(data);
	
	//Signs out from the web
	$scope.Signout = function() {
		$http.get("http://localhost:8080/BooksForAll/Logout?")
		.then(function (success){
			sessionStorage.clear();
	},function(error){
		
	});
		window.location = "index.html";
	}
	//gets the number of likes for the book and who liked the book to display them.
	$http({
		url:"http://localhost:8080/BooksForAll/GetBookLikes?",
		method: "GET",
		params: {bookName: $scope.choosenBook.name} 
	})
	.then(function (success){
		$scope.Likers = success.data;		
	},function(error){
	});
	
	//Gets the reviews of the book to display them
	$http({
		url:"http://localhost:8080/BooksForAll/GetBookReview?",
		method: "GET",
		params: {bookName: $scope.choosenBook.name} 
	})
	.then(function (success){
		$scope.Reviews = success.data;
	},function(error){
	});
	//When choosing a customer gets the customer info from DB. 
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
	//Gets the number of unread messages to display the icon in the nav bar.
	$http.post("http://localhost:8080/BooksForAll/GetUnreadMSGS?")
	.then(function (success){
		$scope.mailCount = success.data.counter;
	},function(error){
		$scope.mailCount = 0;
	});
});