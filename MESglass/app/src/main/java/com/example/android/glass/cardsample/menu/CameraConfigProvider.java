package com.example.android.glass.cardsample.menu;

import android.util.Size;

import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysis.ImageReaderMode;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.PreviewConfig;

/**
 * Provides appropriate {@link androidx.camera.core.UseCaseConfig} objects.
 */
public class CameraConfigProvider {

    /**
     * Returns {@link PreviewConfig} object to create the {@link androidx.camera.core.Preview}
     * instance. It is important to build {@link PreviewConfig} object with the appropriate display
     * size.
     */
    public static PreviewConfig getPreviewConfig(Size displaySize) {
        return new PreviewConfig.Builder()
                .setTargetResolution(displaySize)
                .build();
    }

    /**
     * Returns {@link ImageAnalysisConfig} object to create the {@link androidx.camera.core.ImageAnalysis}
     * instance.
     */
    public static ImageAnalysisConfig getImageAnalysisConfig() {
        return new ImageAnalysisConfig.Builder()
                .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
                .build();
    }
}