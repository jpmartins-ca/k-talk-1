package klx;

import java.util.Arrays;
import java.util.Optional;

public class S12Terminal {

    public static void main(String[] args){
        var nestedList = Arrays.asList(1, 2, 3, 4);
        Optional<Integer> res = nestedList.stream().findFirst(); // Output: 1
        //Short-circuiting only processed one element, not all the stream
        res.ifPresent( i -> System.out.println("res" + i));
        //Notice the functional style of printing res.get(),
        // for prod code, probably will prefer res.orElseThrow()
    }
}
