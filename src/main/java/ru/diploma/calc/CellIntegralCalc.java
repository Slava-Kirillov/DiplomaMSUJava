package ru.diploma.calc;

import org.apache.commons.math3.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.util.DataUtils;

public class CellIntegralCalc {


    /**
     * Вычисление интеграла по одной ячейке, с разбиением ячейки на m*m частей
     *
     * @param x    - точка, в которой ищется результат
     * @param cell - ячейка, на которой считается инткграл
     * @param eps- эпсилол, малый параметр, выбирается как eps = 2*h, где h - максимальный из диаметров всех ячеек
     * @param k    - комплексная величина, волновое число
     * @param g    - параметр, указывает число разбиений по стороне ячейки
     * @return
     */
    public static ComplexVector cellIntegralCalculation(float[] x, float[][] cell, float eps, Complex k, int g, int vecDim) {
        ComplexVector res = new ComplexVector();
        float p, q, p1, q1, a, b, s, a1, a2, a3, a4;

        float[] m1 = new float[vecDim];
        float[] m2 = new float[vecDim];
        float[] rc = new float[vecDim];

        for (int i = 0; i < g; i++) {
            for (int j = 0; j < g; j++) {
                p = ((float) i) / ((float) g);
                p1 = ((float) (i + 1)) / ((float) g);
                q = ((float) j) / ((float) g);
                q1 = ((float) (j + 1)) / ((float) g);

                for (int ii = 0; ii < vecDim; ii++) {
                    a = q * cell[1][ii] + (1.0f - q) * cell[0][ii];
                    b = q * cell[2][ii] + (1.0f - q) * cell[3][ii];

                    a1 = p * b + (1.0f - p) * a;
                    a4 = p1 * b + (1.0f - p1) * a;

                    a = q1 * cell[1][ii] + (1.0f - q1) * cell[0][ii];
                    b = q1 * cell[2][ii] + (1.0f - q1) * cell[3][ii];

                    a2 = p * b + (1.0f - p) * a;
                    a3 = p1 * b + (1.0f - p1) * a;

                    rc[ii] = (a1 + a2 + a3 + a4) / 4.0f;
                    m1[ii] = ((a2 + a3) - (a1 + a4)) / 2.0f;
                    m2[ii] = ((a3 + a4) - (a1 + a2)) / 2.0f;
                }

                float[] rn = DataUtils.getVecMultip(m1, m2, vecDim);
                s = DataUtils.getVectorNorma(rn);

                ComplexVector funcDefOnCell;

                float[] coord_r = {x[0] - rc[0], x[1] - rc[1], x[2] - rc[2]};
                float r = DataUtils.getVectorNorma(coord_r);

                if (r < Math.pow(10, -10)) {
                    funcDefOnCell = new ComplexVector();
                } else {
                    ComplexVector funcv = FuncCalcOnCell.funcV(x, k, rc, r);
                    float tetaEps = (float) (3.0f * Math.pow(r / eps, 2) - 2.0f * Math.pow(r / eps, 3));
                    if (r < eps) {
                        funcDefOnCell = ComplexVector.multiply(tetaEps, funcv);
                    } else {
                        funcDefOnCell = funcv;
                    }
                }

                res.add(ComplexVector.multiply(s, funcDefOnCell));
            }
        }
        return res;
    }
}
