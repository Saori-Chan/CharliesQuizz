var app = angular.module('appGame', ['angular-google-gapi', 'ngRoute']);

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

// Chargé gapi avec angular
app.run(['GApi', 'GAuth',
	function(GApi, GAuth) {
		var BASE = 'https://1-dot-helloworld44230.appspot.com/_ah/api';  
		GApi.load('charlies','v1',BASE).then(function(resp) {
			console.log('api: ' + resp.api + ', version: ' + resp.version + ' loaded');
		},
		function(resp) {
			console.log('an error occured during loading api: ' + resp.api + ', resp.version: ' + version);
		});
	}
]);


app.controller('myController', ['$scope', '$location', 'GApi', function($scope, $location, GApi){
	$scope.play = function(){
		// Ici recuperation des questions à partir du endpoint
		// Probleme endpoint et methode
		GApi.execute('scoreEntityEndPoint', 'listQuestions').then( function(resp) {
			console.log("here");
			console.log(resp);
			$scope.sparqlResult = resp.items;
		  }, function() {
			console.log("error :(");
		});
	  	$scope.nextQuestion();
	};


	$scope.category = function(){
	  $location.path('/category');
	  
	  // En attendant que le endpoint soit OK :
	  $scope.category = { 
		categ: ["Scientist",
		"Monument",
		"Autres"]
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

		if ($scope.nbQuestion == 2){
			$scope.nbQuestion = 0;
			$scope.nbSection++;
		}
		 else{
			$scope.nbQuestion++;
			// $scope.questionAnswer = {
			//   $scope.question: $scope.sparqlResult,
			//   $scope.answers: [
			//     "Do"
			//   ]
			// }
		}

		$scope.questions = {
			question: "Here you find the question..",
			answers: [
				"First",
				"Second",
				"Third"
			],
			good_answer: Math.floor(Math.random()*3),
			answered: false
		};
	};

	// Validé la question (On a decider de mettre la premiere reponse bonne mais pas dans la vue)
	// A revoir
	$scope.valid = function(answer){
		$scope.questions.answered = answer;
		if ($scope.questions.good_answer == $scope.questions.answered) {
			$scope.myscore += 10;
		};
		$scope.nbQuestion++;
	};

	$scope.highscore = function () {
		$location.path('/highscore');

		// Recuperation des highscores
		GApi.execute('scoreentityendpoint', 'listScoreEntity').then( function(resp) {
			console.log(resp);
			$scope.high = resp.items;
		}, function() {
			console.log("error :(");
		});
	}

	// Variable qui gere les pages
	$scope.page = 'Accueil';
	// Variable pour recuperer les resultats sparql
	$scope.sparqlResult;
	// nbQuestion courant
	$scope.nbQuestion = 0;
	// nbSection courant
	$scope.nbSection = 0;
	// score (a voir si on fait ca comme ca !)
	$scope.myscore = 0;
	}
]);

// Alors ça ca fonctionne pas le googleUser je sais pas comment le recuperer 
// (apparement avec gapi c'est faisable mais j'y arrive pas !)
function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
  console.log('Name: ' + profile.getName());
  console.log('Image URL: ' + profile.getImageUrl());
  console.log('Email: ' + profile.getEmail());
}