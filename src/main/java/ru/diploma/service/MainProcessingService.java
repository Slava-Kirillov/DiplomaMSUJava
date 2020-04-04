package ru.diploma.service;

import org.springframework.stereotype.Component;
import ru.diploma.config.ApplicationConfig;
import ru.diploma.config.SysLinEqConfig;
import ru.diploma.data.CellVectors;
import ru.diploma.data.SystemOfLinearEquations;
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
            float[][][] cells = IOService.getArrayOfCellsFromFile(config.getDataFile(),
                    config.getNumPoints(),
                    config.getNumCoordinatePoint());

            float[][] collocationPoint = DataGenService.getCollocationPoints(cells,
                    config.getNumPoints(),
                    config.getNumCoordinatePoint());
            float[] cellArea = DataGenService.getCellArea(cells, config.getNumCoordinatePoint());
            CellVectors cellVectors = dataGenService.getCellVectors(cells);

            if (config.isWriteResults()) {
                IOUtil.writeAllResultToFiles(cellArea, collocationPoint, cells, cellVectors);
                IOUtil.printFigureArea(cellArea);
            }

            SystemOfLinearEquations system = new SystemOfLinearEquations(cells, cellVectors, collocationPoint, eqConfig);

            IOUtil.writeComplexMatrixToFile(system.getMatrix_of_coefficient());
            IOUtil.writeConstantTermToFile(system.getConstant_term());
        } catch (DataReadException | IOException e) {
            e.printStackTrace();
        }
    }
}
