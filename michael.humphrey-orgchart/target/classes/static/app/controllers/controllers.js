/**
 *  Controllers for web app.
 */
app.controller('personController',  function($scope, $location, $window, $resource, baseUrl) {

	$scope.orderByField = 'personnelId';
	$scope.reverseSort = false;

	// http://localhost:8080/persons/
	$scope.personResource = $resource(baseUrl + ":id", {
		id : "@id"
	}, {
			'get':    {method:'GET'},
			'save':   {method:'POST', params: {id: "@id"}},
			'query':  {method:'GET', isArray:true},
			'remove': {method:'DELETE'},
			'delete': {method:'DELETE'},
			'create': {method:'POST', params: {} }
	});

	$scope.currentPerson = null;

	$scope.listPersons = function() {
		$scope.persons = $scope.personResource.query();
		return $scope.persons;
	}

	$scope.deletePerson = function(person) {
		if (confirm("Are you sure you want to delete " + person.firstName + " "
				+ person.lastName + " with PersonnelId:" + person.personnelId
				+ "?") == true) {
			person.$delete().then(function() {
				// splice out the entry so that all persons don't have to be
				// loaded again
				$scope.persons.splice($scope.persons.indexOf(person), 1);
			});
			$location.path('/');
		}
	}

	$scope.createPerson = function(person) {
		new $scope.personResource(person).$create().then(function(newPerson) {
			// push the entry so that all persons don't have to be loaded again
			// currently this is not working correctly, the object created doesn't contain the ID generated when inserted into the database
//			newPerson =  $scope.currentPerson.$get;
//			$scope.persons.push(newPerson);
			$location.path('/');
			$window.location.reload(true);	
		})
	}

	$scope.updatePerson = function(person) {
		person.$save().then(function() {
			for (var i = 0; i < $scope.persons.length; i++) {
				if ($scope.persons[i].id == person.id) {
					$scope.persons[i] = person;
					break;
				}
			}
		});
		$location.path('/');
		// $window.location.reload();
	}

	$scope.editPerson = function(person) {
		$scope.currentPerson = angular.copy(person);
		$location.path('/edit');
	}

	$scope.newPerson = function() {
		$scope.currentPerson = {};
		$location.path('/create');
	}

	$scope.saveEdit = function(person) {

		if (angular.isDefined(person.id)) {
			$scope.updatePerson(person);
		} else {
			$scope.createPerson(person);
		}
		$location.path('/');
		// $window.location.reload();
	}

	$scope.cancelEdit = function() {
		if ($scope.currentPerson && $scope.currentPerson.$get) {
			$scope.currentPerson.$get();
		}
		$scope.currentPerson = {};
		$location.path('/');
	}

	$scope.listPersons();
});