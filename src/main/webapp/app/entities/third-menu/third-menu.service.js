(function() {
    'use strict';
    angular
        .module('eheartApp')
        .factory('ThirdMenu', ThirdMenu);

    ThirdMenu.$inject = ['$resource', 'DateUtils'];

    function ThirdMenu ($resource, DateUtils) {
        var resourceUrl =  'api/third-menus/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
