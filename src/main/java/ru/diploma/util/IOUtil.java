package ru.diploma.util;

import ru.diploma.data.CellVectors;
import ru.diploma.data.complex.Complex;

import java.io.*;

public class IOUtil {

    /**
     * Вычисление площади фигуры по площади ячеек и вывод ркзультата в консоль
     *
     * @param cellArea
     */
    public static void printFigureArea(float[] cellArea) {
        float area = 0;
        for (float v : cellArea) {
            area += v;
        }
        System.out.println(String.format("Площадь фигуры по данным из файла: %f", area));
        System.out.println(String.format("Площадь фигуры по формуле S = 4*Pi*R: %f", Math.PI * 4));
        System.out.println(String.format("Разность: %f", Math.abs(area - Math.PI * 4)));
    }

    /**
     * Вывод результата формирования даныых для расчета в файйл
     *
     * @param cellArea
     * @param collocationPoints
     * @param cells
     * @param cellVectors
     */
    public static void writeAllResultToFiles(float[] cellArea,
                                             float[][] collocationPoints,
                                             float[][][] cells,
                                             CellVectors cellVectors,
                                             String pathToResults) {
        int numberOfLine = cellArea.length;
        writeResultToFile("collocation_points.dat", collocationPoints, 2, numberOfLine, pathToResults);
        writeResultToFile("sphere.dat", cells, 3, numberOfLine, pathToResults);
        writeResultToFile("cell_area.dat", cellArea, 1, numberOfLine, pathToResults);
        writeResultToFile("normal_vectors.dat", cellVectors.getNormal(), 2, numberOfLine, pathToResults);
        writeResultToFile("tau1_vectors.dat", cellVectors.getTau1(), 2, numberOfLine, pathToResults);
        writeResultToFile("tau2_vectors.dat", cellVectors.getTau2(), 2, numberOfLine, pathToResults);
    }

    /**
     * Вывод массива в файл
     */
    public static <T> void writeResultToFile(String fileName, T data, int dimensional, int numberOfLine, String pathToResults) {
        File fileDir = new File(pathToResults + fileName);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"))) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < numberOfLine; ++i) {
                switch (dimensional) {
                    case 1:
                        float[] array1 = (float[]) data;
                        stringBuilder.append(array1[i]).append("\n");
                        break;
                    case 2:
                        float[][] array2 = (float[][]) data;
                        for (int j = 0; j < 3; j++) {
                            stringBuilder.append(array2[i][j]).append(" ");
                        }
                        stringBuilder.append("\n");
                        break;
                    case 3:
                        float[][][] array3 = (float[][][]) data;
                        for (int j = 0; j < 4; j++) {
                            for (int k = 0; k < 3; k++) {
                                stringBuilder.append(array3[i][j][k]).append(" ");
                            }
                        }
                        stringBuilder.append("\n");
                }
            }
            out.append(stringBuilder.toString());
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeComplexMatrixToFile(Complex[][] data, String fileNameReal, String fileNameImag, String pathToResults) {
        float[][] real_part = new float[data.length][data[0].length];
        float[][] imag_part = new float[data.length][data[0].length];

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                real_part[i][j] = data[i][j].getRe();
                imag_part[i][j] = data[i][j].getIm();
            }
        }

        writeMatrixToFile(fileNameReal, real_part, pathToResults);
        writeMatrixToFile(fileNameImag, imag_part, pathToResults);
    }

    public static void writeConstantTermToFile(Complex[] data, String fileNameReal, String fileNameImag, String pathToResults) {
        float[] real_part = new float[data.length];
        float[] imag_part = new float[data.length];


        for (int i = 0; i < data.length; i++) {
            real_part[i] = data[i].getRe();
            imag_part[i] = data[i].getIm();
        }

        writeConstantTermToFile(fileNameReal, real_part, pathToResults);
        writeConstantTermToFile(fileNameImag, imag_part, pathToResults);
    }

    private static void writeConstantTermToFile(String fileName, float[] data, String pathToResults) {
        File fileDir = new File(pathToResults + fileName);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"))) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(data.length).append("\n");
            for (float datum : data) {
                stringBuilder.append(datum).append(" ");
                stringBuilder.append("\n");
            }
            out.append(stringBuilder.toString());
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Вывод матрицы в файл
     */
    private static void writeMatrixToFile(String fileName, float[][] data, String pathToResults) {
        File fileDir = new File(pathToResults + fileName);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"))) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(data.length).append(" ").append(data[0].length).append("\n");
            for (float[] datum : data) {
                for (float v : datum) {
                    stringBuilder.append(v).append(" ");
                }
                stringBuilder.append("\n");
            }
            out.append(stringBuilder.toString());
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
