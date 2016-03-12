package gambol.examples.serialize.benchmark.model;

import com.google.common.collect.Lists;

import java.util.List;

public class FooBuilder {
    private Type type;
    private boolean flag;
    private int num32;
    private long num64;
    private String str;
    private List<Foo> children;

    public FooBuilder setType(Type type) {
        this.type = type;
        return this;
    }

    public FooBuilder setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public FooBuilder setNum32(int num32) {
        this.num32 = num32;
        return this;
    }

    public FooBuilder setNum64(long num64) {
        this.num64 = num64;
        return this;
    }

    public FooBuilder setStr(String str) {
        this.str = str;
        return this;
    }

    public FooBuilder setChildren(List<Foo> children) {
        this.children = children;
        return this;
    }

    public Foo createFoo() {
        return new Foo(type, flag, num32, num64, str, children);
    }


    public static Foo buildObject(String name) {
        return buildObject(name, 0);
    }

    public static Foo buildObject(String name, int numChildren) {
        FooBuilder builder = new FooBuilder()
                .setType(Type.FOO)
                .setFlag(true)
                .setNum32(Integer.MAX_VALUE)
                .setNum64(Long.MAX_VALUE)
                .setStr(name);
        if (numChildren > 0) {
            List<Foo> childList = Lists.newArrayListWithCapacity(numChildren);
            for (int i = 0; i < numChildren; i++) {
                childList.add(buildObject("child " + (i + 1), 0));
            }

            builder.setChildren(childList);
        }
        return builder.createFoo();
    }
}