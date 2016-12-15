
/* Hard Coded stuff */
var CLIENT = '78472264515-sudi3vpi8tr2dqi4ug43pmn1vtd8dhuo.apps.googleusercontent.com';
var BASE = 'https://1-dot-helloworld44230.appspot.com/_ah/api';


var app = angular.module('appGame', ['angular-google-gapi', 'ngRoute', 'ngCookies']);

// Load GAPI into angular
app.run(['$rootScope', '$cookies', 'GApi', 'GData', 'GAuth',
    function($rootScope, $cookies, GApi, GData, GAuth) {

        GApi.load('charlies','v1',BASE).then(
            function(resp) {
    			console.log('Api: ' + resp.api + ', version: ' + resp.version + ' loaded');
    		},
    		function(resp) {
    			console.log('An error occured while loading api: ' + resp.api + ', resp.version: ' + resp.version);
    		}
        );

		GAuth.setClient(CLIENT);
		GAuth.setScope('https://www.googleapis.com/auth/userinfo.profile');

		GAuth.load();

		if ($cookies.get("google_auth_id")) {
			GData.setUserId($cookies.get("google_auth_id"));
		}


        $rootScope.login = function(callback) {
    		GAuth.login().then(function(user) {
    			$rootScope.google_user = user;
    			$cookies.put("google_auth_id", user.id);
    			if (callback) {
    				callback();
    			}
    		}, function() {
    			console.log('fail');
    		});
    	};

        $rootScope.logout = function() {
            GAuth.logout();
    		$rootScope.google_user = null;
    		$cookies.remove("google_auth_id");
        };

        GAuth.checkAuth().then(
    		function(user) {
    			$rootScope.google_user = user;
                if ($rootScope.auth_callback_success)
                    $rootScope.auth_callback_success();
    		},
    		function() {
    			//console.log('error');
                console.log('not logged');
                if ($rootScope.auth_callback_error)
                    $rootScope.auth_callback_error();
    		}
    	);

        $rootScope.currentGame = null;
	}
]);

app.config(function($routeProvider) {
	$routeProvider
	.when("/", {
		templateUrl: "templates/home.html",
        controller: "HomeController"
	})
	.when("/categories", {
		templateUrl: "templates/categories.html",
        controller: "CategoriesController"
	})
    .when("/game/round/:index_round/:question_type", {
        templateUrl: "templates/round.html",
        controller: "GameController"
    })
    .when("/game/end", {
        templateUrl: "templates/endgame.html",
        controller: "EndGameController"
    })
    .when("/highscores", {
        templateUrl: "templates/highscores.html",
        controller: "HighscoresController"
    })
});

/* Home Controller */

app.controller('HomeController', ['$scope', '$location',
    function($scope, $location) {

        $scope.play = function() {

    		if (!$scope.google_user) {
    			$scope.login(function() {
                    $location.path('/categories');
                });
    		} else {
    			$location.path('/categories');
    		}
        };

    }
]);

/* CategoriesController */

app.controller('CategoriesController', ['$rootScope', '$scope', '$location', 'GApi',
    function($rootScope, $scope, $location, GApi) {

        if (!$scope.google_user) {
            $location.path('/');
            return;
        }

        $scope.loading = true;

        GApi.execute('charlies', 'charliesEndpoint.listCategories').then( function(resp) {
            $scope.categories = resp.items;
            $scope.loading = false;
        }, function() {
            $scope.loading = false;
            console.log("error :(");
        });

        $scope.choose = function(category_id) {

            var category = $scope.categories[category_id];
            var count = 2;

            // Create game object
            $rootScope.currentGame = {
                category: category,
                count: count,
                score: 0
            };

            $scope.loading = true;

            GApi.execute('charlies', 'charliesEndpoint.listQuestions',{number: count, category: category}).then( function(resp) {
                $rootScope.currentGame.subjects = resp.items;
                //console.log(resp.items);
                $location.path('/game/round/0/who');
    		}, function() {
                $scope.loading = false;
    			console.log("error while loading questions :(");
    		});

        };

    }
]);

/* GameController */

