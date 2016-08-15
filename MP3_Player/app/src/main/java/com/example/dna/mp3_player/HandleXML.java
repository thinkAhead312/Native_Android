package com.example.dna.mp3_player;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dna on 6/29/16.
 */
public class HandleXML {

    private String title = "title";
    private String image = "image";
    private String music = "music";

    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile  boolean parsingComplete = true;

    public HandleXML(String url) {
        this.urlString = url;
    }

    public String getTitle(){
        return title;
    }

    public String getImage(){
        return image;
    }

    public String music(){
        return music;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser)
    {
        int event;
        String text = null;
        try {
            event = myParser.getEventType(); //return the event that happens
            while (event != XmlPullParser.END_DOCUMENT)
            {
                String name=myParser.getName(); //return the name of the tag
                switch (event)
                {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(name.equals("title"))
                        {
                            title = text;
                        }
                        else if(name.equals("image"))
                        {
                            image = text;
                        }
                        else if(name.equals("guid"))
                        {
                            music = text;
                        }
                        else {}
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();


                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();
                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);
                    parseXMLAndStoreIt(myparser);
                    stream.close();
                }

                catch (Exception e) {
                }
            }
        });
        thread.start();
    }


}
