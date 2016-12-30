(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ProductCategoryDialogController', ProductCategoryDialogController);

    ProductCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductCategory', 'Product'];

    function ProductCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProductCategory, Product) {
        var vm = this;

        vm.productCategory = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.productCategory.id !== null) {
                ProductCategory.update(vm.productCategory, onSaveSuccess, onSaveError);
            } else {
                ProductCategory.save(vm.productCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eheartApp:productCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
