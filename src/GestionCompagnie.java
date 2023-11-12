import java.awt.Font;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class GestionCompagnie {
    static JTextArea sortie;

    public static int menu(String nomCompagnie) {
        String choix;
        String texteMenu = "\tGESTION DES VOLS\n\n1-Liste des vols\n2-Ajout d'un vol\n3-Retrait d'un vol\n4-Modification de la date de départ\n5-Réservation d'un vol\n";
        texteMenu += "0-Terminer\n\nFaites votre choix : ";
        choix = JOptionPane.showInputDialog(null, texteMenu, nomCompagnie, JOptionPane.PLAIN_MESSAGE);
        return Integer.parseInt(choix);
    }

    public static void message(String msg) {
        JOptionPane.showMessageDialog(null, msg, "MESSAGES", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) throws Exception {
        SetFrame choisirCie = new SetFrame();
        choisirCie.setVisible(true);
        choisirCie.setLocationRelativeTo(null);

        // mettre en pause lexécution pour permettre à l'utilisateur de choisir une
        // compagnie
        while (choisirCie.getCieChoisie() == null) {
            Thread.sleep(500);
        }

        String cieChosie = choisirCie.getCieChoisie();
        Compagnie cie = new Compagnie(cieChosie, 15);

        int choix;
        do {
            choix = menu(cie.getNomCompagnie());
            switch (choix) {
                case 1:
                    sortie = cie.getSortie();
                    cie.listerTout();
                    JOptionPane.showMessageDialog(null, sortie, null, JOptionPane.PLAIN_MESSAGE);
                    break;
                case 2:
                    cie.ajouterVol();
                    break;
                case 3:
                    cie.supprimerVol();
                    break;
                case 4:
                    cie.changerDateVol();
                    break;
                case 5:
                    cie.reserverVol();
                    break;
                case 0:
                    cie.enregistrerVolsObj();
                    cie.enregistrerVols();
                    message("Merci d'avoir utilisé notre système.");
                    break;
                default:
                    message("Votre choix est invalide.\nEssayez de nouveau. ");
                    break;
            }
        } while (choix != 0);
    }
}
