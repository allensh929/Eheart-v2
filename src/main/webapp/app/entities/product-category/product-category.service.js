(function() {
    'use strict';
    angular
        .module('eheartApp')
        .factory('ProductCategory', ProductCategory);

    ProductCategory.$inject = ['$resource', 'DateUtils'];

    function ProductCategory ($resource, DateUtils) {
        var resourceUrl =  'api/product-categories/:id';

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
