package nl.mad.toucanpdf.model;

import java.util.List;

/**
 * Interface for page objects that are used in calculating the state of the document.
 * 
 * @author Dylan de Wolff
 */
public interface StatePage extends StateDocumentPart, Page {
    /**
     * Retrieves the filled width of this page.
     * @return Filled width of the page.
     */
    double getFilledWidth();

    /**
     * Retrieves the filled height of this page.
     * @return Filled height of the page.
     */
    double getFilledHeight();

    /**
     * Retrieves the filled width of this page.
     * @param filledWidth New filled width of the page.
     */
    void setFilledWidth(double filledWidth);

    /**
     * Retrieves the filled height of this page.
     * @param filledHeight New filled height of the page.
     */
    void setFilledHeight(double filledHeight);

    /**
     * @return the unfilled height.
     */
    double getRemainingHeight();

    /**
     * @return the unfilled width.
     */
    double getRemainingWidth();

    /**
     * Calculates and returns an open position.
     * @return Position that is available to use.
     */
    Position getOpenPosition();

    /**
     * Calculates and returns an open position.
     * @param requiredSpaceAbove The empty space required above the position.
     * @param requiredSpaceBelow The empty space required below the position.
     * @return Position that is available to use.
     */
    Position getOpenPosition(double requiredSpaceAbove, double requiredSpaceBelow);

    /**
     * Calculates and returns an open position.
     * @param requiredSpaceAbove The empty space required above the position.
     * @param requiredSpaceBelow The empty space required below the position.
     * @param requiredWidth The empty space required to the side of the position.
     * @return Position that is available to use.
     */
    Position getOpenPosition(double requiredSpaceAbove, double requiredSpaceBelow, double requiredWidth);

    /**
     * Calculates and returns an open position on or after the given height and width.
     * @param width Width offset.     
     * @param height Height offset.
     * @param requiredSpaceAbove The empty space required above the position.
     * @param requiredSpaceBelow The empty space required below the position.
     * @param requiredWidth The empty space required to the side of the position.
     * @return Position that is available to use.
     */
    Position getOpenPosition(double width, double height, double requiredSpaceAbove, double requiredSpaceBelow, double requiredWidth);

    /**
     * Returns the available spaces on the given line.
     * @param pos Position to check on.
     * @param ignoreSpacesBeforePositionWidth Whether to ignore open spaces that come before the given position's x value.
     * @param requiredSpaceAbove Amount of empty space required above the given position.
     * @param requiredSpaceBelow Amount of empty space required below the given position.
     * @return List of int arrays, each contains the start- and end point of the space.
     */
    List<int[]> getOpenSpacesOn(Position pos, boolean ignoreSpacesBeforePositionWidth, double requiredSpaceAbove, double requiredSpaceBelow);

    /**
     * Returns the available width at the given position.
     * @param position Position to check.
     * @param requiredSpaceAbove Amount of empty space required above the given position.
     * @param requiredSpaceBelow Amount of empty space required below the given position.
     * @return int containing the available width value.
     */
    int getTotalAvailableWidth(Position position, double requiredSpaceAbove, double requiredSpaceBelow);

    /**
     * Returns the available height at the given position.
     * @param position Position to check/
     * @param requiredSpaceAbove Amount of empty space required above the given position.
     * @param requiredSpaceBelow Amount of empty space required below the given position.
     * @return int containing the available height value.
     */
    int getAvailableHeight(Position position, double requiredSpaceAbove, double requiredSpaceBelow);
}
