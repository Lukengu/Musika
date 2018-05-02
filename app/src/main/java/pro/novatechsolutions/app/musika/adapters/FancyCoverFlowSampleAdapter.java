package pro.novatechsolutions.app.musika.adapters;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pro.novatechsolutions.app.musika.R;
import pro.novatechsolutions.app.musika.coverflow.FancyCoverFlow;
import pro.novatechsolutions.app.musika.coverflow.FancyCoverFlowAdapter;
import pro.novatechsolutions.app.musika.deezer.api.response.AlbumResponse;

public class FancyCoverFlowSampleAdapter  extends FancyCoverFlowAdapter {

    private ArrayList<AlbumResponse> data = new ArrayList<>(0);
    private Context activity;


    public FancyCoverFlowSampleAdapter(Context activity){
        this.activity = activity;
    }

    public void updateData( ArrayList<AlbumResponse> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public AlbumResponse getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getCoverFlowItem(final int i, View reuseableView, ViewGroup viewGroup) throws NotFoundException {
        ImageView my_image = null;
        RelativeLayout container = null;
        TextView title = null;
        LinearLayout title_container = null;


        if (reuseableView != null) {
            // my_image = (ImageView) reuseableView;
            container = (RelativeLayout) reuseableView;
        } else {
            container = new RelativeLayout(viewGroup.getContext());
            my_image = new ImageView(viewGroup.getContext());
            //my_image.setId(R.id.);

            title = new TextView(viewGroup.getContext());

            title_container = new LinearLayout(viewGroup.getContext());
            title.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            try {
                title.setText(getItem(i).getTitle().concat(" - ").concat(getItem(i).getArtist()));
            } catch(NullPointerException e){}

            title.setTextColor(Color.WHITE);
            title.setPadding(30,8, 30, 8);
            title.setGravity(Gravity.CENTER);


            title_container.setLayoutParams(new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) title_container.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
           final int sdk = android.os.Build.VERSION.SDK_INT;

            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {


                    title_container.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.label_background));


            } else {
                 title_container.setBackground(activity.getResources().getDrawable(R.drawable.label_background));
             }
            title_container.addView(title);
            title.setTextSize(11);
            title.setMaxLines(1);
            title.setMaxWidth(65);
            title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
             title.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/ProximaNova-Bold.otf"));








            container.setLayoutParams(new FancyCoverFlow.LayoutParams(490, 600));
            my_image.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));




            my_image.setScaleType(ImageView.ScaleType.FIT_XY);

            Glide
                    .with(activity)
                    .load(getItem(i).getCover_big())
                    .placeholder(R.drawable.ic_loading)
                    .centerCrop()
                    .into(my_image);

            //my_image.setPadding(50,0,50,0);
            container.addView(my_image);
            container.addView(title_container);





        }


        return container;
    }
}
