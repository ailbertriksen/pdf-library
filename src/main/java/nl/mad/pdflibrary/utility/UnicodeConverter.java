package nl.mad.pdflibrary.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import nl.mad.pdflibrary.model.FontMetrics;

/**
 * UnicodeConverter allows you to get the postscript name of a unicode character. This is needed for accessing the metric data of certain font types.
 * @author Dylan de Wolff
 *
 */
public final class UnicodeConverter {
    private static Map<Integer, String> unicodeToPostscript;
    private static final String FILENAME = "glyphlist.txt";
    private static final int KEY_RADIX = 16;

    static {
        unicodeToPostscript = new HashMap<Integer, String>();
        File file = new File(FontMetrics.RESOURCE_LOCATION + FILENAME);
        if (file.isFile()) {
            try {
                RandomAccessFile rfile = new RandomAccessFile(file, "r");
                UnicodeConverter.processGlyphlist(rfile);
                rfile.close();
            } catch (FileNotFoundException e) {
                System.err.print("Could not find glyphlist.txt in the resources folder");
            } catch (IOException e) {
                System.err.print("Exception ocurred while reading glyphlist.txt");
            }
        }
    }

    private UnicodeConverter() {

    }

    /**
     * Returns the postscript name of the given unicode character code.
     * @param code Code of the character.
     * @return String containing the postscript name. Will be null if the character could not be found.
     */
    public static String getPostscriptForUnicode(int code) {
        return unicodeToPostscript.get(code);
    }

    /**
     * Processes the file containing the list of unicode character codes and the corresponding postscript names.
     * @param file
     * @throws IOException
     */
    private static void processGlyphlist(RandomAccessFile file) throws IOException {
        String currentLine = "";
        while ((currentLine = file.readLine()) != null) {
            if (!currentLine.startsWith("#")) {
                StringTokenizer st = new StringTokenizer(currentLine, " ;\r\n\t\f");
                String value = st.nextToken();
                int key = Integer.parseInt(st.nextToken(), KEY_RADIX);
                unicodeToPostscript.put(key, value);
            }
        }
    }

}
