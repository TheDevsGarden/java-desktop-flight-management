import java.io.*;
import java.util.Comparator;

public class Vol implements Serializable, Comparable<Vol> {

    private static final long serialVersionUID = 2040890116313738088L;

    private String numVol;
    private String destination;
    private Date dateVol;
    private int nbReservations;

    Vol(String numVol, String destination, Date dateVol, int nbReservations) {
        this.numVol = numVol;
        this.destination = destination;
        this.dateVol = dateVol;
        this.nbReservations = nbReservations;
    }

    // Autres méthodes get, set toString et autres selon l'énoncé
    public String getNumVol() {
        return this.numVol;
    }

    public String getDestination() {
        return this.destination;
    }

    public Date getDateVol() {
        return this.dateVol;
    }

    public int getNbReservations() {
        return this.nbReservations;
    }

    public void setNbReservations(int nbReservations) {
        this.nbReservations = nbReservations;
    }

    public void setDateVol(Date dateVol) {
        this.dateVol = dateVol;
    }

    public boolean equals(Object obj) {
        Vol autreVol = (Vol) obj;
        if (this.numVol == autreVol.numVol) {
            return true;
        } else {
            return false;
        }
    }

    public int compareTo(Vol unVol) {
        // Par numVol Vol
        return (int) (Integer.parseInt(this.numVol) - Integer.parseInt(unVol.numVol));// En ordre croissant
        // return (int) (unVol.num - this.num);//En ordre décroissant
        // Par titre
        // return this.titre.compareTo(unVol.titre);
    }

    public String toString() {
        return this.numVol + "\t" + String.format("%-20s", this.destination) + "\t" + this.dateVol + "\t"
                + this.nbReservations;
    }

}
