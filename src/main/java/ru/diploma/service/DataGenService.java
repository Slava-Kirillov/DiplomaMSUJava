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
    public static float[][] getCollocationPoints(float[][][] cells, int numPointsCell, int numCoordinatesPoint) {

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

    /**
     *
     * @param cells
     * @return
     */
    public CellVectors getCellVectors(float[][][] cells) {
        int numCoordinatesPoint = config.getNumCoordinatePoint();

        float[][] normal = new float[cells.length][numCoordinatesPoint];
        float[][] tau1 = new float[cells.length][numCoordinatesPoint];
        float[][] tau2 = new float[cells.length][numCoordinatesPoint];

        CellVectors cellVectors = new CellVectors(normal, tau1, tau2);

        for (int i = 0; i < cells.length; ++i) {
            CellDiagonals diagonals = getDiagOfCell(cells[i], config.getNumCoordinatePoint());

            float[] vecMultip = getVecMultip(diagonals.getDiag1(), diagonals.getDiag2(), numCoordinatesPoint);
            float vecMultipNorma = getVectorNorma(vecMultip);
            float diag1Norma = getVectorNorma(diagonals.getDiag1());

            for (int j = 0; j < numCoordinatesPoint; j++) {
                normal[i][j] = vecMultip[j] / vecMultipNorma;
                tau1[i][j] = diagonals.getDiag1()[j] / diag1Norma;
            }
            tau2[i] = getVecMultip(normal[i], tau1[i], numCoordinatesPoint);
        }
        return cellVectors;
    }

    /**
     * Получить норму вектора
     *
     * @param vector
     * @return
     */
    public static float getVectorNorma(float[] vector) {
        return (float) Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2) + Math.pow(vector[2], 2));
    }

    public static float[] getVecMultip(float[] vector1, float[] vector2, int vecDim) {
        float[] vecMultip = new float[vecDim];
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
    public static float[] getCellArea(float[][][] cells, int numCoordPoints) {
        float[] arrayOfCellArea = new float[cells.length];

        for (int i = 0; i < cells.length; ++i) {
            arrayOfCellArea[i] = getCellArea(cells[i], numCoordPoints);
        }
        return arrayOfCellArea;
    }

    private static float getCellArea(float[][] cell, int numCoordPoints) {
        CellDiagonals diagonals = getDiagOfCell(cell, numCoordPoints);
        float[] diag1 = diagonals.getDiag1();
        float[] diag2 = diagonals.getDiag2();

        float diag1Length = (float) Math.sqrt(diag1[0] * diag1[0] + diag1[1] * diag1[1] + diag1[2] * diag1[2]);
        float diag2Length = (float) Math.sqrt(diag2[0] * diag2[0] + diag2[1] * diag2[1] + diag2[2] * diag2[2]);
        float scalarMultDiag1Diag2 = diag1[0] * diag2[0] + diag1[1] * diag2[1] + diag1[2] * diag2[2];
        return (float) (diag1Length *
                diag2Length *
                Math.sqrt(1 - Math.pow((scalarMultDiag1Diag2 / (diag1Length * diag2Length)), 2)) / 2);
    }

    public static CellDiagonals getDiagOfCell(float[][] cell, int numCoordPoints) {

        CellDiagonals diagonals = new CellDiagonals();
        float[] diag1 = new float[numCoordPoints];
        float[] diag2 = new float[numCoordPoints];

        for (int i = 0; i < numCoordPoints; i++) {
            diag1[i] = cell[0][i] - cell[2][i];
            diag2[i] = cell[1][i] - cell[3][i];
        }

        diagonals.setDiag1(diag1);
        diagonals.setDiag2(diag2);
        return diagonals;
    }
}
