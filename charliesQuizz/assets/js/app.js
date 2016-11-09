var app = angular.module('appGame', ['angular-google-gapi', 'ngRoute', 'ngCookies']);

// Charger gapi avec angular
app.run(['GAuth', 'GApi', 'GData', '$rootScope', '$cookies',
    function(GAuth, GApi, GData, $rootScope, $cookies) {

		$rootScope.gdata = GData;

		var CLIENT = '244115085310-dmd5grs3mat4dvirsggn800tr44l9vvd.apps.googleusercontent.com';
		var BASE = 'https://1-dot-helloworld44230.appspot.com/_ah/api';

		GApi.load('charlies','v1',BASE).then(function(resp) {
			console.log('api: ' + resp.api + ', version: ' + resp.version + ' loaded');
		},
		function(resp) {
			console.log('an error occured during loading api: ' + resp.api + ', resp.version: ' + version);
		});

		GAuth.setClient(CLIENT);
		GAuth.setScope('https://www.googleapis.com/auth/userinfo.profile');

		GAuth.load();

		if ($cookies.get("google_auth_id")) {
			GData.setUserId($cookies.get("google_auth_id"));
		}
	}
]);

app.config(function($routeProvider) {
	$routeProvider
	.when("/", {
		templateUrl : "accueil.html"
	})
	.when("/category", {
		templateUrl : "category.html"
	})
	.when("/highscore", {
		templateUrl : "highscore.html"
	})
	.when("/play", {
		templateUrl : "play.html"
	});
});

app.controller('myController', ['$scope', '$location', '$cookies', 'GApi', 'GAuth', 'GData', function($scope, $location, $cookies, GApi, GAuth, GData){

	GAuth.checkAuth().then(
		function(user) {
			console.log(user);
			$scope.google_user = user;
		},
		function() {
			console.log('error');
		}
	);

	$scope.play = function(category){
			//Choix de category par le joueur
			$scope.myCategory = $scope.category.categ[category];
			
			// Variable pour savoir si le quizz est fini
			$scope.finished = false;

			GApi.execute('charlies', 'charliesEndpoint.listQuestions',{number: 10, category:"scientist"}).then( function(resp) {
			$scope.sparqlResult = resp.items;
			$scope.nextQuestion();
		  }, function() {
			console.log("error :(");
		});
	};

	$scope.login = function() {
		GAuth.login().then(function(user) {
			$scope.google_user = user;
			$cookies.put("google_auth_id", user.id);
		}, function() {
			console.log('fail');
		});
	};

	$scope.logout = function() {
		GAuth.logout();
		$scope.google_user = null;
		$cookies.remove("google_auth_id");
	}

	$scope.category = function(){
	  $location.path('/category');
	  
	  // En attendant que le endpoint soit OK :
	  $scope.category = { 
		categ: ["scientist",
		"monument",
		"autres"]
	  };

	  // Ici je recupere les differentes categories
	  // GApi.execute('scoreEntityEndPoint', 'listCategory').then( function(resp) {
	  //       $scope.category = resp.items;
	  //     }, function() {
	  //       console.log("error :(");
	  //   });
	};

	// Question suivantes 
	/* (selon le nb de question passé et 
		a quelle section du resultat sparql on en est !)
	*/
	$scope.nextQuestion = function() {
		$location.path('/play');

		console.log($scope.sparqlResult);

		$scope.questions = $scope.sparqlResult[$scope.cursorSection][$scope.cursorQuestion];

/*			$scope.questions = {
			question: "who ?",
			answers: [
				"good",
				"bad",
				"bad2",
				"bad3"
			]
		};*/
		$scope.questions.pic = $scope.sparqlResult[$scope.cursorSection].pic;
		$scope.questions.answered = false;
		$scope.good_answer = $scope.questions.answers[0];

		$.randomize($scope.questions.answers);

		for (var i = $scope.questions.answers.length - 1; i >= 0; i--) {
			if($scope.questions.answers[i] == $scope.good_answer){
				$scope.questions.good_answer = i;
			}
		};

		switch($scope.cursorQuestion) {
			case "who":
				$scope.cursorQuestion = "when";
				break;
			case "when":
				$scope.cursorQuestion = "where";
				break;
			case "where":
				$scope.cursorQuestion = "who";
				$scope.cursorSection++;
				if($scope.sparqlResult.length <= $scope.cursorSection){
					$scope.finished = true;
				}
				break;
			}
		};

		switch($scope.cursorQuestion) {
			case "who":
				$scope.cursorQuestion = "when";
				break;
			case "when":
				$scope.cursorQuestion = "where";
				break;
			case "where":
				$scope.cursorQuestion = "who";
				$scope.cursorSection++;
				break;
		}
	};

	// Valider la question (On a decider de mettre la premiere reponse bonne mais pas dans la vue)
	$scope.valid = function(answer){
		console.log(answer);
		$scope.questions.answered = answer;
		if ($scope.questions.good_answer == answer) {
			$scope.myscore += 10;
		};
		console.log($scope.myscore);
		//$scope.nextQuestion();
	};

	$scope.highscore = function () {
		$location.path('/highscore');

		// Recuperation des highscores
		GApi.execute('charlies', 'charliesEndpoint.listHighscores', {category: $scope.myCategory}).then( function(resp) {
			console.log(resp);
			$scope.high = resp.items;
		}, function() {
			console.log("error :(");
		});
	};

	// Variable qui gere les pages
	$scope.page = 'Accueil';
	// Variable pour recuperer les resultats sparql
	$scope.sparqlResult = null;
	// nbQuestion courant
	$scope.cursorSection = 0;
	//
	$scope.cursorQuestion = "who";
	// score (a voir si on fait ca comme ca !)
	$scope.myscore = 0;
	
}]);

(function($) {
	$.randomize = function(arr){
		for (var j, x, i = arr.length; i; j = parseInt(Math.random()*i), x = arr[--i], arr[i] = arr[j], arr[j] = x);
		return arr;
	};
})(jQuery);
