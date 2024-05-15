package n.midterm_3.services;

import java.util.Map;

public abstract class ApiService {
    public abstract Object get(String endpoint, Map<String, String> headers, Map<String, String> queryParameters) throws Exception;
    public abstract Object post(String endpoint, Map<String, String> headers, Object body) throws Exception;
    public abstract Object put(String endpoint, Map<String, String> headers, Object body) throws Exception;
    public abstract Object delete(String endpoint, Map<String, String> headers) throws Exception;
}
