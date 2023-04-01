package com.nwmissouri.edu.conferencescheduler.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nwmissouri.edu.conferencescheduler.model.Conference;
import com.nwmissouri.edu.conferencescheduler.model.Event;
import com.nwmissouri.edu.conferencescheduler.model.Speaker;
import com.nwmissouri.edu.conferencescheduler.model.Sponsor;
import com.nwmissouri.edu.conferencescheduler.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FirebaseUtilsManager {
    private static final String TAG = "FirebaseUtilsManager";

    private static FirebaseFirestore sFirestore;
    private static CollectionReference dbRefUsers;
    private static CollectionReference dbConference;
    private static CollectionReference dbSponsors;
    private static CollectionReference dbSpeakers;

    private static StorageReference storageReference;
    private static ProgressDialog sProgressDialog;

    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DATE = "date";
    private static final String COLLECTION_EVENTS = "Events";
    private static final String FIELD_EVENT_TIME = "eventTime";
    private static final String FIELD_PASSWORD = "password";
    private static final String FIELD_USER_TYPE = "userType";

    private static ListenerRegistration eventListener;

    public static void initialize() {
        // Initialize the Firebase database and authentication
        sFirestore = FirebaseFirestore.getInstance();
        dbRefUsers = sFirestore.collection("Users");
        dbConference = sFirestore.collection("Conferences");
        dbSpeakers = sFirestore.collection("Speakers");
        dbSponsors = sFirestore.collection("Sponsors");
        storageReference = FirebaseStorage.getInstance().getReference().child("images");
    }

    public static FirebaseFirestore getDatabaseReference() {
        return sFirestore;
    }


    public static void showLoadingDialog(Context context, String message) {
        if (sProgressDialog == null) {
            sProgressDialog = new ProgressDialog(context);
            sProgressDialog.setMessage(message);
            sProgressDialog.setCancelable(false);
            sProgressDialog.show();
        }
    }

    public static void hideLoadingDialog() {
        if (sProgressDialog != null) {
            sProgressDialog.dismiss();
            sProgressDialog = null;
        }
    }

    public static void login(final Context context, String email, String password, final OnOpListener listener) {

        showLoadingDialog(context, "Logging in...");
        String encryptedPassword = Utils.encrypt(password);

        dbRefUsers.whereEqualTo(FIELD_EMAIL, email).limit(1).get().addOnCompleteListener(task -> {

            hideLoadingDialog();

            if (task.isSuccessful()) {
                if (task.getResult().isEmpty()) {
                    listener.onFailure("Email not exist");
                    return;
                }
                DocumentSnapshot snap = task.getResult().getDocuments().get(0);
                UserModel userModel = snap.toObject(UserModel.class);
                String docId = snap.getId();

                if (userModel == null) {
                    listener.onFailure("Something went wrong");
                    return;
                }

                if (TextUtils.equals(userModel.getPassword(), encryptedPassword)) {

                    MySharedPreferences.getInstance().saveUser(
                            docId,
                            userModel.getName(),
                            userModel.getEmail(),
                            userModel.getUserType());

                    listener.onSuccess("");
                    return;
                }
                listener.onFailure("Invalid Password");
            } else {
                listener.onFailure(task.getException().getMessage());
            }
        });
    }

    public static void createAccount(final Context context, String name, String email, String password, String userType, final OnOpListener listener) {

        if (TextUtils.equals(email.toLowerCase(Locale.ROOT), Constants.PASSWORD_SUPER_ADMIN)) {
            listener.onFailure("Email already exist");
            return;
        }

        showLoadingDialog(context, "Please wait...");
        String encryptedPassword = Utils.encrypt(password);

        dbRefUsers.whereEqualTo(FIELD_EMAIL, email).limit(1).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (!task.getResult().isEmpty()) {
                    listener.onFailure("Email already exist");
                    hideLoadingDialog();
                    return;
                }
                UserModel user = new UserModel(email, encryptedPassword, userType, name);
                DocumentReference ref = dbRefUsers.document();

                ref.set(user).addOnCompleteListener(addTask -> {
                    hideLoadingDialog();
                    if (task.isSuccessful()) {
                        MySharedPreferences.getInstance().saveUser(
                                ref.getId(),
                                user.getName(),
                                user.getEmail(),
                                user.getUserType());
                        listener.onSuccess("");
                    } else {
                        listener.onFailure(addTask.getException().getMessage());
                    }
                });

            } else {
                listener.onFailure("Something went wrong");
                hideLoadingDialog();
            }
        });
    }

    public static void updateName(final Context context, String updatedName, final OnOpListener listener) {
        showLoadingDialog(context, "Please wait...");
        HashMap<String, String> map = new HashMap<>();
        map.put(FIELD_NAME, updatedName);
        MySharedPreferences pref = MySharedPreferences.getInstance();
        dbRefUsers.document(pref.userId()).set(map, SetOptions.merge()).addOnCompleteListener(task -> {
            hideLoadingDialog();
            if (task.isSuccessful()) {
                ToastUtils.showToast(context, "Updated");
                pref.updateName(updatedName);
                listener.onSuccess("");

            } else {
                ToastUtils.showToast(context, task.getException().getMessage());
                listener.onFailure("");
            }
        });

    }

    public static void listenConferenceListUpdate(OnConferenceListUpdateListener listener) {
        Query query = dbConference.orderBy(FIELD_DATE, Query.Direction.DESCENDING).limit(1);
        query.addSnapshotListener((querySnapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                listener.onUpdate(new ArrayList<>());
                return;
            }

            if (querySnapshot != null) {
                List<Conference> documents = querySnapshot.toObjects(Conference.class);
                listener.onUpdate(documents);
            } else {
                listener.onUpdate(new ArrayList<>());
            }
        });
    }

    public static void listenConferenceEventListUpdate(String conferenceId, OnConferenceEventListUpdateListener listener) {
        Query query = dbConference.document(conferenceId).collection(COLLECTION_EVENTS)
                .orderBy(FIELD_EVENT_TIME, Query.Direction.DESCENDING);
        eventListener = query.addSnapshotListener((querySnapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                listener.onUpdate(new ArrayList<>());
                return;
            }
            if (querySnapshot != null) {
                List<Event> documents = querySnapshot.toObjects(Event.class);
                listener.onUpdate(documents);
            } else {
                listener.onUpdate(new ArrayList<>());
            }
        });
    }

    public static void listenScheduledUpdate(String conferenceId, OnConferenceEventListUpdateListener listener) {
        CollectionReference ref = dbConference.document(conferenceId).collection(COLLECTION_EVENTS);
        Query query = ref.orderBy(FIELD_EVENT_TIME, Query.Direction.DESCENDING);
        String email = MySharedPreferences.getInstance().getUser().getEmail().toLowerCase();
        List<Event> list = new ArrayList<>();
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot element : task.getResult().getDocuments()) {
                    Query eventRef = element.getReference().collection("Subscriber").whereEqualTo("email", email);
                    eventRef.get().addOnCompleteListener(eventTask -> {
                        if (eventTask.isSuccessful()) {
                            try {
                                if (!eventTask.getResult().isEmpty()) {
                                    Event data = element.toObject(Event.class);
                                    data.setSubscribeDocumentID(eventTask.getResult().getDocuments().get(0).getId());
                                    list.add(data);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            listener.onUpdate(new ArrayList<>());
                        }
                        listener.onUpdate(list);
                    });
                }

            } else {
                listener.onUpdate(new ArrayList<>());
            }
        });
    }


    public static void listenSpeakersListUpdate(OnSpeakersListUpdateListener listener) {
        Query query = dbSpeakers;
        query.addSnapshotListener((querySnapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                listener.onUpdate(new ArrayList<>());
                return;
            }
            if (querySnapshot != null) {
                List<Speaker> documents = querySnapshot.toObjects(Speaker.class);
                listener.onUpdate(documents);
            } else {
                listener.onUpdate(new ArrayList<>());
            }
        });
    }


    public static void removeConferenceEventListener() {
        if (eventListener != null) {
            eventListener.remove();
        }
    }

    public static void addConference(Context context, String name, Uri pickedImage, Conference conference, final OnOpListener listener) {
        showLoadingDialog(context, "Please wait...");

        DocumentReference doc;
        if (conference != null) {
            doc = dbConference.document(conference.getKey());
        } else {
            doc = dbConference.document();
        }

        uploadImage(context, doc.getId(), pickedImage, new OnOpListener() {
            @Override
            public void onSuccess(String url) {

                String imageUrl;
                if (TextUtils.isEmpty(url)) {
                    imageUrl = conference.getImageUrl();
                } else {
                    imageUrl = url;
                }

                Conference c = new Conference(doc.getId(), name, imageUrl);
                doc.set(c, SetOptions.merge()).addOnCompleteListener(taskAddConference -> {
                    hideLoadingDialog();
                    if (taskAddConference.isSuccessful()) {
                        listener.onSuccess("");
                    } else {
                        listener.onFailure(taskAddConference.getException().getMessage());
                        ToastUtils.showToast(context, taskAddConference.getException().getMessage());
                    }
                });
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

    public static void scheduleEvent(Context context, Event event, final OnOpListener listener) {
        showLoadingDialog(context, "Please wait...");
        CollectionReference collection = dbConference.document(event.getConferenceId()).collection("Events").document(event.getKey()).collection("Subscriber");
        String email = MySharedPreferences.getInstance().getUser().getEmail().toLowerCase();

        HashMap<String, String> map = new HashMap<>();
        map.put("token", "");
        map.put(FIELD_EMAIL, email);

        collection.whereEqualTo(FIELD_EMAIL, email).limit(1).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (!task.getResult().isEmpty()) {
                    hideLoadingDialog();
                    listener.onFailure("Event already Scheduled");
                } else {
                    collection.add(map).addOnCompleteListener(task1 -> {
                        hideLoadingDialog();
                        if (task1.isSuccessful()) {
                            listener.onSuccess("Event Scheduled");
                            subscribeTopic(event.getKey());
                        } else {
                            listener.onFailure(task.getException().getMessage());
                        }
                    });
                }

            } else {
                hideLoadingDialog();
                listener.onFailure(task.getException().getMessage());
            }
        });


    }

    public static void removeEvent(Context context, Event event, final OnOpListener listener) {
        showLoadingDialog(context, "Please wait...");
        DocumentReference doc = dbConference.document(event.getConferenceId()).collection("Events")
                .document(event.getKey()).collection("Subscriber").document(event.getSubscribeDocumentID());

        doc.delete().addOnCompleteListener(task -> {
            hideLoadingDialog();
            if (task.isSuccessful()) {
                listener.onSuccess("Event Removed");
                unSubscribeTopic(event.getKey());
            } else {
                listener.onFailure(task.getException().getMessage());
            }
        });
    }

    public static void addEvent(Context context, String eventId, String conferenceId, Uri pickedImage, Event event, final OnOpListener listener) {
        showLoadingDialog(context, "Please wait...");

        boolean isEditMode = false;
        //boolean shouldSendNotification = false;
        DocumentReference doc;
        if (eventId != null) {
            doc = dbConference.document(conferenceId).collection("Events").document(eventId);
            isEditMode = true;
        } else {
            doc = dbConference.document(conferenceId).collection("Events").document();
        }
        boolean finalIsEditMode = isEditMode;

        uploadImage(context, doc.getId(), pickedImage, new OnOpListener() {
            @Override
            public void onSuccess(String url) {

                String imageUrl;
                if (TextUtils.isEmpty(url)) {
                    imageUrl = event.getImageUrl();
                } else {
                    imageUrl = url;
                }
                event.setKey(doc.getId());
                event.setImageUrl(imageUrl);
                doc.set(event, SetOptions.merge()).addOnCompleteListener(taskAddConference -> {
                    if (taskAddConference.isSuccessful()) {
                        if (finalIsEditMode) {
                            sendNotification(context, doc.getId(), "Event Updated", "Event Updated.", new OnNotificationSentListener() {
                                @Override
                                public void onSuccess(String data) {
                                    listener.onSuccess("");
                                    Log.d("NOTIFICATION: ", data);
                                    hideLoadingDialog();
                                }

                                @Override
                                public void onFailure(String msg) {
                                    listener.onSuccess("");
                                    hideLoadingDialog();
                                    Log.d("NOTIFICATION: ", msg);
                                }
                            });
                        } else {
                            listener.onSuccess("");
                        }

                    } else {
                        listener.onFailure(taskAddConference.getException().getMessage());
                        ToastUtils.showToast(context, taskAddConference.getException().getMessage());
                    }
                });
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

    public static void deleteConference(Context context, String key, final OnOpListener listener) {
        showLoadingDialog(context, "Please wait...");
        dbConference.document(key).delete().addOnCompleteListener(task -> {
            hideLoadingDialog();
            if (task.isSuccessful()) {
                listener.onSuccess("");
            } else {
                listener.onFailure(task.getException().getMessage());
            }
        });
    }

    public static void deleteEvent(Context context, String conferenceId, String eventId, final OnOpListener listener) {
        showLoadingDialog(context, "Please wait...");
        dbConference.document(conferenceId).collection("Events").document(eventId).delete().addOnCompleteListener(task -> {
            hideLoadingDialog();
            if (task.isSuccessful()) {
                listener.onSuccess("");
            } else {
                listener.onFailure(task.getException().getMessage());
            }
        });
    }

    public static void addSpeaker(Context context, String name, String details, Uri pickedImage, final OnOpListener listener) {
        showLoadingDialog(context, "Please wait...");
        DocumentReference doc = dbSpeakers.document();

        uploadImage(context, doc.getId(), pickedImage, new OnOpListener() {
            @Override
            public void onSuccess(String data) {
                Speaker spk = new Speaker(doc.getId(), name, data, details);
                doc.set(spk, SetOptions.merge()).addOnCompleteListener(taskAddData -> {
                    hideLoadingDialog();
                    if (taskAddData.isSuccessful()) {
                        listener.onSuccess("");
                    } else {
                        listener.onFailure(taskAddData.getException().getMessage());
                    }
                });
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

    public static void addSponsor(Context context, String name, String details, String type, Uri pickedImage, final OnOpListener listener) {
        showLoadingDialog(context, "Please wait...");
        DocumentReference doc = dbSponsors.document();

        uploadImage(context, doc.getId(), pickedImage, new OnOpListener() {
            @Override
            public void onSuccess(String data) {
                Sponsor sponsor = new Sponsor(name, type, data, doc.getId(), details);
                doc.set(sponsor, SetOptions.merge()).addOnCompleteListener(taskAddData -> {
                    hideLoadingDialog();
                    if (taskAddData.isSuccessful()) {
                        listener.onSuccess("");
                    } else {
                        listener.onFailure(taskAddData.getException().getMessage());
                    }
                });
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

    public interface OnOpListener {
        void onSuccess(String data);

        void onFailure(String msg);
    }

    public interface OnConferenceListUpdateListener {
        void onUpdate(List<Conference> data);
    }

    public interface OnConferenceEventListUpdateListener {
        void onUpdate(List<Event> data);
    }

    public interface OnSpeakersListUpdateListener {
        void onUpdate(List<Speaker> data);
    }

    public interface OnSponsorsListUpdateListener {
        void onUpdate(List<Sponsor> data);
    }

    public interface OnNotificationSentListener {
        void onSuccess(String data);

        void onFailure(String msg);
    }

    public static void uploadImage(Context context, String id, Uri image, final OnOpListener listener) {

        try {

            if (image == null) {
                listener.onSuccess("");
                return;
            }

            byte[] imageByte = compressImage(context, image);
            StorageReference ref = storageReference.child(id);
            ref.putBytes(imageByte).addOnCompleteListener(taskUpload -> {
                if (taskUpload.isSuccessful()) {
                    ref.getDownloadUrl().addOnCompleteListener(taskDownloadUrl -> {
                        if (taskDownloadUrl.isSuccessful()) {
                            listener.onSuccess(taskDownloadUrl.getResult().toString());
                        } else {
                            listener.onFailure(taskDownloadUrl.getException().getMessage());
                        }
                    });
                } else {
                    listener.onFailure(taskUpload.getException().getMessage());
                }
            });
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }
    }

    public static byte[] compressImage(Context ct, Uri uri) {
        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(ct.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] fileInBytes = baos.toByteArray();
        return fileInBytes;
    }

   

    }

}


