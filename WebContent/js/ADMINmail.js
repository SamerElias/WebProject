var app = angular.module("app", []);
app.controller("ADMINmailController", function($scope,$http) {
	
	//Gets all the unapproved reviews sorted by date.
	$http.get("http://localhost:8080/BooksForAll/GetAllReviews?")
	.then(function (success){
		$scope.reviews = success.data;
	},function(error){
		
	});
	
	//clicking approve for a review sends a post to update the data base, and remove the review from the list.
	$scope.AcceptReview = function(x) {
		var index =  $scope.reviews.indexOf(x);
		var data = {
				username: $scope.reviews[index].username,
				bookname: $scope.reviews[index].bookName,
				accepted: 1
		};
		$http.post("http://localhost:8080/BooksForAll/UpdateReview", data)
		.then(function (success){
			  $scope.reviews.splice(index, 1);  
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
	//clicking decline for a review sends a post to update the data base, and remove the review from the list.
	$scope.DeclineReview = function(x) {
		var index =  $scope.reviews.indexOf(x);
		var data = {
				username: $scope.reviews[index].username,
				bookname: $scope.reviews[index].bookName,
				accepted: 0
		};
		$http.post("http://localhost:8080/BooksForAll/UpdateReview", data)
		.then(function (success){
			  $scope.reviews.splice(index, 1);  
		},function(error){
			
		});
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