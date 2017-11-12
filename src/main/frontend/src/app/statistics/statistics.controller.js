(function () {

  angular
    .module('frontend')
    .controller('StatisticsController', StatisticsController);

  /** @ngInject */
  function StatisticsController($scope, $http,ngDialog,UtilsFunctionsFactory) {
    var vm = this;

    $scope.calculateMainStatistics = function () {
      $http({
        method: "POST",
        url: "http://localhost:8080/krugstat/rest/calculate"
      }).then(function (resp) {
        debugger;
          console.log("Success resp1", resp.data)
          $scope.mainStatistics = resp.data;
          var amplitudes = resp.data.amplitudes;
          var xLabelData = 1;
          var xLabelValues =[];
          for(var i = 0;i<amplitudes.length;i++){
            xLabelValues.push(''+xLabelData);
            xLabelData++;
          }
          var layout = {
            title: 'Гистограмма амплитуд',
            showlegend: true,
            xaxis: {
              tickangle: -45
            },
            yaxis: {
              zeroline: false,
              gridwidth: 2
            },
            bargap :0.05

          };
          var graphData = [
            {
              x: xLabelValues,
              y:amplitudes,
              type: 'bar',
              name:'Амплитуды'
            }
          ];
          $scope.graphData = graphData;
          $scope.layout=layout;
        },
        function (result) {
          console.error(result, result.data);
        });

    }

    $scope.writeAmplitudes = function () {

    }

    $scope.readAmplitudes = function () {

    }

    $scope.writeHeights = function () {

    }

    $scope.readHeights = function () {

    }
    $scope.loadFile = function () {
      $http({
        method: "GET",
        url: "http://localhost:8080/krugstat/rest/Help",
        headers : {
          'Accept': 'application/json, */*'
        }
      }).then(function (resp) {
          console.log("Success resp1", resp)
        $scope.filrnames=resp.data;
        },
        function (result) {
          console.error(result, result.data);
        });
    };
  }
})();
