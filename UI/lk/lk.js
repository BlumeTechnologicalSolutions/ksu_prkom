'use strict';

var lk = angular.module('myApp.lk', ['ngRoute']);

lk.controller('AbiturientCtrl', function($scope, userService) {


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
        if(!loginUser || !loginUser.login || !loginUser.password) {
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
            userService.Authorize(loginUser, $scope.rememberMe).then(function(response) {
                if (response.isSuccess) {
                    console.log(response.object)
                } else {
                    alert(response.message);
                };
            }).catch(function (response) {
                alert("Сервер авторизации не доступен. Обратитесь к администратору. With message:"+response);
            });
        }
    };

    $scope.createAccount = function(){

    }

});
