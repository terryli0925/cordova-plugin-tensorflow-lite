# cordova-plugin-tensorflow-lite
TensorFlow Lite text classification for Cordova

## Installation

### Cordova
```bash
cordova plugin add https://github.com/terryli0925/cordova-plugin-tensorflow-lite
```

## Supported Platforms
 * Android
 * iOS (Not implement yet)

## Basic usage
```javascript
tensorflowlite.load(
    () =>
        tensorflowlite.classify(
          'text',
          data => console.log(data),
          err => console.error(err)
        ),
    err => console.error(err)
);
```

## Method

### load
Load the TF Lite model, dictionary and labels.
```javascript
tensorflowlite.load(successCallback, errorCallback);
```
### unLoad
Unload the TF Lite to free up resources.
```javascript
tensorflowlite.unLoad(successCallback, errorCallback);
```
### classify
Classify an input string and returns the classification results.
```javascript
tensorflowlite.classify(text, successCallback, errorCallback);
```
