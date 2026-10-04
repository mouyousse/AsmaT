package Asmat;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.awt.Color;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


public class pdf {

    private PDDocument pdf;
    private PDPageContentStream contentStream;
    private float margin = 45;
    private float yPosition;
    private PDType1Font fontBold = PDType1Font.HELVETICA_BOLD;
    private PDType1Font font = PDType1Font.HELVETICA;
    private PDType1Font fontItalic = PDType1Font.HELVETICA_OBLIQUE;

    // ===== PALETTE DE COULEURS (joyeuse / enfantine) =====
    private static final Color TEAL = new Color(0, 150, 136);
    private static final Color GOLD = new Color(255, 193, 7);
    private static final Color PINK = new Color(236, 64, 122);
    private static final Color ORANGE = new Color(255, 152, 0);
    private static final Color BLUE = new Color(66, 165, 245);
    private static final Color PURPLE = new Color(126, 87, 194);
    private static final Color GREEN = new Color(67, 160, 71);

    private static final Color LIGHT_BG = new Color(240, 248, 250);     // fond colonne libellé
    private static final Color ZEBRA = new Color(232, 247, 245);        // ligne alternée pastel
    private static final Color BORDER_GRAY = new Color(208, 215, 222);
    private static final Color TEXT_DARK = new Color(35, 40, 50);
    private static final Color TEXT_GRAY = new Color(120, 128, 140);
    private static final Color SUCCESS_BG = new Color(221, 239, 223);
    private static final Color SUCCESS_TEXT = new Color(27, 94, 32);

    private String enTeteLabel = "";

