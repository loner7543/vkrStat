(function () {
  'use strict';

  angular
    .module('frontend')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
     $stateProvider
       .state('products', {
         url: '/products',
         views: {
           'index': {
             templateUrl: 'app/item/item.html',
             controller: 'ItemController',
             controllerAs: 'itemCtrl'
           }
         },
         resolve: {
           // allProducts: function ($http) {
           //   return $http({
           //     method: "POST",
           //     url: "http://localhost:8080/crudGoods/rest/getProducts",
           //     params:{}
           //   });
           // }
         }
       })
       .state('buyers', {
       url: '/buyers',
       views: {
         'index': {
           templateUrl: 'app/buyer/buyer.html',
           controller: 'BuyerController',
           controllerAs: 'buyerCtrl'
         }
       },
       resolve: {}
     })
       .state('discounts', {
       url: '/discounts',
       views: {
         'index': {
           templateUrl: 'app/discount/discount.html',
           controller: 'DiscountController',
           controllerAs: 'discountCtrl'
         }
       },
       resolve: {}
     })
       .state('sales', {
         url: '/sales',
         views: {
           'index': {
             templateUrl: 'app/sale/sale.html',
             controller: 'SaleController',
             controllerAs: 'saleCtrl'
           }
         },
         resolve: {}
       })
       .state('sellers', {
         url: '/sellers',
         views: {
           'index': {
             templateUrl: 'app/seler/seller.html',
             controller: 'SellersController',
             controllerAs: 'sellerCtrl'
           }
         },
         resolve: {}
       });

    $urlRouterProvider.otherwise(function ($injector, $location) {
      console.log($injector, $location);
      return "/";
    });
  }

})();
