package net.mikaelkrok.swf.executor;

import java.util.concurrent.Callable;

public interface StripedCallable<V> extends Callable<V>,
    StripedObject {
}