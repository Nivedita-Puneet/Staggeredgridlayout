package com.nivedita.carouseldemo.utilities;

import android.net.Uri;
import android.util.Log;

import com.nivedita.carouseldemo.model.Image;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by PUNEETU on 03-06-2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    /*Builds a basic Url using Uri class*/

    public static URL buildURL(String imageUrl) {

        Uri buildUri = Uri.parse(imageUrl).buildUpon().build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException exception) {
            Log.e(TAG, exception.getLocalizedMessage());
        }

        return url;
    }


    /*Make http request uri to get xml response*/
    public static String makeHttpRequest(URL url) throws IOException {

        String xmlResponse = "";

        if (url == null) {
            return xmlResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(150000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            /*check the response code to identify if response was obtained successfully*/
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                xmlResponse = readFromStream(inputStream);

            } else {
                Log.i(TAG, "Error response code" + ":" + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retriving the results of movie" + e.getLocalizedMessage());
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return xmlResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }

        }
        return output.toString();
    }

    public static Image getImageUrlFromXMLResponse(String response) {

        Image image = null;
        try {

            Document xmlDocument = getXMLDocument(response);
            xmlDocument.getDocumentElement().normalize();
            Log.i(TAG, xmlDocument.getDocumentElement().getNodeName());

            NodeList list = xmlDocument.getElementsByTagName(Utilconstants.PATTERN);
            for (int i = 0; i < list.getLength(); i++) {

                String title = null;
                String imageUrl = null;


                Node nNode = list.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    title = eElement.getElementsByTagName(Utilconstants.TITLE).item(0).getTextContent();
                    imageUrl = eElement.getElementsByTagName(Utilconstants.IMAGE_URL).item(0).getTextContent();
                    image = new Image(title, imageUrl);
                }
            }

        } catch (ParserConfigurationException exception) {

            Log.e(TAG, exception.getLocalizedMessage());
        } catch (IOException exception) {

            Log.e(TAG, exception.getLocalizedMessage());
        } catch (SAXException exception) {

            Log.e(TAG, exception.getLocalizedMessage());
        }
        return image;
    }

    public static Document getXMLDocument(String response) throws SAXException, IOException, ParserConfigurationException {

        Document document = null;

        InputSource is = new InputSource(new StringReader(response));
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        document = dBuilder.parse(is);

        return document;
    }


}



