'use strict';
var XML, ipAdress;
var serverUrlIndex = 1;
loadXML('client.config.xml');
function parseXML() {
    ipAdress = XML.getElementsByTagName('server')[serverUrlIndex].getAttribute('url');
};
function loadXML(url) {
    var oXML;
    if (window.XMLHttpRequest) { oXML = new XMLHttpRequest(); } else if (window.ActiveXObject) {oXML=new ActiveXObject("Microsoft.XMLHTTP");};
    if (oXML) {
        oXML.onreadystatechange = function() {
            if (oXML.readyState == 4) {
                if (oXML.status == 200) {
                    XML = oXML.responseXML;
                    parseXML();
                };
            };
        };
        oXML.open("GET", url, true);
        oXML.send();
    };
};



var myApp = angular.module('myApp',
    ['ngRoute', 'myApp.services', 'myApp.lk', 'myApp.apply', 'myApp.direction',
        'myApp.prkom', 'myApp.support', 'myApp.registration', 'myApp.remember', 'myApp.confirmationModal',
        'myApp.infoModal', 'myApp.mainPage', 'myApp.login'
    ]);

myApp.directive('htmlCompile', ['$compile', function ($compile) {
    return function(scope, element, attrs) {
        scope.$watch(
            function(scope) {
                // watch the 'compile' expression for changes
                return scope.$eval(attrs.htmlCompile);
            },
            function(value) {
                // when the 'compile' expression changes
                // assign it into the current DOM
                element.html(value);

                // compile the new DOM and link it to the current
                // scope.
                // NOTE: we only compile .childNodes so that
                // we don't get into infinite loop compiling ourselves
                $compile(element.contents())(scope);
            }
        );
    };
}]);

myApp.config(function($routeProvider, $httpProvider) {
    $httpProvider.defaults.withCredentials = true;

    var UserResolve = {
        authorizeCheck: function(userService) {
            return userService.resolveCheck();
        }
    };

    $routeProvider
        .otherwise({redirectTo: '/main'})
        .when('/main', {
            templateUrl: 'mainPage/mainPage.html',
            controller: 'MainPageCtrl',
            resolve: UserResolve
        })
        .when('/lk', {
            templateUrl: 'lk/lk.html',
            controller: 'LkCtrl',
            resolve: UserResolve
        })
        .when('/login', {
            templateUrl: 'login/login.html',
            controller: 'LoginCtrl',
            resolve: UserResolve
        })
        .when('/apply', {
            templateUrl: 'apply/applyDocuments.html',
            controller: 'ApplyDocumentCtrl',
            resolve: UserResolve
        })
        .when('/direction', {
            templateUrl: 'direction/direction.html',
            controller: 'DirectionCtrl',
            resolve: UserResolve
        })
        .when('/priemnaya-komissiya', {
            templateUrl: 'prkom/prkom.html',
            controller: 'PriemCtrl',
            resolve: UserResolve
        })
        .when('/support', {
            templateUrl: 'support/support.html',
            controller: 'SupportCtrl',
            resolve: UserResolve
        })
        .when('/registration', {
            templateUrl: 'registration/registration.html',
            controller: 'RegistrationCtrl',
            resolve: UserResolve
        })
        .when('/remember', {
            templateUrl: 'remember/remember.html',
            controller: 'RememberCtrl',
            resolve: UserResolve
        })

});

myApp.controller('UserCtrl', function($scope, userService, $window) { //это контроллер , он ставится в шаблоне html ng-controller="UserCtrl" - и отвечает за видимость внутри вложенных dom элементов старницы

    getUser();

    function getUser() {
        var userInterval = setInterval(function () {
            if (userService.User) {
                $scope.user = userService.User;
                tryDigest();
                clearInterval(userInterval);
            }
        }, 100);
    }

    function tryDigest() {
        if(!$scope.$$phase) {
            $scope.$digest();
        };
    }

    $scope.logOut = function() {
        userService.logOut();
        $scope.user = null;
        getUser();
    };

    $scope.getCurrentYear = function(){
        return new Date().getFullYear();
    }

});






myApp.filter('notNull', function(){
    return function(input){
        if (input && input.search('null')!=-1)
        {
            input = input.replace('null', '');
        }
        return input;
    }
});

//Функция смены курсора при наведении на правый нижний угол элемента textarea
$(function() {
    //  changes mouse cursor when highlighting loawer right of box
    $(document).on('mousemove', 'textarea', function (e) {
        var a = $(this).offset().top + $(this).outerHeight() - 16,	//	top border of bottom-right-corner-box area
            b = $(this).offset().left + $(this).outerWidth() - 16;	//	left border of bottom-right-corner-box area
        $(this).css({
            cursor: e.pageY > a && e.pageX > b ? 'nw-resize' : ''
        });
    })
});


