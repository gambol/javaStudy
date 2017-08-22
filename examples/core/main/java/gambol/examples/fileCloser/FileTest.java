package gambol.examples.fileCloser;

import com.google.common.io.Closer;
import gambol.examples.guava.Share;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhenbao.zhou on 17/2/20.
 */
public class FileTest {

    public static void main(String[] args) throws Exception {

        File tempDir = new File("/tmp");

        for (; ; ) {
            List<File> files = new ArrayList<>();
            try {
                Path path = tempDir.toPath();
                Files.list(path).close();
//                        .filter(path1 -> path1.getFileName().toString().endsWith("xml"))
//                        .map(Path::toFile)
//                        .collect(toList());

            } catch (Throwable e) {

            }

            Path p = tempDir.toPath();
            p.toString();

            Thread.sleep(100);
        }



    }
}
