(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ProductSubCategoryDialogController', ProductSubCategoryDialogController);

    ProductSubCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductSubCategory', 'ProductCategory'];

    function ProductSubCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProductSubCategory, ProductCategory) {
        var vm = this;

        vm.productSubCategory = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.productcategories = ProductCategory.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.productSubCategory.id !== null) {
                ProductSubCategory.update(vm.productSubCategory, onSaveSuccess, onSaveError);
            } else {
                ProductSubCategory.save(vm.productSubCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eheartApp:productSubCategoryUpdate', result);
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
