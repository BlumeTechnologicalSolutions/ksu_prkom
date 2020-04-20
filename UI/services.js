
var services = angular.module('myApp.services', ['ngRoute']);

services.factory('infoService', function ($uibModal) {
    var service = {};

    service.infoFunction = function(text, title) {
        var modalInstance = $uibModal.open({
            templateUrl: 'modalWindows/InfoModal/infoModal.html',
            controller: 'InfoModalWindowCtrl',
            size: 'size',
            resolve: {
                element: function () {
                    return text;
                },
                title: function () {
                    return title;
                }
            }
        });
    };

    service.openConfirmationModal = function(title, text) {
        var props = {
            animation: true,
            backdrop: 'static',
            keyboard: false,
            templateUrl: 'modalWindows/ConfirmationModal/confirmationModal.html',
            controller: 'ConfirmationModalCtrl',
            resolve: {
                title: function () {
                    return title;
                },
                text: function () {
                    return text;
                }
            }
        };

        return $uibModal.open(props);
    };

    return service;
});

services.factory('datesService', function () {
    var service = {};

    service.getSmallMonth = function(date) {
        switch (date.getMonth()) {
            case 0:
                return "янв";
            case 1:
                return "фев";
            case 2:
                return "мар";
            case 3:
                return "апр";
            case 4:
                return "май";
            case 5:
                return "июн";
            case 6:
                return "июл";
            case 7:
                return "авг";
            case 8:
                return "сен";
            case 9:
                return "окт";
            case 10:
                return "ноя";
            case 11:
                return "дек";
        };
    };

    service.getShortDayOfWeekName = function(dayNumber) {
        switch (dayNumber) {
            case 0:
                return "Вскр";
            case 1:
                return "Пн";
            case 2:
                return "Вт";
            case 3:
                return "Ср";
            case 4:
                return "Чт";
            case 5:
                return "Пт";
            case 6:
                return "Сб";
        };
    };

    service.parseDateToStringSmall = function(date) {
        var date = new Date(date);
        var month = date.getMonth()+1;
        month < 10 ? month = "0" + month : "";
        var day = date.getDate();
        day < 10 ? day = "0" + day : "";
        return day + '.' + month;
    };

    service.parseDateToStringRussian = function(date) {
        var date = new Date(date);
        var month = date.getMonth()+1;
        month < 10 ? month = "0" + month : "";
        var day = date.getDate();
        day < 10 ? day = "0" + day : "";
        return day + '.' + month + '.' + date.getFullYear();
    };

    service.parseDateToString = function(date) {
        var date = new Date(date);
        var month = date.getMonth()+1;
        month < 10 ? month = "0" + month : "";
        var day = date.getDate();
        day < 10 ? day = "0" + day : "";
        return date.getFullYear() + '-' + month + '-' + day;
    };

    service.getDateClass = function(date, productionCalendarList) {
        if (date.getDate()==new Date().getDate() && date.getMonth()==new Date().getMonth() && date.getYear()==new Date().getYear()) return "info";
        else {
            if (productionCalendarList) {
                var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
                var month = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
                var dateFromReport = date.getFullYear() + '-' + month + '-' + day;
                var dateFromList = productionCalendarList[productionCalendarList.map(function (e){return e.wDate;}).indexOf(dateFromReport)];
                if (dateFromList) {
                    if (dateFromList.isDAyOff == true) {
                        return "danger";
                    };
                };
            } else {
                var day = date.getDay();
                if (day==0 || day==6) {
                    return "danger";
                };
            };
        };
        return "";
    };

    service.isWeekendDate = function(date, productionCalendarList) {
        if (productionCalendarList) {
            var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
            var month = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
            var dateFromReport = date.getFullYear() + '-' + month + '-' + day;
            var dateFromList = productionCalendarList[productionCalendarList.map(function (e){return e.wDate;}).indexOf(dateFromReport)];
            if (dateFromList) {
                if (dateFromList.isDAyOff == true) {
                    return true;
                };
            };
        } else {
            var day = date.getDay();
            if (day==0 || day==6) {
                return true;
            };
        };
        return false;
    };

    service.getTimeStringByDate = function(date) {
        if (date) {
            var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
            var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
            var seconds = "00";
            return hours + ":" + minutes;
        };
        return null;
    };

    service.isEqualDates = function(date1, date2) {
        if (date1, date2) {
            var d1 = new Date(date1);
            var d2 = new Date(date2);
            if (d1.getFullYear()==d2.getFullYear() && d1.getMonth()==d2.getMonth() && d1.getDate()==d2.getDate()) {
                return true;
            };
        };
        return false;
    };

    service.isFirstDateBeforeSecondDate = function(date1, date2) {
        if (date1, date2) {
            var d1 = new Date(date1);
            d1.setHours(0);
            d1.setMinutes(0);
            d1.setSeconds(0);
            d1.setMilliseconds(0);
            var d2 = new Date(date2);
            d2.setHours(0);
            d2.setMinutes(0);
            d2.setSeconds(0);
            d2.setMilliseconds(0);
            if (d1.getTime()<d2.getTime()) {
                return true;
            };
        };
        return false;
    };

    service.getDateWithoutTime = function(date) {
        var d = new Date(date);
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);
        d.setMilliseconds(0);
        return d;
    };

    service.getPrettyRussianDateStringByDate = function (date) {
        switch (date.getMonth()) {
            case 0:
                return date.getDate() + " января " + date.getFullYear();
            case 1:
                return date.getDate() + " февраля " + date.getFullYear();
            case 2:
                return date.getDate() + " марта " + date.getFullYear();
            case 3:
                return date.getDate() + " апреля " + date.getFullYear();
            case 4:
                return date.getDate() + " мая " + date.getFullYear();
            case 5:
                return date.getDate() + " июня " + date.getFullYear();
            case 6:
                return date.getDate() + " июля " + date.getFullYear();
            case 7:
                return date.getDate() + " августа " + date.getFullYear();
            case 8:
                return date.getDate() + " сентября " + date.getFullYear();
            case 9:
                return date.getDate() + " октября " + date.getFullYear();
            case 10:
                return date.getDate() + " ноября " + date.getFullYear();
            case 11:
                return date.getDate() + " декабря " + date.getFullYear();
        };
    };

    return service;
});

