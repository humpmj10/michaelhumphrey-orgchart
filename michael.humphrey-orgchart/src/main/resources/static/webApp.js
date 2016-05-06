/**
 * This is the root of the angularjs modules for this app. It will coordinate
 * the view when different pages have been selected.
 */
var app = angular.module('app', [ 'ngRoute', 'ngResource', 'door3.css', 'ui.bootstrap']);
//Used for routing and is base url for all pages
app.constant('baseUrl', 'http://localhost:8080/persons/');

app.config([ '$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {

	// This will create pretty url's
	$locationProvider.html5Mode({ enabled: true, requireBase: false });
	$routeProvider

	.when('/', {
		templateUrl : 'app/views/summary.html',
		css : 'app/css/summary.css'
	})

	.when('/edit', {
		templateUrl : 'app/views/create.html',
		css : 'app/css/forms.css'
	})

	.when('/create', {
		templateUrl : 'app/views/create.html',
		css : 'app/css/forms.css'
	})

	.otherwise({
		redirectTo : '/'
	});
} ]);


