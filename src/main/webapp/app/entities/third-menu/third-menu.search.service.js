(function() {
    'use strict';

    angular
        .module('eheartApp')
        .factory('ThirdMenuSearch', ThirdMenuSearch);

    ThirdMenuSearch.$inject = ['$resource'];

    function ThirdMenuSearch($resource) {
        var resourceUrl =  'api/_search/third-menus/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
