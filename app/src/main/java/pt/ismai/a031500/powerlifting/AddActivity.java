package pt.ismai.a031500.powerlifting;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    TextView date_textView, time_textView;
    Spinner spinner;
    EditText reps_nav, weight_nav;
    int repsInt, weightInt, rm1;
    int day, month, year, hour, minute;
    String minuteStr, hourStr, dayStr, monthStr;
    Button add_button;
    Calendar calendar;
    String spinnerText;
    FirebaseUser user;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        spinner = (Spinner) findViewById(R.id.lift_spinner);
        reps_nav = findViewById(R.id.reps_nav);
        weight_nav = findViewById(R.id.weight_nav);
        add_button = findViewById(R.id.add_button);

        // Update Time displayed in the app screen
        final Handler handler = new Handler();
        onUpdateTime();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                onUpdateTime();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(run);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lift_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addsDataFirestore();
            }
        });
        // when user presses keyboard button "done" data is added to db
        weight_nav.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addsDataFirestore();
                }
                return false;
            }
        });
    }

    public void addsDataFirestore() {
        spinnerText = spinner.getSelectedItem().toString();
        // Prevents error when user doesn't change EditText
        if (reps_nav.length() == 0) {
            return;
        } else {
            repsInt = Integer.parseInt(reps_nav.getText().toString());
        }
        if (weight_nav.length() == 0) {
            return;
        } else {
            weightInt = Integer.parseInt(weight_nav.getText().toString());
        }
        // Brzycki Formula: calculates weight for one rep
        rm1 = (int) (Math.round(weightInt / (1.0278 - 0.0278 * repsInt)));
        // Adds Data to a map
        Map<String, Object> data = new HashMap<>();
        data.put("date", Timestamp.now());
        data.put("lift", spinnerText);
        data.put("reps", repsInt);
        data.put("weight", weightInt);
        data.put("rm1", rm1);
        db.collection("users").document(user.getUid()).collection("lifts").document()
                .set(data, SetOptions.merge());
        // Tells the user that data have been haded to Cloud Firestore
        Toast.makeText(AddActivity.this, R.string.added, Toast.LENGTH_SHORT).show();
        // Removes text from the TextViews
        reps_nav.setText("");
        weight_nav.setText("");
    }

    // Update Time displayed in the app screen
    public void onUpdateTime() {
        calendar = Calendar.getInstance();
        // Adds a zero before 1 - 9
        day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = Integer.toString(day);
        }
        month = calendar.get(Calendar.MONTH);
        month = month + 1;
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = Integer.toString(month);
        }
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 10) {
            hourStr = "0" + hour;
        } else {
            hourStr = Integer.toString(hour);
        }
        minute = calendar.get(Calendar.MINUTE);
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = Integer.toString(minute);
        }
        // Changes Date mm/dd/yyyy
        date_textView = findViewById(R.id.date_textView);
        date_textView.setText(monthStr + "/" + dayStr + "/" + year);
        // Changes Time hh:mm
        time_textView = findViewById(R.id.time_textView);
        time_textView.setText(hourStr + ":" + minuteStr);
    }
}
