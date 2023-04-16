package org.example.args;

import org.junit.jupiter.api.Test;

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

    @Test
    public void should() {
        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
        options.logging();
        options.port();
    }

    record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

}
