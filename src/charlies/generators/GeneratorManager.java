package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import charlies.datastore.DatastoreManager;
import charlies.entities.Athlete;
import charlies.entities.Battle;
import charlies.entities.Scientist;
import charlies.exceptions.NoResultException;
import charlies.exceptions.UnknownCategoryException;
import charlies.sections.QuizzSection;

import com.hp.hpl.jena.query.QuerySolution;

public class GeneratorManager {
	
	private SectionGenerator generator;
	private DatastoreManager datastore;
	private List<String> categories;
	
	public GeneratorManager(DatastoreManager datastore) {
		this.datastore = datastore;
		this.categories = new ArrayList<String>();
		this.categories.add("scientists");
		this.categories.add("battles");
		this.categories.add("athletes");
	}

	public List<QuizzSection> generate(int nb, String category) throws UnknownCategoryException, NoResultException{
		switch (category){
			case "scientists" :
				generator = new ScientistsGenerator(nb,datastore);
				break;
			case "battles" :
				generator = new BattlesGenerator(nb, datastore);
				break;
			case "athletes" :
				generator = new AthletesGenerator(nb, datastore);
				break;
			default :
		  		throw new UnknownCategoryException();
		}
		return generator.generate(nb);
	}
	
	public List<String> getCategories(){
		return this.categories;
	}

	
	
	//Generate entities to be stored in Datastore
	
	
	public List<Scientist> generateScientists(List<QuerySolution> res){
		List<Scientist> scientists = new ArrayList<Scientist>();
		for (QuerySolution sol : res){
			String pic = sol.getResource("pic").toString();
			String name = sol.getLiteral("name").toString();
			String birth = sol.getLiteral("b").toString().substring(0, 10);
			String place = fixLocation(sol.getLiteral("c").toString());
			scientists.add(new Scientist(pic, name, birth, place));
		}
		return scientists;
	}
	
	public List<Battle> generateBattles(List<QuerySolution> res){
		List<Battle> battles = new ArrayList<Battle>();
		String pic2;
		for (int i=0; i<res.size(); ++i){
			QuerySolution sol = res.get(i);
			String pic = sol.getResource("pic").toString();
			String commanders = sol.getLiteral("comm").toString();
			String start = sol.getLiteral("date").toString().substring(0, 10);
			String end = sol.getLiteral("date").toString().substring(0, 10);
			String dates = start + " - " + end;
			String place = fixLocation(sol.getLiteral("coun").toString());
			int j = i;
			do {
				QuerySolution sol2 = res.get(++j);
				pic2 = sol2.getResource("pic").toString();
				if (!pic.equals(pic2)){
					commanders += sol2.getLiteral("comm").toString();
				}
			} while (!pic.equals(pic2) && j-i < 5 && j<res.size());

			battles.add(new Battle(pic, commanders, dates, place));
		}
		return battles;
	}
	
	public List<Athlete> generateAthletes(List<QuerySolution> res){
		List<Athlete> athletes = new ArrayList<Athlete>();
		for (QuerySolution sol : res){
			String pic = sol.getResource("pic").toString();
			String name = sol.getLiteral("name").toString();
			String birth = sol.getLiteral("b").toString().substring(0, 10);
			String place = fixLocation(sol.getLiteral("c").toString());
			athletes.add(new Athlete(pic, name, birth, place));
		}
		return athletes;
	}
	
