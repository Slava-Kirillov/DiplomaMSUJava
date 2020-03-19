package ru.diploma.calc;

import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;

public class CellIntegralCalc {

    /**
     * Вычисление оператора R[sigma, tau]
     */
    public static ComplexVector operatorCalc(float[] x, float[][] cell, float eps, Complex k, int m, ComplexVector tau) {
        ComplexVector f = cellIntegralCalculation(x, cell, eps, k, m, FuncCalcOnCellImpl::funcDefOnCell);

    }

    private static ComplexVector cellIntegralCalculation(float[] x, float[][] cell, float eps, Complex k, int m, FuncCalcOnCell func) {
        float p, q, p1, q1, s, a, b, a1, a2, a3, a4;
        float[] m1, m2, rn, rc = new float[3];
        ComplexVector res = new ComplexVector();

        for (int i = 0; i < m; i++) {
            p = (float) i / (float) m;
            p1 = (float) (i + 1) / (float) m;
            for (int j = 0; j < m; j++) {
                q = (float) j / (float) m;
                q1 = (float) (j + 1) / (float) m;

                a = q * cell[1][]+(1 - q) * cell[0][];

                for (int ii = 0; ii < 3; ii++) {
                    a = q * cell[1][ii] + (1 - q) * cell[0][ii];
                    b = q * cell[2][ii] + (1 - q) * cell[3][ii];

                    a1 = p * b + (1 - p) * a;
                    a4 = p1 * b + (1 - p1) * a;

                    a = q1 * cell[1][ii] + (1 - q1) * cell[0][ii];
                    b = q1 * cell[2][ii] + (1 - q1) * cell[3][ii];

                    a2 = p * b + (1 - p) * a;
                    a3 = p1 * b + (1 - p1) * a;

                    rc[ii] = ((a1 + a2 + a3 + a4) / 4);
                    m1[ii] = ((a2 + a3) - (a1 + a4)) / 2;
                    m2[ii] = ((a3 + a4) - (a1 + a2)) / 2;
                }

                rn[0] = m1[1] * m2[2] - m1[2] * m2[1];
                rn[1] = m1[2] * m2[0] - m1[0] * m2[2];
                rn[2] = m1[0] * m2[1] - m1[1] * m2[0];

                s = (float) Math.sqrt(Math.pow(rn[0], 2) + Math.pow(rn[1], 2) + Math.pow(rn[2], 2));

                res.add(ComplexVector.multiply(s, func.funcDefOnCell(x, eps, k, rc)));
            }
        }

        return res;
    }
}
