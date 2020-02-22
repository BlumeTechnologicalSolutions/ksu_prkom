var confirmationModal = angular.module('myApp.confirmationModal', ['ngRoute', 'ui.bootstrap']);
confirmationModal.controller('ConfirmationModalCtrl', function ($scope, $modalInstance, $sce, title, text) {

    $scope.confirmationModalTitle = title;
    $scope.confirmationModalText = $sce.trustAsHtml(findAndReplaceLink(text.replace(/\r\n|\r|\n/g, " <br /> ")));

    $scope.ok = function () {
        $modalInstance.close();
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});