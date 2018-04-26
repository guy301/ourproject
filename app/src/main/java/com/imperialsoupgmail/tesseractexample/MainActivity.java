package com.imperialsoupgmail.tesseractexample;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
<<<<<<< HEAD
import java.util.List;
import java.util.Map;
=======
>>>>>>> origin/guy

public class MainActivity extends AppCompatActivity {
    private Bundle bundle;
    private Bitmap bitmap;
    private Button takePicturButton;
    private Button set_items;
    private Uri file;
    Bitmap image;

    private TessBaseAPI mTess;
    String datapath = "";
    String Text;
    Intent  CropIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePicturButton = (Button) findViewById(R.id.b_camera);
        set_items = (Button) findViewById(R.id.setItems_b);
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
<<<<<<< HEAD
                HashMap<Item,Integer> itemsList=new HashMap<Item,Integer>();
                itemsList.put(itm,8);
                itemsList.put(itm1,2);
=======
                Item itm2=new Item("banana a",6);
                Item itm3=new Item("apple",6);
                HashMap<Item,Integer> itemsList=new HashMap<Item,Integer>();
                itemsList.put(itm,8);
                itemsList.put(itm1,10);
                itemsList.put(itm2,5);
                itemsList.put(itm3,6);
>>>>>>> origin/guy

                HashMap<Item,Integer> itemsGuy=new HashMap<Item,Integer>();
                itemsGuy.put(itm,0);
                itemsGuy.put(itm1,0);
<<<<<<< HEAD
                HashMap<Item,Integer> itemsSapir=new HashMap<Item,Integer>();
                itemsSapir.put(itm,0);
                itemsSapir.put(itm1,0);
                ArrayList<User> users = new ArrayList<User>();
                User u1 = new User("Guy",itemsGuy);
                User u2 = new User("Sapir",itemsSapir);
                users.add(u1);
                users.add(u2);
                Intent intent = new Intent(MainActivity.this, PickingItemsActivity1.class);
=======
                itemsGuy.put(itm2,0);
                itemsGuy.put(itm3,0);
                HashMap<Item,Integer> itemsSapir=new HashMap<Item,Integer>();
                itemsSapir.put(itm,0);
                itemsSapir.put(itm1,0);
                itemsSapir.put(itm2,0);
                itemsSapir.put(itm3,0);
                HashMap<Item,Integer> itemsJina=new HashMap<Item,Integer>();
                itemsJina.put(itm,0);
                itemsJina.put(itm1,0);
                itemsJina.put(itm2,0);
                itemsJina.put(itm3,0);
                ArrayList<User> users = new ArrayList<User>();
                User u1 = new User("Guy",itemsGuy);
                User u2 = new User("Sapir",itemsSapir);
                User u3 = new User("Jina",itemsJina);
                users.add(u1);
                users.add(u2);
                users.add(u3);
                Intent intent = new Intent(MainActivity.this, PickingItemsActivity.class);
>>>>>>> origin/guy


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
        Text = OCRresult.toString();
        //addButtons(Text);
//        double  m=testAnalize1(Text);
//        Text = Text+ " hit rate: "+m;
        OCRTextView.setText(Text);

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
        String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
        File imageFile = new File(imageFilePath);
        file = Uri.fromFile(imageFile);
        CamIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, file);

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
            String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
            File imageFile = new File(imageFilePath);
            file = Uri.fromFile(imageFile);
            datapath = file.getPath();
            CropIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, file);

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

    public void setItems(View view){
        Intent SI = new Intent(this, settingItems.class);
        SI.putExtra("OCR_text", Text);
        startActivity(SI);
    }
}
