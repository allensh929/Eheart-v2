(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ProductCategoryDetailController', ProductCategoryDetailController);

    ProductCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProductCategory', 'ProductSubCategory', 'Product'];

    function ProductCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, ProductCategory, ProductSubCategory, Product) {
        var vm = this;

        vm.productCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eheartApp:productCategoryUpdate', function(event, result) {
            vm.productCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
