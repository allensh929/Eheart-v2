(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ProductSubCategoryDetailController', ProductSubCategoryDetailController);

    ProductSubCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProductSubCategory', 'ProductCategory'];

    function ProductSubCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, ProductSubCategory, ProductCategory) {
        var vm = this;

        vm.productSubCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eheartApp:productSubCategoryUpdate', function(event, result) {
            vm.productSubCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
