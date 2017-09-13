package me.aboullaite.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Created by aboullaite on 2017-02-25.
 */
public class PdfView extends AbstractPdfView {

    public static final String[] LANGUAGES = { "la", "en", "fr" };

    public static final Rectangle[] RECTANGLES = { new Rectangle(36, 581, 559, 806), new Rectangle(36, 308.5f, 559, 533.5f), new Rectangle(36, 36, 559, 261) };

    @Override
    protected void buildPdfDocument(final Map<String, Object> model, final Document document, final PdfWriter writer, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-pdf-file.pdf\"");
        model.get("users");

        // document.add(new Paragraph("Generated Users " + LocalDate.now()));
        final RedBorder event = new RedBorder();
        writer.setPageEvent(event);
        // step 3
        document.open();
        // step 4
        final PdfContentByte cb = writer.getDirectContent();
        final ColumnText[] columns = new ColumnText[3];
        for (int section = 1; section <= 3; section++) {
            for (int la = 0; la < 3; la++) {
                columns[la] = createColumn(cb, section, LANGUAGES[la], RECTANGLES[la]);
            }
            while (addColumns(columns)) {
                document.newPage();
                for (int la = 0; la < 3; la++) {
                    columns[la].setSimpleColumn(RECTANGLES[la]);

                }
            }
            document.newPage();
        }
        // step 5
        document.close();

    }

    private boolean addColumns(final ColumnText[] columns) throws DocumentException {
        int status = ColumnText.NO_MORE_TEXT;
        for (final ColumnText column : columns) {
            if (ColumnText.hasMoreText(column.go())) {
                status = ColumnText.NO_MORE_COLUMN;
            }
        }
        return ColumnText.hasMoreText(status);
    }



    private ColumnText createColumn(final PdfContentByte cb, final int i, final String la, final Rectangle rect) {
        final ColumnText ct = new ColumnText(cb);
        ct.setSimpleColumn(rect);

        final Phrase p = new Phrase("oooooooooooooooooooooooooooooooo");
        ct.addText(p);
        return ct;

    }

    public class RedBorder extends PdfPageEventHelper {

        protected Rectangle[] rectangles;

        public RedBorder() {
            rectangles = new Rectangle[3];
            for (int i = 0; i < 3; i++) {
                rectangles[i] = new Rectangle(RECTANGLES[i]);
                rectangles[i].setBorder(Rectangle.BOX);
                rectangles[i].setBorderWidth(1);
                rectangles[i].setBorderColor(BaseColor.RED);
            }
        }

        @Override
        public void onEndPage(final PdfWriter writer, final Document document) {
            final PdfContentByte canvas = writer.getDirectContent();
            for (final Rectangle rectangle : rectangles) {
                canvas.rectangle(rectangle);
            }
        }

    }
}


