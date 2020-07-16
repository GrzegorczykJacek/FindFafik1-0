let articleApp = angular.module('articleApp', []);

articleApp.controller('articleController', function ($scope, $http) {
    $scope.init = function () {
        $http({
            method: 'GET',
            url: 'api/articles',
            params: {}
        }).then(
            function (response) {
                $scope.articles = response.data
            }
        );

        $http({
            method: 'GET',
            url: 'https://api.openweathermap.org/data/2.5/weather',
            params: {
                q: 'Lublin,pl',
                appid: '3e6e6c41294c357b0c646eb63d15bac9',
                units: 'metric'
            }
        }).then(
            function (response) {
                $scope.weather = response.data
            }
        );


    };



});