package com.terryli.cordova;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import java.util.List;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.terryli.cordova.TextClassificationClient.Result;

public class TensorFlowLitePlugin extends CordovaPlugin {

    protected static final String TAG = "TensorFlowLitePlugin";

    private TextClassificationClient client;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        boolean result = true;
        try {
            if (action.equals("load")) {
                load(callbackContext);
            } else if (action.equals("unLoad")) {
                unLoad(callbackContext);
            } else if (action.equals("classify")) {
                classify(callbackContext, data.getString(0));
            } else {
                handleError(callbackContext, "Invalid action");
                result = false;
            }
        } catch (Exception e) {
            handleException(callbackContext, e);
            result = false;
        }
        return result;
    }

    private void load(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                client = new TextClassificationClient(cordova.getActivity().getApplicationContext());
                client.load();
                callbackContext.success();
            }
        });
    }

    private void unLoad(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                client.unload();
                callbackContext.success();
            }
        });
    }

    private void classify(CallbackContext callbackContext, final String text) throws JSONException {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                List<Result> results = client.classify(text);
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < results.size(); i++) {
                    Result result = results.get(i);
                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj.put("id", result.getId() != null ? result.getId() : "");
                        jsonObj.put("title", result.getTitle() != null ? result.getTitle() : "");
                        jsonObj.put("confidence", result.getConfidence() != null ? result.getConfidence() : "");
                        jsonArray.put(jsonObj);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                String retData = jsonArray.toString();
                callbackContext.success(retData);
            }
        });
    }

    /**
     * Handles an error while executing a plugin API method. Calls the registered
     * Javascript plugin error handler callback.
     *
     * @param errorMsg Error message to pass to the JS error handler
     */
    private void handleError(CallbackContext callbackContext, String errorMsg) {
        try {
            Log.e(TAG, errorMsg);
            callbackContext.error(errorMsg);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void handleException(CallbackContext callbackContext, Exception exception) {
        handleError(callbackContext, exception.toString());
    }
}