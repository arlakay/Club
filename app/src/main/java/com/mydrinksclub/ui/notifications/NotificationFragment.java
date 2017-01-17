package com.mydrinksclub.ui.notifications;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mydrinksclub.R;
import com.mydrinksclub.api.RestApi;
import com.mydrinksclub.api.services.ApiService;
import com.mydrinksclub.model.Notification;
import com.mydrinksclub.model.NotificationResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ERD on 11/23/2016.
 */

public class NotificationFragment extends Fragment {
    @BindView(R.id.txt_kosong)TextView txtKosong;
    @BindView(R.id.fragment_notification_recycler_view)RecyclerView recyclerView;

    private static final String TAG = "NotificationsFragment";
    private RecyclerView.LayoutManager layoutManager;
    private List<Notification> notificationList;
    private NotificationAdapter adapter2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        getMessageNotification();

        return view;
    }

    private void getMessageNotification(){
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "loading...");

        ApiService apiService =
                RestApi.getClient().create(ApiService.class);

        Call<NotificationResponse> call = apiService.getMessage();
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse>call, Response<NotificationResponse> response) {
                dialog.dismiss();

                notificationList = response.body().messages;
                Log.d(TAG, "Status Code = " + response.code());
                Log.d(TAG, "Data received: " + new Gson().toJson(response.body()));

                if (response.body().status){
                    txtKosong.setVisibility(View.GONE);

                    adapter2 = new NotificationAdapter(notificationList, R.layout.list_item_notifications, getActivity().getApplicationContext(), new NotificationAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Notification model) {

                        }
                    });
                    adapter2.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter2);
                }else{
                    txtKosong.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<NotificationResponse>call, Throwable t) {
                dialog.dismiss();

                Log.e(TAG, t.toString());

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Kesalahan Jaringan");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }
                });
                alertDialog.show();
            }
        });
    }

}
