'use strict';

app.controller('PropertyListController', function($scope, $http) {

	$http.get("http://localhost:8081/api/v1/property").success(
			function(response) {
				$scope.properties = response;
				console.log($scope.properties);
			});

});