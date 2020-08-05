package com.example.glassproject.menu;


import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Objects;

/**
 * Represents the single menu item object.
 */
public class GlassMenuItem {

    private int id;
    private Drawable icon;
    private String text;

    /**
     * {@link GlassMenuItem} object is constructed by usage of this method.
     *
     * @param id is an id of the the current menu item.
     * @param icon is a menu icon {@link Drawable} object.
     * @param text is a String with the menu option label.
     */
    GlassMenuItem(int id, Drawable icon, String text) {
        this.id = id;
        this.icon = icon;
        this.text = text;
    }

    /**
     * Returns menu item id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns menu item icon.
     */
    public Drawable getIcon() {
        return icon;
    }

    /**
     * Returns menu item label.
     */
    public String getText() {
        return text;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, icon, text);
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GlassMenuItem)) {
            return false;
        }
        final GlassMenuItem that = (GlassMenuItem) other;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.icon, that.icon) &&
                Objects.equals(this.text, that.text);
    }

    @NonNull
    @Override
    public String toString() {
        return id + " " + icon + " " + text;
    }
}