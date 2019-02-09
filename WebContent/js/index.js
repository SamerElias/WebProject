angular.module('app',[])
	.controller('loginController', function($scope,$http){
		$scope.submitForm = function(isValid) {
	            var data = {
	            		username : $scope.username,
	            		password : $scope.password
	            		
	            };
	            if (isValid) { 
	    			$http.post("http://localhost:8080/BooksForAll/Login?",data)
	    			.then(function (success){
	            		$scope.records = success.data;
	            		var name = success.data.username;
	            		if(name == null){
	            			window.location="ADMINhomepage.html";
	            			return;
	            		}
	            		sessionStorage.setItem('customer', JSON.stringify($scope.records));
	            		 window.location="homepage.html";
	    		},function(error){
	    			$scope.error = true;
	    		});
	            }
		 };
});