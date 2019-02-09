var app = angular.module("app", []);
app.controller("ADMINhomepageController", function($scope,$http) {
	
	//Gets the book genre to display them as category.
		$http.get("http://localhost:8080/BooksForAll/GetBooksGenre?")
		.then(function (success){
			$scope.genres = success.data;
	},function(error){
		
	});
		
	//Gets featured books to display them as featured on homepage.
	$http.get("http://localhost:8080/BooksForAll/GetFeaturedBooksServlet?")
		.then(function (success){
			$scope.books = success.data;
	},function(error){
		
	});
	//Gets the number of unread messages to display the in the nav bar.
	$http.post("http://localhost:8080/BooksForAll/GetUnreadMSGS?")
	.then(function (success){
		$scope.mailCount = success.data.counter;
	},function(error){
		$scope.mailCount = 0;
	});
	
	//Gets the books by genre
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
		window.location="ADMINebookPage.html";
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