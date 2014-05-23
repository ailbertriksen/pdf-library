package nl.mad.toucanpdf.api;

import java.io.InputStream;

import nl.mad.toucanpdf.image.JPEG;
import nl.mad.toucanpdf.model.Alignment;
import nl.mad.toucanpdf.model.Compression;
import nl.mad.toucanpdf.model.DocumentPartType;
import nl.mad.toucanpdf.model.Image;
import nl.mad.toucanpdf.model.ImageParser;
import nl.mad.toucanpdf.model.ImageType;
import nl.mad.toucanpdf.model.PlaceableDocumentPart;
import nl.mad.toucanpdf.model.Position;

/**
 * This is the base implementation for the image interface. This class can be added to an instance of DocumentBuilder in order to add 
 * images to the document.
 * @author Dylan de Wolff
 *
 */
public class BaseImage extends AbstractPlaceableFixedSizeDocumentPart implements Image {
    private ImageParser image;
    private Compression compressionMethod = Compression.FLATE;
    private boolean wrappable;

    /**
     * Creates a new BaseImage instance.
     */
    public BaseImage() {
        super(DocumentPartType.IMAGE);
    }

    /**
     * 
     * @param imageStream
     * @param filename
     */
    public BaseImage(InputStream imageStream, String filename) {
        this(imageStream, getTypeFromFilename(filename));
    }

    /**
     * Creates a new BaseImage instance.
     * @param imageStream InputStream with the image file to use.
     * @param type The format of the image.
     */
    public BaseImage(InputStream imageStream, ImageType type) {
        this();
        this.parse(imageStream, type);
        this.height = image.getHeight();
        this.width = image.getWidth();
    }

    /**
     * Creates a new instance of BaseImage.
     * @param height Height of the image. (You can fill in any custom height)
     * @param width Width of the image. (You can fill in any custom width)
     * @param imageStream InputStream with the image file to use.
     * @param type The format of the image.
     */
    public BaseImage(int height, int width, InputStream imageStream, ImageType type) {
        this();
        this.parse(imageStream, type);
        this.height = height;
        this.width = width;
    }

    private void parse(InputStream imageStream, ImageType type) {
        switch (type) {
        case JPEG:
            image = new JPEG(imageStream);
            break;
        default:
            //TODO: Log unsupported image type
            break;
        }
    }

    /**
     * Creates a copy of the given Image object.
     * @param image Image to copy.
     */
    public BaseImage(Image image) {
        this();
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.image = image.getImageParser();
        this.wrappable = image.wrappingAllowed();
        this.setAlignment(image.getAlignment());
        this.setPosition(image.getPosition());
        this.marginBottom = image.getMarginBottom();
        this.marginLeft = image.getMarginLeft();
        this.marginTop = image.getMarginTop();
        this.marginRight = image.getMarginRight();
    }

    @Override
    public PlaceableDocumentPart copy() {
        return new BaseImage(this);
    }

    @Override
    public Image align(Alignment alignment) {
        this.setAlignment(alignment);
        return this;
    }

    @Override
    public Image on(Position position) {
        this.setPosition(position);
        return this;
    }

    @Override
    public Image on(int x, int y) {
        return this.on(new Position(x, y));
    }

    @Override
    public Image height(int height) {
        return height(height, true);
    }

    @Override
    public Image height(int height, boolean scaleWidth) {
        //do some scaling 
        this.height = Math.max(1, height);
        return this;
    }

    @Override
    public Image width(int width) {
        return this.width(width, true);
    }

    @Override
    public Image width(int width, boolean scaleHeight) {
        this.width = Math.max(1, width);
        return this;
    }

    @Override
    public ImageParser getImageParser() {
        return image;
    }

    @Override
    public Image compress(Compression method) {
        this.compressionMethod = method;
        return this;
    }

    @Override
    public Compression getCompressionMethod() {
        return this.compressionMethod;
    }

    @Override
    public boolean wrappingAllowed() {
        return this.wrappable;
    }

    @Override
    public Image allowWrapping(boolean isWrappable) {
        this.wrappable = isWrappable;
        return this;
    }

    @Override
    public Image marginTop(int marginTop) {
        this.setMarginTop(marginTop);
        return this;
    }

    @Override
    public Image marginBottom(int marginBottom) {
        this.setMarginBottom(marginBottom);
        return this;
    }

    @Override
    public Image marginRight(int marginRight) {
        this.setMarginRight(marginRight);
        return this;
    }

    @Override
    public Image marginLeft(int marginLeft) {
        this.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * Returns the imagetype corresponding to the given filename.
     * @param filename filename to check.
     * @return The type of the image, null if no corresponding image type could be discovered.
     */
    public static ImageType getTypeFromFilename(String filename) {
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return ImageType.JPEG;
        } else if (filename.endsWith(".png")) {
            return ImageType.PNG;
        } else if (filename.endsWith(".gif")) {
            return ImageType.GIF;
        } else if (filename.endsWith(".bmp")) {
            return ImageType.BMP;
        }
        return null;
    }
}
