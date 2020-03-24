package ru.diploma.calc;

import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.error.DataValidationException;

public class MatrixCalc {

    public static ComplexVector getMatrixElement(int i,
                                                 int k,
                                                 int m,
                                                 int l,
                                                 float[][] cell_i,
                                                 float[] tau_k_l,
                                                 float[] tau_i_m,
                                                 float[] x_i,
                                                 float eps,
                                                 Complex wave_vec) throws DataValidationException {
        int g = 5;// разбиение стороны ячейки

        float additional_coef = 0.0f;
        if (i == k && m == l) {
            additional_coef = 0.5f;
        }

        ComplexVector tau_i_m_complex = new ComplexVector(new Complex(tau_i_m[0], 0),
                new Complex(tau_i_m[1], 0), new Complex(tau_i_m[2], 0));
        ComplexVector tau_k_l_complex = new ComplexVector(new Complex(tau_k_l[0], 0),
                new Complex(tau_k_l[1], 0), new Complex(tau_k_l[2], 0));
        ComplexVector operator_R = OperatorCalculation.operatorCalc(x_i, cell_i, eps, wave_vec, g, tau_k_l_complex, 3);

//        ComplexVector matrix_coef = ComplexVector.ьг
        return new ComplexVector();
    }
}
