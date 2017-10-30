(function () {
  angular
    .module('frontend')
    .controller('ItemController', ItemController);

  /** @ngInject */
  function ItemController($scope,$http,UtilsFunctionsFactory,ngDialog,$state/*allProducts*/) {
    var vm  =this;
    // $scope.items = allProducts.data;
    vm.UtilsFunctionsFactory = UtilsFunctionsFactory;
    $scope.showAddDiv = false;
    $scope.productName = '';
    $scope.unitCoast = "";
    $scope.unitName = "";

    $scope.modalShown = false;

    $scope.addItem = function() {
      //$scope.productName="";
      var data = {
        name:"ProductName",
        unitCoast:"7",
        unitName:"шт"
      };
       $http({
         method:"POST",
         url:"http://localhost:8080/crudGoods/rest/saveProduct",
         params:data
       }).then(function (resp) {
         debugger;
        console.log("resp", resp)
       }, function (result) {
         debugger;
         console.error(result, result.data);
       });
      //ngDialog.open({ template: 'app/item/addItem.html',
      //  className: 'ngdialog-theme-default',
      //  scope: $scope
      //});
    }

    $scope.sendRequest = function () {
      var promise = $http.get("../../data/products.json");
      promise.then(fulfilled, rejected)
    };

    function fulfilled(response) {
      console.log(response);
      $scope.items = response.data;
      var discountMas = [];
      for(var i = 0;i<$scope.items.length;i++){
        var discounts = $scope.items[i].discounts;
        console.log(discounts);
        for(var j =0;j<discounts.length;j++){
          var actualFromDate = UtilsFunctionsFactory.toDate(discounts[j].actualFrom);
          var actualToDate = UtilsFunctionsFactory.toDate(discounts[j].actualTo);
          discounts[j].actualFrom = actualFromDate;
          discounts[j].actualTo = actualToDate;
          discountMas.unshift(discounts[j])
        }
      }
      $scope.discountsMas=discountMas
    }

    function rejected(err) {
      console.log(err);
    }

    vm.addProductClickHandler = function () {
      alert("qwe");
    };

    $scope.editProductHandler = function () {
      $scope.productName = "qwe";
      ngDialog.open({ template: 'app/item/addItem.html',
        className: 'ngdialog-theme-default',
        scope: $scope
      });
    };

    $scope.deleteProductHandler = function () {
      alert("delete")
    }

    $scope.rowClick = function () {
      alert("dedede");
    }

    $scope.showHide1 = function () {
      this.showAddDiv = !this.showAddDiv;
    }
  }
})();
