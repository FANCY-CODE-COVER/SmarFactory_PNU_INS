package com.example.android.glass.cardsample.menu;

import android.view.TextureView;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.camera.core.Preview.OnPreviewOutputUpdateListener;
import androidx.camera.core.Preview.PreviewOutput;
import androidx.camera.core.UseCase;

/**
 * Builds and provides {@link Preview} object to bind the camera with.
 */
public class QRCodePreview implements OnPreviewOutputUpdateListener {

    private final PreviewConfig previewConfig;
    private final TextureView textureView;

    /**
     * Creates {@link QRCodeImageAnalysis} object with {@link PreviewConfig} and {@link TextureView}.
     */
    public QRCodePreview(PreviewConfig previewConfig, TextureView textureView) {
        this.previewConfig = previewConfig;
        this.textureView = textureView;
    }


    /**
     * Sets {@link OnPreviewOutputUpdateListener} and returns created {@link Preview} object.
     */
    public UseCase getUseCase() {
        final Preview preview = new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(this);
        return preview;
    }

    /**
     * Updates given {@link TextureView} with the {@link android.graphics.SurfaceTexture} from {@link
     * PreviewOutput} object. Removing and adding {@link TextureView} to the {@link ViewGroup} is
     * necessary, when camera permission has not been granted yet.
     */
    @Override
    public void onUpdated(@NonNull PreviewOutput output) {
        final ViewGroup viewGroup = (ViewGroup) textureView.getParent();
        viewGroup.removeView(textureView);
        viewGroup.addView(textureView, 0);
        textureView.setSurfaceTexture(output.getSurfaceTexture());
    }
}