package ru.diploma.calc;

import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.util.DataUtils;

public class FuncCalcOnCell {

    public static ComplexVector funcV(float[] x, Complex k, float[] y, float r) {
        Complex r_complex = new Complex(r, 0.0F);
        Complex unit_imag = new Complex(0.0f, 1.0f);
        Complex unit_real = new Complex(1.0f, 0.0f);
        Complex pi = new Complex((float) (4 * Math.PI), 0.0f);

        Complex a1 = Complex.multiply(unit_imag, k);
        a1.multiply(r_complex);

        Complex a2 = Complex.subtract(a1, unit_real);
        Complex a3 = Complex.exp(a1);
        Complex a4 = Complex.multiply(pi, Complex.pow(r_complex, 3));

        Complex interRes = Complex.multiply(a3, a2);
        interRes.divide(a4);

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
    public static float getLengthRadiusVec(float[] x, float[] y) {
        float[] r = {x[0] - y[0], x[1] - y[1], x[2] - y[2]};
        return DataUtils.getVectorNorma(r);
    }

    private static float[] subtractVectors(float[] x, float[] y) {
        return new float[]{x[0] - y[0], x[1] - y[1], x[2] - y[2]};
    }
}
