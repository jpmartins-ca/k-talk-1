package klx;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class S11Intermediate {

    public static void main(String[] args){
        List<List<Integer>> nestedList = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4));
        List<Integer> flatList = nestedList.stream()
                .flatMap(List::stream) // Stream elements are now flattened: 1, 2, 3, 4
                .collect(Collectors.toList());// Output: [1, 2, 3, 4]
    }
}
