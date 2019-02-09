var app = angular.module("app", []);
app.controller("homeController", function($scope,$http) {
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
		$http.get("http://localhost:8080/BooksForAll/GetBooksGenre?")
		.then(function (success){
			$scope.genres = success.data;
	},function(error){
		
	});
	$http.get("http://localhost:8080/BooksForAll/GetFeaturedBooksServlet?")
		.then(function (success){
			$scope.books = success.data;
	},function(error){
		
	});
	//Gets the books by genre to display them.
	$scope.GetBookByGenre = function(name) {
			$http({
				url:"http://localhost:8080/BooksForAll/GetBooksByGenre?",
				method: "GET",
				params: {genre: name}
			})
			.then(function (success){
				$scope.books = success.data;
				document.getElementById('booksGenre').innerHTML = name + " eBooks";
		},function(error){
			
		});
		
	}
	//clicking on book takes you to the ebookpage (ebook info).
	$scope.ChooseBook = function(book){
		sessionStorage.setItem('ChoosenBook', JSON.stringify(book));
		window.location="eBookPage.html";
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
	//Gets the number of unread messages to display the in the nav bar.
	$http.get("http://localhost:8080/BooksForAll/GetUnreadMSGS?")
	.then(function (success){
		$scope.mailCount = success.data.counter;
	},function(error){
		$scope.mailCount = 0;
	});
});