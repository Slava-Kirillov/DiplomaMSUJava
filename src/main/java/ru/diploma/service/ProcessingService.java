package ru.diploma.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.diploma.data.CellVectors;
import ru.diploma.error.DataReadException;

import java.io.*;

@Component
public class ProcessingService {

    private DataGenService dataGenService;
    private IOService ioService;

    @Value("${write.result.to.file}")
    private boolean writeResults;

    public ProcessingService(DataGenService dataGenService, IOService ioService) {
        this.dataGenService = dataGenService;
        this.ioService = ioService;
    }

    public void runProcessing() {
        try {
            float[][][] cells = ioService.getArrayOfCellsFromFile();
            float[][] collocationPoint = dataGenService.getCollocationPoints(cells);
            float[] cellArea = dataGenService.getCellArea(cells);
            CellVectors cellVectors = dataGenService.getCellVectors(cells);
            printFigureArea(cellArea);
        } catch (DataReadException | IOException e) {
            e.printStackTrace();
        }
    }

    private void printFigureArea(float[] cellArea) {
        double area = 0;
        for (float v : cellArea) {
            area += v;
        }
        System.out.println(String.format("Площадь фигуры по данным из файла: %f", area));
        System.out.println(String.format("Площадь фигуры по формуле S = 4*Pi*R: %f", Math.PI * 4));
        System.out.println(String.format("Разность: %f", Math.abs(area - Math.PI * 4)));
    }

//    private void writeAllResultInFiles(float[] cellArea,
//                                       float[][] collocationPoints,
//                                       float[][][] cells,
//                                       CellVectors cellVectors) {
//        if (writeResults) {
//            write_result_to_file(
//                    "collocation_points.dat",
//                    collocationPoints,
//                    struct_of_points -> number_of_coordinates_at_point,
//                    struct_of_points -> number_of_cell
//            );
//            write_result_to_file(
//                    "sphere.dat",
//                    cells,
//                    struct_of_points -> number_of_coordinates_at_point,
//                    struct_of_points -> total_number_of_coordinates /
//                            struct_of_points -> number_of_coordinates_at_point
//            );
//            write_result_to_file(
//                    "cell_area.dat",
//                    cellArea,
//                    1,
//                    struct_of_points -> number_of_cell
//            );
//            write_result_to_file(
//                    "normal_vectors.dat",
//                    cellVectors.getNormal(),
//                    struct_of_points -> number_of_coordinates_at_point,
//                    struct_of_points -> number_of_cell
//            );
//            write_result_to_file(
//                    "tau1_vectors.dat",
//                    cellVectors.getTau1(),
//                    struct_of_points -> number_of_coordinates_at_point,
//                    struct_of_points -> number_of_cell
//            );
//            write_result_to_file(
//                    "tau2_vectors.dat",
//                    cellVectors.getTau2(),
//                    struct_of_points -> number_of_coordinates_at_point,
//                    struct_of_points -> number_of_cell
//            );
//        }
//    }
//
//    /**
//     * Печать результата в файл
//     *
//     * @param number_of_columns
//     * @param number_of_rows
//     */
//    private void write_result_to_file(String fileName, float[] data, int number_of_columns, int number_of_rows) {
//
//        File fileDir = new File("src/main/resources/data/" + fileName);
//        try(Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"))) {
//
//            for (int i = 0; i < number_of_rows; ++i) {
//                for (int j = 0; j < number_of_columns; ++j) {
//                    out.append(data[i][j])
//                }
//                fprintf(file, "%s", "\n");
//            }
//
//            out.append("some UTF-8 text").append("\r\n");
//            out.flush();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
