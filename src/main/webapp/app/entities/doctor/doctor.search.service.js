(function() {
    'use strict';

    angular
        .module('eheartApp')
        .factory('DoctorSearch', DoctorSearch);

    DoctorSearch.$inject = ['$resource'];

    function DoctorSearch($resource) {
        var resourceUrl =  'api/_search/doctors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
