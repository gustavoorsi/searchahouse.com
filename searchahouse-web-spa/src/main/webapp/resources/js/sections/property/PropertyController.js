'use strict';

app.controller('PropertyController', function($scope, $http, $routeParams) {

	getAgent();

	function getAgent() {

		var propertyId = $routeParams.propertyId;

		$http.get("http://localhost:8081/api/v1/property/" + propertyId)
				.success(function(response) {
					$scope.property = response;
					console.log($scope.property);
				});
	}

	$scope.postLead = function() {
		var data = $scope.lead;
		var propertyId = $scope.property.primaryKey;
		console.log($scope.lead);
		console.log(propertyId);
		$http.post("http://localhost:9090/api/v1/leadrouter?propertyId=" + propertyId, data)
				.success(function(data, status, headers, config) {
					console.log("SUCCESS");
					console.log("data: " + data);
					console.log("status: " + status);
					console.log("headers: " + headers('Location'));
					console.log("headers json: " + angular.toJson(headers));
					console.log("config: " + config);
				}).error(function(data, status, headers, config) {
					console.log( "toJson: " + angular.toJson(data) );
					console.log("data[0].message: " + data[0].message);
					console.log("status: " + status);
				});
	}

});