services.filter('orderObjectBy', function() {
    return function(items, field, reverse) {
        var filtered = [];
        var nullObjects = [];

        angular.forEach(items, function(item) {
            if(item[field]) filtered.push(item);
            else nullObjects.push(item);
        });

        filtered.sort(function (a, b) {
            return (a[field] > b[field] ? 1 : -1);
        });

        if(reverse) filtered.reverse();

        angular.forEach(nullObjects, function(item) {
            filtered.push(item);
        });

        return filtered;
    };
});

myApp.factory('userProfile', function($http, $window, $q) {

    var service = {};

    service.getUserEducation = function(userId) {
        var deferred = $q.defer();
        $http.get(ipAdress + '/ksu-prkom-rest/UserProfile/getUserEducation/').success(function(response){
            deferred.resolve(response);
        }).error(function(){
            deferred.reject('Error in getUserEducation user service function');
        });
        return deferred.promise;
    };

    service.saveUserEducation = function(userEducation) {
        var deferred = $q.defer();
        $http.post(ipAdress + '/ksu-prkom-rest/UserProfile/saveUserEducation/',userEducation).success(function(response){
            deferred.resolve(response);
        }).error(function(){
            deferred.reject('Error in saveUserEducation user service function');
        });
        return deferred.promise;
    };


    return service;

})


