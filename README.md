gifflen-sample
==============

AndroidでGif Animation Fileを生成するサンプル

Bitmap color reduction and GIF encoding

@see http://jiggawatt.org/badc0de/android/#gifflen

I've written a small native lib for Android to do color quantization (from 2-256 colors) of a Bitmap and save the result as a frame in an animated GIF (you can add as many frames as you like).
You may hack and slash the library code as you wish to fit your needs. The color quantizer used is Anthony Dekker's NeuQuant, with some modifications made by me.


The Java code to use the library would be something like:

    static {
      System.loadLibrary("gifflen");
    }

    ....

    public native int Init(String gifName, int w, int h, int numColors, int quality,
                           int frameDelay);
    public native void Close();
    public native int AddFrame(int[] inArray);

    ....


    // Filename, width, height, colors, quality, frame delay
    if (Init("/sdcard/foo.gif", width, height, 256, 100, 4) != 0) {
    	Log.e("gifflen", "Init failed");
    }

    int[] pixels = new int[width*height];
    // bitmap should be 32-bit ARGB, e.g. like the ones you get when decoding
    // a JPEG using BitmapFactory
    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

    // Convert to 256 colors and add to foo.gif
    AddFrame(pixels);

    Close();
