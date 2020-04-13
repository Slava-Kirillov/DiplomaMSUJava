package ru.diploma.calc;

import org.junit.jupiter.api.Test;
import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;

public class FuncCalcOnCellImplTest {

    @Test
    public void funcVTest() {
        float[] x = {1, 2, 3};
        float[] y = {4, 5, 6};
        Complex k = new Complex(4.1f, 0.0f);
        ComplexVector res = FuncCalcOnCellImpl.funcV(x, k, y);
        System.out.println(res.getCoordinates()[0]);
        System.out.println(res.getCoordinates()[1]);
        System.out.println(res.getCoordinates()[2]);
    }
}
