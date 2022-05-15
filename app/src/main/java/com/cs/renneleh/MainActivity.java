package com.cs.renneleh;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;
import com.hbb20.CountryCodePicker;
import com.startapp.sdk.adsbase.StartAppAd;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {

    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private ImageView qrCodeIV;
    private EditText dataEdt;
    private Button generateQrBtn;
    private Button save;
    private Button call;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    private CountryCodePicker picker;
    private TextView note;
    private TextView contact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartAppAd.disableSplash();

        // initializing all variables.
        qrCodeIV = findViewById(R.id.idIVQrcode);
        dataEdt = findViewById(R.id.idEdt);
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);
       // save = findViewById(R.id.btnSave);
        picker = findViewById(R.id.picker);
        picker.registerCarrierNumberEditText(dataEdt);
        note = findViewById(R.id.note);
        contact = findViewById(R.id.contact);
        call = findViewById(R.id.call);


        // initializing onclick listener for button.
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(dataEdt.getText().toString())) {

                    // if the edittext inputs are empty then execute
                    // this method showing a toast message.
                    Toast.makeText(MainActivity.this, "Enter phone number to get QR Code", Toast.LENGTH_SHORT).show();
                } else if (dataEdt.getText().toString().replace(" ", "").length() != 10) {
                    Toast.makeText(MainActivity.this, "Please Enter Valid Number"
                            , Toast.LENGTH_SHORT).show();
                } else {

                    note.setVisibility(View.GONE);
                    contact.setVisibility(View.VISIBLE);

                    // below line is for getting
                    // the windowmanager service.
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    // initializing a variable for default display.
                    Display display = manager.getDefaultDisplay();

                    // creating a variable for point which
                    // is to be displayed in QR Code.
                    Point point = new Point();
                    display.getSize(point);

                    // getting width and
                    // height of a point
                    int width = point.x;
                    int height = point.y;

                    // generating dimension from width and height.
                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;

                    // setting this dimensions inside our qr code
                    // encoder to generate our qr code.
                    qrgEncoder = new QRGEncoder("https://api.whatsapp.com/send?phone="
                            + picker.getFullNumberWithPlus()
                            .replace(" ", "").toString(),
                            null, QRGContents.Type.TEXT, dimen);
                    dataEdt.setText("");
                    try {
                        // getting our qrcode in the form of bitmap.
                        bitmap = qrgEncoder.encodeAsBitmap();
                        // the bitmap is set inside our image
                        // view using .setimagebitmap method.
                        qrCodeIV.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        // this method is called for
                        // exception handling.
                        Log.e("Tag", e.toString());
                    }
                }
            }
        });


    }

    public void btn_call(View view) {

        if (TextUtils.isEmpty(dataEdt.getText().toString())) {

            // if the edittext inputs are empty then execute
            // this method showing a toast message.
            Toast.makeText(MainActivity.this, "Enter phone number to get QR Code", Toast.LENGTH_SHORT).show();
        } else if (dataEdt.getText().toString().replace(" ", "").length() != 10) {
            Toast.makeText(MainActivity.this, "Please Enter Valid Number"
                    , Toast.LENGTH_SHORT).show();
        } else {

            note.setVisibility(View.GONE);
            contact.setVisibility(View.VISIBLE);

            // below line is for getting
            // the windowmanager service.
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

            // initializing a variable for default display.
            Display display = manager.getDefaultDisplay();

            // creating a variable for point which
            // is to be displayed in QR Code.
            Point point = new Point();
            display.getSize(point);

            // getting width and
            // height of a point
            int width = point.x;
            int height = point.y;

            // generating dimension from width and height.
            int dimen = width < height ? width : height;
            dimen = dimen * 3 / 4;

            // setting this dimensions inside our qr code
            // encoder to generate our qr code.
            qrgEncoder = new QRGEncoder(PhoneNumberUtils.formatNumber(picker.getFullNumberWithPlus().toString())
                    ,
                    null, QRGContents.Type.TEXT, dimen);
            dataEdt.setText("");
            try {
                // getting our qrcode in the form of bitmap.
                bitmap = qrgEncoder.encodeAsBitmap();
                // the bitmap is set inside our image
                // view using .setimagebitmap method.
                qrCodeIV.setImageBitmap(bitmap);
            } catch (WriterException e) {
                // this method is called for
                // exception handling.
                Log.e("Tag", e.toString());
            }
        }
    }


//    public void btn_save(View view) throws IOException {
//
//
//
//    }

}




