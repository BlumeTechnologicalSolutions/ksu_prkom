'use strict';

var lk = angular.module('myApp.lk', ['ngRoute']);

lk.controller('AbiturientCtrl', function($scope, userService, $location, $rootScope) {

    if (userService.checkToken()) {
        getUserByToken();
    };

    $rootScope.user = userService.User;
    function getUserByToken() {
        if(!userService.User) {
            userService.GetUserByToken().then(function (response) {
                if (response.isSuccess) {
                    userService.User = JSON.parse(response.object);
                    if (userService.User) {
                        $rootScope.user = userService.User;
                        tryDigest();
                    }
                } else {
                    alert(response.message);
                }
            });
        } else {
            $rootScope.user = userService.User;
            tryDigest();
        }
    };

    function tryDigest() {
        if(!$rootScope.$$phase) {
            $rootScope.$digest();
        };
    }

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
            if(!userService.User) {
                userService.Authorize(loginUser).then(function (response) {
                    if (response.isSuccess) {
                        userService.User = JSON.parse(response.object);
                        if (userService.User && userService.User.token) {
                            $rootScope.user = userService.User;
                            userService.setCookie("token", userService.User.token, 14);
                            tryDigest();
                        }

                    } else {
                        alert(response.message);
                    }
                }).catch(function (response) {
                    alert("Сервер авторизации не доступен. Обратитесь к администратору. With message:" + response);
                });
            } else {
                $rootScope.user = userService.User;
            }
        };
    };

});
