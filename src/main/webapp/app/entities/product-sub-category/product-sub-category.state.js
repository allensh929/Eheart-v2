(function() {
    'use strict';

    angular
        .module('eheartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('product-sub-category', {
            parent: 'entity',
            url: '/product-sub-category?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.productSubCategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-sub-category/product-sub-categories.html',
                    controller: 'ProductSubCategoryController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('productSubCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('product-sub-category-detail', {
            parent: 'entity',
            url: '/product-sub-category/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.productSubCategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-sub-category/product-sub-category-detail.html',
                    controller: 'ProductSubCategoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('productSubCategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProductSubCategory', function($stateParams, ProductSubCategory) {
                    return ProductSubCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'product-sub-category',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('product-sub-category-detail.edit', {
            parent: 'product-sub-category-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-sub-category/product-sub-category-dialog.html',
                    controller: 'ProductSubCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProductSubCategory', function(ProductSubCategory) {
                            return ProductSubCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-sub-category.new', {
            parent: 'product-sub-category',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-sub-category/product-sub-category-dialog.html',
                    controller: 'ProductSubCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                img: null,
                                subCategoryPlaceholder1: null,
                                subCategoryPlaceholder2: null,
                                createdDate: null,
                                createdBy: null,
                                lastModifiedDate: null,
                                lastModifiedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('product-sub-category', null, { reload: 'product-sub-category' });
                }, function() {
                    $state.go('product-sub-category');
                });
            }]
        })
        .state('product-sub-category.edit', {
            parent: 'product-sub-category',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-sub-category/product-sub-category-dialog.html',
                    controller: 'ProductSubCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProductSubCategory', function(ProductSubCategory) {
                            return ProductSubCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-sub-category', null, { reload: 'product-sub-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-sub-category.delete', {
            parent: 'product-sub-category',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-sub-category/product-sub-category-delete-dialog.html',
                    controller: 'ProductSubCategoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProductSubCategory', function(ProductSubCategory) {
                            return ProductSubCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-sub-category', null, { reload: 'product-sub-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
