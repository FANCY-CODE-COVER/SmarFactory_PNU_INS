package inc.app.mes.ui.edit;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import inc.app.mes.DTO.PlaceDAO;

public class EditViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EditViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is edit fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}