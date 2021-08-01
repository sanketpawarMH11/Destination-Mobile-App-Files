package com.example.destination.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.destination.R;
import com.example.destination.models.Bus;

public class BusViewHolder extends RecyclerView.ViewHolder {
	public ImageView starView;
	private TextView authorView;
	private TextView bodyView;
	private TextView numStarsView;
	private TextView titleView;

    private TextView vehical1;
    private TextView busNo1;
    private TextView ticketRate1;
    private TextView operator1;
    private TextView seats1;
    private TextView time1;
    private TextView route1;


    public BusViewHolder(View itemView) {
		super(itemView);
		titleView = itemView.findViewById(R.id.post_title);
		authorView = itemView.findViewById(R.id.post_author);
		//starView = itemView.findViewById(R.id.star);
		//numStarsView = itemView.findViewById(R.id.post_num_stars);
		bodyView = itemView.findViewById(R.id.post_body);

	}

	public void bindToBus(Bus bus) {
		titleView.setText(bus.title);
		authorView.setText(bus.author);

		//numStarsView.setText(String.valueOf(bus.starCount));
		bodyView.setText(bus.body);
//		starView.setOnClickListener(starClickListener);

	}
}