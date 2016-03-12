package gambol.examples.serialize.benchmark.model;

import java.io.Serializable;

/**
 * Created by zhenbao.zhou on 16/3/12.
 */
public enum Type implements Serializable {

    UNKNOWN(0),
    FOO(1),
    BAR(2);

    private static final long serialVersionUID = -1;

    private final int value;

    private Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}