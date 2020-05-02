package ru.diploma.data.complex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComplexTest {

    ComplexVector vec1;
    ComplexVector vec2;

    @BeforeEach
    public void init() {
        this.vec1 = new ComplexVector(
                new Complex(1.0f, 1.0f),
                new Complex(9.0f, 2.0f),
                new Complex(2.0f, 3.0f)
        );
        this.vec2 = new ComplexVector(
                new Complex(2.0f, 4.0f),
                new Complex(9.0f, 2.0f),
                new Complex(3.0f, 5.0f)
        );
    }

    @Test
    public void normTest() {
        Assertions.assertEquals(ComplexVector.norm(vec1), 10.0f);
    }

    @Test
    public void vecMultiplyTest() {
        ComplexVector res = ComplexVector.vecMultiply(vec1, vec2);

        Complex[] coordVec1 = res.getCoordinates();
        Assertions.assertEquals(coordVec1[0].getRe(), 5.0);
        Assertions.assertEquals(coordVec1[1].getRe(), -6.0);
        Assertions.assertEquals(coordVec1[2].getRe(), -3.0);
        Assertions.assertEquals(coordVec1[0].getIm(), 20.0);
        Assertions.assertEquals(coordVec1[1].getIm(), 6.0);
        Assertions.assertEquals(coordVec1[2].getIm(), -29.0);
    }

    @Test
    public void multiply1Test() {
        float num = 5.5f;
        ComplexVector res = ComplexVector.multiply(num, vec1);

        Complex[] coordVec1 = res.getCoordinates();
        Assertions.assertEquals(coordVec1[0].getRe(), 5.5);
        Assertions.assertEquals(coordVec1[1].getRe(), 49.5);
        Assertions.assertEquals(coordVec1[2].getRe(), 11.0);
        Assertions.assertEquals(coordVec1[0].getIm(), 5.5);
        Assertions.assertEquals(coordVec1[1].getIm(), 11.0);
        Assertions.assertEquals(coordVec1[2].getIm(), 16.5);
    }

    @Test
    public void multiply2Test() {
        Complex num = new Complex(3,2);
        ComplexVector res = ComplexVector.multiply(num, vec1);

        Complex[] coordVec1 = res.getCoordinates();
        Assertions.assertEquals(coordVec1[0].getRe(), 1.0);
        Assertions.assertEquals(coordVec1[1].getRe(), 23.0);
        Assertions.assertEquals(coordVec1[2].getRe(), 0.0);
        Assertions.assertEquals(coordVec1[0].getIm(), 5.0);
        Assertions.assertEquals(coordVec1[1].getIm(), 24.0);
        Assertions.assertEquals(coordVec1[2].getIm(), 13.0);
    }

    @Test
    public void scalarMultiplyTest() {
        Complex mult = ComplexVector.scalarMultiply(vec1, vec2);
        Assertions.assertEquals(mult.getRe(),112.0);
        Assertions.assertEquals(mult.getIm(),-3.0);

        mult = ComplexVector.scalarMultiply(vec2, vec1);
        Assertions.assertEquals(mult.getRe(),112.0);
        Assertions.assertEquals(mult.getIm(),3.0);
    }

    @Test
    public void sumTest() {
        ComplexVector sum = ComplexVector.sum(vec1, vec2);
        Complex[] coordVec1 = sum.getCoordinates();
        Assertions.assertEquals(coordVec1[0].getRe(), 3.0);
        Assertions.assertEquals(coordVec1[1].getRe(), 18.0);
        Assertions.assertEquals(coordVec1[2].getRe(), 5.0);
        Assertions.assertEquals(coordVec1[0].getIm(), 5.0);
        Assertions.assertEquals(coordVec1[1].getIm(), 4.0);
        Assertions.assertEquals(coordVec1[2].getIm(), 8.0);
    }


    @Test
    public void subtractTest() {
        ComplexVector sub = ComplexVector.subtract(vec1, vec2);
        Complex[] coordSub = sub.getCoordinates();

        Assertions.assertEquals(coordSub[0].getRe(), -1.0);
        Assertions.assertEquals(coordSub[1].getRe(), 0.0);
        Assertions.assertEquals(coordSub[2].getRe(), -1.0);

        Assertions.assertEquals(coordSub[0].getIm(), -3.0);
        Assertions.assertEquals(coordSub[1].getIm(), 0.0);
        Assertions.assertEquals(coordSub[2].getIm(), -2.0);
    }

    @Test
    public void addTest() {
        vec1.add(vec2);
        Complex[] coordVec1 = vec1.getCoordinates();
        Assertions.assertEquals(coordVec1[0].getRe(), 3.0);
        Assertions.assertEquals(coordVec1[1].getRe(), 18.0);
        Assertions.assertEquals(coordVec1[2].getRe(), 5.0);

        Assertions.assertEquals(coordVec1[0].getIm(), 5.0);
        Assertions.assertEquals(coordVec1[1].getIm(), 4.0);
        Assertions.assertEquals(coordVec1[2].getIm(), 8.0);
    }

}
