(function () {
  angular
    .module('frontend')
    .controller('ProfileController', ProfileController);

  /** @ngInject */
  function ProfileController($scope, $http,ngDialog,UtilsFunctionsFactory) {
    var vm = this;

    $scope.getAllSellers = function () {
      var promise = $http.get("../../data/sellers.json");
      promise.then(fulfilled, rejected);

      function fulfilled(response) {
        console.log(response);
      }

      function rejected(err) {
        console.log(err);
      }
    }
  }
})();
