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
        'myApp.infoModal', 'myApp.mainPage'
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

myApp.config(function($routeProvider) {

    var AuthResolve = {
        authorizeCheck: function(userService) {
             return userService.resolveCheck();
        }
    };

    $routeProvider
        .otherwise({redirectTo: '/main'})
        .when('/main', {
            templateUrl: 'mainPage/mainPage.html',
            controller: 'MainPageCtrl',
            resolve: AuthResolve
        })
        .when('/login', {
            templateUrl: 'lk/lk.html',
            controller: 'AbiturientCtrl'
        })
        .when('/apply', {
            templateUrl: 'apply/applyDocuments.html',
            controller: 'ApplyDocumentCtrl'
        })
        .when('/direction', {
            templateUrl: 'direction/direction.html',
            controller: 'DirectionCtrl'
        })
        .when('/priemnaya-komissiya', {
            templateUrl: 'prkom/prkom.html',
            controller: 'PriemCtrl'
        })
        .when('/support', {
            templateUrl: 'support/support.html',
            controller: 'SupportCtrl'
        })
        .when('/registration', {
            templateUrl: 'registration/registration.html',
            controller: 'RegistrationCtrl'
        })
        .when('/remember', {
            templateUrl: 'remember/remember.html',
            controller: 'RememberCtrl'
        })

});

 /*$(document).mouseup(function (e){ // событие клика по веб-документу
    if (location.hash.indexOf('lk')<0) {
        if (!localStorage.token && !sessionStorage.token) {
            if (location.hash.indexOf('clientNps')<0) {
                logOut();
                $window.location.hash = "#/loginPage";
            }
        } else {
            // webSocketCheckVersionService.knockToWebSocket();
            // if (!webSocketCheckVersionService.websocketCheckConnect()) {
            //     webSocketCheckVersionService.webSocketCheckVersion();
            // };
            systemService.systemVersion();
        };
    };
});*/

myApp.controller('UserCtrl', function($scope, userService, $rootScope) { //это контроллер , он ставится в шаблоне html ng-controller="UserCtrl" - и отвечает за видимость внутри вложенных dom элементов старницы
    if(!$rootScope.$$phase) {
        $rootScope.$digest();
    };
    var userInterval = setInterval(function(){
        if (userService.User) {
            clearInterval(userInterval);
            $scope.$apply(function () {
                $rootScope.user = userService.User;
            })
        };
    }, 100);

    $scope.logOut = function() {
        userService.logOut();
        $rootScope.user = null;
    };
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


