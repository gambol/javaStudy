package gambol.examples.writecode.prettycode;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhenbao.zhou on 16/5/10.
 */
public class IfKiller2 {

    /**
     * 处理命令行
     * 
     * @param line
     * @return
     */
    void process(String line) throws IOException {
        Preconditions.checkNotNull(line);
        List<String> commands = Splitter.on(" ").splitToList(line);
        String command = commands.get(0);
        if ("rm".equals(command)) {
            new File(commands.get(1)).delete();
        } else if ("copy".equals(command)) {
            Files.copy(new File(commands.get(1)), new File(commands.get(2)));
        } else if ("mv".equals(command)) {
            Files.move(new File(commands.get(1)), new File(commands.get(2)));
        }
    }

    Map<String, FileOperation> map = ImmutableMap.<String, FileOperation> of("rm", new RmOperation(), "copy",
            new CopyOperation());

    void process2(String line) throws IOException {
        Preconditions.checkNotNull(line);
        List<String> commands = Splitter.on(" ").splitToList(line);
        String command = commands.get(0);
        FileOperation fileOperation = map.get(command);
        fileOperation.operate(commands);
    }

    interface FileOperation {
        void operate(List<String> commands) throws IOException; // 执行文件操作
    }

    static class RmOperation implements FileOperation {
        @Override
        public void operate(List<String> commands) {
            new File(commands.get(1)).delete();
        }
    }

    static class CopyOperation implements FileOperation {
        @Override
        public void operate(List<String> stringList) throws IOException {
            Files.copy(new File(stringList.get(1)), new File(stringList.get(2)));
        }
    }

}
