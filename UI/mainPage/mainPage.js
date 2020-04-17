'use strict';

var mainPage = angular.module('myApp.mainPage', ['ngRoute']);

mainPage.controller('MainPageCtrl', function($scope, userService) {

    var userInterval = setInterval(function () {
        if (userService.User) {
            //$scope.user = userService.User;
            clearInterval(userInterval);

        }
    }, 150);


  /*  "months" : [ "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" ],
        "monthsShort" : [ "Янв", "Фев", "Мар", "Апр", "Май", "Июн",
        "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек" ],
        "weekdays" : [ "Воскресенье", "Понедельник","Вторник","Среда","Четверг",
        "Пятница","Суббота" ],
        "weekdaysShort" : [ "Вс","Пн","Вт","Ср","Чт",
        "Пт","Сб" ],
        "weekdaysMin" : [ "Вс","Пн","Вт","Ср","Чт",
        "Пт","Сб" ]*/
    $("#eventCalendar").simpleCalendar({
        //Defaults options below
        //string of months starting from january
        months: ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"],
        days: ["Воскресенье", "Понедельник","Вторник","Среда","Четверг",
            "Пятница","Суббота"],
        weekdaysShort : [ "Вс","Пн","Вт","Ср","Чт",
            "Пт","Сб" ],
        displayYear: true,              // Display year in header
        fixedStartDay: true,            // Week begin always by monday
        displayEvent: true,             // Display existing event
        events: [{
            startDate: 1587054220824,
            endDate: 1587054220824,
            summary: "tetst"
        }],                     // List of events
        onInit: function (calendar) {}, // Callback after first initialization
        onMonthChange: function (month, year) {}, // Callback on month change
        onDateSelect: function (date, events) {} // Callback on date selection
    });

});
