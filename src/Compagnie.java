import java.io.*;
import javax.swing.*;
import java.util.*;

public class Compagnie {
    public static final int MAX_PLACES = 340;
    public static int nbVols;

    private BufferedReader tmpVolsRead;
    private BufferedWriter tmpVolsWrite;
    private ObjectInputStream tmpVolsReadObj;
    private ObjectOutputStream tmpVolsWriteObj;

    private String nomCompagnie;
    private int maxVols;
    private int nbVolsActifs = 0;
    private ArrayList<Vol> tabVols;
    private JTextArea sortie = new JTextArea();

    // Constucteurs

    Compagnie(String nomCompagnie, int maxVols) throws IOException {
        this.nomCompagnie = nomCompagnie;
        this.maxVols = maxVols;
        this.tabVols = new ArrayList<>(this.maxVols);
        chargerVols();
    }

    public String getNomCompagnie() {
        return this.nomCompagnie;
    }

    public static void message(String msg) {
        JOptionPane.showMessageDialog(null, msg, "MESSAGES", JOptionPane.PLAIN_MESSAGE);
    }

    public boolean validerFormatDate(String date) {
        int taille = date.length();
        if (taille == 10) {
            for (int i = 0; i < taille; i++) {
                char currentChar = date.charAt(i);
                if (i == 2 || i == 5) {
                    if (currentChar != '/') {
                        return false;
                    }
                } else {
                    if (currentChar < '0' || currentChar > '9') {
                        return false;
                    }
                }
            }

            // Extract day, month, and year values from the date string
            int jour = Integer.parseInt(date.substring(0, 2));
            int mois = Integer.parseInt(date.substring(3, 5));
            int annee = Integer.parseInt(date.substring(6, 10));

            // Validate month
            if (mois < 1 || mois > 12) {
                return false;
            }

            // Validate day based on the month and year
            int[] joursParMois = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

            // Check for leap year
            if (annee % 4 == 0 && ((annee % 100 != 0) || (annee % 400 == 0))) {
                joursParMois[1] = 29;
            }

            if (jour < 1 || jour > joursParMois[mois - 1]) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    public void loadSelectedCompanyData(String nomCompagnie) {
        this.nomCompagnie = nomCompagnie;

        String objFileName = nomCompagnie + ".obj";
        String txtFileName = nomCompagnie + ".txt";
        File objFile = new File(objFileName);

        if (objFile.exists()) {
            chargerVolsObj(objFileName);
        } else {
            chargerVols(txtFileName);
        }
    }

    public void chargerVolsObj(String fileName) {

    }

    public void chargerVols(String fileName) {

    }
    // public boolean validerFormatDate(String date) {
    // int taille = date.length();
    // if (taille == 10) {
    // for (int i = 0; i < taille; i++) {
    // switch (i) {
    // case 2:
    // case 5:
    // if (date.charAt(i) != '/') {
    // return false;
    // }
    // break;
    // case 0:
    // case 1:
    // case 3:
    // case 4:
    // case 6:
    // case 7:
    // case 8:
    // case 9:
    // if (date.charAt(i) < '0' || date.charAt(i) > '9') {
    // return false;
    // }
    // break;
    // }
    // }
    // return true;
    // } else {
    // return false;
    // }
    // }

    public void ajouterVol() {
        boolean dateValide = false;
        String elems[] = new String[3];
        if (tabVols.size() == maxVols) {
            message("Impossible d'ajouter un nouveau vol la capacité maximale est de " + maxVols);
        } else { // On peut ajouter un nouveau vol
            String numVol = JOptionPane.showInputDialog(null, "Entrez le numéro du vol", "AJOUT D'UN VOL",
                    JOptionPane.PLAIN_MESSAGE);
            int pos = rechercherVol(numVol);
            if (pos == -1) { // Ce vol de numéro numVol n'esite pas, alors on peut l'ajouter
                String destination = JOptionPane.showInputDialog(null, "Entrez la destination", "AJOUT D'UN VOL",
                        JOptionPane.PLAIN_MESSAGE);
                while (!dateValide) {
                    String date = JOptionPane.showInputDialog(null, "Entrez la date dans le format JJ/MM/AAAA",
                            "AJOUT D'UN VOL",
                            JOptionPane.PLAIN_MESSAGE);
                    dateValide = validerFormatDate(date);
                    if (dateValide) {
                        elems = date.split("/");
                        int jour = Integer.parseInt(elems[0]);
                        int mois = Integer.parseInt(elems[1]);
                        int annee = Integer.parseInt(elems[2]);
                        tabVols.add(new Vol(numVol, destination, new Date(jour, mois, annee), 0));
                        nbVolsActifs++;
                    } else {
                        message("Format de date invalide, vous devez taper JJ/MM/AAAA. Merci.");
                    }
                }
            } else {
                message("Ce numéro " + numVol + " de vol existe déjà.");
            }
        }
    }

    public void changerDateVol() {
        boolean dateValide = false;
        String elems[] = new String[3];
        String numVol = JOptionPane.showInputDialog(null, "Entrez le numéro du vol", "MODIFIER LA DATE D'UN VOL",
                JOptionPane.PLAIN_MESSAGE);
        int pos = rechercherVol(numVol);
        if (pos != -1) {// Trouvé
            while (!dateValide) {
                String date = JOptionPane.showInputDialog(null,
                        "La date actuelle du vol " + numVol + " est " + tabVols.get(pos).getDateVol() // the arraylist
                                                                                                      // equivalent is
                                                                                                      // tabVols.get(pos).getDateVol()
                                + "\nEntrez la nouvelle date dans le format JJ/MM/AAAA",
                        "MODIFIER LA DATE D'UN VOL",
                        JOptionPane.PLAIN_MESSAGE);
                dateValide = validerFormatDate(date);
                if (dateValide) {
                    elems = date.split("/");
                    int jour = Integer.parseInt(elems[0]);
                    int mois = Integer.parseInt(elems[1]);
                    int annee = Integer.parseInt(elems[2]);
                    tabVols.get(pos).setDateVol(new Date(jour, mois, annee));
                } else {
                    message("Format de date invalide, vous devez taper JJ/MM/AAAA. Merci.");
                }
            }
        } else {
            message("Vol " + numVol + " introuvable!");
        }
    }

    public void enregistrerVolsObj() throws IOException {
        try {
            tmpVolsWriteObj = new ObjectOutputStream(new FileOutputStream("src/donnees/" + this.nomCompagnie + ".obj"));
            tmpVolsWriteObj.writeObject(tabVols);
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. Vérifiez vos données.");
        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors de la sauvegarde du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            tmpVolsWriteObj.close();
        }
    }

    public void enregistrerVols() throws IOException {
        String ligne;
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/donnees/" + this.nomCompagnie + ".txt"));
        for (Vol unVol : this.tabVols) {
            if (unVol != null) {
                Date dtv = unVol.getDateVol();
                ligne = unVol.getNumVol() + ";" + unVol.getDestination() + ";";
                ligne += dtv.getJour() + ";" + dtv.getMois() + ";" + dtv.getAnnee() + ";";
                ligne += unVol.getNbReservations();
                bw.write(ligne);
                bw.newLine();
            } else {
                break;
            }
        }
        bw.close();
    }

    public void trierVols() {
        Vol tmp;
        for (int i = 0; i < tabVols.size() - 1; i++) {
            for (int j = (i + 1); j < tabVols.size(); j++) {
                if (tabVols.get(j).getNumVol().compareTo(tabVols.get(i).getNumVol()) < 0) {
                    tmp = tabVols.get(i);
                    tabVols.set(i, tabVols.get(j)); // the arraylist equivalent is tabVols.set(i, tabVols.get(j))
                    tabVols.set(j, tmp); // the arraylist equivalent is tabVols.set(j, tmp)
                }
            }
        }
    }

    // private void decalerVols(int pos) {
    // while (tabVols.get(pos + 1) != null) { // the arraylist equivalent is
    // tabVols.get(pos + 1) != null
    // tabVols.set(pos, tabVols.get(pos + 1)); // the arraylist equivalent is
    // tabVols.set(pos) = tabVols.get(pos +
    // // 1)
    // ++pos;
    // }
    // tabVols.set(pos, tabVols.get(pos + 1));
    // }

    private int rechercherVol(String numVol) {
        boolean trouve = false;
        int i = 0;
        while (i < tabVols.size() && !trouve) {
            if (tabVols.get(i).getNumVol().equalsIgnoreCase(numVol)) {
                trouve = true;
            } else {
                ++i;
            }
        }
        if (trouve) {
            return i;
        } else {
            return -1;
        }
    }

    public void reserverVol() {
        String numVol = JOptionPane.showInputDialog(null, "Entrez le numéro du vol à réserver", "RÉSERVATION D'UN VOL",
                JOptionPane.PLAIN_MESSAGE);
        int pos = rechercherVol(numVol);
        int placesAReserver;
        if (pos != -1) {// Trouvé
            int placesDisponibles = MAX_PLACES - tabVols.get(pos).getNbReservations();
            do {
                placesAReserver = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Le nombre de places disponibles pour le vol " + numVol + " est " + placesDisponibles
                                + "\nEntrez le nombre de places à réserver ou 0 (zéro) pour quitter",
                        "RÉSERVATION D'UN VOL",
                        JOptionPane.PLAIN_MESSAGE));
                if (placesAReserver > 0) {
                    if (placesAReserver <= placesDisponibles) {
                        tabVols.get(pos).setNbReservations(tabVols.get(pos).getNbReservations() + placesAReserver);
                        placesAReserver = 0;
                    } else {
                        message("Le nombre de places entrées dépasse le nombre de places disponibles");
                    }
                }
            } while (placesAReserver > 0);
        } else {
            System.out.println("Vol " + numVol + " introuvable!");
        }
    }

