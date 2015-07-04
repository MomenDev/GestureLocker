package com.android.gesturelocker.database;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class GesturePassword {

	public static final String GESTUREDATA = "GestureData";
	public static final String PASSWORD = "password";

	public static void savePassword(Context context, String md5String) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				GESTUREDATA, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString(PASSWORD, md5String);
		editor.commit();
	}
	
	public static String getPassword(Context context)
	{
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				GESTUREDATA, Activity.MODE_PRIVATE);
		String md5password = mySharedPreferences.getString(PASSWORD,
				"");
		return md5password;
	}

	public static boolean isPasswordCorrect(Context context, String new_md5password) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				GESTUREDATA, Activity.MODE_PRIVATE);
		String original_md5password = mySharedPreferences.getString(PASSWORD,
				"");

		return new_md5password.equals(original_md5password);
	}

	public static String passwordToString(List<Integer> passwordList) {
		StringBuffer stringBuffer = new StringBuffer();
		for (Integer integer : passwordList) {
			stringBuffer.append(integer);
		}

		return stringBuffer.toString();
	}

}
