'use strict';

var lk = angular.module('myApp.lk', ['ngRoute']);

lk.controller('LkCtrl', function($scope, userService, $location, userProfile, infoService) {


    $scope.isError = false;
    $scope.sexes = [{'sex':'Мужской'},{'sex':'Женский'}];
    $scope.selectedPage = 'mainInfo';

    $scope.userRepresentative = {};
    $scope.userAddress = {};
    $scope.userEducation = {};
    $scope.userDocument = {};

    getInformation();

    function getInformation() {
        getUser();
        getUserDocuments();
        getUserEducation();
        getUserAddress();
        getUserRepresentative():
    }

    function setMasks(){
        $("#dateOfBirth").mask("99.99.9999", {placeholder: "дд.мм.гггг" });
        $("#documentReceiveDate").mask("99.99.9999", {placeholder: "дд.мм.гггг" });
        $("#oldDocumentReceiveDate").mask("99.99.9999", {placeholder: "дд.мм.гггг" });
        $("#representativeDate").mask("99.99.9999", {placeholder: "дд.мм.гггг" });
        $("#educationDate").mask("99.99.9999", {placeholder: "дд.мм.гггг" });
    };


    function getUser() {
        setTimeout(function () {
            if (userService.User) {
                $scope.user = userService.User;
                tryDigest();
                setTimeout(function () {
                    setMasks();
                },400);
            } else {
                $location.path('/main');
                tryDigest();
            }
        }, 400);
    }

    function getUserDocuments() {
        userProfile.getUserDocuments().then(function (response) {
            if(response.isSuccess){
                $scope.userDocument = response.object;
            } else {
                infoService.infoFunction(response.message, "Ошибка");
            }
        })
    }

    function getUserEducation() {
        userProfile.getUserEducation().then(function (response) {
            if(response.isSuccess){
                $scope.userEducation = response.object;
            } else {
                infoService.infoFunction(response.message, "Ошибка");
            }
        })
    }

    function getUserAddress() {
        userProfile.getUserAddress().then(function (response) {
            if(response.isSuccess){
                $scope.userAddress = response.object;
            } else {
                infoService.infoFunction(response.message, "Ошибка");
            }
        })
    }

    function getUserRepresentative() {
        userProfile.getUserRepresentative().then(function (response) {
            if(response.isSuccess){
                $scope.userRepresentative = response.object;
            } else {
                infoService.infoFunction(response.message, "Ошибка");
            }
        })
    }

    function tryDigest() {
        if(!$scope.$$phase) {
            $scope.$apply();
        };
    }

    $scope.setPage = function (selectedPage) {
        return 'lk/includePages/' + selectedPage + '.html';
    }

    $scope.setSelectedSex = function (sex) {
        $scope.sex = sex;
    };

    $scope.saveUserMainInfo = function(){
        if($scope.sex == $scope.sexes[0]) $scope.user.sex = true;
        if($scope.user && $scope.user.sex && $scope.user.contactPhone  && $scope.user.dateOfBirth
            && $scope.user.firstName && $scope.user.lastName  && $scope.user.foreignLanguage){
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
        var userDocument = JSON.parse(JSON.stringify($scope.userDocument));
        if(!userDocument.documentType || !userDocument.citizenship || !userDocument.series || !userDocument.documentNumber ||
            !userDocument.documentCode || !userDocument.receiveDate || !userDocument.receiveBy || !userDocument.placeOfBirth){
            userProfile.saveUserDocuments()

        } else {
            $scope.isUserDocumentError = true;
            setTimeout(function () {
                $scope.isUserDocumentError = false;
            }, 2500)
        }
        //userDocument.oldCitizenship
        //userDocument.oldDocumentType
        //userDocument.oldSeries
        //userDocument.oldDocumentNumber
        //userDocument.oldDocumentCode
        //userDocument.oldReceiveDate
        //userDocument.oldReceiveBy
        //userDocument.oldPlaceOfBirth
    }

    $scope.saveUserRepresentative = function () {
        var userRepresentative = JSON.parse(JSON.stringify($scope.userRepresentative));
        if(!userRepresentative.lastName || !userRepresentative.firstName ||  !userRepresentative.citizenship ||
            !userRepresentative.documentType || !userRepresentative.series || !userRepresentative.number || !userRepresentative.documentCode ||
            !userRepresentative.receiveDate || !userRepresentative.receiveBy || !userRepresentative.placeOfBirth){
            userProfile.saveUserRepresentative()
        } else {
            $scope.isUserRepresentativeError = true;
            setTimeout(function () {
                $scope.isUserRepresentativeError = false;
            }, 2500)
        }
    }

    $scope.saveUserAddress = function () {
        var userAddress = JSON.parse(JSON.stringify($scope.userAddress));
        if(!userAddress.country || !userAddress.region || !userAddress.localityType || !userAddress.localityName ||
            !userAddress.street || !userAddress.houseNumber || !userAddress.roomNumber){
            userProfile.saveUserAddress()
        } else {
            $scope.isUserAddressError = true;
            setTimeout(function () {
                $scope.isUserAddressError = false;
            }, 2500)
        }
    }

    $scope.saveUserEducation = function () {
        var userEducation = JSON.parse(JSON.stringify($scope.userEducation));
        if(!userEducation.documentType || !userEducation.series || !userEducation.number || !userEducation.receiveDate ||
            !userEducation.receiveBy || !userEducation.finishDate || !userEducation.country || !userEducation.region || !userEducation.district){
            userProfile.saveUserAddress()
        } else {
            $scope.isUserEducationError = true;
            setTimeout(function () {
                $scope.isUserEducationError = false;
            }, 2500)
        }
    }

});
