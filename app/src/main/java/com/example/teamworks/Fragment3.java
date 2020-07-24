package com.example.teamworks;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.net.ssl.SSLContext;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Fragment3 extends Fragment {
    private ListView listview;

    class SpaceCraft {
        @SerializedName( "albumId" )
        private int albumid;
        @SerializedName( "id" )
        private int id;
        @SerializedName( "title" )
        private String title;
        @SerializedName( "url" )
        private String url;
        @SerializedName( "turl" )
        private String turl;

        public int getAlbumid() {
            return albumid;
        }

        public void setAlbumid(int albumid) {
            this.albumid = albumid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTurl() {
            return turl;
        }

        public void setTurl(String turl) {
            this.turl = turl;
        }

        @Override
        public String toString() {
            return "SpaceCraft{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", turl='" + turl + '\'' +
                    '}';
        }

        public SpaceCraft(int albumid, int id, String title, String url, String turl) {
            this.albumid = albumid;
            this.id = id;
            this.title = title;
            this.url = url;
            this.turl = turl;


        }
    }

    interface MyAPIService {
        @GET("https://jsonplaceholder.typicode.com/photos")
        Call<List<SpaceCraft>> getSpaceCraft();
    }

  static class RetrofitClientInstance {

         private static Retrofit retrofit;
        private static final String BASE_URL = "https://github.com";

        public static Retrofit getRetrofitInstance() {
            if (retrofit == null){
                try{
                    retrofit = new Retrofit.Builder()
                            .baseUrl( BASE_URL )
                            .addConverterFactory( GsonConverterFactory.create() )
                            .build();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
            return retrofit;
        }
    }

    class ListViewAdapter extends BaseAdapter{

        private List<SpaceCraft> spaceCrafts;
        private Context context;

        public ListViewAdapter(List<SpaceCraft> spaceCrafts, Context context) {
            this.spaceCrafts = spaceCrafts;
            this.context = context;
        }

        public List<SpaceCraft> getSpaceCrafts() {
            return spaceCrafts;
        }

        public void setSpaceCrafts(List<SpaceCraft> spaceCrafts) {
            this.spaceCrafts = spaceCrafts;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return spaceCrafts.size();
        }

        @Override
        public Object getItem(int position) {
            return spaceCrafts.get( position );
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
           if (view == null)
           {
               view = LayoutInflater.from( context ).inflate( R.layout.model,viewGroup, false );

           }
            ImageView spacecraftImageView = view.findViewById( R.id.spacecraftImageView );
            final SpaceCraft thisSpacecraft = spaceCrafts.get( position );

            if(thisSpacecraft.getUrl() != null && thisSpacecraft.getUrl().length() > 0 ){

                Picasso.get().load( thisSpacecraft.getUrl() ).placeholder( R.drawable.gmail ).into(spacecraftImageView);
            }else {
                Toast.makeText( getContext() , "Empty Image URL...",Toast.LENGTH_SHORT ).show();
                Picasso.get().load( R.drawable.gmail ).into(spacecraftImageView);
            }
            return view;
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate( R.layout.fragment3_layout,container,false );


        listview = v.findViewById( R.id.listView );

        MyAPIService myAPIService = RetrofitClientInstance.getRetrofitInstance().create(MyAPIService.class);
        Call<List<SpaceCraft>> call = myAPIService.getSpaceCraft();
        call.enqueue( new Callback<List<SpaceCraft>>() {
            @Override
            public void onResponse(Call<List<SpaceCraft>> call, Response<List<SpaceCraft>> response) {
                 populateListView( response.body() );
            }

            @Override
            public void onFailure(Call<List<SpaceCraft>> call, Throwable t) {
                Toast.makeText( getContext(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        } );

    return v;

    }
        private void populateListView(List<SpaceCraft> spaceCraftList)
        {

            Adapter adapter = new ListViewAdapter( spaceCraftList, getContext() );
            listview.setAdapter( (ListAdapter) adapter );
         }

}
