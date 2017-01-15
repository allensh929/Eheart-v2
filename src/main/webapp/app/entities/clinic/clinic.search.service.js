(function() {
    'use strict';

    angular
        .module('eheartApp')
        .factory('ClinicSearch', ClinicSearch);

    ClinicSearch.$inject = ['$resource'];

    function ClinicSearch($resource) {
        var resourceUrl =  'api/_search/clinics/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
