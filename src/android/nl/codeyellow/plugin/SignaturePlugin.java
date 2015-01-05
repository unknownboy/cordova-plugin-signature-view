/**
 * A plugin for Apache Cordova (Phonegap) which will ask the user to write his
 * or her a signature, which gets captured into an image.
 *
 * Copyright (c) 2013-2014, Code Yellow B.V.
 *
 * Heavily based on Holly Schinsky's tutorial:
 * http://devgirl.org/2013/09/17/how-to-write-a-phonegap-3-0-plugin-for-android/
 */
package nl.codeyellow.plugin;

import android.app.Activity;
import android.app.FragmentManager;
import nl.codeyellow.app.SignatureDialogFragment;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignaturePlugin extends CordovaPlugin {
	public static final String ACTION_NEW = "new";
	public static final String DEFAULT_TITLE_VALUE = "Please sign below";
	public static final String DEFAULT_HTMLFILE_VALUE = null;
	public static final String DEFAULT_SAVE_VALUE = "Save";
	public static final String DEFAULT_CLEAR_VALUE = "Clear";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext)
            throws JSONException {

        if (ACTION_NEW.equals(action)) {
			JSONObject arg_object = args.getJSONObject(0);

            // TODO: Make default title translatable
            String title = DEFAULT_TITLE_VALUE;
            String htmlFile = DEFAULT_HTMLFILE_VALUE;
            String save = DEFAULT_SAVE_VALUE;
            String clear = DEFAULT_CLEAR_VALUE;

			if (arg_object.has("title")) {
				title = arg_object.getString("title");
			}
			if (arg_object.has("webpage")) {
				htmlFile = arg_object.getString("webpage");
			}
			if (arg_object.has("clear")) {
				clear = arg_object.getString("clear");
			}
			if (arg_object.has("save")) {
				save = arg_object.getString("save");
			}

            Activity act = this.cordova.getActivity();
            FragmentManager fragmentManager = act.getFragmentManager();
            SignatureDialogFragment signatureDialogFragment = new SignatureDialogFragment(title, save, clear, htmlFile, callbackContext);
            signatureDialogFragment.show(fragmentManager, "dialog");

            return true;
        } else {
            callbackContext.error("Unknown action: " + action);
			// Returning false results in a "MethodNotFound" error.
            return false;
        }
    }

}
