import java.io.IOException;
import java.util.Calendar;

import nl.mad.pdflibrary.DocumentBuilder;
import nl.mad.pdflibrary.api.BaseText;
import nl.mad.pdflibrary.model.Font;
import nl.mad.pdflibrary.model.FontFamilyType;
import nl.mad.pdflibrary.model.Page;
import nl.mad.pdflibrary.model.Text;

public class Main {

    /**
     * This is only for testing purposes
     * @param args 
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        //ultimateDocumentCreation();
        documentStateTest();
    }

    private static void documentStateTest() {
        DocumentBuilder builder = new DocumentBuilder();
        builder.addPage()
                .add(new BaseText("Fixed Test").on(0, 832).size(12))
                .add(new BaseText("Fixed Test 2").on(200, 815))
                .add(new BaseText(
                        "Test Test Test Test Test Test Test Test Test Test Test TEst Test Test Test Test Test Test Test Test Test Test Teeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeest"));
        //        builder.addParagraph()
        //                .addText(builder.createText("Paragraaftest boe"))
        //                .addText(builder.createText("Oh boehoe hoe boe."))
        //                .addText(
        //                        builder.createText("Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test TestTest Test Test Test Test Test Test TestTest Test Test Test Test Test Test TestTest Test Test Test Test Test Test TestTest Test Test Test Test Test Test TestTest Test Test Test Test Test Test Test"));
        ((BaseText) (builder.getPreview().getPages().get(0).getContent().get(1))).getTextSplit().toString();
        builder.finish();
    }

    private static void ultimateDocumentCreation() {
        //create new document
        DocumentBuilder builder = new DocumentBuilder();
        //set metadata
        builder.title("Simple Document Example").writtenBy("Example Author").on(Calendar.getInstance()).about("Toucan-PDF").filename("document");

        //add font to the document and store it for the upcoming text
        Font font = builder.addFont().family(FontFamilyType.HELVETICA).boldItalic();

        //add text to the document
        Text text = builder
                .addText(
                        "Example Example Example Example Example Example Example Example Example Example Example Example Example Example Example Example Example Example Example")
                .size(11).font(font);

        Page page2 = builder.addPage();
        builder.addText("This shouldn't though.").size(12);

        Page page = builder.getPage(1);
        builder.setCurrentPage(page);
        page.marginBottom(20).marginTop(50).marginLeft(10).marginRight(60);
        builder.addText("Test Test Test Test Test Test Test Test Test Test Test aaaaabbbbbcccccdddddeeeeefffffggggghhhhhiiiiijjjjjkkkkklllllmmmmmnnnnnooooopppppqqqqqrrrrrsssssttttt-aaaaabbbbbcccccdddddeeeeefffffggggghhhhhiiiiijjjjjkkkkklllllmmmmmnnnnnooooopppppqqqqqrrrrrsssssttttt-aaaaabbbbbcccccdddddeeeeefffffggggghhhhhiiiiijjjjjkkkkklllllmmmmmnnnnnooooopppppqqqqqrrrrrsssssttttt");

        builder.addText("Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 Test2 aaaaabbbbbcccccdddddeeeeefffffggggghhhhhiiiiijjjjjkkkkklllllmmmmmnnnnnooooopppppqqqqqrrrrrsssssttttt");

        //create another font
        Font secondFont = builder.addFont().family(FontFamilyType.TIMES_ROMAN).bold();

        //add paragraph to the document and add text objects to the paragraph
        builder.addParagraph()
                .on(10, 20)
                .addText(builder.createText().text("This stays").font(secondFont))
                .addText(builder.createText("together"))
                .addText(
                        builder.createText(
                                "TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST")
                                .size(2));

        builder.addText("Hopefully this causes some overflow").size(10);
        builder.addText("Hopefully this causes some overflow").size(10); //Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow Hopefully this causes some overflow")
        //                .size(10);

        Page pagex = builder.getPage(2);
        Page pagexy = builder.getPage(3);
        System.out.println(page2);
        System.out.println(pagex);
        System.out.println(pagexy);

        //finish up the document by creating a PDF file
        builder.finish();
    }
}
