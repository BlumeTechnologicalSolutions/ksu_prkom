'use strict';

var remember = angular.module('myApp.remember', ['ngRoute']);

remember.controller('RememberCtrl', function($scope, userService, infoService, $location) {

    $scope.rememberUser = {};

    $scope.remember = function () {
        if(!$scope.email){
            if(!$scope.email) { $("#email").focus();$("#email").addClass('ng-invalids');}
            setTimeout(function(){ $("#email").removeClass('ng-invalids'); },2000);
        } else {
            userService.remember($scope.email).then(function(response) {
                if (response.isSuccess) {
                    infoService.infoFunction(response.object, "Информация");
                    $location.path('/login')
                } else {
                    infoService.infoFunction(response.message, "Ошибка");
                };
            }).catch(function (response) {
                infoService.infoFunction(response, "Ошибка");
            });
        }
    };

});
