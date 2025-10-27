package klx;

import java.util.stream.Stream;

@SuppressWarnings({"java:S125"}) // not production code, just a demo
public class S10StreamsCreation {

    public static void main(String[] args){
        Stream.generate(Math::random).limit(2).forEach(System.out::println);
        Stream.iterate(0,n->n+2).limit(2).forEach(System.out::println);
        //for(;;){System.out.println(Math.random());}
        Stream.generate(Math::random).forEach(System.out::println);
        // Non-Short-Circuit Consumes infinite Stream
    }

}
