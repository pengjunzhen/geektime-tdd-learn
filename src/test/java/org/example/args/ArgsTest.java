package org.example.args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgsTest {

    // 策略一：用下标去直接定位
    // -l -p 8080 -d /usr/logs

    // 策略二：拆分成数组
    // 实际上我们可以把它看成是一系列以dash开头的标志，然后把这个数组进行分割
    // 分割成的数组如下。分完段之后，我们就可以由对应的Annotation 标注好的类型结构里面去读
    // 读取的时候时候再根据它生成的新的小结构转换成对应的这样一个类型。
    // [-l], [-p, 8080], [-d, /usr/logs]
    // 上面这种是我们选择的实现策略（其实这种方式也是需要用到下标定位的）

    // ①像上面那种利用数组进行分段，我们可以利用一个index 标志位，找到每一个位置，然后去读。
    // ②我们也可以把这么个list做成一个特定的interface对它进行整体的分类处理
    // 这里我们选那种写法都可以，还有map的写法等

    // 策略三：map
    // {-l:[], -p:[8080], -d:[/usr/logs] }

    // 拆分任务
    // Single Option:
    // - bool: -l
    @Test
    public void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
        assertTrue(option.logging());
    }

    @Test
    public void should_set_boolean_option_to_false_if_flag_not_present() {
        BooleanOption option = Args.parse(BooleanOption.class);
        assertFalse(option.logging());
    }

    record BooleanOption(@Option("l") boolean logging) {
    }


    // - int: -p 8080
    @Test
    public void should_parse_int_as_option_value() {
        IntOption option = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, option.port());
    }

    record IntOption(@Option("p") int port) {
    }


    // - String: -d /usr/logs
    @Test
    public void should_get_string_as_option_value() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", option.directory());
    }

    record StringOption(@Option("d") String directory) {

    }

    // Multi options:
    // -l -p 8080 -d /usr/logs
    @Test
    public void should_parse_multi_options() {
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    // Sad Path:
    // TODO: - bool: -l t / -l t f
    // TODO: - int: -p / -p 8080 9090
    // TODO: - String: -d / -d /usr/logs /usr/vars
    // default value：
    // TODO: - bool: false
    // TODO: - int: 0
    // TODO: - string: ""

    @Test
    @Disabled
    public void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertArrayEquals(new int[]{1, 2, -3, 5}, options.decimals());
    }

    record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }

}
