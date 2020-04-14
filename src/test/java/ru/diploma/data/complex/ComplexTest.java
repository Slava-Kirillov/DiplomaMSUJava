package ru.diploma.data.complex;

import org.junit.jupiter.api.Test;

public class ComplexTest {

    @Test
    public void normTest() {
        ComplexVector vec = new ComplexVector(
                new Complex(1.0f, 1.0f),
                new Complex(2.0f, 2.0f),
                new Complex(3.0f, 3.0f)
        );

        System.out.println(ComplexVector.norm(vec));
    }
}
