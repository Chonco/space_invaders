import assets.AssetsConstants;

public class Validations {
    public static void validateGameConfigurations() {
        validateColors();
        validatePixelSize();
    }

    private static void validateColors() {
        if (AssetsConstants.BACKGROUND_COLOR == AssetsConstants.OBJECTS_COLOR) {
            throw new IllegalArgumentException("Colors must be different.");
        }
    }

    private static void validatePixelSize() {
        if (AssetsConstants.PIXEL_SIZE <= 0) {
            throw new IllegalArgumentException("Pixel size must be grater than 0");
        }
    }
}
