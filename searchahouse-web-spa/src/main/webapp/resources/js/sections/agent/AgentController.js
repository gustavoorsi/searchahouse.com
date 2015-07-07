'use strict';

app.controller('AgentController', function($scope, $http, $routeParams) {

	getAgent();

	function getAgent() {

		var agentId = $routeParams.agentId;

		$http.get("http://localhost:8081/api/v1/agent/" + agentId).success(
				function(response) {
					$scope.agent = response;
					console.log($scope.agent);
				});
	}

});