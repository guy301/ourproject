package com.imperialsoupgmail.tesseractexample;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Bundle bundle;
    private Bitmap bitmap;
    private Button takePicturButton;
    private Uri file;
    Bitmap image;

    private TessBaseAPI mTess;
    String datapath = "";
    Intent  CropIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePicturButton = (Button) findViewById(R.id.b_camera);

        image = BitmapFactory.decodeResource(getResources(), R.drawable.test_image);

        //initialize Tesseract API
        String language = "eng";
        datapath = getFilesDir()+ "/tesseract/";
        mTess = new TessBaseAPI();

        checkFile(new File(datapath + "tessdata/"));

        mTess.init(datapath, language);

        Button usersBtn = (Button) findViewById(R.id.users);
        usersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item itm=new Item("coke",5);
                Item itm1=new Item("tea",6);
                HashMap<Item,Integer> itemsList=new HashMap<Item,Integer>();
                itemsList.put(itm,8);
                itemsList.put(itm1,2);

                HashMap<Item,Integer> itemsGuy=new HashMap<Item,Integer>();
                itemsGuy.put(itm,0);
                itemsGuy.put(itm1,0);
                HashMap<Item,Integer> itemsSapir=new HashMap<Item,Integer>();
                itemsSapir.put(itm,0);
                itemsSapir.put(itm1,0);
                ArrayList<User> users = new ArrayList<User>();
                User u1 = new User("Guy",itemsGuy);
                User u2 = new User("Sapir",itemsSapir);
                users.add(u1);
                users.add(u2);
                Intent intent = new Intent(MainActivity.this, PickingItemsActivity1.class);


                intent.putExtra("Items",itemsList);

                intent.putExtra("Users",users);
                startActivity(intent);
            }
        });

    }


    public void addButtons(String s)
    {
        String[] words = s .split("\\s+");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ImageContainer);
        int i=0;
        for(String word : words){

            Button yourButton = new Button(this);
            yourButton .setText(word);
            //here can set to button OnClickListener if you want
            yourButton .setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            yourButton.setY(300+i*150);
            i++;//s
            layout.addView(yourButton );
        }
    }
    public void processImage(View view){
        String OCRresult = null;
        mTess.setImage(bitmap);
        OCRresult = mTess.getUTF8Text();
        TextView OCRTextView = (TextView) findViewById(R.id.OCRTextView);
        String s=OCRresult.toString();
        addButtons(s);
        double  m=testAnalize1(s);
        s=s+ " hit rate: "+m;
        OCRTextView.setText(s);

    }


    double testAnalize1(String s)
    {
        String[] lines = s.split(System.getProperty("line.separator"));
        String[] words=lines[0].split(" ");
        String expected="Item price quantity";
        int lineLength=lines[0].length();
        double miss=0;
        double hit=0;
        for(int i=0;i<lineLength;i++)
        {
           if(lines[0].charAt(i)==expected.charAt(i)  )
               hit++;
           else
               miss++;
        }
        return (hit/(hit+miss));
    }
    private void checkFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/eng.traineddata";
            AssetManager assetManager = getAssets();

            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }


            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePicturButton.setEnabled(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {//from camera
            if (resultCode == RESULT_OK) {
                CropImage();
                //imageView.setImageURI(file);
            }
        } else if (requestCode == 2)//from gallery
        {
            if (data != null) {
                file = data.getData();
                CropImage();
            }
        } else if (requestCode == 1)//finished cropping
        {
            if (data != null) {
                bundle = data.getExtras();
                bitmap = bundle.getParcelable("data");
                //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                // imageView.setImageBitmap(bitmap);
                File f = new File(file.getPath());///////////

                //if (f.exists()) f.delete();/////////////


            }
        }
    }



    public void takePicture(View view) {
        Intent CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(CamIntent, 100);
    }

    public void gallery(View view)
    {
        Intent GalIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent, "select image from Gallery"), 2);
    }

    private void CropImage() {
        try{
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(file, "image/*");
            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 256);
            CropIntent.putExtra("outputY", 256);
            //CropIntent.putExtra("aspectX", 1);
            //CropIntent.putExtra("aspectY", 1);
            //CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);
            file = Uri.fromFile(getOutputMediaFile());
            datapath = file.getPath();
            CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, file);

            startActivityForResult(CropIntent, 1);
        }
        catch (ActivityNotFoundException ex)
        {

        }
    }
    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }
}
