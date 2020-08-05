package com.example.glassproject.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.glassproject.R;

/**
 * Fragment with the main card layout.
 */
public class MainLayoutFragment extends BaseFragment {

    private static final String TEXT_KEY = "text_key";
    private static final String FOOTER_KEY = "footer_key";
    private static final String TIMESTAMP_KEY = "timestamp_key";
    private static final int BODY_TEXT_SIZE = 40;

    /**
     * Returns new instance of {@link MainLayoutFragment}.
     *
     * @param text is a String with the card main text.
     * @param footer is a String with the card footer text.
     * @param timestamp is a String with the card timestamp text.
     */
    public static MainLayoutFragment newInstance(String text, String footer, String timestamp,
                                                 @Nullable Integer menu) {
        final MainLayoutFragment myFragment = new MainLayoutFragment();

        final Bundle args = new Bundle();
        args.putString(TEXT_KEY, text);
        args.putString(FOOTER_KEY, footer);
        args.putString(TIMESTAMP_KEY, timestamp);
        if (menu != null) {
            args.putInt(MENU_KEY, menu);
        }
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_layout, container, false);
        if (getArguments() != null) {
            final TextView textView = new TextView(getContext());
            textView.setText(getArguments().getString(TEXT_KEY, getString(R.string.empty_string)));
            textView.setTextSize(BODY_TEXT_SIZE);
            textView.setTypeface(Typeface.create(getString(R.string.thin_font), Typeface.NORMAL));

            final FrameLayout bodyLayout = view.findViewById(R.id.body_layout);
            bodyLayout.addView(textView);

            final TextView footer = view.findViewById(R.id.footer);
            footer.setText(getArguments().getString(FOOTER_KEY, getString(R.string.empty_string)));

            final TextView timestamp = view.findViewById(R.id.timestamp);
            timestamp.setText(getArguments().getString(TIMESTAMP_KEY, getString(R.string.empty_string)));
        }
        return view;
    }
}
