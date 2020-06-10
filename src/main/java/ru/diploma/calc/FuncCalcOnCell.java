package ru.diploma.calc;

import org.apache.commons.math3.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.util.DataUtils;

public class FuncCalcOnCell {

    public static ComplexVector funcV(float[] x, Complex k, float[] y, float r) {
        Complex r_complex = new Complex(r, 0.0F);
        Complex unit_imag = new Complex(0.0f, 1.0f);
        Complex unit_real = new Complex(1.0f, 0.0f);
        Complex pi = new Complex((float) (4 * Math.PI), 0.0f);

        Complex a1 = unit_imag.multiply(k);
        a1 = a1.multiply(r_complex);

        Complex a2 = a1.subtract(unit_real);
        Complex a3 = a1.exp();
        Complex a4 = pi.multiply(r_complex.pow(3));

        Complex interRes = a3.multiply(a2);
        interRes = interRes.divide(a4);

        float[] vec = subtractVectors(y, x);

        return new ComplexVector(
                interRes.multiply(vec[0]),
                interRes.multiply(vec[1]),
                interRes.multiply(vec[2])
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
