package jp.dalvik.gifflen;

import android.os.Bundle;
import android.app.Activity;

import org.jiggawatt.giffle.Giffle;

/**
 * @author D.Furiya
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Giffle encoder = new Giffle();

		int[] list = new int[] {
			R.drawable.target1,
			R.drawable.target2,
			R.drawable.target3,
			R.drawable.target4,
			R.drawable.target5
		};

		encoder.encode(this, "/sdcard/sample.gif", 320, 320, list, 500);

	}
}