package nl.pdflibrary.pdf;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import nl.pdflibrary.pdf.object.AbstractPdfObject;
import nl.pdflibrary.pdf.object.PdfDictionary;
import nl.pdflibrary.pdf.object.PdfIndirectObject;
import nl.pdflibrary.pdf.object.PdfIndirectObjectReference;
import nl.pdflibrary.pdf.object.PdfName;
import nl.pdflibrary.pdf.object.PdfNameValue;
import nl.pdflibrary.pdf.object.PdfObjectType;
import nl.pdflibrary.pdf.object.PdfPage;
import nl.pdflibrary.pdf.object.PdfPageTree;

/** 
 * Represents the body section of a PDF file. Responsible for creating indirect objects and storing all 
 * the indirect PDF objects, including the catalog and page tree. 
 * 
 * @author Dylan de Wolff
 * @see PdfIndirectObject
 * @see PdfDocument
 */
public class PdfBody {
    private ArrayList<PdfIndirectObject> indirectObjects;
    /**
     * Represents the catalog object of the body, stored outside of the general object list to allow for easier updating
     */
    private PdfIndirectObject catalog;
    /**
     * Represents the page tree of PDF documents, stored outside of the general object list to allow for easier updating
     */
    private PdfPageTree pageTree;
    /**
     * Represents the offset caused by storing the catalog separately
     */
    private static final int OBJECT_NUMBER_OFFSET = 1;

    /**
     * Creates a new instance of the PdfBody. This will also result in the creation of the page tree and catalog.
     */
    public PdfBody() {
        indirectObjects = new ArrayList<PdfIndirectObject>();
        pageTree = createPageTree();
        catalog = createCatalog(pageTree);
    }

    /**
     * Used to add a PdfObject to the body, automatically creates an indirect object representation for the given PdfObject.
     *
     * @param object The PdfObject that will be added to the body
     * @return The indirect object created with the PdfObject
     */
    public PdfIndirectObject addObject(AbstractPdfObject object) {
        PdfIndirectObject indirectObject = new PdfIndirectObject(getTotalIndirectObjectsAmount() + 1, 0, object, true);
        this.indirectObjects.add(indirectObject);
        return indirectObject;
    }

    /**
     * Adds a page object to the body. Also adds the page to the page tree.
     * 
     * @param page The page object that will be added
     * @return The indirect object created with the Page
     */
    public PdfIndirectObject addPage(PdfPage page) {
        PdfIndirectObject indirectPage = new PdfIndirectObject(getTotalIndirectObjectsAmount() + 1, 0, page, true);
        this.pageTree.add(indirectPage);
        return indirectPage;
    }

    /**
     * Writes all the indirect objects stored in the body to the given OutputStream. Also sets the starting byte of
     * the indirect objects. This is needed for the creation of the cross reference table.
     * 
     * @param os The data output stream that will be written to
     * @throws IOException
     */
    public void writeToFile(DataOutputStream os) throws IOException {
        for (PdfIndirectObject object : getAllIndirectObjects()) {
            object.setStartByte(os.size());
            object.writeToFile(os);
        }
    }

    public ArrayList<PdfIndirectObject> getIndirectObjects() {
        return this.indirectObjects;
    }

    /**
     * Returns the number of indirect objects contained in the body, including the separately stored catalog, pageTree and page objects
     * @return the number of objects
     */
    public int getTotalIndirectObjectsAmount() {
        if (indirectObjects != null && pageTree != null) {
            return this.indirectObjects.size() + pageTree.getSize() + PdfBody.OBJECT_NUMBER_OFFSET;
        } else {
            return PdfBody.OBJECT_NUMBER_OFFSET;
        }
    }

    /**
     * Creates the catalog object. The catalog forms the root of the PDF file and refers to the first page tree of the document.
     * 
     * @param pages The first page node that the catalog should refer to
     * @return The indirect object for the catalog
     */
    private PdfIndirectObject createCatalog(PdfPageTree pages) {
        PdfDictionary catalogDictionary = new PdfDictionary(PdfObjectType.CATALOG);
        PdfIndirectObject indirectCatalog = new PdfIndirectObject(1, 0, catalogDictionary, true);
        catalogDictionary.put(new PdfName(PdfNameValue.TYPE), new PdfName(PdfNameValue.CATALOG));
        catalogDictionary.put(new PdfName(PdfNameValue.PAGES), pages.getReference());
        return indirectCatalog;
    }

    /**
     * Creates a new page tree.
     * 
     * @return The newly made page tree object
     */
    //TODO: Add new pagetrees to the existing page tree
    private PdfPageTree createPageTree() {
        PdfDictionary pages = new PdfDictionary(PdfObjectType.PAGETREE);
        PdfPageTree pageTreeObj = new PdfPageTree(getTotalIndirectObjectsAmount() + 1, 0, pages, true);
        return pageTreeObj;
    }

    public PdfIndirectObject getCatalog() {
        return this.catalog;
    }

    public PdfIndirectObjectReference getCatalogReference() {
        return this.catalog.getReference();
    }

    /**
     * Returns an arraylist containing all indirect objects contained in the body. This includes the separately stored 
     * catalog and page tree objects.
     * @return an arraylist containing all indirect objects
     */
    public ArrayList<PdfIndirectObject> getAllIndirectObjects() {
        ArrayList<PdfIndirectObject> allIndirectObjects = new ArrayList<PdfIndirectObject>();
        allIndirectObjects.add(catalog);
        allIndirectObjects.addAll(pageTree.getPageTreeObjects());
        allIndirectObjects.addAll(this.indirectObjects);
        return allIndirectObjects;
    }
}
