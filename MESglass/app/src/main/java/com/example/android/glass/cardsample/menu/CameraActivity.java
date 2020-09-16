package com.example.android.glass.cardsample.menu;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.TextureView;

import com.example.android.glass.cardsample.BaseActivity;
import com.example.android.glass.cardsample.R;
import com.example.android.glass.cardsample.menu.CameraConfigProvider;
import com.example.android.glass.cardsample.menu.QRCodeImageAnalysis;
import com.example.android.glass.cardsample.menu.QRCodePreview;
import com.example.glass.ui.GlassGestureDetector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraX;
import androidx.core.content.ContextCompat;

public class CameraActivity extends BaseActivity implements QRCodeImageAnalysis.QrCodeAnalysisCallback {

    /**
     * Key for the scan result in an {@link Intent}.
     */
    public static final String QR_SCAN_RESULT = "SCAN_RESULT";

    /**
     * Request code for the camera permission. This value doesn't have any special meaning.
     */
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 105;

    /**
     * Single thread executor service for the image analysis purposes.
     */
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);
        executorService = Executors.newSingleThreadExecutor();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    @Override
    public boolean onGesture(GlassGestureDetector.Gesture gesture) {
        switch (gesture) {
            case SWIPE_DOWN:
                finishNoQR();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSIONS_REQUEST_CODE) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    finishNoQR();
                } else {
                    startCamera();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onQrCodeDetected(String result) {
        final Intent intent = new Intent();
        intent.putExtra(QR_SCAN_RESULT, result);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void startCamera() {
        final TextureView textureView = findViewById(R.id.view_finder);
        final QRCodePreview qrCodePreview = new QRCodePreview(
                CameraConfigProvider.getPreviewConfig(getDisplaySize()),
                textureView);
        final QRCodeImageAnalysis qrCodeImageAnalysis = new QRCodeImageAnalysis(
                CameraConfigProvider.getImageAnalysisConfig(), executorService, this);

        CameraX.bindToLifecycle(this, qrCodePreview.getUseCase(), qrCodeImageAnalysis.getUseCase());
    }

    private void finishNoQR() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private Size getDisplaySize() {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return new Size(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }
}