package ru.diploma.service;

import org.springframework.stereotype.Component;
import ru.diploma.config.ApplicationConfig;
import ru.diploma.config.SysLinEqConfig;
import ru.diploma.data.CellVectors;
import ru.diploma.data.SystemOfLinearEquations;
import ru.diploma.data.complex.Complex;
import ru.diploma.error.DataReadException;
import ru.diploma.util.IOUtil;

import java.io.IOException;

@Component
public class MainProcessingService {

    private DataGenService dataGenService;
    private ApplicationConfig config;
    private SysLinEqConfig eqConfig;

    public MainProcessingService(DataGenService dataGenService, ApplicationConfig config, SysLinEqConfig eqConfig) {
        this.dataGenService = dataGenService;
        this.config = config;
        this.eqConfig = eqConfig;
    }

    public void runProcessing() {
        try {
            //ячейки
            float[][][] cells = IOService.getArrayOfCellsFromFile(config.getDataFile(), config.getNumPoints(), config.getNumCoordinatePoint());
            //точки коллокации
            float[][] collocationPoint = DataGenService.getCollocationPoints(cells, config.getNumPoints(), config.getNumCoordinatePoint());
            //площади ячеек
            float[] cellArea = DataGenService.getCellArea(cells, config.getNumCoordinatePoint());

            //базис на ячейках
            CellVectors cellVectors = dataGenService.getCellVectors(cells);

            if (config.isWriteResults()) {
                IOUtil.writeAllResultToFiles(cellArea, collocationPoint, cells, cellVectors);
                IOUtil.printFigureArea(cellArea);
            }

            SystemOfLinearEquations system = new SystemOfLinearEquations(cells, cellVectors, collocationPoint, eqConfig);

            String realMatrixFile = "real_part_matrix";
            String imagMatrixFile = "imag_part_matrix";

            String realConstantTerm = "real_part_constant_term";
            String imagConstantTerm = "image_part_constant_term";

            IOUtil.writeComplexMatrixToFile(system.getMatrix_of_coefficient(), realMatrixFile, imagMatrixFile);
            IOUtil.writeConstantTermToFile(system.getConstant_term(), realConstantTerm, imagConstantTerm);
        } catch (DataReadException | IOException e) {
            e.printStackTrace();
        }
    }

    public void resultProcessing() {
        try {
            Complex[] arrayComplex = IOService.getResutlFromFile();



            System.out.println(arrayComplex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
