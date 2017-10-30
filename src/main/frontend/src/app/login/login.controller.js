(function () {
  angular
    .module('frontend')
    .controller('LoginController', LoginController)

  /** @ngInject */
  function LoginController($scope, $http,$interval, $location,ngDialog,UtilsFunctionsFactory) {
    var vm = this;
    vm.UtilsFunctionsFactory = UtilsFunctionsFactory;

    $scope.getAllBuyers = function () {
     // var promise = $http.get($location.protocol() + '://' + $location.host() + ':'+ $location.port() + "/crudGoods/data/buyers.json");
       var promise = $http.get("../../data/buyers.json");
      promise.then(fulfilled, rejected)
    }

    function fulfilled(response){
      console.log(response)
    }

    function rejected(err) {
      console.log(err);
    }
  }

})();
// gttp data - тело запроса bodyParam
