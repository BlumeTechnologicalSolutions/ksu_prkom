'use strict';

var mainPage = angular.module('myApp.mainPage', ['ngRoute']);

mainPage.controller('MainPageCtrl', function($scope, userService, $rootScope) {

    var userInterval = setInterval(function () {
        if ($rootScope.user) {
            $scope.user = $rootScope.user;
            clearInterval(userInterval);

        }
    }, 150);


});
