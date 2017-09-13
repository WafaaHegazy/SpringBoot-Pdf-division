package me.aboullaite.view;

import java.util.ArrayList;
import java.util.List;
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
import com.itextpdf.text.pdf.PdfWriter;

import me.aboullaite.model.Employee;
//import me.aboullaite.view.PdfView.RedBorder;

/**
 * Created by aboullaite on 2017-02-25.
 */
public class PdfView extends AbstractPdfView {

    public static final String[] LANGUAGES = { "la", "en", "fr" };

    public static final Rectangle[] RECTANGLES = { new Rectangle(36, 270, 123, 559), new Rectangle(270, 270, 806, 559), new Rectangle(36, 36, 559, 261) };

    @Override
    protected void buildPdfDocument(final Map<String, Object> model, final Document document, final PdfWriter writer, final HttpServletRequest request,
            final HttpServletResponse response)
                    throws Exception {
        response.setHeader("Content-Disposition", "inline; filename=\"my-pdf-file.pdf\"");
        final List<Employee> users = (List<Employee>) model.get("users");
        for (final Employee u : users) {
            // final Employee u = users.get(1);
            final List<String> fields = new ArrayList<>();
            if (u.getEmployeeEmail() != null && !u.getEmployeeEmail().equals("")) {
                fields.add(u.getEmployeeEmail());
            }
            if (u.getEmployeeLastName() != null && !u.getEmployeeLastName().equals("")) {
                fields.add(u.getEmployeeLastName());
            }
            if (u.getEmployeeFirstName() != null && !u.getEmployeeFirstName().equals("")) {
                fields.add(u.getEmployeeFirstName());
            }

            final PdfContentByte cb = writer.getDirectContent();
            final ColumnText[] columns = new ColumnText[fields.size()];

            for (int la = 0; la < fields.size(); la++) {
                columns[la] = createColumn(cb, 1, fields.get(la), RECTANGLES[la]);
            }
            while (addColumns(columns)) {

                for (int la = 0; la < fields.size(); la++) {

                    columns[la].setSimpleColumn(RECTANGLES[la]);

                }
            }
            document.newPage();
        }
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
        rect.setBorder(Rectangle.BOX);
        rect.setBorderColor(BaseColor.BLUE);
        ct.setSimpleColumn(rect);

        final Phrase p = new Phrase(la);
        ct.addText(p);
        return ct;

    }

}
