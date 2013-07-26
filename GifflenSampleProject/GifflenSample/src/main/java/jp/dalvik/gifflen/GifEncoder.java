package jp.dalvik.gifflen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

/**
 * @author D.furiya
 */
public class GifEncoder {

	private static final String TAG = "GifEncoder";

	private static final int COLOR = 256;
	private static final int QUALITY = 10;

	static {
		System.loadLibrary("gifflen");
	}

	/**
	 * Gifflen AddFrame
	 * @param pixels
	 * @return
	 */
	public native int AddFrame(int[] pixels);

	/**
	 * Gifflen Init
	 * @param name File Name : "/sdcard/sample.gif"
	 * @param width
	 * @param height
	 * @param color
	 * @param quality
	 * @param delay
	 * @return
	 */
	public native int Init(String name, int width, int height, int color, int quality, int delay);

	/**
	 * * Gifflen Close
	 */
	public native void Close();

	/**
	 * Create Gif Animation File
	 * @param context
	 * @param name			File Name
	 * @param width
	 * @param height
	 * @param fileList		List<File> File List
	 * @param delay
	 * @return
	 */
	public boolean encode(final Context context, final String name, final int width, final int height, final List<File> fileList, final int delay) {

		int state;
		int[] pixels = new int[width * height];

		state = Init(name, width, height, COLOR, QUALITY, delay / 10);
		if (state != 0) {
			// Failed
			return false;
		}

		Iterator<File> ite =  fileList.iterator();

		while (ite.hasNext()) {
			Bitmap bitmap = null;
			try {
				File file = (File) ite.next();
				bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				return false;
			}

			if (width < bitmap.getWidth() || height < bitmap.getHeight()) {
				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			}
			bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
			AddFrame(pixels);
			bitmap.recycle();
		}

		Close();

		return true;
	}

	/**
	 * Create Gif Animation File
	 * @param context
	 * @param name			File Name
	 * @param width
	 * @param height
	 * @param drawableList	Drawable Resrouce R.drawable.* File List
	 * @param delay
	 * @return
	 */
	public boolean encode(final Context context, final String name, final int width, final int height, final int[] drawableList, final int delay) {

		int state;
		int[] pixels = new int[width * height];

		state = Init(name, width, height, COLOR, QUALITY, delay / 10);
		if (state != 0) {
			// Failed
			return false;
		}

		for (int drawable : drawableList) {
			Bitmap bitmap = null;
			bitmap = BitmapFactory.decodeResource(context.getResources(), drawable);
			if (width < bitmap.getWidth() || height < bitmap.getHeight()) {
				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			}
			bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
			AddFrame(pixels);
			bitmap.recycle();
		}
		Close();

		return true;
	}
}