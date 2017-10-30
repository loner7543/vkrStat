(function () {
  angular
    .module('frontend')
    .controller('SellersController', SellersController);

  /** @ngInject */
  function SellersController($scope, $http,ngDialog,UtilsFunctionsFactory) {
    var vm = this;
    $scope.sellerFirstName = "";
    $scope.sellerMiddleName="";
    $scope.sellerLastName="";
    $scope.birthDate = "";
    $scope.sellerEmail="";
    $scope.sellerDeliveryAddress="";

    $scope.getAllSellers = function () {
      var promise = $http.get("../../data/sellers.json");
      promise.then(fulfilled, rejected);

      function fulfilled(response) {
        console.log(response);
        for (var i = 0;i<response.data.length;i++){
          var orderDate = UtilsFunctionsFactory.toDate(response.data[i].sale.orderDate);// из продажи
          var deliveryDate = UtilsFunctionsFactory.toDate(response.data[i].sale.deliveryDate);// из продажи
          var formattedBirthDate = UtilsFunctionsFactory.toDate(response.data[i].birthDate);
          response.data[i].birthDate = formattedBirthDate;
          response.data[i].sale.orderDate = orderDate;
          response.data[i].sale.deliveryDate = deliveryDate;
        }
        $scope.sellers = response.data;
      }

      function rejected(err) {
        console.log(err);
      }

      $scope.addNewSeller = function () {
        ngDialog.open({ template: 'app/seler/addSellerDialog.html',
          className: 'ngdialog-theme-default',
          scope: $scope
        });
      };

      $scope.editSeller = function () {
        ngDialog.open({ template: 'app/seler/addSellerDialog.html',
          className: 'ngdialog-theme-default',
          scope: $scope
        });
      };

      $scope.deleteSeller = function () {

      }
    }
  }
})();
