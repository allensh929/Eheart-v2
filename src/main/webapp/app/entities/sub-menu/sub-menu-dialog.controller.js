(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('SubMenuDialogController', SubMenuDialogController);

    SubMenuDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SubMenu', 'ThirdMenu', 'Menu', 'Upload', 'Ahdin'];

    function SubMenuDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SubMenu, ThirdMenu, Menu, Upload, Ahdin) {
        var vm = this;

        vm.subMenu = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.thirdmenus = ThirdMenu.query();
        vm.menus = Menu.query();
        $scope.imageUpload = imageUpload;

        function imageUpload (uploadFile){
            console.log('image upload:');
            console.log('image upload:' + $scope.editor);

            var uploadImageFile = function(compressedBlob) {
                Upload.upload({

                    url: '/api/upload',
                    fields: {},
                    file: compressedBlob,
                    method: 'POST'

                }).progress(function (evt) {

                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ');

                }).success(function (data, status, headers, config) {

                    //update the url
                    var imgURL = "http://localhost:8082/"+ data.file;
                    $scope.editor.summernote('insertImage', imgURL);

                }).error(function (data, status, headers, config) {

                    console.log('error status: ' + status);
                });
            };


            //TODO gif no compress
            if (uploadFile != null) {
                Ahdin.compress({
                    sourceFile: uploadFile[0],
                    maxWidth: 1280,
                    maxHeight:1000,
                    quality: 0.8
                }).then(function(compressedBlob) {
                    console.log('compressed image by ahdin.');
                    uploadImageFile(compressedBlob);
                });
            }


        }

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
