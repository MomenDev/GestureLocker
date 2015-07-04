package com.android.gesturelocker;

import com.android.gesturelocker.database.GesturePassword;
import com.android.gesturelocker.ui.GestureLockActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DemoActivity extends Activity {

	private Button mBtInitPassword;

	private Button mBtConfirmPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_layout);

		mBtInitPassword = (Button) findViewById(R.id.bt_init_password);
		mBtConfirmPassword = (Button) findViewById(R.id.bt_confirm_password);

		mBtInitPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(DemoActivity.this, GestureLockActivity.class);
				intent.putExtra(GestureLockActivity.KEY_MODE,
						GestureLockActivity.MODE_INIT_PASSWORD);
				startActivityForResult(intent, 1);
			}
		});

		mBtConfirmPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(DemoActivity.this, GestureLockActivity.class);
				intent.putExtra(GestureLockActivity.KEY_MODE,
						GestureLockActivity.MODE_CONFIRM_PASSWORD);
				intent.putExtra(GestureLockActivity.KEY_ORIGINAL_MD5_PASSWORD,
						GesturePassword.getPassword(DemoActivity.this));
				startActivityForResult(intent, 2);

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(resultCode != RESULT_OK)
			return;
		
		int mode = data.getIntExtra(GestureLockActivity.KEY_MODE, 0);
		
		if(mode == GestureLockActivity.MODE_INIT_PASSWORD)
		{
			String password = data.getStringExtra(GestureLockActivity.KEY_PASSWORD);
			Toast.makeText(this, "password:"+password, Toast.LENGTH_SHORT).show();
			GesturePassword.savePassword(this, password);
		}
		else if(mode == GestureLockActivity.MODE_CONFIRM_PASSWORD)
		{
			Toast.makeText(this, "confirm password", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(this, "no result", Toast.LENGTH_SHORT).show();
		}
	}

}
