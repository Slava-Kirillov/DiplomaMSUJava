package ru.diploma.service;

import org.springframework.stereotype.Component;
import ru.diploma.config.ApplicationConfig;
import ru.diploma.data.CellDiagonals;
import ru.diploma.data.CellVectors;

@Component
public class DataGenService {

    private ApplicationConfig config;

    public DataGenService(ApplicationConfig applicationConfig) {
        this.config = applicationConfig;
    }

    /**
     * Получить точки коллокации
     *
     * @param cells
     * @return
     */
    public float[][] getCollocationPoints(float[][][] cells) {
        int numPointsCell = config.getNumPoints();
        int numCoordinatesPoint = config.getNumCoordinatePoint();

        float[][] arrayOfCollocationPoints = new float[cells.length][numCoordinatesPoint];
        float coordinate = 0;
        float initial_coordinate = 0;

        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < numCoordinatesPoint; ++j) {
                for (int k = 0; k < numPointsCell; ++k) {
                    coordinate = coordinate + cells[i][k][j];
                }
                arrayOfCollocationPoints[i][j] = coordinate / 4;
                coordinate = initial_coordinate;
            }
        }
        return arrayOfCollocationPoints;
    }

    public CellVectors getCellVectors(float[][][] cells) {
        int numPointsCell = config.getNumPoints();
        int numCoordinatesPoint = config.getNumCoordinatePoint();

        float[][] normal = new float[numPointsCell][numCoordinatesPoint];
        float[][] tau1 = new float[numPointsCell][numCoordinatesPoint];
        float[][] tau2 = new float[numPointsCell][numCoordinatesPoint];

        CellVectors cellVectors = new CellVectors(normal, tau1, tau2);

        for (int i = 0; i < cells.length; ++i) {
            CellDiagonals diagonals = getDiagOfCell(cells[i]);

            float[] vecMultip = getVecMultip(diagonals.getDiag1(), diagonals.getDiag2());
            float vecMultipNorma = getVectorNorma(vecMultip);
            float diag1Norma = getVectorNorma(diagonals.getDiag1());

            for (int j = 0; j < numCoordinatesPoint; j++) {
                normal[i][j] = vecMultip[j] / vecMultipNorma;
                tau1[i][j] = diagonals.getDiag1()[i] / diag1Norma;
            }
            tau2[i] = getVecMultip(normal[i], tau1[i]);
        }
        return cellVectors;
    }

    /**
     * Получить норму вектора
     *
     * @param vector
     * @return
     */
    float getVectorNorma(float[] vector) {
        return (float) Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2) + Math.pow(vector[2], 2));
    }

    private float[] getVecMultip(float[] vector1, float[] vector2) {
        float[] vecMultip = new float[config.getNumCoordinatePoint()];
        vecMultip[0] = vector1[1] * vector2[2] - vector1[2] * vector2[1];
        vecMultip[1] = vector1[2] * vector2[0] - vector1[0] * vector2[2];
        vecMultip[2] = vector1[0] * vector2[1] - vector1[1] * vector2[0];
        return vecMultip;
    }

    /**
     * Получить площади ячеек
     *
     * @param cells
     * @return
     */
    public float[] getCellArea(float[][][] cells) {
        float[] arrayOfCellArea = new float[cells.length];

        for (int i = 0; i < cells.length; ++i) {
            arrayOfCellArea[i] = getCellArea(cells[i]);
        }
        return arrayOfCellArea;
    }

    private float getCellArea(float[][] cell) {
        CellDiagonals diagonals = getDiagOfCell(cell);
        float[] diag1 = diagonals.getDiag1();
        float[] diag2 = diagonals.getDiag2();

        float diag1Length = (float) Math.sqrt(diag1[0] * diag1[0] + diag1[1] * diag1[1] + diag1[2] * diag1[2]);
        float diag2Length = (float) Math.sqrt(diag2[0] * diag2[0] + diag2[1] * diag2[1] + diag2[2] * diag2[2]);
        float scalarMultDiag1Diag2 = diag1[0] * diag2[0] + diag1[1] * diag2[1] + diag1[2] * diag2[2];
        return (float) (diag1Length *
                diag2Length *
                Math.sqrt(1 - Math.pow((scalarMultDiag1Diag2 / (diag1Length * diag2Length)), 2)) / 2);
    }

    private CellDiagonals getDiagOfCell(float[][] cell) {
        int numPointsCell = config.getNumPoints();
        int numCoordinatesPoint = config.getNumCoordinatePoint();

        CellDiagonals diagonals = new CellDiagonals();
        float[] diag1 = new float[numCoordinatesPoint];
        float[] diag2 = new float[numCoordinatesPoint];

        for (int i = 0; i < numCoordinatesPoint; i++) {
            diag1[i] = cell[0][i] - cell[2][i];
            diag2[i] = cell[1][i] - cell[3][i];
        }

        diagonals.setDiag1(diag1);
        diagonals.setDiag2(diag2);
        return diagonals;
    }
}
