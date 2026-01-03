package Asmat;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;

import java.io.File;
import java.time.format.TextStyle;
import java.util.Locale;

public class pdf {

    public pdf(Enfant enfant, Fp fp, File outputFile) {

        try {
            PdfWriter writer = new PdfWriter(outputFile);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            /* =========================
               TITRE
            ========================= */
            Paragraph title = new Paragraph("FICHE DE PRÉSENCE")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold();

            document.add(title);
            document.add(new Paragraph("\n"));

            /* =========================
               INFOS ENFANT
            ========================= */
            ConfigurationEnfant c = enfant.getConfiguration();

            Table infoTable = new Table(2);
            infoTable.setWidth(UnitValue.createPercentValue(100));

            infoTable.addCell(cellLabel("Nom :"));
            infoTable.addCell(cellValue(c.getNom()));

            infoTable.addCell(cellLabel("Prénom :"));
            infoTable.addCell(cellValue(c.getPrenom()));

            infoTable.addCell(cellLabel("Mois :"));
            infoTable.addCell(cellValue(fp.getMonth().toString()));

            infoTable.addCell(cellLabel("Année :"));
            infoTable.addCell(cellValue(String.valueOf(fp.getYear())));

            document.add(infoTable);
            document.add(new Paragraph("\n"));

            /* =========================
               TABLEAU JOURNALIER
            ========================= */
            Paragraph subtitle = new Paragraph("Détail journalier")
                    .setBold()
                    .setFontSize(14);

            document.add(subtitle);

            Table table = new Table(new float[]{1, 2, 2, 2, 3});
            table.setWidth(UnitValue.createPercentValue(100));

            header(table, "Jour");
            header(table, "Heures");
            header(table, "Repas");
            header(table, "Entretien");
            header(table, "Commentaire");

            for (int jour : fp.getJours().keySet()) {
                Presence p = fp.getJours().get(jour);

                table.addCell(String.valueOf(jour));
                table.addCell(String.valueOf(p.getTotalHeures()));
                table.addCell(String.valueOf(p.getIndRepas()));
                table.addCell(String.valueOf(p.getIndEntretien()));
                table.addCell(p.getCommentaire() == null ? "" : p.getCommentaire());
            }

            document.add(table);
            document.add(new Paragraph("\n"));

            /* =========================
               RÉCAPITULATIF MENSUEL
            ========================= */
            Paragraph recapTitle = new Paragraph("Récapitulatif mensuel")
                    .setBold()
                    .setFontSize(14);

            document.add(recapTitle);

            Table recap = new Table(2);
            recap.setWidth(UnitValue.createPercentValue(60));

            // ⬇⬇⬇ ICI tu branches TES attributs ⬇⬇⬇
            recap.addCell(cellLabel("Nombre de jours d'activites"));
            recap.addCell(cellValue(String.valueOf(fp.getNombredejoursactivites())));

            recap.addCell(cellLabel("Total heures"));
            recap.addCell(cellValue(String.valueOf(fp.getHeures())));

            recap.addCell(cellLabel("Total repas"));
            recap.addCell(cellValue(String.valueOf(fp.getRepas())));

            recap.addCell(cellLabel("Total indemnités entretien"));
            recap.addCell(cellValue(String.valueOf(fp.getIndmenitesEntretien())));

            recap.addCell(cellLabel("Tarif horaire"));
            recap.addCell(cellValue(String.valueOf(fp.getTarif())));

            recap.addCell(cellLabel("Salaire net"));
            recap.addCell(cellValue(String.valueOf(fp.getSalaireNet())));

            document.add(recap);

            /* =========================
               FIN
            ========================= */
            document.close();

            System.out.println("PDF généré : " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =========================
       HELPERS
    ========================= */

    private static Cell cellLabel(String text) {
        return new Cell()
                .add(new Paragraph(text).setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY);
    }

    private static Cell cellValue(String text) {
        return new Cell().add(new Paragraph(text));
    }

    private static void header(Table table, String text) {
        table.addHeaderCell(
                new Cell()
                        .add(new Paragraph(text).setBold())
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(TextAlignment.CENTER)
        );
    }
}
