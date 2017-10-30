(function () {

  angular
    .module('frontend')
    .controller('StatisticsController', StatisticsController);

  /** @ngInject */
  function StatisticsController($scope, $http,ngDialog,UtilsFunctionsFactory) {
    var vm = this;

    $scope.getAllSells = function () {
      var promise = $http.get("../../data/sells.json");
      promise.then(fulfilled, rejected)
    };

    function fulfilled(response) {
      console.log(response);
    }

    function rejected(err) {
      console.log(err);
    }
  }
})();
