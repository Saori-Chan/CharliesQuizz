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
			GApi.execute('charlies', 'charliesEndpoint.listQuestions',{number: 10, category:"scientist"}).then( function(resp) {
				$scope.sparqlResult = resp.items;
				$scope.nextQuestion();
			  }, function() {
				console.log("error :(");
			});
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
					break;
			}
		};

		// Validé la question (On a decider de mettre la premiere reponse bonne mais pas dans la vue)
		// A revoir
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
			GApi.execute('charlies', 'charliesEndpoint.listHighscores', {category: "scientist"}).then( function(resp) {
				console.log(resp);
				$scope.high = resp.items;
			}, function() {
				console.log("error :(");
			});
		}

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

(function($) {
	$.randomize = function(arr){
		for (var j, x, i = arr.length; i; j = parseInt(Math.random()*i), x = arr[--i], arr[i] = arr[j], arr[j] = x);
		return arr;
	};
})(jQuery);