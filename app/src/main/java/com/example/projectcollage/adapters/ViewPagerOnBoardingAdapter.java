package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import com.example.projectcollage.R;

public class ViewPagerOnBoardingAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;

    public ViewPagerOnBoardingAdapter(Context context) {
        this.context = context;
    }
    private final int[] images ={
            R.drawable.p4,
            R.drawable.p2,
            R.drawable.p3
    };
    private final int[] imagesWave ={
            R.drawable.w1,
            R.drawable.w4,
            R.drawable.w3
    };
    private final String[] titles ={
            "التعلم",
            "البحث العلمي",
            "خدمه المجتمع"
    };
    private final String[] disc ={
            "ان الانسان الميت هو الانسان الذي كف عن التعلم واكتساب الخبرات , ولهذا ترون اننا محاطون بالموتي الاحياء طيلة الوقت و التعلم هو اقوي سلاح يمكنك استخدامة لتغيير العالم",
            "يعتبر البحث العلمي اهم اداه لمعرفة حقائق الكون والانسان و الحياه ويتيح البحث العلمي للباحث الاعتماد علي نفسة في اكتساب المعلومات",
            "يساهم في تخليص المجتمع من الظواهر السلبية لانة يجد حلول لهذه الظواهر"
    };

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.view_pager_on_boarding, container, false);
        ImageView imageView = view.findViewById(R.id.imgViewPager);
        ImageView imageViewWave = view.findViewById(R.id.imgViewPagerWave);
        TextView titleTextView=view.findViewById(R.id.txtTitleViewPager);
        TextView discTextView=view.findViewById(R.id.txtDiscViewPager);
        imageView.setImageResource(images[position]);
        imageViewWave.setImageResource(imagesWave[position]);
        titleTextView.setText(titles[position]);
        discTextView.setText(disc[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
