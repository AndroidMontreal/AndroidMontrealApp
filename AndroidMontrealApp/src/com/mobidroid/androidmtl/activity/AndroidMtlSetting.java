package com.mobidroid.androidmtl.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.mobidroid.androidmtl.R;

public class AndroidMtlSetting extends Activity {

	private SharedPreferences sharedPreference;
	public static final int BEGINNER = R.id.radioButton1;
	public static final int INTERMEDIATE = R.id.radioButton2;
	public static final int EXPERT = R.id.radioButton3;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doSetting();
		setContentView(R.layout.androidmtlsetting);
		sharedPreference = getSharedPreferences("setting", 0);
		int checkedButton = sharedPreference.getInt("level", BEGINNER);
		RadioGroup group = (RadioGroup) findViewById(R.id.setting);
		group.check(checkedButton);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			private int level;

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radioButton1:
					level = R.id.radioButton1;
					break;
				case R.id.radioButton2:
					level = R.id.radioButton2;
					break;
				case R.id.radioButton3:
					level = R.id.radioButton3;
					break;
				}
				SharedPreferences.Editor editor = sharedPreference.edit();
				editor.putInt("level", level);
				editor.commit();

			}

		});
		
	}

	public void doSetting() {

	}

}
