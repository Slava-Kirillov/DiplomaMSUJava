package ru.diploma.calc;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Test;
import ru.diploma.data.complex.ComplexVector;

import static ru.diploma.calc.FuncCalcOnCell.getLengthRadiusVec;

public class FuncCalcOnCellTest {

    @Test
    public void funcVTest() {
        float[] x = {1, 2, 3};
        float[] y = {4, 5, 6};
        Complex k = new Complex(4.1f, 0.0f);
        ComplexVector res = FuncCalcOnCell.funcV(x, k, y, getLengthRadiusVec(x, y));
        System.out.println(res.getCoordinates()[0]);
        System.out.println(res.getCoordinates()[1]);
        System.out.println(res.getCoordinates()[2]);
    }
}
