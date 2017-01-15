(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ThirdMenuDialogController', ThirdMenuDialogController);

    ThirdMenuDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThirdMenu', 'SubMenu'];

    function ThirdMenuDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThirdMenu, SubMenu) {
        var vm = this;

        vm.thirdMenu = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.submenus = SubMenu.query();

        $scope.imageUpload = imageUpload;

        function imageUpload (uploadFile){
            console.log('image upload:');
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
                    var imgURL = IMAGE_ROOT + data.file;
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
            if (vm.thirdMenu.id !== null) {
                ThirdMenu.update(vm.thirdMenu, onSaveSuccess, onSaveError);
            } else {
                ThirdMenu.save(vm.thirdMenu, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eheartApp:thirdMenuUpdate', result);
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
