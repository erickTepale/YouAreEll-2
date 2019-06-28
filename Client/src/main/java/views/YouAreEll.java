package views;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import controllers.*;
import models.Id;
import models.Message;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class YouAreEll {

    private MessageController msgCtrl;
    private IdController idCtrl;
    private ObjectMapper objectMapper;

    public YouAreEll (MessageController m, IdController j) {
        // used j because i seems awkward
        this.msgCtrl = m;
        this.idCtrl = j;
        this.objectMapper = new ObjectMapper();
    }

    public static void main(String[] args) {
        // hmm: is this Dependency Injection?
        YouAreEll urlhandler = new YouAreEll(MessageController.getInstance(), IdController.getInstance());
//        System.out.println(urlhandler.MakeURLCall("/ids", "GET", ""));
//        System.out.println(urlhandler.MakeURLCall("/messages", "GET", ""));
    }

    public String get_ids() {
        return MakeURLCall("/ids", "GET", "");
    }

    public String get_messages() {
        return MakeURLCall("/messages", "GET", "");
    }

    public String putId(String jpayload) {return MakeURLCall("/ids", "PUT", jpayload);}

    public RequestBody parsePayload(String jpayload){

        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jpayload );
    }

    public String MakeURLCall(String mainurl, String method, String jpayload) {
        OkHttpClient client = new OkHttpClient();


        switch (method){
            case "GET":
                Request request = new Request.Builder()
                        .url("http://zipcode.rocks:8085" + mainurl)
                        .build();
                return requestGET(client, request, mainurl);
            case "PUT":
                Request requestPut = new Request.Builder()
                        .url("http://zipcode.rocks:8085" + mainurl)
                        .put(parsePayload(jpayload))
                        .build();
                return requestPUT(client, requestPut, mainurl);
        }

        return "nada";
    }


    private String requestPUT(OkHttpClient client, Request request, String mainurl) {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();

        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
}


    private String requestGET(OkHttpClient client, Request request, String mainurl){
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            switch (mainurl){
                case "/ids":
                    ArrayList<Id> ids = objectMapper.readValue(response.body().string(), new TypeReference<List<Id>>(){});
                    IdController.getInstance().setIdList(ids);
                    //turns into string ? im not sure lulul
                    return ids.stream().map(Object::toString)
                            .collect(Collectors.joining(" "));

                case "/messages":
                    HashSet<Message> messages = objectMapper.readValue(response.body().string(), new TypeReference<HashSet<Message>>(){});
                    MessageController.getInstance().setMessagesSeen(messages);
                    return messages.stream().map(Object::toString).collect(Collectors.joining(" "));
            }

        } catch (IOException e) {
            System.out.println("failed");
            e.printStackTrace();
        }

        return null;
    }


}
