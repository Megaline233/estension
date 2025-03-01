package xyz.Extencion.extencion.event;

public interface Cancellable {
    boolean isCancelled();
    void cancel();
}