package com.example.projectcollage.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.projectcollage.fragments.ChatsFragment;
import com.example.projectcollage.fragments.ClassroomFragment;
import com.example.projectcollage.fragments.HomeFragment;
import com.squareup.picasso.Picasso;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private boolean hideClassroom=false;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity,boolean hideClassroom) {
        super(fragmentActivity);
        this.hideClassroom=hideClassroom;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (hideClassroom){
            switch (position){
                case 0:
                    return new HomeFragment();
                case 1:
                    return new ClassroomFragment();
                default:
                    return new ChatsFragment();

            }

        }else {
            if (position == 0) {
                return new HomeFragment();
            }
            return new ChatsFragment();
        }

    }
    @Override
    public int getItemCount() {
        if (hideClassroom){
            return 3;
        }else {
            return 2;
        }
    }
}
