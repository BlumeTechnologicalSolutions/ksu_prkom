'use strict';

var registration = angular.module('myApp.registration', ['ngRoute']);

registration.controller('RegistrationCtrl', function($scope, userService) {

    $scope.newUser = {};
    // firstName lastName patronymic phone password passwordReply controlQuestion controlAnswer
    $scope.continueRegistration = function(){
        if(!$scope.newUser || !$scope.newUser.firstName || !$scope.newUser.lastName || !$scope.newUser.patronymic || !$scope.newUser.phone || !$scope.newUser.password
            || !$scope.newUser.passwordReply || !$scope.newUser.controlQuestion || !$scope.newUser.controlAnswer){
            if(!$scope.newUser || !$scope.newUser.login)  $("#firstName").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.lastName) $("#lastName").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.patronymic)  $("#patronymic").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.phone)  $("#phone").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.password)  $("#password").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.passwordReply)  $("#passwordReply").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.controlQuestion)  $("#controlQuestion").css({"border": "1px solid red"});
            if(!$scope.newUser || !$scope.newUser.controlAnswer)  $("#controlAnswer").css({"border": "1px solid red"});
            setTimeout(function(){
                $("#firstName").css({"border": ""});$("#lastName").css({"border": ""});
                $("#patronymic").css({"border": ""});$("#phone").css({"border": ""});
                $("#password").css({"border": ""});$("#passwordReply").css({"border": ""});
                $("#controlQuestion").css({"border": ""});$("#controlAnswer").css({"border": ""});
            },2000);
        } else {
            userService.registration($scope.newUser).then(function(response) {
                if (response.isSuccess) {
                    console.log("ok")
                } else {
                    alert(response.message)
                };
            }).catch(function (response) {
                alert("Сервер временно не доступен.");
            });
        }
    }


});
