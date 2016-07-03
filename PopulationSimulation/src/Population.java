import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Population {
	
	protected float deathProb		= .014f;
	protected float birthProb		= .03f;
	protected float marriageProb	= .1f;
	protected int thisYear = 1;
	
	LinkedList<Person> alives;
	LinkedList<Person> deads;
	Map<String, Person> peopleMap = new HashMap<String, Person>();
	
	public static final int MARRIABLE_MIN_AGE = 20;
	LinkedList<Marriage> aliveMarriages;
	LinkedList<Marriage> deadMarriages;
	HashMap<Person, Marriage> marriageMap;
	
	class ThisYearInfo {
		int total = 0;
		int maleCount = 0;
		int femaleCount = 0;
		int birthCount = 0;
		int maleBirthCount = 0;
		int femaleBirthCount = 0;
		int deathCount = 0;
		int marriageCount = 0;
		
		void init(){
			total = 0;
			maleCount = 0;
			femaleCount = 0;
			birthCount = 0;
			maleBirthCount = 0;
			femaleBirthCount = 0;
			deathCount = 0;
			marriageCount = 0;
		}
	}
	ThisYearInfo thisYearInfo = new ThisYearInfo();
	
	public Population(int thisYear){
		this.thisYear = thisYear;
		initPeople();
	}
	
	public Person find(String fullName){
		return peopleMap.get(fullName);
	}
	
	protected void initPeople(){
		alives = new LinkedList<Person>();
		deads = new LinkedList<Person>();
		aliveMarriages = new LinkedList<Marriage>();
		deadMarriages = new LinkedList<Marriage>();

		for(int i=0 ; i < Person.LAST_NAMES.length ; i++){
			String firstName = Person.randomFirstName();
			Person male = new Person(
					Person.LAST_NAMES[i], firstName, Gender.MALE, 0);
			
			firstName = Person.randomFirstName();
			Person female = new Person(
					Person.LAST_NAMES[i], firstName, Gender.FEMALE, 0);
			
			alives.add(male);
			alives.add(female);
			peopleMap.put(male.getFullName(), male);
			peopleMap.put(female.getFullName(), female);
		}
		
		thisYearInfo.total = thisYearInfo.birthCount = alives.size();
		this.thisYearInfo.maleCount = thisYearInfo.maleBirthCount = Person.LAST_NAMES.length;
		this.thisYearInfo.femaleCount = thisYearInfo.femaleBirthCount = Person.LAST_NAMES.length;
		
	}
		
	public float getDeathProb() {
		return deathProb;
	}

	public void setDeathProb(float deathProb) {
		this.deathProb = deathProb;
	}

	public float getBirthProb() {
		return birthProb;
	}

	public void setBirthProb(float birthProb) {
		this.birthProb = birthProb;
	}

	public float getMarriageProb() {
		return marriageProb;
	}

	public void setMarriageProb(float marriageProb) {
		this.marriageProb = marriageProb;
	}

	public int getThisYear() {
		return thisYear;
	}

	protected void giveBirth(){
		Iterator<Marriage> it = aliveMarriages.iterator();
		while( it.hasNext() ){
			Marriage m = it.next();
			if( randomGiveBirth() ){			
				Person child = new Person(
						m.getHusband(), m.getWife(), Person.randomFirstName(), 
						Gender.randomGender(), thisYear);
				m.addChild(child);
				this.alives.add(child);
				this.peopleMap.put(child.getFullName(), child);
				thisYearInfo.birthCount++;
				if( child.getGender() == Gender.MALE ){
					thisYearInfo.maleBirthCount++;
				}else{
					thisYearInfo.femaleBirthCount++;
				}
			}
		}
	}
	
	protected void liveOrDie(Iterator<Person> it, Person p){
		if( randomDie() ){
			p.die(thisYear);
			it.remove();
			deads.add(p);
			thisYearInfo.deathCount++;
		}
	}
	
	protected void marryOrNot(
			Person p, 
			List<Person> marriableMales, 
			List<Person> marriableFemales)
	{
		if( p.isMarried() == false && 
				p.getAge(thisYear) >= MARRIABLE_MIN_AGE && 
				randomMarry() )
		{
			if( p.getGender() == Gender.MALE ){
				marriableMales.add(p);
			}else{
				marriableFemales.add(p);
			}
		}
	}
	
	protected void marry(
			List<Person> marriableMaleList, 
			List<Person> marriableFemaleList)
	{
		Collections.shuffle(marriableMaleList);
		Collections.shuffle(marriableFemaleList);
		Iterator<Person> itMale = marriableMaleList.iterator();
		Iterator<Person> itFemale = marriableFemaleList.iterator();
		while(itMale.hasNext() && itFemale.hasNext()){
			Person husband = itMale.next();
			Person wife = itFemale.next();
			Marriage m = new Marriage(husband, wife, thisYear);
			husband.setMarriage(m);
			wife.setMarriage(m);
			aliveMarriages.add(m);
			thisYearInfo.marriageCount++;
		}
	}
	
	public void printSummary(PrintStream out){
		//PrintStream out = System.out;
		out.println("["+thisYear+"³â]");
		out.println("ÃÑÀÎ±¸ = "+thisYearInfo.total);
		out.println("³²:³à = "+thisYearInfo.maleCount+":"+thisYearInfo.femaleCount);
		out.println("°áÈ¥ = "+thisYearInfo.marriageCount);
		out.println("Ãâ»ê = "+thisYearInfo.birthCount);
		out.println("³²:³à = "+thisYearInfo.maleBirthCount+":"+thisYearInfo.femaleBirthCount);
		out.println("»ç¸ÁÀÚ = "+thisYearInfo.deathCount);
		out.println();
	}
	
	protected boolean randomDie(){
		if( deathProb > Math.random() ){
			return true;
		}
		return false;
	}
	
	protected boolean randomGiveBirth(){
		if( this.birthProb > Math.random() ){
			return true;
		}
		return false;
	}
	
	protected boolean randomMarry(){
		if( marriageProb > Math.random() ){
			return true;
		}
		return false;
	}
	
	public void simulateNextYear(){
		thisYear++;
		
		List<Person> marriableMales = new LinkedList<Person>();
		List<Person> marriableFemales = new LinkedList<Person>();
		
		this.thisYearInfo.init();
		
		// live or die
		Iterator<Person> it = alives.iterator();
		while( it.hasNext() ){
			Person p = it.next();
			liveOrDie(it, p);
			if( p.isAlive(thisYear) ){
				marryOrNot(p, marriableMales, marriableFemales);				
			}
		}
		
		// rearrange marriages
		Iterator<Marriage> it2 = aliveMarriages.iterator();
		while( it.hasNext() ){
			Marriage m = it2.next();
			if( m.isAlive(thisYear) == false ){
				it2.remove();
				deadMarriages.add(m);
			}
		}
		
		// ¾Ö±â ³º±â
		giveBirth();
		
		// °áÈ¥½ÃÅ°±â
		marry(marriableMales, marriableFemales);
		
		// ³²³à ¸í¼ö °è»ê
		it = alives.iterator();
		while( it.hasNext() ){
			Person p = it.next();
			if( p.getGender() == Gender.MALE ){
				thisYearInfo.maleCount++;
			}else{
				thisYearInfo.femaleCount++;
			}
		}
		
		thisYearInfo.total = alives.size();
	}
	
	public void writeDetail(PrintStream out) throws FileNotFoundException{
		//PrintStream out = new PrintStream(new FileOutputStream(new File(outfile), true));
		out.println("["+thisYear+"]");
		int no = 1;
		Iterator<Person> it = this.alives.iterator();
		while( it.hasNext() ){
			Person p = it.next();
			p.writeDetail(no, thisYear, out);
			no++;
		}
		out.println("\n");
		out.flush();
	}
}
