(function() {
    'use strict';

    angular
        .module('eheartApp')
        .factory('SubMenuSearch', SubMenuSearch);

    SubMenuSearch.$inject = ['$resource'];

    function SubMenuSearch($resource) {
        var resourceUrl =  'api/_search/sub-menus/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
