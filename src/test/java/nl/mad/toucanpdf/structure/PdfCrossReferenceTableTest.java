package nl.mad.toucanpdf.structure;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import nl.mad.toucanpdf.pdf.structure.PdfCrossReferenceTable;
import nl.mad.toucanpdf.pdf.syntax.PdfIndirectObject;
import nl.mad.toucanpdf.pdf.syntax.PdfName;
import nl.mad.toucanpdf.utility.ByteEncoder;

import org.junit.Before;
import org.junit.Test;

public class PdfCrossReferenceTableTest {
    private PdfCrossReferenceTable xref;

    @Before
    public void setUp() throws Exception {
        xref = new PdfCrossReferenceTable();
    }

    @Test
    public void testFillTableWithIndirectObjects() {
        ArrayList<PdfIndirectObject> testArray = new ArrayList<PdfIndirectObject>();
        testArray.add(new PdfIndirectObject(1, 0, new PdfName("Test"), true));
        xref.fillTableWithIndirectObjects(testArray);

        assertEquals("Cross reference number is incorrect. ", true, xref.isObjectInTable(1));
        assertEquals("The amount of cross references is incorrect. ", 1, xref.getCrossReferenceAmount());
    }

    @Test
    public void testWriteToFile() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        ArrayList<PdfIndirectObject> testArray = new ArrayList<PdfIndirectObject>();
        testArray.add(new PdfIndirectObject(1, 0, new PdfName("Test"), true));
        xref.fillTableWithIndirectObjects(testArray);
        dos.writeChars("a");
        int expectedValue = dos.size();
        xref.writeToFile(dos);
        assertEquals("The start byte is incorrect.", true, Arrays.equals(ByteEncoder.getBytes(String.valueOf(expectedValue)), xref.getStartByte()));
        //test writing itself
    }
}
