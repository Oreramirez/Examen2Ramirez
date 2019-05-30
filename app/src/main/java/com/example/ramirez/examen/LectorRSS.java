package com.example.ramirez.examen;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class LectorRSS extends AsyncTask <Void,Void,Void> {
    //captura de la informacion
    ArrayList<Noticia> noticias;
    RecyclerView recyclerView;
    Context context;
    String direccion="https://es.rbth.com/rss";
    //String direccion="https://medium.com/rss";
    //String direccion="http://www.upt.edu.pe/upt/web";
    URL url;
    ProgressDialog progressDialog;

    //constructor
    public LectorRSS(Context context,RecyclerView recyclerView){
        this.recyclerView=recyclerView;
        this.context=context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
    }

    //metodos
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        TraductorNoticia traductorNoticia = new TraductorNoticia(noticias,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(traductorNoticia);
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        procesarXML(ObtenerDatos());
        return null;
    }
    private void procesarXML(Document data){
        if(data!=null){
            //Log.d("Elemento Root",data.getDocumentElement().getNodeName());
            noticias=new ArrayList<>();
            //Construimos los elementos y obtenemos informacion

            Element root=data.getDocumentElement();
            Node channel=root.getChildNodes().item(1);
            NodeList items=channel.getChildNodes();
            for (int i=0;i<items.getLength();i++){
                Node hijoActual=items.item(i);
                if(hijoActual.getNodeName().equalsIgnoreCase("item")){
                    Noticia noticia=new Noticia();

                    NodeList itemChild=hijoActual.getChildNodes();
                    for (int j=0;j<itemChild.getLength();j++){
                        Node actual=itemChild.item(j);
                        //Log.d("Elementos",actual.getTextContent());
                        if(actual.getNodeName().equalsIgnoreCase("title")){
                            noticia.setmTitulo(actual.getTextContent());
                        }
                        else if(actual.getNodeName().equalsIgnoreCase("link")){
                            noticia.setmEnlace(actual.getTextContent());
                        }
                        else if(actual.getNodeName().equalsIgnoreCase("description")){
                            noticia.setmDescripcion(actual.getTextContent());
                        }
                        else if(actual.getNodeName().equalsIgnoreCase("enclosure")){
                            String mUrl=actual.getAttributes().item(0).getTextContent();
                            noticia.setmImagen(mUrl);
                        }
                        else if(actual.getNodeName().equalsIgnoreCase("pubDate")){
                            noticia.setmFecha(actual.getTextContent());
                        }
                    }
                    noticias.add(noticia);
                    Log.d("Titulo",noticia.getmTitulo());
                    Log.d("Link",noticia.getmEnlace());
                    Log.d("Descripcion",noticia.getmDescripcion());
                    Log.d("Imagen",noticia.getmImagen());
                    Log.d("Fecha",noticia.getmFecha());
                }
            }
        }
    }

    public Document ObtenerDatos(){
        try {
            //hacer una peticion

            url=new URL(direccion);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");

            //procesar una peticion
            InputStream inputStream= connection.getInputStream();
            DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=builderFactory.newDocumentBuilder();
            Document xmlDoc=builder.parse(inputStream);
            return xmlDoc;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
