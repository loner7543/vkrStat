(function () {

  angular
    .module('frontend')
    .controller('SaleController', SaleController);

  /** @ngInject */
  function SaleController($scope, $http,ngDialog,UtilsFunctionsFactory) {
    var vm = this;
    $scope.orderDate="";
    $scope.deliveryDate = "";
    $scope.amountProduct="";
    $scope.showAddDiv = false;

    $scope.getAllSells = function () {
      var promise = $http.get("../../data/sells.json");
      promise.then(fulfilled, rejected)
    };

    function fulfilled(response) {
      console.log(response);
      for (var i = 0;i<response.data.length;i++){
        var formattedOrderDate = UtilsFunctionsFactory.toDate(response.data[i].orderDate);
        var formattedDeliveryDate = UtilsFunctionsFactory.toDate(response.data[i].deliveryDate);
        response.data[i].orderDate = formattedOrderDate;
        response.data[i].deliveryDate = formattedDeliveryDate;
      }
      $scope.sales = response.data;
      var products = [];
      for (var i= 0;i<$scope.sales.length;i++){
        var productsS = $scope.sales[i].products;
        console.log(productsS);
        for(var j =0;j<productsS.length;j++){
          products.unshift(productsS[j]);
        }
      }
      $scope.salesProducts = products;

      var buyers = [];
      for (var i= 0;i<$scope.sales.length;i++){
        var buyer = $scope.sales[i].buyer;
        buyers.unshift(buyer);
      }
      $scope.salesBuyers = buyers;// все покупатели со всех продаж
    }

    function rejected(err) {
      console.log(err);
    }

    $scope.addNewSale = function () {
      ngDialog.open({ template: 'app/sale/addSaleDialog.html',
        className: 'ngdialog-theme-default',
        scope: $scope
      });
    };

    $scope.editSale = function () {
      ngDialog.open({ template: 'app/sale/addSaleDialog.html',
        className: 'ngdialog-theme-default',
        scope: $scope
      });
    }

    $scope.deleteSale = function () {

    }

    $scope.showAddDiv = function () {
      this.showAddDiv = !this.showAddDiv;
    }
  }
})();
