(function() {
    'use strict';
    angular
        .module('eheartApp')
        .factory('SubMenu', SubMenu);

    SubMenu.$inject = ['$resource', 'DateUtils'];

    function SubMenu ($resource, DateUtils) {
        var resourceUrl =  'api/sub-menus/:id';

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
