package com.pps.asciigame.common.util;

import com.pps.asciigame.common.JsonParser;
import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.Operation;
import com.pps.asciigame.common.model.OperationType;


public class OperationFactory {
    public static Operation createOperation(final Base homeBase, final Base targetBase, final OperationType type) {
        final var effect = JsonParser.stringify(type.getEffect());
        final var costs = JsonParser.stringify(type.getCosts());
        return new Operation(homeBase, targetBase, type, costs, effect);
    }
}
