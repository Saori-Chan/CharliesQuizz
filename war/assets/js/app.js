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
			$scope.google_user = user;
		},
		function() {
			console.log('error');
		}
	);

	$scope.game = function(category){
		//Choix de category par le joueur
		$scope.myCategory = $scope.category.categories[category];

		// Variable pour savoir si le quizz est fini
		$scope.finished = false;

		GApi.execute('charlies', 'charliesEndpoint.listQuestions',{number: 5, category: $scope.myCategory}).then( function(resp) {
			$scope.sparqlResult = resp.items;
			$scope.nextQuestion();
		}, function() {
			console.log("error :(");
		});
	};

	$scope.login = function(callback) {
		GAuth.login().then(function(user) {
			$scope.google_user = user;
			$cookies.put("google_auth_id", user.id);
			if (callback) {
				callback();
			}
		}, function() {
			console.log('fail');
		});
	};

	$scope.logout = function() {
		GAuth.logout();
		$scope.google_user = null;
		$cookies.remove("google_auth_id");
	}

	$scope.play = function(){

		var request = function() {
			$location.path('/category');

			// En attendant que le endpoint soit OK :
			$scope.category = {
				categories: [
					"scientist",
					"monument",
					"autres"
				]
			};

			// Ici je recupere les differentes categories
			// GApi.execute('scoreEntityEndPoint', 'listCategory').then( function(resp) {
			//       $scope.category = resp.items;
			//     }, function() {
			//       console.log("error :(");
			//   });
		}

		if (!$scope.google_user) {
			$scope.login(request);
		} else {
			request();
		}
	};

	// Question suivantes
	/* (selon le nb de question passé et
		a quelle section du resultat sparql on en est !)
	*/
	$scope.nextQuestion = function() {
		$location.path('/play');

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

		$scope.questions = $scope.sparqlResult[$scope.cursorSection][$scope.cursorQuestion];

		$scope.questions.pic = $scope.sparqlResult[$scope.cursorSection].pic;
		$scope.questions.answered = false;
		$scope.good_answer = $scope.questions.answers[0];

		$.randomize($scope.questions.answers);

		for (var i = $scope.questions.answers.length - 1; i >= 0; i--) {
			if($scope.questions.answers[i] == $scope.good_answer){
				$scope.questions.good_answer = i;
			}
		};

        if ($scope.cursorQuestion == "where") {
            $scope.initMap();
        }
	};

	// Valider la question (On a decider de mettre la premiere reponse bonne mais pas dans la vue)
	$scope.valid = function(answer){
		$scope.questions.answered = answer;
		if ($scope.questions.good_answer == answer) {
			$scope.myscore += 10;
		};
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

    $scope.initMap = function() {
        $scope.map_choice = null;
        var mapCanvas = document.getElementById("map");
        var myCenter= new google.maps.LatLng(31.6341600,-7.9999400);
        var mapOptions = {center: myCenter, zoom: 2};
        var map = new google.maps.Map(mapCanvas, mapOptions);
        var infowindow = new google.maps.InfoWindow();

        function placeMarker(map,infowindow, location, fn) {
            var marker = new google.maps.Marker({
                position: location,
                map: map,
                draggable: true
            });
            var latLng = new google.maps.LatLng(marker.position.lat(),marker.position.lng());

            infowindow.open(map,marker);
            geocoderLatLng(map,infowindow,latLng);

            google.maps.event.addListener(marker,'dragend', function(event) {
                latLng = new google.maps.LatLng(marker.position.lat(),marker.position.lng());
                geocoderLatLng(map,infowindow,latLng);
            });
        };

        function geocoderLatLng(map, infowindow,latLng) {
            var geocoder = new google.maps.Geocoder;
            geocoder.geocode({'location':latLng}, function(results, status) {
                if(status === google.maps.GeocoderStatus.OK) {
                    if (results[1]) {
                        var i = 0;
                        while(results[1].address_components[i].types[0] != "country") {
                            i++;
                        }
                        var country = results[1].address_components[i].long_name;
                        infowindow.setContent(country);
                        $scope.map_choice = country;
                        $scope.$apply();
                    } else {
                        console.error('No results found');
                    }
                } else {
                    console.error('Geocoder failed due to: ' + status);
                }
            });
        };

        google.maps.event.addListenerOnce(map, 'click', function(event) {
            placeMarker(map, infowindow, event.latLng);
        });
    };

	// Variable pour recuperer les resultats sparql
	$scope.sparqlResult = null;
	// nbQuestion courant
	$scope.cursorSection = -1;
	//
	$scope.cursorQuestion = "where";
	// score (a voir si on fait ca comme ca !)
	$scope.myscore = 0;

    $scope.map_choice = "France";

}]);

(function($) {
	$.randomize = function(arr){
		for (var j, x, i = arr.length; i; j = parseInt(Math.random()*i), x = arr[--i], arr[i] = arr[j], arr[j] = x);
		return arr;
	};
})(jQuery);
