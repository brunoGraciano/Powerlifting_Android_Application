package pt.ismai.a031500.powerlifting;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyCustomerAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<VideoDetails> videoDetailsArrayList;
    LayoutInflater inflater;

    public MyCustomerAdapter(Activity activity, ArrayList<VideoDetails> videoDetailsArrayList) {
        this.activity = activity;
        this.videoDetailsArrayList = videoDetailsArrayList;
    }

    @Override
    public int getCount() {
        return videoDetailsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.videoDetailsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = this.activity.getLayoutInflater();
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.thumbnail_video);
        TextView textView = convertView.findViewById(R.id.title_video);
        LinearLayout linearLayout = convertView.findViewById(R.id.root);
        final VideoDetails videoDetails = this.videoDetailsArrayList.get(position);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, VideoPlayActivity.class);
                i.putExtra("videoId", videoDetails.getVideoId());
                activity.startActivity(i);
            }
        });


        Picasso.get().load(videoDetails.getUrl()).into(imageView);

        textView.setText(videoDetails.getTitle());

        return convertView;
    }
}
