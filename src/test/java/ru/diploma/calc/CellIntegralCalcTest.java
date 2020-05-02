package ru.diploma.calc;

import org.junit.jupiter.api.Test;
import ru.diploma.data.CellVectors;
import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.util.DataUtils;

public class CellIntegralCalcTest {

    @Test
    public void cellIntegralCalculationTest() {
        float[][] cell = {{0,0,0},{2,0,0},{2,2,0},{0,2,0}};
        float[][][] cells = new float[1][][];
        cells[0] = cell;

        CellVectors vectors = DataUtils.getCellVectors(cells, 3);
        ComplexVector tau_k_l_complex1 = new ComplexVector(vectors.getTau1()[0][0], vectors.getTau1()[0][1], vectors.getTau1()[0][2]);
        ComplexVector tau_k_l_complex2 = new ComplexVector(vectors.getTau2()[0][0], vectors.getTau2()[0][1], vectors.getTau2()[0][2]);

        float[] x = DataUtils.getCollocationPoints(cells, 4,3)[0];
        float eps = 0.25f;
        Complex k = new Complex(4.1f, 0.0f);
        int m = 5;

        ComplexVector vec = CellIntegralCalc.cellIntegralCalculation(x,cell, eps, k, m, 3);

        ComplexVector.vecMultiply(tau_k_l_complex1, vec);
        ComplexVector.vecMultiply(tau_k_l_complex2, vec);
        Complex[] coord = vec.getCoordinates();
        System.out.println(coord[0].getRe() + " " + coord[0].getIm());
        System.out.println(coord[1].getRe() + " " + coord[1].getIm());
        System.out.println(coord[2].getRe() + " " + coord[2].getIm());
    }
}
