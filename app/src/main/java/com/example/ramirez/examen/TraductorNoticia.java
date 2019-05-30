package com.example.ramirez.examen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TraductorNoticia extends RecyclerView.Adapter<TraductorNoticia.MyViewHolder> {

    ArrayList<Noticia> noticias;
    Context context;

    public TraductorNoticia(ArrayList<Noticia> noticias, Context context) {
        this.noticias = noticias;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_noticia,viewGroup,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        final Noticia actual=noticias.get(i);
        holder.mTitulo.setText(actual.getmTitulo());
        holder.mDescripcion.setText(corregirdescripcion(actual.getmDescripcion()));
        holder.mFecha.setText(actual.getmFecha());
        //Picasso.get().load(actual.getmImagen()).into(holder.mImagen);
        //Picasso.with(context).load(actual.getmImagen()).into(holder.mImagen);
        Picasso.get().load(actual.getmImagen()).into(holder.mImagen);
        holder.mImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,DetalleNoticia.class);
                intent.putExtra("Enlace",actual.getmEnlace());
                context.startActivity(intent);
            }
        });
    }

    private String corregirdescripcion(String s) {

        String desOriginal=s;
        String separador="/>";
        String[] partes=desOriginal.split(separador);
        String devolver=partes[1];
        return devolver;
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTitulo,mDescripcion,mFecha;
        ImageView mImagen;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTitulo=(TextView) itemView.findViewById(R.id.tituloId);
            mDescripcion=(TextView) itemView.findViewById(R.id.descripcionId);
            mFecha=(TextView) itemView.findViewById(R.id.fechaId);
            mImagen=(ImageView) itemView.findViewById(R.id.imageViewId);
        }
    }
}