    public void supprimerVol() {
        String numVol = JOptionPane.showInputDialog(null, "Entrez le numéro du vol", "SUPPRESSION D'UN VOL",
                JOptionPane.PLAIN_MESSAGE);
        int pos = rechercherVol(numVol);
        if (pos != -1) { // Trouvé
            tabVols.remove(pos);
            --nbVolsActifs;
        } else {
            System.out.println("Vol " + numVol + " introuvable!");
        }
    }

    // public void supprimerVol() {
    // String numVol = JOptionPane.showInputDialog(null, "Entrez le numéro du vol",
    // "SUPPRESSION D'UN VOL",
    // JOptionPane.PLAIN_MESSAGE);
    // int pos = rechercherVol(numVol);
    // if (pos != -1) {// Trouvé
    // tabVols.set(pos, null);
    // decalerVols(pos);
    // --nbVolsActifs;
    // } else {
    // System.out.println("Vol " + numVol + " introuvable!");
    // }
    // }

    public void afficherEntete(String suiteEntete) {
        // sortie.setFont(new Font("monospace", Font.PLAIN, 12));
        sortie.append("\tVOLS POUR LA COMPAGNIE : " + this.nomCompagnie + "\n");
        sortie.append("Numéro\tDestination\t\tDate\tRéservations\n");
        sortie.append(suiteEntete);
    }

