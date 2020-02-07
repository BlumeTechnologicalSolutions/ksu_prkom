'use strict';

var loginPage = angular.module('myApp.lk', ['ngRoute']);

loginPage.controller('AbiturientCtrl', function($scope, userService) {


    //if (localStorage.token || sessionStorage.token) {
    //    getUserByToken();
    //};

    function getUserByToken() {
        userService.GetUserByToken().then(function (data) {
            if (userService.User) {
                console.log("ok")
            };
        });
    };

    $scope.authorization = function () {
        var loginUser = $scope.loginUser;
        if(!loginUser || !loginUser.login || !loginUser.password){
            if(!loginUser || !loginUser.login) { $("#inputLogin").focus();$("#inputLogin").css({"border": "1px solid red"});}
            if(!loginUser || !loginUser.password) {
                $("#inputPassword").css({"border": "1px solid red"});
                if(loginUser && loginUser.login) {
                    $("#inputPassword").focus();
                };
            };
            setTimeout(function(){
                $("#inputLogin").css({"border": ""});
                $("#inputPassword").css({"border": ""});
            },2000);
        } else {
            userService.Authorize(loginUser, $scope.rememberMe).then(function(user) {
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

    $scope.createAccount = function(){

    }

});
