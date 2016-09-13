package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 17.07.16.
 */
public class ScriptSetReferenceCellCommand implements ScriptCommand {
    private final com.avelov.Center.Scripts.ScriptArithmeticExpression expression;
    private final CellSetter cs;

    public ScriptSetReferenceCellCommand(final int index,
                                         ScriptSetOperator sso,
                                         final int x,
                                         final int y,
                                         com.avelov.Center.Scripts.ScriptArithmeticExpression expr) {
        this.expression = expr;

        switch (sso) {
            case ASSIGN:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            //Coordinates co = new Coordinates(thisCell.x + x, thisCell.y + y);
                            //Cell c = board.getCell(co);
                            board.setNextValue(thisCell.x + x, thisCell.y + y, index, expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            case ASSIGN_ADD:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            //Coordinates co = new Coordinates(thisCell.x + x, thisCell.y + y);
                            //Cell c = board.getCell(co);
                            board.setNextValue(thisCell.x + x, thisCell.y + y, index, thisCell.getNextValue(x, y, index) + expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            case ASSIGN_SUBTRACT:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            board.setNextValue(thisCell.x + x, thisCell.y + y, index, thisCell.getNextValue(x, y, index) - expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            case ASSIGN_MULTIPLY:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            board.setNextValue(thisCell.x + x, thisCell.y + y, index, thisCell.getNextValue(x, y, index) * expression.ValueFloat(thisCell, variables, board));

                        }
                    };
                break;
            case ASSIGN_DIVIDE:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            board.setNextValue(thisCell.x + x, thisCell.y + y, index, thisCell.getNextValue(x, y, index) / expression.ValueFloat(thisCell, variables, board));

                        }
                    };
                break;
            default: //MODULO
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            board.setNextValue(thisCell.x + x, thisCell.y + y, index, thisCell.getNextValue(x, y, index) % expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
        }
    }

    @Override
    public boolean run(Cell thisCell, Variables variables, Board board) {
        cs.Set(thisCell, variables, board);
        return true;
    }

    private interface CellSetter {
        void Set(Cell thisCell, Variables variables, Board board);
    }
}
