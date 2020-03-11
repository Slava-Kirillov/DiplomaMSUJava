package ru.diploma.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CellVectors {
    private float[][] normal;
    private float[][] tau1;
    private float[][] tau2;
}