    public pdf(Enfant enfant, Fp fp, File outputFile) {
        try {
            pdf = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            pdf.addPage(page);
            InputStream is = getClass().getResourceAsStream("/Asmat/images/fp.png");

            PDImageXObject image = PDImageXObject.createFromByteArray(
                    pdf,
                    is.readAllBytes(),
                    "fp"
            );

            contentStream = new PDPageContentStream(pdf, page);

            ConfigurationEnfant c = enfant.getConfiguration();
            enTeteLabel = "Fiche de présence - " + IntEnMois(fp.getMonth()) + " " + fp.getYear();

            // ===== BANDEAU D'EN-TÊTE =====
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            float bannerHeight = 175f;

            fillRect(0, pageHeight - bannerHeight, pageWidth, bannerHeight, TEAL);

            float imgW = 170, imgH = 150;
            contentStream.drawImage(image, (pageWidth - imgW) / 2, pageHeight - 150, imgW, imgH);

            writeCenteredText(page, IntEnMois(fp.getMonth()).toUpperCase() + " " + fp.getYear(), 11, false, new Color(224, 247, 244), pageHeight - 165);

            yPosition = pageHeight - bannerHeight - 18;

            // ===== ASSISTANTE MATERNELLE =====
            AsmatInfo asmat = AsmatInfo.getInstance();
            yPosition = drawSectionTitle("Assistante maternelle", TEAL);
            String[][] assmatInfo = {
                    {"Nom", asmat.getNom()},
                    {"Prénom", asmat.getPrenom()},
                    {"Adresse", asmat.getAdresse()},
                    {"Téléphone", asmat.getTelephone()},
                    {"N° salarié", asmat.getNumeroSalarie()}
            };
            yPosition = drawTwoColumnTable(assmatInfo);

            // ===== IDENTITÉ =====
            yPosition = drawSectionTitle("Informations générales", GOLD);
            String[][] identite = {
                    {"Nom enfant", c.getNom()},
                    {"Prénom enfant", c.getPrenom()},
                    {"Date de naissance", formatDate(c.getDateNaissance())}
            };
            yPosition = drawTwoColumnTable(identite);

            // ===== PARENT RÉFÉRENT =====
            yPosition = drawSectionTitle("Parent référent", PINK);
            String[][] parentReferent = {
                    {"Nom", c.isPereReferent() ? c.getPere() : c.getMere()},
                    {"Adresse", c.isPereReferent() ? c.getAdressePere() : c.getAdresseMere()},
                    {"Téléphone", c.isPereReferent() ? c.getTelpere() : c.getTelmere()}
            };
            yPosition = drawTwoColumnTable(parentReferent);

            // ===== REPAS =====
            yPosition = drawSectionTitle("Repas", ORANGE);
            String[][] repas = {
                    {"Repas fourni", c.isRepasFourni() ? "Oui" : "Non"},
                    {"Prix du repas", c.isRepasFourni() ? fmt(c.getRepasPrix()) + " €" : "-"}
            };
            yPosition = drawTwoColumnTable(repas);

            // ===== CONTRAT =====
            yPosition = drawSectionTitle("Contrat", BLUE);
            String[][] contrat = {
                    {"Type de contrat", c.getTypeContrat()},
                    {"Durée du contrat", c.getDureeContrat()},
                    {"Semaines / an", String.valueOf(c.getSemaines())},
                    {"Heures / semaine", fmt(c.getNbHeuresSemaine())},
                    {"Taux horaire net", fmt(c.getTauxHoraireNet()) + " €"},
                    {"Mensualisation", fmt(c.getMensualisation()) + " €"},
                    {"Majoration", fmt(c.getMajoration())},
                    {"Majoration février", fmt(c.getMajorationfevrier())}
            };
            yPosition = drawTwoColumnTable(contrat);

            // ===== TABLEAU JOURNALIER (page 2) =====
            forceNewPage();
            yPosition = drawSectionTitle("Détail journalier", PURPLE);
            String[] headers = {"Jour", "Arrivée", "Départ", "Heures", "Ajustement", "Repas", "Entretien", "Commentaire"};
            float[] colWidths = {60, 48, 48, 48, 55, 42, 48, 156};
            yPosition = drawDailyTable(fp, headers, colWidths);

            // ===== RÉCAPITULATIF MENSUEL =====
            yPosition -= 14;
            yPosition = drawSectionTitle("Récapitulatif mensuel", GREEN);
            double heuresSuppReelles = calculerHeuresSuppReelles(fp);
            String[][] recapData;
            if (fp.ismoiscomplet()) {
                double heuresMensualisees = calculerHeuresMensualisees(c);

                recapData = new String[][] {
                        {"Jours mensualisés", String.valueOf(c.getJourmois())},
                        {"Jours réels", String.valueOf(fp.getNombreDeJoursActivites())},
                        {"Heures mensualisées", fmt(heuresMensualisees)},
                        {"Heures supplémentaires", fmt(heuresSuppReelles)},
                        {"Total repas", fmt(fp.getRepas())},
                        {"Indemnités entretien", fmt(fp.getIndemnitesEntretien())},
                        {"Mensualité", fmt(c.getMensualisation()) + " €"},
                        {"Salaire net", fmt(fp.getSalaire())}
                };
            } else {
                // Mois incomplet : on affiche uniquement les valeurs réelles, pas de mensualisation
                recapData = new String[][] {
                        {"Jours réels", String.valueOf(fp.getNombreDeJoursActivites())},
                        {"Heures réelles", fmt(fp.getHeures())},
                        {"Heures supplémentaires", fmt(heuresSuppReelles)},
                        {"Total repas", fmt(fp.getRepas())},
                        {"Indemnités entretien", fmt(fp.getIndemnitesEntretien())},
                        {"Salaire net", fmt(fp.getSalaire())}
                };
            }
            yPosition = drawTwoColumnTable(recapData, true);

            contentStream.close();
            addFootersAndPageNumbers();
            pdf.save(outputFile);
            pdf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== CALCULS =====

    private double calculerHeuresMensualisees(ConfigurationEnfant c) {
        double heuresSemaine = c.getNbHeuresSemaine();
        double semaines = c.getSemaines();
        return heuresSemaine * semaines / 12.0;
    }

    /**
     * Heures supplémentaires réelles du mois : somme des ajustements saisis sur les présences,
     * PAS la valeur contractuelle fixe hrssupp de ConfigurationEnfant.
     */
    private double calculerHeuresSuppReelles(Fp fp) {
        double total = 0;
        for (Presence p : fp.getJours()) {
            LocalDate date = LocalDate.parse(p.getDay());
            if (date.getMonthValue() == fp.getMonth() && date.getYear() == fp.getYear()) {
                total += p.getAjustement();
            }
        }
        return total;
    }


    private String formatDate(LocalDate d) {
        return d == null ? "" : d.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Arrondit et formate un nombre à 2 décimales (format FR, virgule).
     */
    private String fmt(double value) {
        return String.format(Locale.FRANCE, "%.2f", value);
    }

    // ===== PAGINATION =====

    private void checkNewPage(float rowHeight) throws Exception {
        if (yPosition - rowHeight < margin + 15) {
            contentStream.close();
            PDPage newPage = new PDPage(PDRectangle.A4);
            pdf.addPage(newPage);
            contentStream = new PDPageContentStream(pdf, newPage);
            drawMiniHeader(newPage);
        }
    }

    private void forceNewPage() throws Exception {
        contentStream.close();
        PDPage newPage = new PDPage(PDRectangle.A4);
        pdf.addPage(newPage);
        contentStream = new PDPageContentStream(pdf, newPage);
        drawMiniHeader(newPage);
    }

    /**
     * Petit bandeau de continuité en haut des pages suivantes, avec une icône nuage,
     * pour garder l'ambiance colorée sur tout le document.
     */
    private void drawMiniHeader(PDPage page) throws Exception {
        float pw = page.getMediaBox().getWidth();
        float ph = page.getMediaBox().getHeight();
        fillRect(0, ph - 24, pw, 24, TEAL);
        drawText(enTeteLabel, margin + 10, ph - 16, fontBold, 9.5f, Color.WHITE);
        yPosition = ph - 24 - 14;
    }

    // ===== TITRES DE SECTION =====

    private float drawSectionTitle(String title, Color accent) throws Exception {
        yPosition -= 17;
        checkNewPage(17);
        fillRect(margin, yPosition - 11, 4, 13, accent);
        drawText(title.toUpperCase(Locale.FRENCH), margin + 12, yPosition - 8, fontBold, 11.5f, accent);
        yPosition -= 15;
        contentStream.setStrokingColor(accent);
        contentStream.setLineWidth(1.1f);
        contentStream.moveTo(margin, yPosition);
        contentStream.lineTo(margin + 505, yPosition);
        contentStream.stroke();
        yPosition -= 7;
        return yPosition;
    }

    // ===== TEXTE CENTRÉ (bandeau de garde) =====

    private void writeCenteredText(PDPage page, String text, float fontSize, boolean bold, Color color, float y) throws Exception {
        PDType1Font f = bold ? fontBold : font;
        float textWidth = f.getStringWidth(text) / 1000 * fontSize;
        float startX = (page.getMediaBox().getWidth() - textWidth) / 2;
        drawText(text, startX, y, f, fontSize, color);
    }

    // ===== PRIMITIVES DE DESSIN =====

    private void fillRect(float x, float y, float w, float h, Color color) throws Exception {
        contentStream.setNonStrokingColor(color);
        contentStream.addRect(x, y, w, h);
        contentStream.fill();
    }

    private void strokeRect(float x, float y, float w, float h, Color color, float lineWidth) throws Exception {
        contentStream.setStrokingColor(color);
        contentStream.setLineWidth(lineWidth);
        contentStream.addRect(x, y, w, h);
        contentStream.stroke();
    }

    private void drawText(String text, float x, float y, PDType1Font f, float size, Color color) throws Exception {
        contentStream.beginText();
        contentStream.setFont(f, size);
        contentStream.setNonStrokingColor(color);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text == null ? "" : text);
        contentStream.endText();
    }

    // ===== TABLEAUX DEUX COLONNES (style carte) =====

    private float drawTwoColumnTable(String[][] data) throws Exception {
        return drawTwoColumnTable(data, false);
    }

    private float drawTwoColumnTable(String[][] data, boolean highlightLastRow) throws Exception {
        float rowHeight = 17f;
        float cellMargin = 7f;
        float tableWidth = 505f;
        float labelWidth = 190f;
        float valueWidth = tableWidth - labelWidth;

        for (int idx = 0; idx < data.length; idx++) {
            String[] row = data[idx];
            boolean isLast = highlightLastRow && idx == data.length - 1;
            float rh = isLast ? 22f : rowHeight;
            checkNewPage(rh);

            if (isLast) {
                fillRect(margin, yPosition - rh, tableWidth, rh, SUCCESS_BG);
                strokeRect(margin, yPosition - rh, tableWidth, rh, SUCCESS_TEXT, 1.2f);
            } else {
                fillRect(margin, yPosition - rh, labelWidth, rh, LIGHT_BG);
                fillRect(margin + labelWidth, yPosition - rh, valueWidth, rh, Color.WHITE);
                strokeRect(margin, yPosition - rh, labelWidth, rh, BORDER_GRAY, 0.5f);
                strokeRect(margin + labelWidth, yPosition - rh, valueWidth, rh, BORDER_GRAY, 0.5f);
            }

            float fontSize = isLast ? 12.5f : 10f;
            Color textColor = isLast ? SUCCESS_TEXT : TEXT_DARK;

            drawText(row[0], margin + cellMargin, yPosition - rh + 6.5f, fontBold, fontSize, textColor);
            drawText(row[1], margin + labelWidth + cellMargin, yPosition - rh + 6.5f, isLast ? fontBold : font, fontSize, textColor);

            yPosition -= rh;
        }

        return yPosition;
    }

    // ===== TABLEAU JOURNALIER (zébré, pastel) =====

    private float drawDailyTable(Fp fp, String[] headers, float[] colWidths) throws Exception {
        float rowHeight = 15f;
        float headerHeight = 17f;
        float cellMargin = 4f;
        float tableWidth = sum(colWidths);

        checkNewPage(headerHeight);
        fillRect(margin, yPosition - headerHeight, tableWidth, headerHeight, TEAL);
        float nextX = margin;
        for (int i = 0; i < headers.length; i++) {
            drawText(headers[i], nextX + cellMargin, yPosition - headerHeight + 5, fontBold, 8.5f, Color.WHITE);
            nextX += colWidths[i];
        }
        yPosition -= headerHeight;

        java.time.YearMonth yearMonth = java.time.YearMonth.of(fp.getYear(), fp.getMonth());
        int totalJoursMois = yearMonth.lengthOfMonth();
        java.util.List<Presence> joursAffiches = new java.util.ArrayList<>();
        for (int jourNum = 1; jourNum <= totalJoursMois; jourNum++) {
            LocalDate date = LocalDate.of(fp.getYear(), fp.getMonth(), jourNum);
            joursAffiches.add(fp.getOrCreatePresence(date));
        }

        for (Presence p : joursAffiches) {
            LocalDate date = LocalDate.parse(p.getDay());

            String jour = date.getDayOfWeek()
                    .getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH);
            int numero = date.getDayOfMonth();
            String[] values = {
                    jour + " " + numero,
                    fmt(p.getHeureArrive()),
                    fmt(p.getHeureDepart()),
                    fmt(p.getTotalHeures()),
                    fmt(p.getAjustement()),
                    fmt(p.getIndRepas()),
                    fmt(p.getIndEntretien()),
                    p.getCommentaire() == null ? "" : p.getCommentaire()
            };
            checkNewPage(rowHeight);

            boolean fini = p.isFinis() && (p.getHeureArrive() != 0 || p.getHeureDepart() != 0);
            Color rowBg = fini ? Color.WHITE : new Color(238, 238, 238);
            Color rowText = fini ? TEXT_DARK : TEXT_GRAY;
            fillRect(margin, yPosition - rowHeight, tableWidth, rowHeight, rowBg);

            nextX = margin;
            for (int i = 0; i < values.length; i++) {
                strokeRect(nextX, yPosition - rowHeight, colWidths[i], rowHeight, BORDER_GRAY, 0.4f);
                drawText(values[i], nextX + cellMargin, yPosition - rowHeight + 4.5f, font, 8f, rowText);
                nextX += colWidths[i];
            }
            yPosition -= rowHeight;
        }
        return yPosition;
    }

    // ===== PIED DE PAGE =====

    private void addFootersAndPageNumbers() throws Exception {
        int total = pdf.getNumberOfPages();
        String generatedOn = "Généré le " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        for (int i = 0; i < total; i++) {
            PDPage p = pdf.getPage(i);
            PDPageContentStream cs = new PDPageContentStream(pdf, p, PDPageContentStream.AppendMode.APPEND, true, true);
            float pw = p.getMediaBox().getWidth();

            cs.setStrokingColor(BORDER_GRAY);
            cs.setLineWidth(0.5f);
            cs.moveTo(margin, margin - 8);
            cs.lineTo(pw - margin, margin - 8);
            cs.stroke();

            cs.beginText();
            cs.setFont(fontItalic, 8);
            cs.setNonStrokingColor(TEXT_GRAY);
            cs.newLineAtOffset(margin, margin - 20);
            cs.showText(generatedOn);
            cs.endText();

            String pageLabel = "Page " + (i + 1) + " / " + total;
            float textWidth = font.getStringWidth(pageLabel) / 1000 * 8;
            cs.beginText();
            cs.setFont(font, 8);
            cs.setNonStrokingColor(TEXT_GRAY);
            cs.newLineAtOffset(pw - margin - textWidth, margin - 20);
            cs.showText(pageLabel);
            cs.endText();

            cs.close();
        }
    }

    /**
     * method pour retourner le mois en francais qui correspon au int exemple 3 -> mars
     * @param mois le mois a retourne
     * @return le mois
     */
    private String IntEnMois(Integer mois) {
        switch (mois) {
            case 1: return "janvier";
            case 2: return "février";
            case 3: return "mars";
            case 4: return "avril";
            case 5: return "mai";
            case 6: return "juin";
            case 7: return "juillet";
            case 8: return "août";
            case 9: return "septembre";
            case 10:return "octobre";
            case 11:return "novembre";
            case 12:return "décembre";
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