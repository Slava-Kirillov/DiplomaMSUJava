package ru.diploma.calc;

import ru.diploma.data.complex.Complex;
import ru.diploma.data.complex.ComplexVector;
import ru.diploma.error.DataValidationException;
import ru.diploma.service.DataGenService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CellIntegralCalc {

    private static float s_sum = 0;
    private static int sub_cell_count = 0;

    public static float getS_sum() {
        return s_sum;
    }

    /**
     * Вычисление интеграла по одной ячейке, с разбиением ячейки на m*m частей
     *
     * @param x    - точка, в которой ищется результат
     * @param cell - ячейка, на которой считается инткграл
     * @param eps- эпсилол, малый параметр, выбирается как eps = 2*h, где h - максимальный из диаметров всех ячеек
     * @param k    - комплексная величина, волновое число
     * @param m    - параметр, указывает число разбиений по стороне ячейки
     * @param func - функция, которая вычисляется на ячейке
     * @return
     * @throws DataValidationException
     */
    public static ComplexVector cellIntegralCalculation(float[] x,
                                                        float[][] cell,
                                                        float eps,
                                                        Complex k,
                                                        int m,
                                                        FuncCalcOnCell func,
                                                        int vecDim) throws DataValidationException {
        ComplexVector res = new ComplexVector();
        float p, q, p1, q1, a, b, s, a1, a2, a3, a4;

        float[] m1 = new float[vecDim];
        float[] m2 = new float[vecDim];
        float[] rc = new float[vecDim];

        for (int i = 0; i < m; i++) {
            p = (float) i / (float) m;
            p1 = (float) (i + 1) / (float) m;
            for (int j = 0; j < m; j++) {
                q = (float) j / (float) m;
                q1 = (float) (j + 1) / (float) m;

                for (int ii = 0; ii < vecDim; ii++) {
                    a = q * cell[1][ii] + (1 - q) * cell[0][ii];
                    b = q * cell[2][ii] + (1 - q) * cell[3][ii];

                    a1 = p * b + (1 - p) * a;
                    a4 = p1 * b + (1 - p1) * a;

                    a = q1 * cell[1][ii] + (1 - q1) * cell[0][ii];
                    b = q1 * cell[2][ii] + (1 - q1) * cell[3][ii];

                    a2 = p * b + (1 - p) * a;
                    a3 = p1 * b + (1 - p1) * a;

                    rc[ii] = (a1 + a2 + a3 + a4) / 4;
                    m1[ii] = ((a2 + a3) - (a1 + a4)) / 2;
                    m2[ii] = ((a3 + a4) - (a1 + a2)) / 2;
                }

                float[] rn = DataGenService.getVecMultip(m1, m2, vecDim);

                s = (float) Math.sqrt(Math.pow(rn[0], 2) + Math.pow(rn[1], 2) + Math.pow(rn[2], 2));

                s_sum += s;
                sub_cell_count++;
//                printToFile(rc);

                res.add(ComplexVector.multiply(s, func.funcDefOnCell(x, eps, k, rc)));
            }
        }

        return res;
    }

    private static void printToFile(float[] rc) {
        String filePath = "src/main/resources/data/rc_all.dat";
        File file = new File(filePath);

        if (file.exists() && sub_cell_count == 1) {
            file.delete();
        }

        try {
            String text = rc[0] + " " + rc[1] + " " + rc[2] + "\n";
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(text);
            bufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
