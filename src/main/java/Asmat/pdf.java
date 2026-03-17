package Asmat;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.awt.Color;
import java.io.File;
import java.time.format.DateTimeFormatter;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


public class pdf {

    private PDDocument pdf;
    private PDPageContentStream contentStream;
    private float margin = 50;
    private float yPosition;
    private PDType1Font fontBold = PDType1Font.HELVETICA_BOLD;
    private PDType1Font font = PDType1Font.HELVETICA;

    public pdf(Enfant enfant, Fp fp, File outputFile) {
        try {
            pdf = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            pdf.addPage(page);
            PDImageXObject image =
                    PDImageXObject.createFromFile("C:\\Users\\amaya\\IdeaProjects\\AsmaT\\src\\main\\resources\\Asmat\\images\\fp.png", pdf);


            contentStream = new PDPageContentStream(pdf, page);
            contentStream.drawImage(
                    image,
                    page.getMediaBox().getWidth()/2-100,
                    page.getMediaBox().getHeight()-160,
                    200,
                    160
            );

            yPosition = page.getMediaBox().getHeight() - margin;

            ConfigurationEnfant c = enfant.getConfiguration();

            // ===== TITRE =====
            yPosition -= 90;
            writeCenteredText(moisEnFrancais(fp.getMonth()).toUpperCase() + " " + fp.getYear(), 12);
            yPosition -= 20;

            // ===== IDENTITÉ =====
            yPosition = drawSectionTitle("Informations générales");
            String[][] identite = {
                    {"Nom enfant", c.getNom()},
                    {"Prénom enfant", c.getPrenom()},
                    {"Date de naissance", formatDate(c.getDateNaissance())}
            };
            yPosition = drawTwoColumnTable(identite);

            // ===== EMPLOYEUR =====
            yPosition = drawSectionTitle("Employeur");
            String[][] employeur = {
                    {"Nom employeur", c.isPereReferent() ? c.getPere() : c.getMere()},
                    {"Adresse", c.isPereReferent() ? c.getAdressePere() : c.getAdresseMere()}
            };
            yPosition = drawTwoColumnTable(employeur);

            // ===== CONTRAT =====
            yPosition = drawSectionTitle("Contrat");
            String[][] contrat = {
                    {"Type de contrat", c.getTypeContrat()},
                    {"Durée du contrat", c.getDureeContrat()},
                    {"Semaines / an", String.valueOf(c.getSemaines())},
                    {"Heures / semaine", String.valueOf(c.getNbHeuresSemaine())},
                    {"Taux horaire net", c.getTauxHoraireNet() + " €"},
                    {"Mensualisation", String.valueOf(c.getMensualisation())},
                    {"Majoration", String.valueOf(c.getMajoration())},
                    {"Majoration février", String.valueOf(c.getMajorationfevrier())}
            };
            yPosition = drawTwoColumnTable(contrat);

            // ===== TABLEAU JOURNALIER =====
            yPosition = drawSectionTitle("Détail journalier");
            String[] headers = {"Jour", "Heures", "Repas", "Entretien", "Commentaire"};
            float[] colWidths = {50, 60, 60, 70, 250};
            yPosition = drawDailyTable(fp, headers, colWidths);

            // ===== RÉCAPITULATIF MENSUEL =====
            yPosition -= 20;
            yPosition = drawSectionTitle("Récapitulatif mensuel");
            String[][] recapData = {
                    {"Jours d'activité", String.valueOf(fp.getNombredejoursactivites())},
                    {"Total heures", String.valueOf(fp.getHeures())},
                    {"Total repas", String.valueOf(fp.getRepas())},
                    {"Indemnités entretien", String.valueOf(fp.getIndmenitesEntretien())},
                    {"Salaire net", String.valueOf(fp.getSalaireNet())}
            };
            yPosition = drawTwoColumnTable(recapData, true);

            contentStream.close();
            pdf.save(outputFile);
            pdf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatDate(java.time.LocalDate d) {
        return d == null ? "" : d.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private void checkNewPage(float rowHeight) throws Exception {
        if (yPosition - rowHeight < margin) {
            contentStream.close();
            PDPage newPage = new PDPage(PDRectangle.A4);
            pdf.addPage(newPage);
            contentStream = new PDPageContentStream(pdf, newPage);
            yPosition = newPage.getMediaBox().getHeight() - margin;
        }
    }

    private float drawSectionTitle(String title) throws Exception {
        yPosition -= 20;
        checkNewPage(20);
        contentStream.beginText();
        contentStream.setFont(fontBold, 14);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(title);
        contentStream.endText();
        yPosition -= 15;
        return yPosition;
    }

    private void writeCenteredText(String text, int fontSize) throws Exception {
        PDRectangle pageSize = pdf.getPage(pdf.getNumberOfPages() - 1).getMediaBox();
        float titleWidth = fontBold.getStringWidth(text) / 1000 * fontSize;
        float startX = (pageSize.getWidth() - titleWidth) / 2;
        contentStream.beginText();
        contentStream.setFont(fontBold, fontSize);
        contentStream.newLineAtOffset(startX, yPosition);
        contentStream.showText(text);
        contentStream.endText();
    }

    private float drawTwoColumnTable(String[][] data) throws Exception {
        return drawTwoColumnTable(data, false);
    }

    private float drawTwoColumnTable(String[][] data, boolean highlightSecondColumn) throws Exception {
        float rowHeight = 20;
        float cellMargin = 5;
        float tableWidth = 500;
        float colWidth = tableWidth / 2;

        for (String[] row : data) {
            checkNewPage(rowHeight);

            float nextX = margin;
            for (int i = 0; i < row.length; i++) {
                // fond vert pour le salaire net
                if (highlightSecondColumn && row[0].equals("Salaire net") && i == 1) {
                    contentStream.setNonStrokingColor(Color.GREEN);
                    contentStream.addRect(nextX, yPosition - rowHeight, colWidth, rowHeight);
                    contentStream.fill();
                    contentStream.setNonStrokingColor(Color.BLACK);
                }

                contentStream.addRect(nextX, yPosition - rowHeight, colWidth, rowHeight);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(i == 0 ? fontBold : font, 12);
                contentStream.newLineAtOffset(nextX + cellMargin, yPosition - 15);
                contentStream.showText(row[i] == null ? "" : row[i]);
                contentStream.endText();

                nextX += colWidth;
            }
            yPosition -= rowHeight;
        }

        return yPosition;
    }

    private float drawDailyTable(Fp fp, String[] headers, float[] colWidths) throws Exception {
        float rowHeight = 20;
        float cellMargin = 5;

        // HEADER
        checkNewPage(rowHeight);
        float nextX = margin;
        contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
        contentStream.addRect(margin, yPosition - rowHeight, sum(colWidths), rowHeight);
        contentStream.fill();
        contentStream.setNonStrokingColor(Color.BLACK);

        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.setFont(fontBold, 12);
            contentStream.newLineAtOffset(nextX + cellMargin, yPosition - 15);
            contentStream.showText(headers[i] == null ? "" : headers[i]);
            contentStream.endText();
            nextX += colWidths[i];
        }
        yPosition -= rowHeight;

        // LIGNES
        for (int semaines : fp.getSemaines().keySet()) {
            for (Presence p : fp.getSemaines().get(semaines)) {
                String[] values = {
                        String.valueOf(p.getDay()),
                        String.valueOf(p.getTotalHeures()),
                        String.valueOf(p.getIndRepas()),
                        String.valueOf(p.getIndEntretien()),
                        p.getCommentaire() == null ? "" : p.getCommentaire()
                };
                checkNewPage(rowHeight);
                nextX = margin;
                for (int i = 0; i < values.length; i++) {
                    contentStream.addRect(nextX, yPosition - rowHeight, colWidths[i], rowHeight);
                    contentStream.stroke();

                    contentStream.beginText();
                    contentStream.setFont(font, 10);
                    contentStream.newLineAtOffset(nextX + cellMargin, yPosition - 15);
                    contentStream.showText(values[i] == null ? "" : values[i]);
                    contentStream.endText();

                    nextX += colWidths[i];
                }
                yPosition -= rowHeight;
            }
        }
        return yPosition;
    }
    private String moisEnFrancais(String mois) {
        switch (mois) {
            case "JANUARY": return "janvier";
            case "FEBRUARY": return "février";
            case "MARCH": return "mars";
            case "APRIL": return "avril";
            case "MAY": return "mai";
            case "JUNE": return "juin";
            case "JULY": return "juillet";
            case "AUGUST": return "août";
            case "SEPTEMBER": return "septembre";
            case "OCTOBER": return "octobre";
            case "NOVEMBER": return "novembre";
            case "DECEMBER": return "décembre";
            default:
                throw new IllegalArgumentException("Mois invalide: " + mois);
        }
    }

    private float sum(float[] arr) {
        float s = 0;
        for (float f : arr) s += f;
        return s;
    }
}
