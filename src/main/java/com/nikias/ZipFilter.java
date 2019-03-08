package com.nikias;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ZipFilter {
    public static void main(String[] args) {
        List<Double> numbers = List.of(1.0, 2.0, 3.0);
        List<Boolean> conditions = List.of(true, true, false);
        double[] results = IntStream.range(0, numbers.size())
                .filter(i -> conditions.get(i))
                .mapToDouble(i -> numbers.get(i) )
                .toArray();
        Arrays.stream(results).forEach(System.out::println);
    }
}
