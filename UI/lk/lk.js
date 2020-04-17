'use strict';

var lk = angular.module('myApp.lk', ['ngRoute']);

lk.controller('LkCtrl', function($scope, userService, $location) {

    getUser();

    function getUser() {
        if (!userService.User) {
            $location.path('/login')
        } else {
            $scope.user = userService.User;
        }
    }

});
