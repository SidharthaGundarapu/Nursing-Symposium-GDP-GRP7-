package nwmsu.android.conferenceapp.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;

import nwmsu.android.conferenceapp.hidden.DatabaseInfo;
import nwmsu.android.conferenceapp.dbmodels.*;

public final class DatabaseTools {
    private static final DatabaseInfo myInfo = new DatabaseInfo();
    //private final FirebaseFirestore db;
    private Object db; // This is just a placeholder for the database

    // Exapmple add document for when integration to FB complete w/ Firestore
//    public static void addUserDocument( HashMap<String, Object> myModel, String modelID) {
//        this.db.collection("users").document( modelID)
//                .set(user)
//                .addOnSuccessListener(new OnSuccessListener<Void>(){
//                    @Override
//                    public void onSuccess( Void aVoid) {
//                        Log.d("USER", "DocumentSnapshot successfuly written!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("USER", "Error writing document", e);
//                    }
//                });
//    }

}