'use strict';

var app = angular.module('searchahouse-spaweb', [ 'ngRoute' ]);

app.config([ '$routeProvider', '$locationProvider', '$httpProvider',
		function($routeProvider, $locationProvider, $httpProvider) {
			$locationProvider.html5Mode(false);
			$routeProvider.when('/', {
				templateUrl : 'views/sections/home/home.html',
				controller : 'HomeController'
			}).when('/about', {
				templateUrl : 'views/sections/about/about.html',
				controller : 'AboutController'
			}).when('/agent/:agentId', {
				templateUrl : 'views/sections/agent/agent.html',
				controller : 'AgentController'
			}).when('/agents', {
				templateUrl : 'views/sections/agent/agentList.html',
				controller : 'AgentListController'
			}).when('/property/:propertyId', {
				templateUrl : 'views/sections/property/property.html',
				controller : 'PropertyController'
			}).when('/properties', {
				templateUrl : 'views/sections/property/propertyList.html',
				controller : 'PropertyListController'
			}).otherwise({
				redirectTo : '/'
			});

			var interceptor = [ '$q', '$location', function($q, $location) {

				function success(response) {
					return response;
				}

				function error(response) {
					if (response.status === 404) {
						$location.path("/404").replace();
					} else if (response.status === 403) {
						$location.path("/403").replace();
					} else if (response.status === 401) {
						$location.path("/401").replace();
					}

					return $q.reject(response);
				}

				return function(promise) {
					return promise.then(success, error);
				};
			} ];

//			 $httpProvider.responseInterceptors.push(interceptor);
		} ]);
