package p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog;

public interface TimePickerCallback {
    void dismissDialog();

    void confirmDialog();

    void setTime(long t);
}
