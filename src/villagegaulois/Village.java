package villagegaulois;



import personnages.*;


public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		marche = new Marche(nbEtals);
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException{
		StringBuilder chaine = new StringBuilder();
		if (chef == null) {
			throw new VillageSansChefException("Il n'y a pas de chef");
		}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int found = marche.trouverEtalLibre();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n" );
		if (found == -1) {
			chaine.append("Tous les étals sont occupés !");
		}else {
			marche.utiliserEtal(found, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n° " + (found+1) + ".");
		}
		
		return chaine.toString() + "\n";
	}
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des " + produit + " sont: \n");
		Etal[] etalsProduit = marche.trouverEtals(produit);
		if (etalsProduit != null) {
			for (int i = 0; i < etalsProduit.length; i++) {
				chaine.append("-" + etalsProduit[i].getVendeur().getNom() +"\n");
			}
			
		}
		return chaine.toString();
		
	}
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		StringBuilder chaine = new StringBuilder();
		if (etal != null) {
			chaine.append(etal.libererEtal());
		}
		return chaine.toString() ;
	}
	public String afficherMarche() {
		String intro = "Le marché du village <<" +  nom + ">> possède plusieurs étals :\n"; 
		return intro + marche.afficherMarche();
		}
	
	
	

	public static class Marche {
		private Etal[] etals;

		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i]= new Etal();
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		public int trouverEtalLibre() {
			int found = -1;
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					found = i;
					break;
				}
			}
			return found;

		}

		public Etal[] trouverEtals(String produit) {
			int nbEtal = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					nbEtal++;
				}
			}
			Etal[] etalsProd = null;
			if (nbEtal > 0) {
				etalsProd = new Etal[nbEtal];
				int nbEtalTrouve = 0;
				for (int i = 0; i < etals.length
						&& nbEtalTrouve < nbEtal; i++) {
					if (etals[i].isEtalOccupe()
							&& etals[i].contientProduit(produit)) {
						etalsProd[nbEtalTrouve] = etals[i];
						nbEtalTrouve++;
					}
				}
			}
			return etalsProd;
		}

		public Etal trouverVendeur(Gaulois gaulois) {
			Etal found = new Etal();
			for (int i = 0; i < etals.length; i++) {

				if (etals[i].getVendeur() == gaulois) {
					found = etals[i];
					break;
				}
			}
			return found;
		}
		public String afficherMarche() {
			StringBuilder found = new StringBuilder();
			int nbEtalsVides = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					found.append(etals[i].afficherEtal());
				}else {
					nbEtalsVides ++;
				}
			}
			if (nbEtalsVides != 0) {
				found.append("Il reste " + nbEtalsVides + " étal(s) non utilisé(s) dans le marché.");
			}
			return found.toString();
		}

	}

	public static void main(String[] args) {

		Marche marche = new Marche(5);
		
		Gaulois obelix = new Gaulois("Obélix", 25);
		Gaulois asterix = new Gaulois("Astérix", 8);
		Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);
		
		marche.utiliserEtal(0, assurancetourix, "lyres", 5);
		marche.utiliserEtal(1, obelix, "menhirs", 5);
		marche.utiliserEtal(2, asterix, "fleurs", 10);
		
		System.out.println(marche.afficherMarche());
		
	}

}
