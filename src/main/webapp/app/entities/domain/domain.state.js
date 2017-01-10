(function() {
    'use strict';

    angular
        .module('eheartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('domain', {
            parent: 'entity',
            url: '/domain?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.domain.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/domain/domains.html',
                    controller: 'DomainController',
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
                    $translatePartialLoader.addPart('domain');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('domain-detail', {
            parent: 'entity',
            url: '/domain/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.domain.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/domain/domain-detail.html',
                    controller: 'DomainDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('domain');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Domain', function($stateParams, Domain) {
                    return Domain.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'domain',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('domain-detail.edit', {
            parent: 'domain-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/domain/domain-dialog.html',
                    controller: 'DomainDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Domain', function(Domain) {
                            return Domain.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('domain.new', {
            parent: 'domain',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/domain/domain-dialog.html',
                    controller: 'DomainDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                domainPlaceholder1: null,
                                domainPlaceholder2: null,
                                domainPlaceholder3: null,
                                createdDate: null,
                                createdBy: null,
                                lastModifiedDate: null,
                                lastModifiedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('domain', null, { reload: 'domain' });
                }, function() {
                    $state.go('domain');
                });
            }]
        })
        .state('domain.edit', {
            parent: 'domain',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/domain/domain-dialog.html',
                    controller: 'DomainDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Domain', function(Domain) {
                            return Domain.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('domain', null, { reload: 'domain' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('domain.delete', {
            parent: 'domain',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/domain/domain-delete-dialog.html',
                    controller: 'DomainDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Domain', function(Domain) {
                            return Domain.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('domain', null, { reload: 'domain' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
