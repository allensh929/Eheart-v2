(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ProductSubCategoryDialogController', ProductSubCategoryDialogController);

    ProductSubCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductSubCategory', 'ProductCategory', 'Upload', 'Ahdin'];

    function ProductSubCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProductSubCategory, ProductCategory, Upload, Ahdin) {
        var vm = this;

        vm.productSubCategory = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.productcategories = ProductCategory.query();
        vm.onFileSelect = onFileSelect;

        function onFileSelect (uploadFile){

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
                    vm.productSubCategory.img = data.file;

                }).error(function (data, status, headers, config) {

                    console.log('error status: ' + status);
                });
            };


            //TODO gif no compress
            if (uploadFile != null) {
                Ahdin.compress({
                    sourceFile: uploadFile,
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
