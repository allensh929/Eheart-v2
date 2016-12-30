(function() {
    'use strict';

    angular
        .module('eheartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('third-menu', {
            parent: 'entity',
            url: '/third-menu?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.thirdMenu.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/third-menu/third-menus.html',
                    controller: 'ThirdMenuController',
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
                    $translatePartialLoader.addPart('thirdMenu');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('third-menu-detail', {
            parent: 'entity',
            url: '/third-menu/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.thirdMenu.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/third-menu/third-menu-detail.html',
                    controller: 'ThirdMenuDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('thirdMenu');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ThirdMenu', function($stateParams, ThirdMenu) {
                    return ThirdMenu.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'third-menu',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('third-menu-detail.edit', {
            parent: 'third-menu-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/third-menu/third-menu-dialog.html',
                    controller: 'ThirdMenuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThirdMenu', function(ThirdMenu) {
                            return ThirdMenu.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('third-menu.new', {
            parent: 'third-menu',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/third-menu/third-menu-dialog.html',
                    controller: 'ThirdMenuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                seq: null,
                                link: null,
                                createdDate: null,
                                createdBy: null,
                                lastModifiedDate: null,
                                lastModifiedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('third-menu', null, { reload: 'third-menu' });
                }, function() {
                    $state.go('third-menu');
                });
            }]
        })
        .state('third-menu.edit', {
            parent: 'third-menu',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/third-menu/third-menu-dialog.html',
                    controller: 'ThirdMenuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ThirdMenu', function(ThirdMenu) {
                            return ThirdMenu.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('third-menu', null, { reload: 'third-menu' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('third-menu.delete', {
            parent: 'third-menu',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/third-menu/third-menu-delete-dialog.html',
                    controller: 'ThirdMenuDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ThirdMenu', function(ThirdMenu) {
                            return ThirdMenu.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('third-menu', null, { reload: 'third-menu' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
