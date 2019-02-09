var app = angular.module("app", []);
app.controller("ADMINuserListController", function($scope,$http) {
	
	//Gets all customers to display them in the table.
	$http.get("http://localhost:8080/BooksForAll/GetAllUsers?")
	.then(function (success){
		$scope.allUsers = success.data;
	},function(error){
		
	});
	//clicking on the row chooses customer and take the admin to user profile.
	$scope.ChooseCustomer = function(x) {
		sessionStorage.setItem('ChoosenCustomer', JSON.stringify(x));
		window.location = "ADMINprofile.html";
	}
	//Clicking remove user button.
	$scope.RemoveCustomer = function(x,event) {
		$scope.ChoosenCustomer = x;
		$("#myModal").modal();
		event.stopPropagation()
	}
	//Confirm remove user.
	$scope.proceedRemove = function() {
		var index =  $scope.allUsers.indexOf($scope.ChoosenCustomer);
		var data={
				username : $scope.ChoosenCustomer.username
			};
		$http.post("http://localhost:8080/BooksForAll/RemoveUser",data)
		.then(function (success){
			 $scope.allUsers.splice(index, 1);  
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



//clicking on table headers sorts the table by the clicked header.
function sortTable(n) {
	  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	  table = document.getElementById("tab1");
	  switching = true;
	  dir = "asc"; 
	  while (switching) {
	    switching = false;
	    rows = table.getElementsByTagName("TR");
	    for (i = 1; i < (rows.length - 1); i++) {
	      shouldSwitch = false;
	      x = rows[i].getElementsByTagName("TD")[n];
	      y = rows[i + 1].getElementsByTagName("TD")[n];
	      if (dir == "asc") {
	        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
	          shouldSwitch= true;
	          break;
	        }
	      } else if (dir == "desc") {
	        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
	          shouldSwitch= true;
	          break;
	        }
	      }
	    }
	    if (shouldSwitch) {
	      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
	      switching = true;
	      switchcount ++; 
	    } else {
	      if (switchcount == 0 && dir == "asc") {
	        dir = "desc";
	        switching = true;
	      }
	    }
	  }
	}
//search by the username.
function Search() {
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("myInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("tab1");
	  tr = table.getElementsByTagName("tr");

	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[1];
	    if (td) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    } 
	  }
	}