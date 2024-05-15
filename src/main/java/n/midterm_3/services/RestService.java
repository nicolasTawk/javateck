package n.midterm_3.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.json.JSONObject;

public class RestService extends ApiService {
    private final String baseUrl;
    private final HttpClient client;

    public RestService(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public Object get(String endpoint, Map<String, String> headers, Map<String, String> queryParameters) throws Exception {
        URI uri = new URI(baseUrl + endpoint + "?" + buildQueryString(queryParameters));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .headers(buildHeaders(headers))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response);
    }

    @Override
    public Object post(String endpoint, Map<String, String> headers, Object body) throws Exception {
        URI uri = new URI(baseUrl + endpoint);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .headers(buildHeaders(headers))
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject(body).toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response);
    }

    @Override
    public Object put(String endpoint, Map<String, String> headers, Object body) throws Exception {
        URI uri = new URI(baseUrl + endpoint);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .headers(buildHeaders(headers))
                .PUT(HttpRequest.BodyPublishers.ofString(new JSONObject(body).toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response);
    }

    @Override
    public Object delete(String endpoint, Map<String, String> headers) throws Exception {
        URI uri = new URI(baseUrl + endpoint);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .headers(buildHeaders(headers))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response);
    }

    private String[] buildHeaders(Map<String, String> headers) {
        return headers.entrySet().stream()
                .flatMap(entry -> java.util.stream.Stream.of(entry.getKey(), entry.getValue()))
                .toArray(String[]::new);
    }

    private String buildQueryString(Map<String, String> queryParameters) {
        if (queryParameters == null || queryParameters.isEmpty()) {
            return "";
        }
        return queryParameters.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((param1, param2) -> param1 + "&" + param2)
                .orElse("");
    }

    private Object handleResponse(HttpResponse<String> response) throws Exception {
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            if (!response.body().isEmpty()) {
                return new JSONObject(response.body());
            } else {
                return response.body();
            }
        } else {
            throw new HttpException(response.statusCode(), response.body());
        }
    }

    public static class HttpException extends Exception {
        private final int statusCode;
        private final String message;

        public HttpException(int statusCode, String message) {
            super("HttpException: " + statusCode + " " + message);
            this.statusCode = statusCode;
            this.message = message;
        }

        @Override
        public String toString() {
            return "HttpException: " + statusCode + " " + message;
        }
    }
}
