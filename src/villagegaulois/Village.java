package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
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

	public static class Marche {
		private Etal[] etals;

		public Marche(int nbEtals) {
			etals = new Etal[nbEtals]; /*=============================*/
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		public int trouverEtalLibre() {
			int found = -1;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() == false) {
					found = i;
					break;
				}
			}
			return found;

		}

		public Etal[] trouverEtals(String produit) {
			Etal[] etalProd = new Etal[etals.length]; // systemarraycopy ou array.copyoff
			int nbProduits = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit) == true) {
					etalProd[nbProduits] = etals[i];
					nbProduits++;
				}
			}
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
			String found = "";
			int nbEtalsVides = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() == true) {
					found += etals[i].afficherEtal() + "\n";
				}else {
					nbEtalsVides ++;
				}
			}		
			return found + "Il reste " + nbEtalsVides + " étals non utilisés dans le marché.";
		}

	}

	public static void main(String[] args) {
		Gaulois a = new Gaulois("a", 1);
		Gaulois b = new Gaulois("b", 1);
		Gaulois c = new Gaulois("c", 1);
		Marche marche = new Marche(6);
		marche.utiliserEtal(0, a, "test", 12);
		marche.utiliserEtal(1, b, "test", 12);
		marche.utiliserEtal(2, c, "taest", 12);
		
		marche.afficherMarche();

	}

}
