(function() {
    'use strict';

    angular
        .module('eheartApp')
        .factory('DomainSearch', DomainSearch);

    DomainSearch.$inject = ['$resource'];

    function DomainSearch($resource) {
        var resourceUrl =  'api/_search/domains/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
