package rest.dto;

public interface RestCallback<T> {
    void onFinish(T result);
}
