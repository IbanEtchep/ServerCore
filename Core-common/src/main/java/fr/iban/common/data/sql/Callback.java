package fr.iban.common.data.sql;

public interface Callback<V extends Object, T extends Throwable> {

    void call(V result, T thrown);

}

