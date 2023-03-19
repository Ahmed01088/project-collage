package com.example.projectcollage.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.projectcollage.databinding.ActivityLiveBinding;

import java.util.HashSet;
import java.util.Set;

import io.agora.agorauikit_android.AgoraConnectionData;
import io.agora.agorauikit_android.AgoraSettings;
import io.agora.agorauikit_android.AgoraVideoViewer;

public class LiveActivity extends AppCompatActivity {
    ActivityLiveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AgoraSettings agoraSettings = new AgoraSettings();
        Set<AgoraSettings.BuiltinButton> enabledButtons = new HashSet<>();
        enabledButtons.add(AgoraSettings.BuiltinButton.CAMERA);
        enabledButtons.add(AgoraSettings.BuiltinButton.MIC);
        enabledButtons.add(AgoraSettings.BuiltinButton.FLIP);
        agoraSettings.setEnabledButtons(enabledButtons);
        AgoraVideoViewer agView ;
        try {
            agView = new AgoraVideoViewer(
                    this, new AgoraConnectionData("app-id-here"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        this.addContentView(agView, layoutParams);

        agView.join("test");

    }
}