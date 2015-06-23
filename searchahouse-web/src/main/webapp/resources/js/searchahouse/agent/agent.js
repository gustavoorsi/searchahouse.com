// http://www.sitepoint.com/api-calls-angularjs-http-service/
'use strict';

angular.module('myApp', []).controller('AgentController',		
		
		function($scope, $http) {
			
			fetchProperties();

			function fetchProperties() {
				
				$http.get("http://localhost:8081/api/v1/agent/2agent@example.com/properties")
					.success( function(response) {
							$scope.properties = response;
//							console.log($scope.properties);
						});

			}
		});