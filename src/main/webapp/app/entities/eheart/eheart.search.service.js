(function() {
    'use strict';

    angular
        .module('eheartApp')
        .factory('EheartSearch', EheartSearch);

    EheartSearch.$inject = ['$resource'];

    function EheartSearch($resource) {
        var resourceUrl =  'api/_search/ehearts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
