'use strict';

var lk = angular.module('myApp.lk', ['ngRoute']);

lk.controller('LkCtrl', function($scope, userService, $location, userProfile, infoService) {

    getInformation();

    function getInformation() {
        getUser();

    }

    function getUser() {
        if (!userService.User) {
            $location.path('/login')
        } else {
            $scope.user = JSON.parse(JSON.stringify(userService.User));
        }
    }

    $scope.isError = false;
    $scope.sexes = [{'sex':'Мужской'},{'sex':'Женский'}];
    $scope.selectedPage = 'mainInfo';

    $scope.setPage = function (selectedPage) {
        return 'lk/includePages/' + selectedPage + '.html';
    }

    $scope.setSelectedSex = function (sex) {
        $scope.sex = sex;
    };

    setTimeout(function () {
        if($scope.user.sex) $scope.sex = $scope.sexes[0];
        else $scope.sex = $scope.sexes[0];
    },100);


    $scope.saveUserMainInfo = function(){
        if($scope.sex == $scope.sexes[0]) $scope.user.sex = true;
        if($scope.user && $scope.user.sex && $scope.user.contactPhone  && $scope.user.dateOfBirth
            && $scope.user.firstName && $scope.user.lastName && $scope.user.patronymic && $scope.user.foreignLanguage){
            var user = JSON.parse(JSON.stringify($scope.user));
            userProfile.saveUserMainInfo(user).then(function (response) {
                if(response.isSuccess){
                    var newUser = response.object;
                } else {
                    infoService.infoFunction(response.message, "Ошибка сохранения основной информации")
                }
            })
            console.log($scope.sex)
        } else {
            $scope.isError = true;
            setTimeout(function () {
                $scope.isError = false;
            }, 2500)
        }
    }

    $scope.saveUserDocuments = function(){
        // userDocument.documentType userDocument.citizenship userDocument.series  userDocument.documentNumber

    }

    $scope.saveUserRepresentative = function () {
        
    }

    $scope.saveUserAddress = function () {
        
    }
    
    $scope.saveUserEducation = function () {
        
    }

});
