(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('SubMenuDialogController', SubMenuDialogController);

    SubMenuDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SubMenu', 'ThirdMenu', 'Menu'];

    function SubMenuDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SubMenu, ThirdMenu, Menu) {
        var vm = this;

        vm.subMenu = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.thirdmenus = ThirdMenu.query();
        vm.menus = Menu.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subMenu.id !== null) {
                SubMenu.update(vm.subMenu, onSaveSuccess, onSaveError);
            } else {
                SubMenu.save(vm.subMenu, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eheartApp:subMenuUpdate', result);
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
