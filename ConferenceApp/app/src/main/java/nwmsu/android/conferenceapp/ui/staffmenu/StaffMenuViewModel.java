package nwmsu.android.conferenceapp.ui.staffmenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StaffMenuViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StaffMenuViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is staff menu fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}