var app = angular.module("app", []);
app.controller("ADMINprofileController", function($scope,$http) {
	var data = sessionStorage.getItem("ChoosenCustomer");
	if(data == null){
		 window.location="ADMINhomepage.html";
	}
	$scope.user = JSON.parse(data);
	//gets books that the customer liked
	$http({
	    url: "http://localhost:8080/BooksForAll/GetBooksLikedByUsername?", 
	    method: "GET",
	    params: {Username: $scope.user.username}
	 })
	.then(function (success){
		$scope.LikedBooks = success.data;
	},function(error){
		
	});
	//Gets owned books for customer.
	$http({
	    url: "http://localhost:8080/BooksForAll/GetOwnedBooks?", 
	    method: "GET",
	    params: {Username: $scope.user.username}
	 })
	.then(function (success){
		$scope.OwnedBooks = success.data;
	},function(error){
		
	});
	//Gets the number of unread messages to display the in the nav bar.
	$http.post("http://localhost:8080/BooksForAll/GetUnreadMSGS?")
	.then(function (success){
		$scope.mailCount = success.data.counter;
	},function(error){
		$scope.mailCount = 0;
	});
	//signs out from the site.
	$scope.Signout = function() {
		$http.get("http://localhost:8080/BooksForAll/Logout?")
		.then(function (success){
			sessionStorage.clear();
	},function(error){
		
	});
		window.location = "index.html";
	}
	//clicking on a liked book or purchased book will take you the the ebook info page
	$scope.ChooseBook = function(book){
		$http({
			url:"http://localhost:8080/BooksForAll/GetBookInfoByName?",
			method: "GET",
			params: {bookName: book}
		})
		.then(function (success){
			sessionStorage.setItem('ChoosenBook', JSON.stringify(success.data));
			window.location="ADMINebookPage.html";
	},function(error){
	});
	
	}
	
});