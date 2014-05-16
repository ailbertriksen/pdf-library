package nl.mad.toucanpdf.model.state;

import nl.mad.toucanpdf.model.Cell;
import nl.mad.toucanpdf.model.Position;

public interface StateCell extends Cell {
    double getRequiredHeight(double leading);

    double getRequiredWidth();

    Cell setPosition(Position position);

    StateCellContent getStateCellContent();
}
