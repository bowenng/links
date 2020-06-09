package com.example.sharedlinksdemo;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Link {
    public String thumbnailUrl;
    public String linkUrl;
    public String title;

    public Link(String thumbnailUrl, String linkUrl, String title) {
        this.thumbnailUrl = thumbnailUrl;
        this.linkUrl = linkUrl;
        this.title = title;
    }
}
