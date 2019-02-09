var validationApp = angular.module('validationApp', []);

validationApp.controller('mainController', function($scope,$http) {

			
	$scope.submitForm = function(isValid) {
		var data = {
				username : $scope.username,
        		password : $scope.password,
        		email : $scope.email,
        		street : $scope.street,
        		city : $scope.city,
        		zipCode : $scope.zipcode,
        		phone : $scope.phone,
        		nickname : $scope.nickname,
        		description : $scope.desc
        		
        };
		
		if (isValid) { 
    			$http.post("http://localhost:8080/BooksForAll/Register?",data)
    			.then(function (success){
    				$scope.records = success.data;
    				sessionStorage.setItem('customer', JSON.stringify($scope.records));
            		 window.location="homepage.html";
    		},function(error){
    			if(error.status == 400){
    				alert("Username already exists please choose another username.")
    			}
    		});
		}else{
			$("#xModal").modal()
		}
	};

});