package com.android.gesturelocker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.android.gesturelocker.R;
import com.android.gesturelocker.common.MD5;
import com.android.gesturelocker.ui.GestureLockViewGroup.OnGestureLockViewListener;

public class GestureLockActivity extends Activity {

	private GestureLockViewGroup mGestureLockViewGroup;

	public static final String KEY_MODE = "LockerMode";

	public static final String KEY_PASSWORD = "NewPassword";

	public static final String KEY_ORIGINAL_MD5_PASSWORD = "OriginalMD5Password";

	public static final int MODE_NONE = 1;

	public static final int MODE_CONFIRM_PASSWORD = 2;

	public static final int MODE_INIT_PASSWORD = 3;

	public static final int MIN_PASSWORD_LENGTH = 4;

	private int mLockerMode = MODE_NONE;
	private String mOriginalMd5Password;

	private OnGestureLockViewListener mGestureLockViewListener = new OnGestureLockViewListener() {

		@Override
		public void onUnmatchedExceedBoundary() {
			Toast.makeText(GestureLockActivity.this, "错误次数超限",
					Toast.LENGTH_SHORT).show();
			setResult(RESULT_CANCELED);
			finish();
		}

		@Override
		public void onBlockSelected(int cId) {
		}

		@Override
		public void onGestureEnd(String password) {

			Intent data = new Intent();
			data.putExtra(KEY_MODE, mLockerMode);
			data.putExtra(KEY_PASSWORD, password);
			
			if (mLockerMode == MODE_INIT_PASSWORD) {
				if (password.length() < MIN_PASSWORD_LENGTH) {
					Toast.makeText(GestureLockActivity.this, "密码太短",
							Toast.LENGTH_SHORT).show();
					return;
				}
				GestureLockActivity.this.setResult(RESULT_OK, data);
				finish();
			} else if (mLockerMode == MODE_CONFIRM_PASSWORD) {
				boolean correct = MD5.encrypt(password).equals(
						mOriginalMd5Password);
				String note = correct ? "密码正确" : "密码错误";
				Toast.makeText(GestureLockActivity.this, note,
						Toast.LENGTH_SHORT).show();
				if (correct) {
					setResult(RESULT_OK, data);
					finish();
				}
			} else {
				Toast.makeText(GestureLockActivity.this, "错误的锁类型",
						Toast.LENGTH_SHORT).show();
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesture_layout);
		Intent intent = getIntent();
		mLockerMode = intent.getIntExtra(KEY_MODE, MODE_NONE);
		mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
		mGestureLockViewGroup
				.setOnGestureLockViewListener(mGestureLockViewListener);

		if (mLockerMode == MODE_CONFIRM_PASSWORD) {
			mOriginalMd5Password = intent
					.getStringExtra(KEY_ORIGINAL_MD5_PASSWORD);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			setResult(RESULT_CANCELED);
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}
