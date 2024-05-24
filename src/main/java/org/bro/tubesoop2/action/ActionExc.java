package org.bro.tubesoop2.action;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ActionExc<T> {
    public ArrayList<Consumer<T>> list;
    public ActionExc(){
        list = new ArrayList<>();
    }
    public void AddListener(Consumer<T> r){
        list.add(r);
    }
    public void RemoveListener(Consumer<T> r){
        list.remove(r);
    }

    public void Notify(T t) throws Exception{
        boolean anyException = false;
        list.forEach(c -> {

            c.accept(t);
        });
    }
}
