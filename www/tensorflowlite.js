module.exports = {
  load: function (successCallback, errorCallback) {
    cordova.exec(
      successCallback,
      errorCallback,
      'TensorFlowLitePlugin',
      'load',
      []
    );
  },
  unLoad: function (successCallback, errorCallback) {
    cordova.exec(
      successCallback,
      errorCallback,
      'TensorFlowLitePlugin',
      'unLoad',
      []
    );
  },
  classify: function (text, successCallback, errorCallback) {
    cordova.exec(
      successCallback,
      errorCallback,
      'TensorFlowLitePlugin',
      'classify',
      [text]
    );
  }
};
