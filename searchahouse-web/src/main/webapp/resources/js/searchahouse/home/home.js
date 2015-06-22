// http://www.sitepoint.com/api-calls-angularjs-http-service/
'use strict';

angular.module('myApp', [])
  .controller('AgentAutocompleteController', function($scope, $http) {
    var pendingTask;

    $scope.change = function() {
      if (pendingTask) {
        clearTimeout(pendingTask);
      }
      pendingTask = setTimeout(fetch, 300);
    };

    function fetch() {
    	if ($scope.search.trim()) {
    		$http.get("http://localhost:8081/api/v1/agent/autocomplete/" + $scope.search )
	          .success(function(response) {
	          $scope.agents = response;
	        });
    	}
    	
    }


    $scope.select = function() {
      this.setSelectionRange(0, this.value.length);
    }
  });