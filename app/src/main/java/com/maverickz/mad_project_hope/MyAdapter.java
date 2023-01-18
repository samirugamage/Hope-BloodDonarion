package com.maverickz.mad_project_hope;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public MyAdapter(Context context, ArrayList<Post> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    ArrayList<Post> list;
    int pos;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        this.pos = position;
        Post post = list.get(position);
        holder.heading.setText(post.getHeading());
       // holder.imageUri.setText(post.getImageUri());
        holder.description.setText(post.getDescription());

//        Glide.with(context).load(image)



        Picasso.get().load(post.getImageUri()).into(holder.img);
//        holder.img.setImageURI(Uri.parse(post.getImageUri()));
//Edit Option-----------------------------------------------------------------------------------------------------------Start--------------------
        holder.editfloatingActionBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.editfloatingActionBut.getContext()).setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true, 1500).create();

                View myview = dialogPlus.getHolderView();
                EditText editpostheadingtext = myview.findViewById(R.id.editpostheadingtext);
                EditText editpostdescriptiontext = myview.findViewById(R.id.editpostdescriptiontext);
                Button idpostupdatebtn = myview.findViewById(R.id.idpostupdatebtn);

                editpostheadingtext.setText(post.getHeading());
                editpostdescriptiontext.setText(post.getDescription());

                dialogPlus.show();


                idpostupdatebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("heading",editpostheadingtext.getText().toString());
                        map.put("description",editpostdescriptiontext.getText().toString());

                       //Firebase product_key = getItem(position);

                        FirebaseDatabase.getInstance().getReference().child("Post")
                                .child(post.getID())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();

                                    }
                                });
                    }
                });

            }
        });

//Edit Option---------------------------------------------------------------------------------------------------------End------------------------

        holder.deletefloatingActionBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.deletefloatingActionBut.getContext());
                builder.setTitle("Are you Sure ?");
                builder.setMessage("Do you really want to delete this post ? This process cannot be undone...");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("Post").child(post.getID()).removeValue();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView heading, description,imageUri;
        ImageView img;
        FloatingActionButton editfloatingActionBut;
        FloatingActionButton deletefloatingActionBut;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            heading = itemView.findViewById(R.id.itempostcardHeading);
            description = itemView.findViewById(R.id.itempostcardDescription);
            img = itemView.findViewById(R.id.itempostimage);

            //ImageView img = (ImageView) imageUri.findViewById(R.id.itempostimage);
         //img = itemView.find

            editfloatingActionBut = itemView.findViewById(R.id.editfloatingActionBut);
            deletefloatingActionBut = itemView.findViewById(R.id.deletefloatingActionBut);
        }
    }
}
