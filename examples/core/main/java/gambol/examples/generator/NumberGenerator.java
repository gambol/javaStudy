package gambol.examples.generator;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Set;

/**
 * Created by zhenbao.zhou on 16/8/29.
 */
public class NumberGenerator {

    public static void main(String[] args) {

        NumberGenerator numberGenerator = new NumberGenerator();
        Set<String> set = Sets.newHashSet();
        for (int i = 0; i != 300 ; ++i) {
            String str = Strings.padStart(String.valueOf(i), 6, '0');
            NumberInfo numberInfo = NumberInfo.make(str);
            String generate = numberGenerator.generate(numberInfo.left, numberInfo.center, numberInfo.right);
            if (!set.add(generate)) {
                System.out.println(generate);
            } else {
                System.out.println("ok: " + generate);
            }
        }
    }

    public static final short NUMBER_SIZE = 300;
    public static final int COORDINATE_SIZE = 100;

    private static final byte[] HIGH_DATA = new byte[COORDINATE_SIZE];

    private static final byte[] MID_DATA = new byte[COORDINATE_SIZE];

    private static final byte[] LOW_DATA = new byte[COORDINATE_SIZE];

    private static final String ZERO = "0";

    public NumberGenerator() {
        DataInputStream is = null;
        try {
            byte[] data = new byte[NUMBER_SIZE];
            // is = new DataInputStream(new FileInputStream(OigConstants.FILE_PATH));
//            for (int i = 0; i != NUMBER_SIZE; ++i) {
//                data[i] = is.readByte();
//            }

            // TODO 这段逻辑需要改哦。 改成从文件里读取映射
            for(int i = 0; i < NUMBER_SIZE; i++) {
                data[i] = (byte)((i + 99) % 100);
            }
            System.arraycopy(data, 0, HIGH_DATA, 0, 100);
            System.arraycopy(data, 100, MID_DATA, 0, 100);
            System.arraycopy(data, 200, LOW_DATA, 0, 100);
            check();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable e) {

                }
            }
        }
    }

    private void check() {
        check(HIGH_DATA);
        check(MID_DATA);
        check(LOW_DATA);
    }

    private void check(byte[] datas) {
        Set<Byte> set = Sets.newHashSet();
        for (byte data : datas) {
            set.add(data);
            if (data < 0 || data >= 100) {
                throw new RuntimeException("not legal data");
            }
        }
        if (set.size() != 100) {
            throw new RuntimeException("not legal data");
        }
    }

    public String generate(int left, int center, int right) {
        char[] datas = new char[6];
        getDataStr(datas, 0, HIGH_DATA[left]);
        getDataStr(datas, 2, MID_DATA[center]);
        getDataStr(datas, 4, LOW_DATA[right]);
        return new String(datas);
    }

    private void getDataStr(char[] datas, int i, int data) {
        Preconditions.checkArgument(data < 100 && data >= 0, "the data [%s] should in [0,99]!", data);
        if (data < 0) {
            datas[i] = '0';
            datas[i + 1] = (char) (data + 48);
        } else {
            datas[i] = (char) (data / 10 + 48);
            datas[i + 1] = (char) (data % 10 + 48);
        }
    }
}

 class NumberInfo{

        public final int left;

        public final int center;

        public final int right;

        public NumberInfo(int left, int center, int right) {
            this.left = left;
            this.center = center;
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NumberInfo)) return false;

            NumberInfo that = (NumberInfo) o;

            if (center != that.center) return false;
            if (left != that.left) return false;
            if (right != that.right) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = left;
            result = 31 * result + center;
            result = 31 * result + right;
            return result;
        }

        @Override
        public String toString() {
            return "NumberInfo{" +
                    "left=" + left +
                    ", center=" + center +
                    ", right=" + right +
                    '}';
        }

        public int toNumber() {
            return left * 10000 + center * 100 + right;
        }

        public String toNumberStr() {
            StringBuilder sb = new StringBuilder();
            sb.append(left).append(center).append(right);
            return sb.toString();
        }

        public static NumberInfo make(String str) {
            int left = Short.parseShort(str.substring(0, 2));
            int center = Short.parseShort(str.substring(2, 4));
            int right = Short.parseShort(str.substring(4, 6));
            return new NumberInfo(left, center, right);
        }
}


