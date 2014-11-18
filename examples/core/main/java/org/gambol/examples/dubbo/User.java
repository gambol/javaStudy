package org.gambol.examples.dubbo;

import java.io.Serializable;

/**
 * User: zhenbao.zhou
 * Date: 11/17/14
 * Time: 8:19 PM
 */

public class User implements Serializable {

    String name;
    int age;
    String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
