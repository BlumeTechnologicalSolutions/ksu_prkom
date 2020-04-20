'use strict';

var registration = angular.module('myApp.registration', ['ngRoute']);

registration.controller('RegistrationCtrl', function($scope, userService, infoService, $location) {

    $scope.newUser = {};
    $scope.step = 1;
    $scope.activationCode = "";

    getRegistrationSecretQuestions();

    function getRegistrationSecretQuestions(){
        userService.getRegistrationSecretQuestions().then(function (response) {
            if(response.isSuccess){
                $scope.secretQuestions = response.object;
            } else {
                infoService.infoFunction(response.message, "Ошибка!");
            }
        })
    }

    $scope.sendCodeToEmail = function(){
        if(!$scope.newUser || !$scope.newUser.firstName || !$scope.newUser.lastName || !$scope.newUser.login || !$scope.newUser.password
            || !$scope.newUser.passwordReply || !$scope.newUser.controlQuestionSelected || !$scope.newUser.controlAnswer){
            if(!$scope.newUser || !$scope.newUser.login)  $("#login").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.lastName) $("#lastName").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.firstName)  $("#firstName").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.password)  $("#password").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.passwordReply)  $("#passwordReply").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.controlQuestionSelected)  $("#controlQuestion").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.controlAnswer)  $("#controlAnswer").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.email)  $("#email").addClass('ng-invalids');
            setTimeout(function(){
                $("#firstName").removeClass('ng-invalids');$("#lastName").removeClass('ng-invalids');$("#login").removeClass('ng-invalids');
                $("#password").removeClass('ng-invalids');$("#passwordReply").removeClass('ng-invalids');
                $("#controlQuestion").removeClass('ng-invalids');$("#controlAnswer").removeClass('ng-invalids');$("#email").removeClass('ng-invalids');
            },2500);
        } else {
            userService.sendRegistrationCode($scope.newUser.email).then(function(response) {
                if (response.isSuccess) {
                    //infoService.infoFunction(response.object, "Информация");
                    $scope.step = 2;
                } else {
                    infoService.infoFunction(response.message, "Ошибка отправки кода подтверждения!");
                };
            })
        }

    }

    $scope.goToLogin = function(){
        $location.path('/login');
    }

    $scope.continueRegistration = function(){

        if(!$scope.newUser || !$scope.newUser.firstName || !$scope.newUser.lastName || !$scope.newUser.login || !$scope.newUser.password
            || !$scope.newUser.passwordReply || $scope.activationCode == "" || !$scope.newUser.controlQuestionSelected || !$scope.newUser.controlAnswer){
            if(!$scope.newUser || !$scope.newUser.login)  $("#login").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.lastName) $("#lastName").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.firstName)  $("#firstName").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.password)  $("#password").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.passwordReply)  $("#passwordReply").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.controlQuestionSelected)  $("#controlQuestion").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.controlAnswer)  $("#controlAnswer").addClass('ng-invalids');
            if(!$scope.newUser || !$scope.newUser.email)  $("#email").addClass('ng-invalids');
            if($scope.activationCode == "")  $("#code").addClass('ng-invalids');
            setTimeout(function(){
                $("#firstName").removeClass('ng-invalids');$("#lastName").removeClass('ng-invalids');$("#login").removeClass('ng-invalids');
                $("#password").removeClass('ng-invalids');$("#passwordReply").removeClass('ng-invalids');
                $("#controlQuestion").removeClass('ng-invalids');$("#code").removeClass('ng-invalids');$("#controlAnswer").removeClass('ng-invalids');$("#email").removeClass('ng-invalids');
            },2500);
        } else {
            if($scope.newUser.password == $scope.newUser.passwordReply) {
                var newUser = $scope.newUser;
                newUser.controlQuestion = $scope.newUser.controlQuestionSelected.question;
                newUser.passwordReply  = undefined;
                userService.registration(newUser, $scope.activationCode.toString()).then(function (response) {
                    if (response.isSuccess) {
                        $scope.step = 3;
                        //infoService.infoFunction(response.object, "Информация");
                    } else {
                        infoService.infoFunction(response.message, "Ошибка регистарции!")
                    };
                }).catch(function (response) {
                    infoService.infoFunction("Сервер временно не доступен.", "Ошибка регистарции!")
                });
            } else {
                infoService.infoFunction("Пароли не совпадают!", "Ошибка регистарции!")

            }
        }
    }


});