myApp.factory('userService', function($http, $window, $q, $location, $rootScope) {

    var service = {};

    if (!document.cookie) {
        service = { User: undefined };
    };

    function getCookie(name) {
        //to do check for getting token
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length == 2)
            return parts.pop().split(";").shift();
        else
            return null;
    }

    service.checkToken = function(){
        var token = getCookie("token");
        if(token){
            return true;
        }
    }

    service.setCookie = function(name,value,days){
        setCookie(name,value,days);
    };

    function setCookie(name,value,days){
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days*24*60*60*1000));
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + (value || "")  + expires + "; path=/";
    }

    service.GetUserByToken = function() {
        var deferred = $q.defer();
        $http.get(ipAdress + '/ksu-prkom-rest/UserService/getUserByToken' ).success(function(user){
            deferred.resolve(user);
        }).error(function(){
            service.User = undefined;
            $location.path('/main');
        });
        return deferred.promise;
    };

    service.Authorize = function(user) {
        var deferred = $q.defer();
        $http.post(ipAdress + '/ksu-prkom-rest/UserService/authorization', user).success(function (autorizedUser) {
            deferred.resolve(autorizedUser);
        }).error(function (data) {
            service.User = undefined;
            $location.path('/main');
            deferred.reject('Error in authtorize user service function');
        });
        return deferred.promise;
    };

    service.resolveCheck = function() {
        var defered = $q.defer();
        var count = 0;
        var userInterval = setInterval(function () {
            count++;
            if (count == 4) {
                clearInterval(userInterval);
                service.GetUserByToken().then(function (response) {
                    if(response.isSuccess){
                        var user = response.object;
                        service.User = user;
                        defered.resolve(user);
                        tryDigest();
                    } else {
                        defered.resolve(null);
                    }
                }, function (reason) {
                    infoService.infoFunction("Сайт временно недосутпен, пожалуйста, обратитесь на сервис позже", "Ошибка подключения к серверу")
                    defered.resolve(null);
                });
            } else {
                if (service.User) {
                    clearInterval(userInterval);
                    var currentUser = service.User;
                    if (!currentUser.isDeleted) {
                        defered.resolve(null);
                    } else {
                        //$location.path('/login');
                    };
                    defered.resolve(service.User);
                    tryDigest();
                } else {
                    defered.resolve(null);
                }
            };
        }, 50);

        return defered.promise;
    };

    function tryDigest() {
        if(!$rootScope.$$phase) {
            $rootScope.$apply();
        };
    }

    service.sendRegistrationCode = function(email) {
        var deferred = $q.defer();
        $http.get(ipAdress + '/ksu-prkom-rest/UserService/sendRegistrationCode/'+email+'/').success(function(response){
            deferred.resolve(response);
        }).error(function(){
            deferred.reject('Error in sendRegistrationCode user service function');
        });
        return deferred.promise;
    };

    service.registration = function(newUser, activationCode) {
        var deferred = $q.defer();
        $http.post(ipAdress + '/ksu-prkom-rest/UserService/registration/'+activationCode+'/',newUser).success(function(response){
            deferred.resolve(response);
        }).error(function(){
            deferred.reject('Error in registration user service function');
        });
        return deferred.promise;
    };

    service.remember = function(email) {
        var deferred = $q.defer();
        $http.get(ipAdress + '/ksu-prkom-rest/UserService/remember/'+email+'/').success(function(response){
            deferred.resolve(response);
        }).error(function(){
            deferred.reject('Error in remember user service function');
        });
        return deferred.promise;
    };

    service.getRegistrationSecretQuestions = function() {
        var deferred = $q.defer();
        $http.get(ipAdress + '/ksu-prkom-rest/UserService/getRegistrationSecretQuestions').success(function(response){
            deferred.resolve(response);
        }).error(function(){
            deferred.reject('Error in getRegistrationSecretQuestions user service function');
        });
        return deferred.promise;
    };

    service.logOut = function() {
        service.User = undefined;
        setCookie("token", "", -1);
        $location.path("/main");
    };

    return service;
});
