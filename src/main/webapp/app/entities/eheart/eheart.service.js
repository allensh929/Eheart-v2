(function() {
    'use strict';
    angular
        .module('eheartApp')
        .factory('Eheart', Eheart);

    Eheart.$inject = ['$resource', 'DateUtils'];

    function Eheart ($resource, DateUtils) {
        var resourceUrl =  'api/ehearts/:id';

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
