package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import rest.dto.query.QueryRequest;
import rest.dto.query.QueryResponse;
import rest.dto.RestCallback;

import java.io.IOException;

public class RestSquilService {
    private static final String SQUIL_SERVICE_URL = "http://3.65.33.203:8080/api/v1/containers";

    public static void createContainer(String userID) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(SQUIL_SERVICE_URL + "?userID=" + userID).post(RequestBody.create(
                null,
                "")).build();
        client.newCall(request).execute();
    }

    public static void performQueryRequest(String userID, String query, RestCallback<String> finishCallback) {

        QueryRequest queryRequest = new QueryRequest(userID, query);

        ObjectMapper mapper = new ObjectMapper();
        String bodyString = "";
        try {
            bodyString = mapper.writeValueAsString(queryRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), bodyString);

        Request request = new Request.Builder()
                .url(SQUIL_SERVICE_URL + "/execute")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        OkHttpClient client = new OkHttpClient();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                finishCallback.onFinish(null);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() == 409) {
                    createContainer(userID);
                    performQueryRequest(userID, query, finishCallback);
                } else {
                    finishCallback.onFinish(mapper.readValue(response.body().byteStream(),
                            QueryResponse.class).getResult());
                }
            }
        });
    }
}

