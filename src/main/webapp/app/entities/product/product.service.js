(function() {
    'use strict';
    angular
        .module('eheartApp')
        .factory('Product', Product);

    Product.$inject = ['$resource', 'DateUtils'];

    function Product ($resource, DateUtils) {
        var resourceUrl =  'api/products/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.produceDate = DateUtils.convertDateTimeFromServer(data.produceDate);
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
