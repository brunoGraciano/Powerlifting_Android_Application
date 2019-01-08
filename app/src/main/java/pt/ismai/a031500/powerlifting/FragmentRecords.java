package pt.ismai.a031500.powerlifting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FragmentRecords extends Fragment {
    TextView bench_textView, military_textView, deadlift_textView, squat_textView;
    String measure;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Query queryBench, querySquat, queryDeadlift, queryMilitary;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference liftsRef;
    ProgressBar progressBar2;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Records");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_records, container, false);
        progressBar2 = v.findViewById(R.id.progressBar2);
        bench_textView = v.findViewById(R.id.bench_textView);
        military_textView = v.findViewById(R.id.military_textView);
        deadlift_textView = v.findViewById(R.id.deadlift_textView);
        squat_textView = v.findViewById(R.id.squat_textView);

        liftsRef = db.collection("users").document(user.getUid()).collection("lifts");
        queryBench = liftsRef.whereEqualTo("lift", "Bench Press").orderBy("rm1", Query.Direction.DESCENDING).limit(1);
        querySquat = liftsRef.whereEqualTo("lift", "Back Squat").orderBy("rm1", Query.Direction.DESCENDING).limit(1);
        queryDeadlift = liftsRef.whereEqualTo("lift", "Deadlift").orderBy("rm1", Query.Direction.DESCENDING).limit(1);
        queryMilitary = liftsRef.whereEqualTo("lift", "Military Press").orderBy("rm1", Query.Direction.DESCENDING).limit(1);

        queryBench.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressBar2.setVisibility(View.VISIBLE);
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Lift liftBd = documentSnapshot.toObject(Lift.class);
                    measure = String.valueOf(liftBd.getRm1());
                    if (measure.equals("")) {
                        bench_textView.setText("No results!");
                    } else {
                        bench_textView.setText(measure + " Kg");
                    }
                }
                progressBar2.setVisibility(View.INVISIBLE);
            }
        });
        querySquat.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Lift liftBd = documentSnapshot.toObject(Lift.class);
                    measure = String.valueOf(liftBd.getRm1());
                    if (measure.equals("")) {
                        squat_textView.setText("No results!");
                    } else {
                        squat_textView.setText(measure + " Kg");
                    }
                }

            }
        });
        queryDeadlift.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Lift liftBd = documentSnapshot.toObject(Lift.class);
                    measure = String.valueOf(liftBd.getRm1());
                    if (measure.equals("")) {
                        deadlift_textView.setText("No results!");
                    } else {
                        deadlift_textView.setText(measure + " Kg");
                    }
                }

            }
        });
        queryMilitary.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Lift liftBd = documentSnapshot.toObject(Lift.class);
                    measure = String.valueOf(liftBd.getRm1());
                    if (measure.equals("")) {
                        military_textView.setText("No results!");
                    } else {
                        military_textView.setText(measure + " Kg");
                    }
                }

            }
        });


        return v;
    }
}
