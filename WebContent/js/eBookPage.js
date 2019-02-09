var app = angular.module("app", []);
app.controller("eBookPageController", function($scope,$http) {
	var data = sessionStorage.getItem("customer");
	if(data == null){
		$http.get("http://localhost:8080/BooksForAll/GetCustomerBySession?")
		.then(function (success){
			sessionStorage.setItem('customer', JSON.stringify(success.data));
	},function(error){
		window.location="index.html";
	});
		 
	}
	var user = JSON.parse(data);
	var data = sessionStorage.getItem("ChoosenBook");
	if(data == null){
		window.location="homepage.html";
	}
	$scope.choosenBook = JSON.parse(data);
	$scope.user = user;
	//signs out from the site.
	$scope.Signout = function() {
		$http.get("http://localhost:8080/BooksForAll/Logout?")
		.then(function (success){
			sessionStorage.clear();
	},function(error){
		
	});
		window.location = "index.html";
	}
	//Gets the reviews of the book to display them.
	$http({
		url:"http://localhost:8080/BooksForAll/GetBookReview?",
		method: "GET",
		params: {bookName: $scope.choosenBook.name} 
	})
	.then(function (success){
		$scope.Reviews = success.data;
	},function(error){
	});
	//Gets the book likers.
	var getLikers = function(){
		$http({
			url:"http://localhost:8080/BooksForAll/GetBookLikes?",
			method: "GET",
			params: {bookName: $scope.choosenBook.name} 
			})
	.then(function (success){
		$scope.Likes = success.data;
		for (i=0; i<$scope.Likes.length; i++){
			if($scope.Likes[i].username == user.username){
				$("#likeHeart").removeClass("glyphicon-heart-empty");
				$("#likeHeart").addClass("glyphicon-heart");
			}
		}
		var i = 0;
		var likers = "";
		for (i=0; i<$scope.Likes.length; i++){
			if(i > 3){
				likers = likers.concat(" and " + ($scope.Likes.length - 4) + " more..");
				break;
			}
			if(i != 0){
			likers = likers.concat(", " + $scope.Likes[i].nickname);
			}
			else{
				likers = likers.concat($scope.Likes[i].nickname);
			}			
		}
		$scope.Likers = likers;
	},function(error){
	});
	}
	
	getLikers();

	//Sends the server to update DB when the user likes the book
	var LikeBook = function(){
		 var data = {
         		username : user.username,
         		bookname : $scope.choosenBook.name
         		
         };
		$http.post("http://localhost:8080/BooksForAll/LikeBook",data)
		.then(function (success){
			$scope.Likes = success.data;
			getLikers()
		},function(error){
			
		});
	};
	//Sends the server to update DB when the user unlikes the book
	var UnlikeBook = function(){
		 var data = {
        		username : user.username,
        		bookname : $scope.choosenBook.name
        		
        };
		$http.post("http://localhost:8080/BooksForAll/UnlikeBook",data)
		.then(function (success){
			$scope.Likes = success.data;
			getLikers()
		},function(error){
			
		});
	};
	//sets the liked button to liked/unliked state
	$(document).ready(function () {
		$("#likeButton").click(function(){
			   if($("#likeHeart").hasClass("glyphicon-heart")){
				   $("#likeHeart").removeClass("glyphicon-heart");
				   $("#likeHeart").addClass("glyphicon-heart-empty");
				   UnlikeBook();
				   
			   }else {
				   $("#likeHeart").removeClass("glyphicon-heart-empty");
				   $("#likeHeart").addClass("glyphicon-heart");
				   LikeBook();
			   }
			    
			});
		});
		//submiting the customer review.
		$scope.submitReview = function(isValid) {
			if(isValid){
			$("#success-alert").hide();
			var data= {
			    username: user.username,
        		bookname: $scope.choosenBook.name,
        		content: $scope.review
		  };
			var request = $.ajax({
				  url: "http://localhost:8080/BooksForAll/AddReview?",
				  type: "POST",
				  data: JSON.stringify(data),
				  contentType: "application/json"
				});

			request.done(function(msg) {
				 $("#success-alert").show();
				$scope.review = '';
				$scope.userForm.$setPristine();
				});

		}
		}
		$scope.hideSuccessAlert = function() {
			$("#success-alert").hide();
			
		}
		//Checks if the customer has purchased the book to enable/disable the ability to write a review
		$http({
			url:"http://localhost:8080/BooksForAll/GetOwnedStatus?",
			method: "GET",
			params:{bookName: $scope.choosenBook.name}
			})
		.then(function (success){
			$("#priceP").remove();
			$("#purchaseButton").text("Continue reading");
			$("#purchaseButton").click(function() {
				window.location = "eBook.html"
			})
		},function(error){
			$("#review").remove();
			$("#purchaseButton").click(function() {
				sessionStorage.setItem('PurchaseBook', JSON.stringify($scope.choosenBook));
				window.location = "Purchase.html"
			})
		});
		//Gets the number of unread messages to display the in the nav bar.
		$http.post("http://localhost:8080/BooksForAll/GetUnreadMSGS?")
		.then(function (success){
			$scope.mailCount = success.data.counter;
		},function(error){
			$scope.mailCount = 0;
		});
});