var app = angular.module("app", []);
app.controller("myeBookController", function($scope,$http) {
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
	
	//Gets all the books that was purchased
	$http.get("http://localhost:8080/BooksForAll/GetOwnedBooks?")
	.then(function (success){
		$scope.books = success.data;
	},function(error){
		
	});
	//clicking on book takes you to the ebookpage (ebook info).
	$scope.ChooseBook = function(book){
		sessionStorage.setItem('ChoosenBook', JSON.stringify(book));
		window.location="eBookPage.html";
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
	//Gets the number of unread messages to display the in the nav bar.
	$http.post("http://localhost:8080/BooksForAll/GetUnreadMSGS?")
	.then(function (success){
		$scope.mailCount = success.data.counter;
	},function(error){
		$scope.mailCount = 0;
	});
});