    public void listerVols() {
        sortie.setText("");
        nbVols = 0;
        afficherEntete("\n");
        tabVols.forEach((unVol) -> {
            Vol leVol;
            if (unVol instanceof Vol) {
                leVol = (Vol) unVol;
                sortie.append(leVol.toString() + "\n");
                nbVols++;
            }
        });
        sortie.append("\n Nombre de vols = " + nbVols);
    }

    public void listerTout() {
        tabVols.sort(null);
        listerVols();
    }

    public JTextArea getSortie() {
        return this.sortie;
    }

    // public void listerVols() {
    // System.out.println("\tVOLS POUR LA COMPAGNIE : " + this.nomCompagnie + "\n");
    // System.out.println("Numéro\tDestination\t\tDate\tRéservations\n");
    // trierVols();
    // for (Vol unVol : this.tabVols) {
    // if (unVol != null) {
    // System.out.println(unVol);
    // } else {
    // break;
    // }
    // }
    // }

    public void chargerVolsObj() throws Exception {
        try {
            tmpVolsReadObj = new ObjectInputStream(new FileInputStream("src/donnees/" + this.nomCompagnie + ".obj"));
            tabVols = (ArrayList<Vol>) tmpVolsReadObj.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier obj introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. Vérifiez vos données.");
        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            tmpVolsReadObj.close();
        }
    }

    public void chargerVols() throws IOException {
        String elems[] = new String[6], ligne;
        BufferedReader br = new BufferedReader(new FileReader("src/donnees/" + this.nomCompagnie + ".txt"));
        // 14567;Toronto;12;04;2002;167
        while ((ligne = br.readLine()) != null && this.tabVols.size() < this.maxVols) {
            elems = ligne.split(";");
            tabVols.add(new Vol(elems[0], elems[1],
                    new Date(Integer.parseInt(elems[2]), Integer.parseInt(elems[3]), Integer.parseInt(elems[4])),
                    Integer.parseInt(elems[5])));
            this.nbVolsActifs++;
        }
        br.close();
    }
    // toString
}
