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
        'myApp.prkom', 'myApp.support', 'myApp.registration', 'myApp.remember']);

myApp.config(function($routeProvider) {

    var Auth={
        authorize: function(userService) {
            // return userService.getNewUser();
        }
    };

    $routeProvider
        .otherwise({redirectTo: '/lk'})
        .when('/lk', {
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
        }).when('/remember', {
            templateUrl: 'remember/remember.html',
            controller: 'RememberCtrl'
        })

});

myApp.controller('CopyrightDateCtrl',function ($scope, dateFilter) {
    $scope.date = new Date();
    $scope.$watch('date', function (date)
    {
        $scope.dateString = dateFilter(date, 'yyyy');
    });
});



myApp.controller('UserCtrl', function($scope) { //это контроллер , он ставится в шаблоне html ng-controller="UserCtrl" - и отвечает за видимость внутри вложенных dom элементов старницы

    //$scope видимость контекста внутри контроллера
    $scope.helloWorld = "hello world";

    function getText(){ //функция js
        var text = 1;
        text = text + 1;
        return text;
    }

    $scope.getText = function(){ //функция ангуляра она может быть вызвана например событиями ng-click="getText()" или ng-mouseenter и другие
        var text = 1;
        text = text + 1;
        return text;
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


