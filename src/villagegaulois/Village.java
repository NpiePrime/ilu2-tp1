package villagegaulois;

import java.util.Arrays;
import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		Marche marche = new Marche(nbEtals);
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

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int found = trouverEtalLibre();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n" );
		if (found == -1) {
			chaine.append("Tous les étals sont occupés !");
		}else {
			marche.utiliserEtal(found, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des fleurs à l'étal n° " + found + ".");
		}
		
		return chaine.toString();
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
			Etal[] etalProd = new Etal[etals.length]; 
			int nbProduits = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalProd[nbProduits] = etals[i];
					nbProduits++;
				}
			}
			etalProd = Arrays.copyOf(etalProd, nbProduits);
			return etalProd;
		}

		public Etal trouverVendeur(Gaulois gaulois) {
			Etal found = null;
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
//		Gaulois a = new Gaulois("a", 1);
//		Gaulois b = new Gaulois("b", 1);
//		Gaulois c = new Gaulois("c", 1);
//		Marche marche = new Marche(4);
//		marche.utiliserEtal(0, a, "test", 12);
//		marche.utiliserEtal(1, b, "test", 12);
//		marche.utiliserEtal(2, c, "taest", 12);
//		
//		System.out.println(marche.afficherMarche());
//		
////		Etal[] etalProd = new Etal[15];
////		etalProd = marche.trouverEtals("tes");
////		
//		for (int i = 0; i < 15; i++) {
//			System.out.println( marche.trouverEtals("test")[i]);
//			
//		}
//		System.out.println(marche.trouverVendeur(a));
		
//		
//		System.out.println(marche.afficherMarche());

	}

}