app.controller('GameController', ['$rootScope', '$scope', '$routeParams', '$location',
    function($rootScope, $scope, $routeParams, $location) {

        // Redirect if refreshed
        if (!$routeParams.index_round ||
            !$routeParams.question_type ||
            !$rootScope.currentGame) {
            $location.path('/categories');
            return;
        }

        var subject = $rootScope.currentGame.subjects[$routeParams.index_round];

        if (!subject) {
            $location.path('/game/end');
            return;
        }

        $scope.subject = subject;
        $scope.question_type = $routeParams.question_type;

        $scope.chooseAnswer = function(index) {

            if ($scope.question.answered !== null)
                return;

            $scope.question.answered = index;

            $scope.timer.stop();

            if ($scope.question.answered === $scope.question.right_answer) {
                $rootScope.currentGame.score += $scope.timer.percentage*5;
            }

            if ($scope.question_type == 'who')
                subject.name = $scope.question.right_answer;
            else if ($scope.question_type == 'when')
                subject.date = $scope.question.right_answer;
            else if ($scope.question_type == 'where')
                subject.location = $scope.question.right_answer;
        };

        $scope.next = function() {

            switch($scope.question_type) {
                case 'who':
                    $routeParams.question_type = 'when';
                    break;
                case 'when':
                    $routeParams.question_type = 'where';
                    break;
                case 'where':
                    $routeParams.question_type = 'summary';
                    break;
            }

            $location.path('/game/round/'+$routeParams.index_round+'/'+$routeParams.question_type);
        };

        $scope.nextSubject = function() {
            $location.path('/game/round/'+(parseInt($routeParams.index_round)+1)+'/who');
        };

        $scope.setMapPick = function(pick) {
            $scope.map_pick = pick;
            $scope.$apply();
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
                            $scope.setMapPick(country);
                        } else {
                            console.error('No results found');
                            infowindow.setContent("");
                            $scope.setMapPick(null);
                        }
                    } else {
                        console.error('Geocoder failed due to: ' + status);
                        infowindow.setContent("");
                        $scope.setMapPick(null);
                    }
                });
            };

            google.maps.event.addListenerOnce(map, 'click', function(event) {
                placeMarker(map, infowindow, event.latLng);
            });
        };

        $scope.timer = {
            percentage: 100,
            id: null,
            start: function() {
                $scope.timer.id = setInterval(function() {
                    $scope.timer.percentage -= 2;
                    $scope.$apply();
                    if ($scope.timer.percentage <= 0)
                        $scope.timer.stop();
                },200);
            },
            stop: function() {
                if ($scope.timer.id != null) {
                    clearInterval($scope.timer.id);
                    $scope.timer.id = null;
                    $scope.chooseAnswer("No answer");
                    $scope.$apply();
                }
            }
        };

        if ($scope.question_type != 'summary') {
            $scope.question = {
                pic: subject.pic,
                title: subject[$scope.question_type].question,
                right_answer: subject[$scope.question_type].answers[0],
                answers: $.randomize(subject[$scope.question_type].answers),
                answered: null
            };

            $scope.timer.start();
        }

        if ($scope.question_type == 'where') {
            $scope.initMap();
        }
    }
]);

/* EndGameController */

app.controller('EndGameController', ['$rootScope', '$scope', '$location', 'GApi',
    function($rootScope, $scope, $location, GApi) {

        GApi.execute('charlies', 'charliesEndpoint.insertScore', {category: $rootScope.currentGame.category, name: $rootScope.google_user.name, pic: $rootScope.google_user.picture, score: $rootScope.currentGame.score}).then( function(resp) {
            $scope.loading = false;
        }, function() {
            $scope.loading = false;
            console.log("error :(");
        });

    	// Redirect if refreshed
        if (!$rootScope.currentGame) {
            $location.path('/');
            return;
        }

    }
]);

/* HighscoresController */

app.controller('HighscoresController', ['$rootScope', '$scope', '$location', 'GApi',
    function($rootScope, $scope, $location, GApi) {
        $scope.loading = true;

        GApi.execute('charlies', 'charliesEndpoint.listCategories').then( function(resp) {
            $scope.categories = resp.items;
            //$scope.loading = false;
        }, function() {
            //$scope.loading = false;
            console.log("error :(");
        });

        $('ul.tabs').tabs();


        $rootScope.auth_callback_success = function() {
            $scope.loadHighscores = function(category) {
                $scope.selectHighscore = true;

                if (category == "all") {
                    $scope.highscoreCategory = true;
                    GApi.execute('charlies', 'charliesEndpoint.getHighscores', {player: $rootScope.google_user.name}).then( function(resp) {
            			$scope.highscores = resp;
                        $scope.loading = false;
            		}, function(err) {
                        $scope.loading = false;
                        console.log(err);
            			console.log("error :(");
            		});
                }else {
                    $scope.highscoreCategory = false;
                    GApi.execute('charlies', 'charliesEndpoint.getHighscores', {category: category, player: $rootScope.google_user.name}).then( function(resp) {
            			$scope.highscores = resp;
                        $scope.loading = false;
            		}, function() {
                        $scope.loading = false;
            			console.log("error :(");
            		});
                }
            };

            $scope.loadHighscores('all');
        }

        $rootScope.auth_callback_error = function() {
            $scope.login($rootScope.auth_callback_success);
        }
    }
]);

(function($) {
	$.randomize = function(arr){
		for (var j, x, i = arr.length; i; j = parseInt(Math.random()*i), x = arr[--i], arr[i] = arr[j], arr[j] = x);
		return arr;
	};
})(jQuery);
