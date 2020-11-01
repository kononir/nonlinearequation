package com.bsuir.nonlinearequation.data;

import com.bsuir.linearsystem.model.Vector;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FunctionCoordinates {
    private final Vector xVector;
    private final Vector yVector;
}
