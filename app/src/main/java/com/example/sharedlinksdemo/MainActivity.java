package com.example.sharedlinksdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mLinksRecyclerView;
    private LinksAdapter mAdapter;
    private List<Link> mLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinks = new ArrayList<>();
        // Get a handle to the RecyclerView.
        mLinksRecyclerView = findViewById(R.id.links_recyclerView);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new LinksAdapter(MainActivity.this, mLinks);
        // Connect the adapter with the RecyclerView.
        mLinksRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mLinksRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        new InitLinks().execute();
    }



    public class InitLinks extends AsyncTask<Void, Void, Void> {
        List<String> urls = Arrays.asList("https://www.youtube.com/watch?v=94rCjYxvzEE",
                "https://www.bignerdranch.com/blog/understanding-androids-layoutinflater-inflate/",
                "https://www.youtube.com/watch?v=HYLbHkBi6So",
                "https://www.youtube.com/watch?v=xvqsFTUsOmc&t=889s",
                "https://www.youtube.com/watch?v=zPx5N6Lh3sw");


        @Override
        protected Void doInBackground(Void... voids) {
            for (String url:
                 urls) {
                String linkUrl = "";
                String title = "";
                String thumbnailUrl = "";
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements elements=doc.select("meta");
                    for(Element e: elements){
                        //fetch image url from content attribute of meta tag.
                        thumbnailUrl = e.attr("content");

                        //OR more specifically you can check meta property.
                        if(e.attr("property").equalsIgnoreCase("og:image")){
                            thumbnailUrl = e.attr("content");
                            break;
                        }
                    }

                    title = doc.title();
                    linkUrl = url;
                } catch (IOException e){
                    linkUrl = "ERROR Url";
                    title = "ERROR TItle";
                    Log.d(Link.class.getSimpleName(), e.toString());
                } catch (Exception e) {
                    Log.d("ERROR", e.toString());
                } finally {
                    mLinks.add(new Link(thumbnailUrl, linkUrl, title));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            mAdapter.notifyDataSetChanged();
        }
    }
}