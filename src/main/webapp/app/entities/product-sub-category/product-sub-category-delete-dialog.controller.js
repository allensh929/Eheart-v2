(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ProductSubCategoryDeleteController',ProductSubCategoryDeleteController);

    ProductSubCategoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProductSubCategory'];

    function ProductSubCategoryDeleteController($uibModalInstance, entity, ProductSubCategory) {
        var vm = this;

        vm.productSubCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProductSubCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
