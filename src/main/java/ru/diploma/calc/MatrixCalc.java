package ru.diploma.calc;

import ru.diploma.data.CellDiagonals;
import ru.diploma.data.CellVectors;
import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.error.DataValidationException;
import ru.diploma.service.DataGenService;

public class MatrixCalc {

    public static Complex[][] getMatrix(float[][][] cells, CellVectors cellVectors, float[][] collocationPoints) throws DataValidationException {
        Complex[][] matrix = new Complex[cells.length * 2][cells.length * 2];
        float eps = getEps(cells);
        Complex wave_vec = new Complex(4.1f, 0.0f);

        float[][][] tau_vectors = new float[2][cells.length][3];
        tau_vectors[0] = cellVectors.getTau1();
        tau_vectors[1] = cellVectors.getTau2();

        for (int i = 0; i < collocationPoints.length; i++) {
            for (int k = 0; k < cells.length; k++) {
                for (int m = 0; m < 2; m++) {
                    for (int l = 0; l < 2; l++) {
                        Complex matrixElement_kk_ii = getMatrixElement(i, k, m, l, cells[i], tau_vectors[k][l],
                                tau_vectors[i][m], collocationPoints[i], eps, wave_vec);
                        int kk = 2 * k + l; // номер столбца для вставки в matrix
                        int ii = 2 * i + m; // номер строки для вставки в matrix
                        matrix[ii][kk] = matrixElement_kk_ii;
                    }
                }
            }
        }
        return matrix;
    }

    private static Complex getMatrixElement(int i, int k, int m, int l, float[][] cell_i, float[] tau_k_l,
                                            float[] tau_i_m, float[] x_i, float eps, Complex wave_vec)
            throws DataValidationException {
        int g = 5;// разбиение стороны ячейки

        float additional_coef = 0.0f;
        if (i == k && m == l) {
            additional_coef = 0.5f;
        }

        Complex additional_coef_complex = new Complex(additional_coef, 0.0f);
        ComplexVector tau_i_m_complex = new ComplexVector(new Complex(tau_i_m[0], 0.0f),
                new Complex(tau_i_m[1], 0), new Complex(tau_i_m[2], 0.0f));
        ComplexVector tau_k_l_complex = new ComplexVector(new Complex(tau_k_l[0], 0.0f),
                new Complex(tau_k_l[1], 0), new Complex(tau_k_l[2], 0.0f));
        ComplexVector operator_R = OperatorCalculation.operatorCalc(x_i, cell_i, eps, wave_vec, g, tau_k_l_complex, 3);

        return Complex.add(additional_coef_complex, ComplexVector.scalarMultiply(tau_i_m_complex, operator_R));
    }

    private static float getEps(float[][][] cells) {
        int numCoordinates = cells[0][0].length;
        float h = -1;

        for (float[][] cell : cells) {
            CellDiagonals diags = DataGenService.getDiagOfCell(cell, numCoordinates);
            float diag1 = DataGenService.getVectorNorma(diags.getDiag1());
            float diag2 = DataGenService.getVectorNorma(diags.getDiag2());

            if (h == -1) {
                h = diag1;
            }
            if (diag1 < h) {
                h = diag1;
            }
            if (diag2 < h) {
                h = diag2;
            }
        }

        return 2 * h;
    }
}
