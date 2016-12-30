(function() {
    'use strict';

    angular
        .module('eheartApp')
        .factory('ProductCategorySearch', ProductCategorySearch);

    ProductCategorySearch.$inject = ['$resource'];

    function ProductCategorySearch($resource) {
        var resourceUrl =  'api/_search/product-categories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
