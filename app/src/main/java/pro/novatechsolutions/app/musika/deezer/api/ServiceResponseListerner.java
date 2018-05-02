package pro.novatechsolutions.app.musika.deezer.api;

public interface ServiceResponseListerner<T> {
    void onSuccess(T object);
    void onFailure(DeezerApiException e);
}
