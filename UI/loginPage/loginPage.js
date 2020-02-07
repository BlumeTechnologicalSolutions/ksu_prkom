'use strict';

var loginPage = angular.module('myApp.loginPage', ['ngRoute']);

loginPage.controller('LoginPageController', function($scope, userService, $timeout) {

    $scope.loginUser = {};

    $scope.authorization = function () {
        var loginUser = $scope.loginUser;
        if(!loginUser || !loginUser.login || !loginUser.password){
            if(!loginUser || !loginUser.login) {
                $("#inputLogin").focus();$("#inputLogin").css({"border": "1px solid red"});
            }
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
            userService.Authorize(loginUser).then(function(user) {
                if (!user) {
                    console.log("ok")
                } else {
                    alert("Email или пароль не врены. Пожалуйста, проверьте данные и повторите попытку");
                };
            }).catch(function (response) {
                alert("Сервер временно не доступен.");
            });
        }
    };

    function getUserByToken() {
        userService.GetUserByToken().then(function (data) {
            if (userService.User) {
                console.log("ok")
            };
        });
    };

});
