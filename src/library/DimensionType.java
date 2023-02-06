package library;

public enum DimensionType {
    TWO_DIMENSION("2D"),
    THREE_DIMENSION("3D"),
    IMAX_2D("IMAX 2D"),
    IMAX_3D("IMAX 3D"),
    FOUR_DX("4DX");

    private final String dimension;

    DimensionType(String dimension) {
        this.dimension = dimension;
    }

    public String getDimension() {
        return dimension;
    }
}
