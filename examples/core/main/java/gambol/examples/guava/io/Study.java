package gambol.examples.guava.io;

import com.google.common.base.Charsets;
import com.google.common.io.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.util.List;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.getResource;
import static com.google.common.io.Resources.newReaderSupplier;
import static com.google.common.io.Resources.readLines;

/**
 * @author kris.zhang
 */
public class Study {

    public static void main(String[] args) throws IOException {
        /**
         * 关于guava io抽象的还是不错的，也比较常用。流分为字符流和字节流，按照流向分为源(Sources)与汇 (Sinks)
         * 方向是：source->sink
         * 因此2*2=4，有四种：
         */
        ByteSource byteSource = null;
        ByteSink byteSink = null;
        CharSource charSource = null;
        CharSink charSink = null;

        /** 针对字符流和字节流的操作集中在以下两个类中的静态方法中 */
        ByteStreams byteStreams = null;//无法实例化，用静态方法
        CharStreams charStreams = null;//无法实例化，用静态方法

        ByteStreams.copy(new FileInputStream(""),new FileOutputStream(""));
        ByteStreams.copy(new FileInputStream("").getChannel(),new FileOutputStream("").getChannel());
        CharStreams.asCharSink(new OutputSupplier<Appendable>() {
            @Override
            public Appendable getOutput() throws IOException {
                return new StringBuilder();
            }
        });

        CharStreams.asCharSink(new OutputSupplier<Appendable>() {
            @Override
            public Appendable getOutput() throws IOException {
                return new StringBuilder();
            }
        });

        /**
         * 资源加载
         */

        /** 项目中使用的方式：*/
        /** 1 */
        System.out.println(Study.class.getResource("applicationContext.xml"));//null
        System.out.println(Study.class.getResource("/applicationContext.xml"));//null

        /** 2 */
        System.out.println(Study.class.getClassLoader().getResource("applicationContext.xml"));//ok ,
        System.out.println(Study.class.getClassLoader().getResource("/applicationContext.xml"));//ok,

        /** 3 */
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("applicationContext.xml"));//ok
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("/applicationContext.xml"));//ok

        /**
         * 1. path 不以’/'开头时默认是从此类所在的包下取资源，
         * 以’/'开头则是从ClassPath根下获取。其只是通过path构造一个绝对路径，最终还是由ClassLoader获取资源。
         *
         * 2. 默认则是从ClassPath根下获取，path不能以’/'开头。
         *
         * 3. 为啥我们使用上下文类加载器，而不是指定某个类的类加载器
         */

        /** 为了保持统一，我建议项目中使用guava的Resources统一资源的加载操作 注意这里不能有/ */
        URL url = Resources.getResource("applicationContext.xml");
        charSource = Resources.asCharSource(url, Charsets.UTF_8);

        byteSource = Resources.asByteSource(url);

        /** 基本流处理 这里只说字符流，字节流基本是一样的 */
        charSink.write("nihao");
        charSink.openStream().write("");
        charSource.read();
        CharSource.wrap("").openStream();

        /** 直接加载到内存 */
        List<String> allLines = readLines(url, UTF_8);

        /** 读取行 */
        Resources.readLines(url, Charsets.UTF_8, new LineProcessor<Object>() {
            @Override
            public boolean processLine(String line) throws IOException {
                return false;
            }

            @Override
            public Object getResult() {
                return null;
            }
        });

        /** 采用static import的方式 */
        Object result = readLines(getResource(""),UTF_8, new LineProcessor<Object>(){

            @Override
            public boolean processLine(String line) throws IOException {
                return false;
            }

            @Override
            public Object getResult() {
                return null;
            }
        });

        /** Files使用与jdk7的Files很像不过jdk中操作更多的是Path而不是File */
        Files.createTempDir();

        Files.touch(new File(""));
        Files.asByteSink(new File(""));
        Files.asByteSource(new File(""));
        Files.asCharSink(new File(""), Charsets.UTF_8);
        Files.asCharSource(new File(""), Charsets.UTF_8);

        MappedByteBuffer mappedByteBuffer = Files.map(new File(""));

        Files.copy(new File("from"),new File("to"));
        Files.createTempDir();//创建临时目录

        /** 注意这个是File，不是Files，Files只提供创建临时目录 */
        File.createTempFile("","");//创建临时份文件

        Files.copy(new File(""),new File(""));

        /** closer 一般用于jdk7以前版本，如果我们项目使用jdk7直接用try-with-resource吧 */
        /** http://code.google.com/p/guava-libraries/wiki/ClosingResourcesExplained */
    }

}
