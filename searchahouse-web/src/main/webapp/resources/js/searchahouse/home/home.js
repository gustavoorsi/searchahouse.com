// http://www.sitepoint.com/api-calls-angularjs-http-service/
'use strict';

var myApp = angular.module('myApp', []);

myApp.controller('LocationAutocompleteController', function($scope, $http) {
    var pendingTask;

    $scope.change = function() {
      if (pendingTask) {
        clearTimeout(pendingTask);
      }
      pendingTask = setTimeout(fetch, 50);
    };

    function fetch() {
    	if ($scope.search.trim()) {
    		$http.get("http://localhost:8081/api/v1/location/autocomplete/" + $scope.search )
	          .success(function(response) {
	          $scope.locations = response;
	          console.log( $scope.locations );
	        });
    	}
    	
    }


    $scope.select = function() {
      this.setSelectionRange(0, this.value.length);
    }
  });