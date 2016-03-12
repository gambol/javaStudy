package gambol.examples.serialize.benchmark.model;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
public class Foo implements Serializable {

    private static final long serialVersionUID = -1;

    protected Type type = Type.UNKNOWN;

    protected boolean flag;

    protected int num32;

    protected long num64;

    protected String str;

    protected List<Foo> children;

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setNum32(int num) {
        this.num32 = num;
    }

    public int getNum32() {
        return num32;
    }

    public void setNum64(long num) {
        this.num64 = num;
    }

    public long getNum64() {
        return num64;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setChildren(List<Foo> children) {
        this.children = children;
    }

    public List<Foo> getChildren() {
        return this.children;
    }

    public Foo(Type type, boolean flag, int num32, long num64, String str, List<Foo> children) {
        this.type = type;
        this.flag = flag;
        this.num32 = num32;
        this.num64 = num64;
        this.str = str;
        this.children = children;
    }
}
