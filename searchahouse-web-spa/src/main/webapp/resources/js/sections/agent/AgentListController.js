'use strict';

app.controller('AgentListController', function($scope, $http) {

	$http.get("http://localhost:8081/api/v1/agent").success(
			function(response) {
				$scope.agents = response;
				console.log($scope.agents);
			});

});