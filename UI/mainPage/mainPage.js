'use strict';

var mainPage = angular.module('myApp.mainPage', ['ngRoute']);

mainPage.controller('MainPageCtrl', function($scope, userService) {

    var userInterval = setInterval(function () {
        if (userService.User) {
            $scope.user = userService.User;
            clearInterval(userInterval);

        }
    }, 150);


});
