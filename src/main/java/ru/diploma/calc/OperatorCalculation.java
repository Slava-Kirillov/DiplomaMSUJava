package ru.diploma.calc;

import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.error.DataValidationException;

public class OperatorCalculation {

    /**
     * Вычисление интеграла по ячейке R[sigma, tau]
     */
    public static ComplexVector operatorCalc(float[] x,
                                             float[][] cell,
                                             float eps,
                                             Complex k,
                                             int m,
                                             ComplexVector vector,
                                             int vecDim)
            throws DataValidationException {
        ComplexVector integral = CellIntegralCalc.cellIntegralCalculation(x,
                cell,
                eps,
                k,
                m,
                FuncCalcOnCellImpl::funcDefOnCell,
                vecDim);
        return ComplexVector.vecMultiply(vector, integral);
    }
}
