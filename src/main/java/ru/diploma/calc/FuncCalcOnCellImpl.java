package ru.diploma.calc;

import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.error.DataValidationException;

public class FuncCalcOnCellImpl {

    /**
     * Функция, заданная на ячейке f(x, eps, k, y) = V(x-y)*TetaEps(x-y)
     *
     * @return
     */
    public static ComplexVector funcDefOnCell(float[] x, float eps, Complex k, float[] y) throws DataValidationException {
        if (x.length != 3 || y.length != 3 || eps == 0.0) {
            throw new DataValidationException("Vectors have incorrect format");
        }

        ComplexVector funcV = funcV(x, eps, k, y);
        float tetaEps = funcTetaEps(x, y, eps);

        return ComplexVector.multiply(tetaEps, funcV);
    }

    /**
     * Вычисляет функцию tetaEps
     *
     * @param x
     * @param y
     * @param eps
     * @return
     */
    private static float funcTetaEps(float[] x, float[] y, float eps) {
        float r = getLengthRadiusVec(x, y);
        if (r < eps && r > Math.pow(10, -10)) {
            return (float) (3 * Math.pow(r / eps, 2) - 2 * Math.pow(r / eps, 3));
        }
        if (r >= eps) {
            return 1;
        }
        return 0;
    }

    private static ComplexVector funcV(float[] x, float eps, Complex k, float[] y) {
        float r = getLengthRadiusVec(x, y);
        Complex a1 = new Complex(-k.getIm() * r, k.getRe() * r);
        Complex a2 = Complex.exp(a1);
        Complex a3 = new Complex(-1, 0);
        Complex a4 = new Complex(4 * Math.PI * Math.pow(r, 3), 0);

        Complex interRes = Complex.divide(Complex.multiply(a2, Complex.add(a1, a3)), a4);

        float[] vec = subtractVectors(y, x);

        return new ComplexVector(
                Complex.multiply(vec[0], interRes),
                Complex.multiply(vec[1], interRes),
                Complex.multiply(vec[2], interRes)
        );
    }

    /**
     * Вычисляет длинну вектора r = y - x,
     *
     * @param x
     * @param y
     * @return
     */
    private static float getLengthRadiusVec(float[] x, float[] y) {
        return (float) Math.sqrt(Math.pow(y[0] - x[0], 2) + Math.pow(y[1] - x[1], 2) + Math.pow(y[2] - x[2], 2));
    }

    private static float[] subtractVectors(float[] x, float[] y) {
        return new float[]{
                x[0] - y[0],
                x[1] - y[1],
                x[2] - y[2]
        };
    }
}
