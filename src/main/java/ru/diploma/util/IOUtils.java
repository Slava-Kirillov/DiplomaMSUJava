package ru.diploma.util;

import ru.diploma.error.DataReadException;

import java.io.*;

public class IOUtils {

    /**
     * Метод возвращает многомерный массив, в котором содержатся координаты точек ячеек,
     * на которые разбит исследуемый объект
     * @param numberOfPointsPerCell
     * @param numberOfCoordinatesPerPoint
     * @return
     * @throws IOException
     * @throws DataReadException
     */
    public static float[][][] getArrayOfCells(int numberOfPointsPerCell,
                                              int numberOfCoordinatesPerPoint) throws IOException, DataReadException {
        File file = new File("src/main/resources/data/sphere_30_50.dat");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line = null;
            String[] numbers = null;
            int lineCount = 0;
            int numberOfCells = 0;

            // в цикле while считываем файл до строки с координатами яцейки
            while ((line = reader.readLine()) != null) {
                lineCount++;
                numbers = line.split(" ");
                if (numbers.length < numberOfPointsPerCell * numberOfCoordinatesPerPoint) {
                    if (lineCount == 4) {
                        numberOfCells = Integer.parseInt(numbers[0]);
                    }
                } else {
                    break;
                }
            }

            if (numberOfCells != 0) {
                float[][][] arrayOfCell = new float[numberOfCells][numberOfPointsPerCell][numberOfCoordinatesPerPoint];
                for (int i = 0; i < numberOfCells - 1; i++) { // (- 1) так как первая строка с ячейкой была прочитана в цикле while
                    line = reader.readLine();
                    lineCount++;
                    numbers = line.split(" ");
                    if (numbers.length == numberOfPointsPerCell * numberOfCoordinatesPerPoint) {
                        for (int j = 0; j < numberOfPointsPerCell; j++) {
                            for (int k = 0; k < numberOfCoordinatesPerPoint; k++) {
                                int index = k + j * numberOfCoordinatesPerPoint;
                                arrayOfCell[i][j][k] = Float.parseFloat(numbers[index]);
                            }
                        }
                    } else {
                        throw new DataReadException("Can not read file. Wrong number of coordinates. File line:" + lineCount);
                    }
                }
                return arrayOfCell;
            }
            throw new DataReadException("Wrong data. Number of cells:" + numberOfCells);
        }
    }

}
