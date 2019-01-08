package pt.ismai.a031500.powerlifting;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Lift {
    private int rm1;
    private Timestamp date;
    private String lift;
    private int reps;
    private int weight;

    public Lift() {
    } // Needed for Firebase

    public Lift(int rm1, Timestamp date, String lift, int reps, int weight) {
        this.rm1 = rm1;
        //this.date = date;
        this.lift = lift;
        this.reps = reps;
        this.weight = weight;
    }

    public int getRm1() {
        return rm1;
    }

    public void setRm1(int rm1) {
        this.rm1 = rm1;
    }

    @ServerTimestamp
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getLift() {
        return lift;
    }

    public void setLift(String lift) {
        this.lift = lift;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}