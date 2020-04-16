'use strict';

var registration = angular.module('myApp.registration', ['ngRoute']);

registration.controller('RegistrationCtrl', function($scope, userService) {

    $scope.newUser = {};

    $scope.continueRegistration = function(){
        $scope.newUser.controlQuestion = "test";
        if(!$scope.newUser || !$scope.newUser.firstName || !$scope.newUser.lastName || !$scope.newUser.login || !$scope.newUser.password
            || !$scope.newUser.passwordReply || !$scope.newUser.controlQuestion || !$scope.newUser.controlAnswer){
            if(!$scope.newUser || !$scope.newUser.login)  $("#login").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.lastName) $("#lastName").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.firstName)  $("#firstName").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.password)  $("#password").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.passwordReply)  $("#passwordReply").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.controlQuestion)  $("#controlQuestion").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.controlAnswer)  $("#controlAnswer").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.email)  $("#email").addClass('ng-invalids');
            setTimeout(function(){
                $("#firstName").removeClass('ng-invalids');$("#lastName").removeClass('ng-invalids');$("#login").removeClass('ng-invalids');
                $("#password").removeClass('ng-invalids');$("#passwordReply").removeClass('ng-invalids');
                $("#controlQuestion").removeClass('ng-invalids');$("#controlAnswer").removeClass('ng-invalids');$("#email").removeClass('ng-invalids');
            },2500);
        } else {
            if($scope.newUser.password == $scope.newUser.passwordReply) {
                var newUser = $scope.newUser;

                newUser.passwordReply  = undefined;
                userService.registration(newUser).then(function(response) {
                    if (response.isSuccess) {
                        alert(response.object);
                    } else {
                        alert(response.message)
                    };
                }).catch(function (response) {
                    alert("Сервер временно не доступен.");
                });
            } else {
                alert("Пароли не совпадают!")
            }
        }
    }


});
