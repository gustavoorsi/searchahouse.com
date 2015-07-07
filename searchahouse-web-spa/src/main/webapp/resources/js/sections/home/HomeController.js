'use strict';

app.controller('HomeController', function($scope, $http) {

	getTop3Agents();

	function getTop3Agents() {
		$http.get("http://localhost:8081/api/v1/agent").success(
				function(response) {
					$scope.agents = response._embedded.agentList;
					console.log($scope.agents);
				});
	}

	var pendingTask;

	$scope.change = function() {
		if (pendingTask) {
			clearTimeout(pendingTask);
		}
		pendingTask = setTimeout(fetch, 50);
	};

	function fetch() {
		if ($scope.propertySearch.trim()) {
			$("#propertiesResult").show();
			$http.get(
					"http://localhost:8081/api/v1/property?ac=true&qt=address&q="
							+ $scope.propertySearch).success(
					function(response) {
						$scope.properties = response;
						console.log($scope.properties);
					});
		} else {
			$("#propertiesResult").hide();
		}
	}

	$scope.select = function() {
		this.setSelectionRange(0, this.value.length);
	}

});