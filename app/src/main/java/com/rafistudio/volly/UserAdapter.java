package com.rafistudio.volly;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UserModel> userModelList;
    private Boolean userList;

    public UserAdapter(List<UserModel> userModelList, Boolean userList) {
        this.userModelList = userModelList;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_list_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        String name = userModelList.get(position).getName();
        String description = userModelList.get(position).getDescription();
        String email = userModelList.get(position).getEmail();
        String password = userModelList.get(position).getPassword();

        viewHolder.setData(name, description, email, password);

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.name_user_item);
        }

        public void setData(final String name, final String description, final String email, final String password) {

            Name.setText(name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog userDetailsDialog = new Dialog(itemView.getContext());
                    userDetailsDialog.setContentView(R.layout.dialog_user);
                    userDetailsDialog.setCancelable(true);

                    userDetailsDialog.getWindow().getAttributes().windowAnimations = R.style.LeftToRight;

                    userDetailsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    TextView dialogName = userDetailsDialog.findViewById(R.id.dialogUser_name);
                    TextView dialogDescription = userDetailsDialog.findViewById(R.id.dialogUser_description);
                    TextView dialogEmailAddress = userDetailsDialog.findViewById(R.id.dialogUser_email);

                    dialogName.setText(name);
                    dialogDescription.setText(description);
                    dialogEmailAddress.setText(email);

                    userDetailsDialog.show();
                }
            });

        }
    }
}

