public class Date {
    private int jour;
    private int mois;
    private int annee;

    Date(){}
    Date(int jour, int mois, int annee){
        setJour(jour);
        setMois(mois);
        setAnnee(annee);
    }

    public int getJour(){
        return this.jour;
    }
    
    public int getMois() {
        return this.mois;
    }
    
    public int getAnnee() {
        return this.annee;
    }

    public void setJour(int jour){
        if(jour >= 1 && jour <= 31){
            this.jour = jour;
        }else {
            System.out.println("Jour inavalide");
        }
    }
    
    public void setMois(int mois) {
        if (mois >= 1 && mois <= 12) {
            this.mois = mois;
        } else {
            System.out.println("Mois inavalide");
        }
    }

    public void setAnnee(int annee) {
        if (annee >= 1000) {
            this.annee = annee;
        } else {
            System.out.println("Annee inavalide");
        }
    }

    public String toString(){
        String jour, mois;
        if(this.jour < 10){
            jour = "0"+this.jour;
        }else {
            jour=this.jour+"";
        }
        if (this.mois < 10) {
            mois = "0" + this.mois;
        } else {
            mois = this.mois + "";
        }
        return jour+"/"+mois+"/"+this.annee;
    }
}
