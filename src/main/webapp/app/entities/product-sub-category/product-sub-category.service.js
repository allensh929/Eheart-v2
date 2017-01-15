(function() {
    'use strict';
    angular
        .module('eheartApp')
        .factory('ProductSubCategory', ProductSubCategory);

    ProductSubCategory.$inject = ['$resource', 'DateUtils'];

    function ProductSubCategory ($resource, DateUtils) {
        var resourceUrl =  'api/product-sub-categories/:id';

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
