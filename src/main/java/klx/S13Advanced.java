package klx;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"java:S112"}) // not production code, just a demo
public class S13Advanced {

    public static void main(String[] args){
        List<String> list = Arrays.asList("a", "b", "c");
        list.stream()
                .map ( (String val) -> {// cannot throw checked exceptions here
                    try {
                        throw new Exception ("Simulate Checked Exception");
                    } catch (Exception e) { throw new RuntimeException ("WrapperException"); }
                }).forEach(System.out::println);
    }
}
