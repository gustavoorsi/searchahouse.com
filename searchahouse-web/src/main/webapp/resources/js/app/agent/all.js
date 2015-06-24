// http://www.sitepoint.com/api-calls-angularjs-http-service/
'use strict';

angular.module('myApp', []).controller('AgentsController',		
		
		function($scope, $http) {
			
			fetchAgents();

			function fetchAgents() {
				
				$http.get("http://localhost:8081/api/v1/agent")
					.success( function(response) {
							$scope.agents = response;
							console.log($scope.agents);
						});

			}
		});