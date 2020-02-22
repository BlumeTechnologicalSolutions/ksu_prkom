'use strict';

var registration = angular.module('myApp.registration', ['ngRoute']);

registration.controller('RegistrationCtrl', function($scope, userService) {

    $scope.newUser = {};
    $scope.continueRegistration = function(){
        if(!$scope.newUser || !$scope.newUser.firstName || !$scope.newUser.lastName || !$scope.newUser.login || !$scope.newUser.password
            || !$scope.newUser.passwordReply || !$scope.newUser.controlQuestion || !$scope.newUser.controlAnswer){
            if(!$scope.newUser || !$scope.newUser.login)  $("#login").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.lastName) $("#lastName").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.firstName)  $("#firstName").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.password)  $("#password").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.passwordReply)  $("#passwordReply").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.controlQuestion)  $("#controlQuestion").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.controlAnswer)  $("#controlAnswer").css({"border": "1px solid red"});
            setTimeout(function(){
                $("#firstName").css({"border": ""});$("#lastName").css({"border": ""});$("#login").css({"border": ""});
                $("#password").css({"border": ""});$("#passwordReply").css({"border": ""});
                $("#controlQuestion").css({"border": ""});$("#controlAnswer").css({"border": ""});
            },2000);
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
