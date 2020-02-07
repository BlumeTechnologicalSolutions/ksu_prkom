'use strict';

var remember = angular.module('myApp.remember', ['ngRoute']);

remember.controller('RememberCtrl', function($scope, userService) {

    $scope.rememberUser = {};

    $scope.remember = function () {
        if(!$scope.rememberUser || !$scope.rememberUser.login ){
            if(!$scope.rememberUser.login) { $("#inputLogin").focus();$("#inputLogin").css({"border": "1px solid red"});}
            setTimeout(function(){$("#inputLogin").css({"border": ""});},2000);
        } else {
            userService.remember($scope.rememberUser).then(function(user) {
                if (!user) {
                    console.log("ok")
                } else {
                    alert("Имя пользователя или пароль не верны. Используйте для авторизации ваши имя и пароль, используемые для входа в операционную систему.");
                };
            }).catch(function (response) {
                alert("Сервер авторизации не доступен. Обратитесь к администратору.");
            });
        }
    };

});
