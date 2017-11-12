(function () {
  angular
    .module('frontend')
    .controller('ProfileController', ProfileController);

  /** @ngInject */
  function ProfileController($scope, $http,UtilsFunctionsFactory) {
    var vm = this;

    $scope.calculateProfile=function () {
      $http({
        method: "POST",
        url: "http://localhost:8080/krugstat/rest/calculateProfile",
        params:{
          fileName:"BEN_120.DAT"
        }
      }).then(function (resp) {
          console.log("Профиль", resp)
          $scope.filrnames=resp.data;
        },
        function (result) {
          console.error(result, result.data);
        });
    }
  }
})();
