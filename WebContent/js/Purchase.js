var app = angular.module("app", []);
app.controller("purchaseController", function($scope,$http) {
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
	var data = sessionStorage.getItem("PurchaseBook");
	if(data == null){
		window.location="homepage.html";
	}
	$scope.choosenBook = JSON.parse(data);
	//signs out from the site.
	$scope.Signout = function() {
		$http.get("http://localhost:8080/BooksForAll/Logout?")
		.then(function (success){
			sessionStorage.clear();
	},function(error){
		
	});
		window.location = "index.html";
	}
	//cheks if the book was already purchased.
	$http({
		url:"http://localhost:8080/BooksForAll/GetOwnedStatus?",
		 method: "GET",
		 params: {bookname: $scope.choosenBook.name}
		
	})
	.then(function (success){
		window.location = "homepage.html"
	},function(error){
		
	});
	
	var purchasereq = {
			bookname: $scope.choosenBook.name
	}
	//purchase the book.
	$scope.Purchase = function() {
		$http.post("http://localhost:8080/BooksForAll/PurchaseBook",purchasereq)
		.then(function (success){
			$("#xModal").modal()
		},function(error){
			
		});	
	}
	$scope.Cancel = function() {
		window.history.back();
	}
	$scope.Done = function() {
		
		window.location = "eBookPage.html";
	}
	//Gets the number of unread messages to display the in the nav bar.
	$http.post("http://localhost:8080/BooksForAll/GetUnreadMSGS?")
	.then(function (success){
		$scope.mailCount = success.data.counter;
	},function(error){
		$scope.mailCount = 0;
	});

	$("#sel1").on('change', function() {
		  switch (this.value) {
		  case "VISA":
			  $("#cardNum").attr("pattern","^(4)([0-9]{15})$");
			  break;
		  case "AMEX":
			  $("#cardNum").attr("pattern","^(34)([0-9]{13})$");
			  break;
		  }
	});
});