	//Make location matches with GoogleMaps' countries.
	public String fixLocation(String location){
		switch (location) {
		case "Venda": location = "South Africa"; break;
		case "the United States": location="United States"; break;
		case "Sloviet Union": location="Russia"; break;
		case "Palestine": location="Israel"; break;
		case "West Germany": location="Germany"; break;
		case "Prussia": location="Germany"; break;
		case "Ukrainian SSR": location="Russia"; break;	
		case "Abkhazia": location="Georgia"; break;
		case "Afghan Interim Authority": location="Afghanistan"; break;
		case "Austria-Hungary": location="Hungary"; break;
		case "Austrian Netherlands": location="Netherlands"; break;
		case "Azaward": location="Mali"; break;
		case "Azerbaijan People's Government": location="Azerbaijan"; break;
		case "Baden": location="France"; break;
		case "Brandenburg": location="Germany"; break;
		case "Brithish Solomon Islands": location="Solomon Islands"; break;
		case "Burma": location="Myanmar (Burma)"; break;
		case "Byelorussian SSR": location="Belarus"; break;
		case "Cape Colony": location="South Africa"; break;
		case "Catalonia": location="Spain"; break;
		case "Ceylon": location="Sri Lanka"; break;
		case "Cocos Islands": location="Cocos (Keeling) Islands"; break;
		case "Czechoslovakia": location="Czech Republic"; break;
		case "Danzig": location="Poland"; break; 
		case "Darfur": location="Sudan"; break;
		case "Dutch East Indies": location="Indonesia"; break;
		case "East Africa": location="Tanzania"; break;
		case "East Timor": location="TImor-Leste"; break;
		case "Falkland Island": location="Falkland Islands (Islas Malvinas)"; break;
		case "Frankfurt": location="Germany"; break;
		case "French Equatorial Africa": location="Gabon"; break;
		case "French Indochina": location="Indochina"; break;
		case "Galicia": location="Spain"; break;
		case "General Government": location="Germany"; break;
		case "Genoa": location="Italy"; break;
		case "Georgia Colony": location="Georgia"; break;
		case "Georgia Soviet Army": location="Georgia"; break;
		case "German-occupied France": location="France"; break;
		case "Grenada": location="Spain"; break;
		case "Holy Roman Empire": location="Germany"; break;
		case "Hyderabad": location="India"; break;
		case "Imperial Japon": location="Japon"; break;
		case "Iraqi Kurdistant": location="Iraq"; break;
		case "Irish Free State": location="Irland"; break;
		case "Isle de France": location="France"; break;
		case "Joseon": location="South Korea"; break;
		case "Karelo-Finnish SSR": location="Russia"; break;
		case "Kingdom of Armenia": location="Armenia"; break;
		case "Kosovo": location="Serbia"; break;
		case "Kosovo and Metohija": location="Serbia"; break;
		case "Levant and Mesopotamia": location="Iraq"; break;
		case "Lombardy–Venetia": location="Italy"; break;
		case "Mahabad": location="Iran"; break;
		case "Manchukuo": location="China"; break;
		case "Maratha": location="India"; break;
		case "Massachusetts": location="United States"; break;
		case "Mecklenburg-Schwerin": location="Germany"; break;
		case "Mughal Empire": location="India"; break;
		case "Myanmar": location="Myanmar (Burma)"; break;
		case "Mysore": location="India"; break;
		case "Nagorno-Karabakh": location="Azerbaijan"; break;
		case "Netherlands New Guinea": location="Indonesia"; break;
		case "North Borneo": location="Malaysia"; break;
		case "North Vietnam": location="Vietnam"; break;
		case "Northern Somalia Protectorate": location="Somalia"; break;
		case "Ottoman Empire": location="Turquey"; break;
		case "Papua": location="Indonesia"; break;
		case "Pomerania": location="Poland"; break;
		case "Regensburg": location="Germany"; break;
		case "Republic of Macedonia": location="Macedonia (FYROM)"; break;
		case "Saint-Domingue": location="Dominican Republic"; break;
		case "Saxony": location="Germany"; break;
		case "Serbia and Montenegro": location="Serbia";
		case "South African Republic": location="South Africa"; break;
		case "South Ossetia": location="Georgia"; break;
		case "South Vietnam": location="Vietnam"; break;
		case "South Yemen": location="Yemen"; break;
		case "South-West Africa": location="South Africa"; break;
		case "Soviet Union": location="Russia"; break;
		case "Spanish Netherlands": location="Spain"; break;
		case "Spanish Sahara": location="Marocco"; break;
		case "Spanish protectorate in Morocco": location="Marocco"; break;
		case "Straits Settlements": location="Malaysia"; break;
		case "Territory of the Military Commander in Serbia": location="Serbia"; break;
		case "Texas": location="United States"; break;
		case "Togoland": location="Togo"; break;
		case "Vichy France": location="France"; break;
		case "Vojna Krajin": location="Croatia"; break;
		case "Warsaw": location="Poland"; break;
		case "Württemberg": location="Germany"; break;
		case "the Central African Republic": location="Central African Republic"; break;
		case "the Democratic Republic of the Congo": location="Democratic Republic of the Congo"; break;
		case "the Netherlands": location="Netherlands"; break;
		case "the People's Republic of China": location="China"; break;
		case "the Philippines": location="Philippines"; break;
		case "the Republic of the Congo": location="Democratic Republic of the Congo"; break;
		case "Armenian SSR": location="Armenia"; break;
		case "Bohemia and Moravia": location="Czech Republic"; break;
		case "East Germany": location="Germany"; break;
		case "Georgian Sloviet Army": location="Georgia"; break;
		case "Latvian SSR": location="Russia"; break;
		case "Lithuanian SSR": location="Russia"; break;
		case "Sark": location="Guernsey"; break;
		case "Tajik SSR": location="Russia"; break;
		case "Uzbek SSR": location="Uzbekistan"; break;
		
	}
		return location;
	}
	
}
