package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 17.07.16.
 */
public class ScriptSetThisCellCommand implements ScriptCommand {
    private final com.avelov.Center.Scripts.ScriptArithmeticExpression expression;
    private final CellSetter cs;

    public ScriptSetThisCellCommand(final int index,
                                    com.avelov.Center.Scripts.ScriptSetOperator sso,
                                    com.avelov.Center.Scripts.ScriptArithmeticExpression expr) {
        this.expression = expr;

        switch (sso) {
            case ASSIGN:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, com.avelov.Center.Scripts.Variables variables, Board board) {
                            thisCell.setNextValue(index, expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            case ASSIGN_ADD:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, com.avelov.Center.Scripts.Variables variables, Board board) {
                            thisCell.setNextValue(index, thisCell.getNextValue(index) + expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            case ASSIGN_SUBTRACT:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, com.avelov.Center.Scripts.Variables variables, Board board) {
                            thisCell.setNextValue(index, thisCell.getNextValue(index) - expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            case ASSIGN_MULTIPLY:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, com.avelov.Center.Scripts.Variables variables, Board board) {
                            thisCell.setNextValue(index, thisCell.getNextValue(index) * expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            case ASSIGN_DIVIDE:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, com.avelov.Center.Scripts.Variables variables, Board board) {
                            thisCell.setNextValue(index, thisCell.getNextValue(index) / expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            default: //MODULO
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, com.avelov.Center.Scripts.Variables variables, Board board) {
                            thisCell.setNextValue(index, thisCell.getNextValue(index) % expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
        }
    }

    @Override
    public boolean Run(Cell thisCell, com.avelov.Center.Scripts.Variables variables, Board board) {
        cs.Set(thisCell, variables, board);
        return true;
    }

    private interface CellSetter {
        void Set(Cell thisCell, com.avelov.Center.Scripts.Variables variables, Board board);
    }
}
