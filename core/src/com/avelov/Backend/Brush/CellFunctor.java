package com.avelov.Backend.Brush;

import com.avelov.Backend.Cell.Cell;

/**
 * Backendowy brush, frontendowy chyba nie będzie siedział w tej paczce.
 */
public interface CellFunctor
{
    void apply(Cell c);
}
