'use strict';

var lk = angular.module('myApp.lk', ['ngRoute']);

lk.controller('LkCtrl', function($scope, userService, $location) {

    getInformation();

    function getInformation() {
        getUser();

    }


    function getUser() {
        if (!userService.User) {
            $location.path('/login')
        } else {
            $scope.user = userService.User;
        }
    }

    $scope.sexes = [{'sex':'Мужской'},{'sex':'Женский'}];
    $scope.selectedPage = 'mainInfo';

    $scope.setPage = function (selectedPage) {
        return 'lk/includePages/' + selectedPage + '.html';
    }

    $scope.setSelectedSex = function (sex) {
        $scope.sex = sex;
    };

    setTimeout(function () {
        if($scope.user.sex) $scope.sex = $scope.sexes[0];
        else $scope.sex = $scope.sexes[0];
    },100);

    $scope.saveMainInfo = function(){
        if($scope.sex == $scope.sexes[0]) $scope.user.sex = truel
        console.log($scope.sex)
    }


});
