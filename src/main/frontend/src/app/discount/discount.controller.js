(function () {

  angular
    .module('frontend')
    .controller('DiscountController', DiscountController);

  /** @ngInject */
  function DiscountController($scope, $http,UtilsFunctionsFactory,ngDialog) {
    var vm = this;
    $scope.amountDiscount="";
    $scope.sendRequest = function () {
      var promise = $http.get("../../data/discounts.json");
      promise.then(fulfilled, rejected)
    };

    function fulfilled(resp) {
      console.log(resp.data);
      for (var i = 0;i<resp.data.length;i++){
        var formattedFromDate = UtilsFunctionsFactory.toDate(resp.data[i].actualFrom);
        var formattedToDate = UtilsFunctionsFactory.toDate(resp.data[i].actualTo);
        resp.data[i].actualTo = formattedToDate;
        resp.data[i].actualFrom = formattedFromDate;
      }
      $scope.discounts = resp.data;
    }

    function rejected(errror) {
      console.log(errror)
    }

    $scope.addDiscount = function() {
      ngDialog.open({ template: 'app/discount/addDiscount.html',
        className: 'ngdialog-theme-default',
        scope: $scope
      });
    }

    $scope.editDiscount = function () {
      ngDialog.open({ template: 'app/discount/addDiscount.html',
        className: 'ngdialog-theme-default',
        scope: $scope
      });
    }

    $scope.deleteDiscount = function () {

    }

  }
})();
