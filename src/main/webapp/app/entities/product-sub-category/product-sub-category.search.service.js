(function() {
    'use strict';

    angular
        .module('eheartApp')
        .factory('ProductSubCategorySearch', ProductSubCategorySearch);

    ProductSubCategorySearch.$inject = ['$resource'];

    function ProductSubCategorySearch($resource) {
        var resourceUrl =  'api/_search/product-sub-categories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
