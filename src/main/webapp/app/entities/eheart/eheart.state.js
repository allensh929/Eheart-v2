(function() {
    'use strict';

    angular
        .module('eheartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('eheart', {
            parent: 'entity',
            url: '/eheart?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.eheart.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/eheart/ehearts.html',
                    controller: 'EheartController',
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
                    $translatePartialLoader.addPart('eheart');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('eheart-detail', {
            parent: 'entity',
            url: '/eheart/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.eheart.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/eheart/eheart-detail.html',
                    controller: 'EheartDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('eheart');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Eheart', function($stateParams, Eheart) {
                    return Eheart.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'eheart',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('eheart-detail.edit', {
            parent: 'eheart-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eheart/eheart-dialog.html',
                    controller: 'EheartDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Eheart', function(Eheart) {
                            return Eheart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eheart.new', {
            parent: 'eheart',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eheart/eheart-dialog.html',
                    controller: 'EheartDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                logo: null,
                                email: null,
                                phone: null,
                                fax: null,
                                address: null,
                                wechat: null,
                                copyright: null,
                                eheartPlaceholder1: null,
                                eheartPlaceholder2: null,
                                eheartPlaceholder3: null,
                                createdDate: null,
                                createdBy: null,
                                lastModifiedDate: null,
                                lastModifiedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('eheart', null, { reload: 'eheart' });
                }, function() {
                    $state.go('eheart');
                });
            }]
        })
        .state('eheart.edit', {
            parent: 'eheart',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eheart/eheart-dialog.html',
                    controller: 'EheartDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Eheart', function(Eheart) {
                            return Eheart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eheart', null, { reload: 'eheart' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eheart.delete', {
            parent: 'eheart',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eheart/eheart-delete-dialog.html',
                    controller: 'EheartDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Eheart', function(Eheart) {
                            return Eheart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eheart', null, { reload: 'eheart' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
