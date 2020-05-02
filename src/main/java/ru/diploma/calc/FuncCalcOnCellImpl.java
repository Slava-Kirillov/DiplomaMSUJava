package ru.diploma.calc;

import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.util.DataUtils;

public class FuncCalcOnCellImpl {

    /**
     * Функция, заданная на ячейке f(x, eps, k, y) = V(x-y)*TetaEps(x-y)
     *
     * @return
     */
    public static ComplexVector funcDefOnCell(float[] x, float eps, Complex k, float[] y) {
        float[] coord_r = {x[0] - y[0], x[1] - y[1], x[2] - y[2]};
        float r = DataUtils.getVectorNorma(coord_r);

        if (r < Math.pow(10, -10)) {
            return new ComplexVector();
        } else {
            ComplexVector funcV = funcV(x, k, y, r);
            float tetaEps = (float) (3 * Math.pow(r / eps, 2) - 2 * Math.pow(r / eps, 3));
            if (r < eps) {
                return ComplexVector.multiply(tetaEps, funcV);
            } else {
                return funcV;
            }
        }
    }

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

//                Complex a1 = new Complex(-k.getIm() * r, k.getRe() * r);
//        Complex a2 = Complex.exp(a1);
//        Complex a3 = new Complex(-1, 0);
//        Complex a4 = new Complex((float) (4 * Math.PI * Math.pow(r, 3)), 0);

//        Complex interRes = Complex.divide(Complex.multiply(a2, Complex.add(a1, a3)), a4);

        float[] vec = subtractVectors(y, x);

        ComplexVector complex = new ComplexVector(
                Complex.multiply(vec[0], interRes),
                Complex.multiply(vec[1], interRes),
                Complex.multiply(vec[2], interRes)
        );

//        if (Float.isNaN(complex.getCoordinates()[0].getRe())) {
//            System.out.println("Test");
//        }

        return complex;
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
