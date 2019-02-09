var app = angular.module("app", []);
app.controller("eBookController", function($scope,$http) {
	var dataa = sessionStorage.getItem("ChoosenBook");
	if(dataa == null){
		window.location="homepage.html";
	}
	$scope.choosenBook = JSON.parse(dataa);
	//Get the book HTML to read.
	$http({
		 url: "http://localhost:8080/BooksForAll/GetBook?",
		 method: "GET",
		 params: {bookName: $scope.choosenBook.name}
	})
	.then(function (success){
		 $("#includedContent").append(success.data);
},function(error){
	
});
	var modalopened = true;
	//gets the customer reading session to continue reading from where he left.
	$http({
		url:"http://localhost:8080/BooksForAll/GetOwnedStatus?",
		method: "GET",
		 params: {bookName: $scope.choosenBook.name}
		})
	.then(function (success){
		$scope.scroll = success.data.readingSession;
		$("#xModal").modal();
	},function(error){
			
	});	
	//Before fully closing the window sends the 
	window.onbeforeunload = function() {
		if(modalopened == false){
			var data = {
				readingSession: $(window).scrollTop(),
				bookname: $scope.choosenBook.name
			};
			$http.post("http://localhost:8080/BooksForAll/SetReadingSession",data)
				.then(function (success){
					
			},function(error){
				
			});
		}
	};
	
	//Case customer clicked rest session, updates the DB to rest.
	$scope.Rest = function() {
		modalopened = false;
		var data = {
				readingSession: 0,
				bookname: $scope.choosenBook.name
			};
		$http.post("http://localhost:8080/BooksForAll/SetReadingSession",data)
			.then(function (success){
				
		},function(error){
			
		});
	}
	
	//case the customer choose to continue reading where he left off 
	$scope.Continue = function() {
		$(window).scrollTop($scope.scroll);
		modalopened = false;
		
		
	}

});