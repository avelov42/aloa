package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 16.07.16.
 */
public class ScriptSetVariableCommand implements ScriptCommand {
    private final ScriptArithmeticExpression expression;
    private final CellSetter cs;

    public ScriptSetVariableCommand(final int index,
                                    ScriptSetOperator op,
                                    ScriptArithmeticExpression expr) {
        this.expression = expr;

        switch (op) {
            case ASSIGN:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            variables.SetFloat(index, expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            case ASSIGN_ADD:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            variables.SetFloat(index, variables.GetFloat(index) + expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            case ASSIGN_SUBTRACT:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            variables.SetFloat(index, variables.GetFloat(index) - expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            case ASSIGN_MULTIPLY:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            variables.SetFloat(index, variables.GetFloat(index) * expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            case ASSIGN_DIVIDE:
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            variables.SetFloat(index, variables.GetFloat(index) / expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
            default: //MODULO
                    cs = new CellSetter() {
                        @Override
                        public void Set(Cell thisCell, Variables variables, Board board) {
                            variables.SetFloat(index, variables.GetFloat(index) % expression.ValueFloat(thisCell, variables, board));
                        }
                    };
                break;
        }
    }

    @Override
    public boolean Run(Cell thisCell, Variables variables, Board board)
    {
        cs.Set(thisCell, variables, board);
        return true;
    }

    private interface CellSetter {
        void Set(Cell thisCell, Variables variables, Board board);
    }
}
