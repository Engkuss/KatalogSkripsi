package com.subayu.agus.katalog;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.provider.SyncStateContract;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.util.UUID;


public class Upload extends AppCompatActivity implements View.OnClickListener, OnPageChangeListener, OnLoadCompleteListener, RadioGroup.OnCheckedChangeListener {
    Button btnupload,bt2;
    EditText ed1,ed2,ed3;
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final int PERMISSION_CODE = 42042;
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    String alamatfile;
    String PathHolder;
    String Tugas;
    RadioGroup rd;
    private final static int REQUEST_CODE = 42;
    public static final String UPLOAD_URL = "http://katskrip.esy.es/server/upload.php";
    //public static final String UPLOAD_URL = "http://192.168.253.2/kus/server/upload.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getSupportActionBar().setTitle("Upload File");

        pdfView = (PDFView)findViewById(R.id.pdfView);
        ed1 = (EditText)findViewById(R.id.edfile);
        ed2 = (EditText)findViewById(R.id.ednim);
        ed3 = (EditText)findViewById(R.id.edjudul);
        rd = (RadioGroup)findViewById(R.id.radioGroup);

        btnupload = (Button)findViewById(R.id.btnupload);
        bt2 = (Button)findViewById(R.id.btupload);
        btnupload.setOnClickListener(this);
        bt2.setOnClickListener(this);
        rd.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnupload:
                pickFile();
                break;
            case R.id.btupload:
                uploadMultipart();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    PathHolder = data.getData().getPath();
                    Uri uri = data.getData();
                    alamatfile = getFileName(uri);
                    ed1.setText(alamatfile);
                    displayFromUri(uri);
                    Toast.makeText(Upload.this, uri.toString() + PathHolder, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Upload.this, "Error Ambil File", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    void launchPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            //alert user that file manager not working
            Toast.makeText(this, "Error Ambil File", Toast.LENGTH_SHORT).show();
        }
    }
    public void pickFile() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{READ_EXTERNAL_STORAGE},PERMISSION_CODE);
        }else {
            launchPicker();
        }


    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
    private void displayFromUri(Uri uri) {
        pdfFileName = getFileName(uri);

        pdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchPicker();
            }
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }

    @Override
    public void loadComplete(int nbPages) {

    }
    public void uploadMultipart() {
        //getting name for the pdf
        String name = ed2.getText().toString().trim();
        String judul = ed3.getText().toString().trim();
        String jenis = Tugas;

        //getting the actual path of the pdf
        String path = PathHolder;

        if (path == null) {

            Toast.makeText(this, "Mohon Cari Berkas Anda Dulu", Toast.LENGTH_LONG).show();
        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();
                final ProgressDialog pdLoading = new ProgressDialog(Upload.this);
                //Creating a multi part request
                new MultipartUploadRequest(this, UPLOAD_URL)
                        .addFileToUpload(path, "pdf") //Adding file
                        .addParameter("name", name)
                        .addParameter("judul", judul)
                        .addParameter("jenis", jenis)
                        .setMethod("POST")

                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(3)
                        .setDelegate(new UploadStatusDelegate() {
                            @Override
                            public void onProgress(Context context, UploadInfo uploadInfo) {

                                //pdLoading.setMessage("\tLoading...");
                                //pdLoading.setCancelable(false);
                                //pdLoading.show();
                            }

                            @Override
                            public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
                                //pdLoading.dismiss();
                                exception.printStackTrace();
                                Toast.makeText(getApplicationContext(),"Error Upload :"+exception.toString(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                                //pdLoading.dismiss();
                                Toast.makeText(getApplicationContext(),"Sukses Upload :"+serverResponse.toString(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {
                                //pdLoading.dismiss();
                                Toast.makeText(getApplicationContext(),"Upload dibatalkan user",Toast.LENGTH_LONG).show();
                            }
                        })
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
                exc.printStackTrace();
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onPause() {

        super.onPause();
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        RadioButton rb = (RadioButton) group.findViewById(checkedId);
        if (null != rb && checkedId > -1) {
            Tugas = rb.getText().toString();
            Toast.makeText(Upload.this, rb.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
