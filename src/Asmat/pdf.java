package Asmat;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.File;

public class pdf {
    private Enfant e;
    private Fp fp;
    File f;
    public pdf(Enfant enfant, Fp fp,File f) {
        this.e = enfant;
        this.f = f;
        this.fp = fp;
        genererPDF(enfant,fp,f);
    }
    public void genererPDF(Enfant enfant, Fp fp, File outputFile) {
        try {
            PdfWriter writer = new PdfWriter(outputFile);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Titre
            document.add(new Paragraph("Fiche de Présence - " + enfant.getConfiguration().getNom() + " " + enfant.getConfiguration().getPrenom()));

            // Tableau récapitulatif des jours
            Table table = new Table(new float[]{2, 2, 2, 2, 2});
            table.addHeaderCell("Jour");
            table.addHeaderCell("Heures");
            table.addHeaderCell("Repas");
            table.addHeaderCell("Entretien");
            table.addHeaderCell("Commentaire");

            for (int jour : fp.getJours().keySet()) {
                Presence p = fp.getJours().get(jour);
                table.addCell(String.valueOf(jour));
                table.addCell(String.valueOf(p.getTotalHeures()));
                table.addCell(String.valueOf(p.getIndRepas()));
                table.addCell(String.valueOf(p.getIndEntretien()));
                table.addCell(p.getCommentaire() != null ? p.getCommentaire() : "");
            }

            document.add(table);

            document.close();
            System.out.println("PDF généré : " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
