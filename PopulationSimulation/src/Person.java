import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

enum Gender{
	MALE, FEMALE;
	
	public static Gender randomGender(){
		if( Math.random() < 0.5 ){
			return MALE;
		}
		return FEMALE;
	}
	
	public String toString(){
		if( this == MALE ){
			return "남";
		}
		return "여";
	}
}

public class Person {
	public static final String[] LAST_NAMES = {
			"Anderson", "Allen", "Adams",
			"Brown", "Baker", "Bailey", "Bell", "Brooks", "Bennett", "Butler", "Barnes",
			"Clark", "Campbell", "Carter", "Collins", "Cook", "Cooper", "Cox", "Cruz",
			"Davis", "Díaz",
			"Evans", "Edwards",
			"Flores", "Foster", "Fisher",
			"Hernández", "Harris", "Hall", "Hill", "Howard", "Hughes",
			"Garcia", "Gonzalez", "Green", "Gomez", "Gray", "Gutierrez",
			"Johnson", "Jones", "Jackson", "James", "Jenkins",
			"King", "Kelly",
			"Miller", "Martinez", "Moore", "Martin", "Mitchell", "Morris", "Murphy", "Morgan", "Myers", "Morales",
			"Nelson", "Nguyen",
			"Ortiz",
			"Lopez", "Lee", "Lewis", "Long",
			"Perez", "Phillips", "Parker", "Peterson", "Price", "Powell", "Perry",
			"Rodriguez", "Robinson", "Ramirez", "Roberts", "Rivera", "Rogers", "Reed", "Richardson", "Reyes", "Ross", "Russell",
			"Smith", "Sanchez", "Scott", "Stewart", "Sanders", "Sullivan",
			"Taylor", "Thomas", "Thompson", "Turner", "Torres",
			"Williams", "Wilson", "White", "Walker", "Wright", "Ward", "Wood", "Watson",
			"Young"
	};
	
	public static final int FIRST_NAME_MIN_LENGTH = 2;
	public static final int FIRST_NAME_MAX_LENGHT = 20; 
	public static final int NOT_DEAD = -1;
	public static final int WRITE_DETAIL_SKIP_NO = -1;
	
	protected static long lastId = 0;
	
	protected long id;
	protected String lastName;
	protected String firstName;
	protected int birthYear;
	protected int deathYear = NOT_DEAD;
	protected Gender gender;
	protected Person father;
	protected Person mother;
	//protected Person spouse;
	protected Marriage marriage;
	//protected ArrayList<Person> children = new ArrayList<Person>();
	
	public Person(String lastName, String firstName, Gender gender, int birthYear){
		this.id = nextId();
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
		this.birthYear = birthYear;
	}
	
	public Person(
			Person father, Person mother, 
			String firstName, Gender gender, int birthYear)
	{
		this(father.getLastName(), firstName, gender, birthYear);
		this.father = father;
		this.mother = mother;
	}
	
	public void die(int deathYear){
		this.deathYear = deathYear;
	}
	
	public int getAge(int presentYear){
		return presentYear - birthYear;
	}
	
	public int getBirthYear(){
		return birthYear;
	}
	
	public int getDeathYear(){
		return deathYear;
	}
			
	public String getFullName(){
		return firstName + ' ' + lastName;
	}
	
	public Gender getGender() {
		return gender;
	}

	public long getId(){
		return id;
	}
	
	public String getLastName() {
		return lastName;
	}

	public Person getSpouse() {
		Person spouse = null;
		if(null != marriage){
			spouse = marriage.getSpouse(this);
		}
		return spouse;
	}	
	
	public void setMarriage(Marriage marriage){
		this.marriage = marriage;
	}
	
	public boolean isAlive(int thisYear){
		if( deathYear != Person.NOT_DEAD && deathYear <= thisYear ){
			return false;
		}
		return true;
	}
	
	public boolean isMarried(){
		if(marriage == null){
			return false;
		}
		return true;
	}
	
	public static long nextId(){
		return ++lastId;
	}
	
	public static String randomFirstName(){
		Random r = new Random();
		int len = r.nextInt(FIRST_NAME_MAX_LENGHT - FIRST_NAME_MIN_LENGTH) + FIRST_NAME_MIN_LENGTH;
		char [] firstName = new char[len];
		int alphaNum = 'z' - 'a' + 1;
		for(int i=0 ; i < firstName.length ; i++){
			firstName[i] = (char) ('a' + r.nextInt(alphaNum));
		}
		firstName[0] = Character.toUpperCase(firstName[0]);
		return new String(firstName);
	}
	
	public void showDetail(PrintStream out, int thisYear){
		writeDetail(WRITE_DETAIL_SKIP_NO, thisYear, out);
		writeAncestors(out, thisYear);
	}
	
	public String toSimpleString(String pre, int thisYear){
		return "\t\t"+pre+": "+getFullName()+" (id:"+getId()+", " +
				(isAlive(thisYear)?("age:"+(thisYear-birthYear)+")"):("death year:"+getDeathYear()+")"));
	}
	
	public void writeAncestors(PrintStream out, int thisYear){
		if( father != null ){
			out.println("\t  [Ancestors]");
			father.writeAncestors(out, thisYear);
			out.println(father.toSimpleString("fa", thisYear));
			out.println(mother.toSimpleString("ma", thisYear));
		}
	}
	
	public void writeDetail(int no, int thisYear, PrintStream out){
		int age = thisYear - birthYear;
		if( WRITE_DETAIL_SKIP_NO != no ){
			out.print("\t"+no+".");
		}
		out.println("[id="+id+"] "+firstName+" "+lastName+" ("+gender+", age:"+age+")");
		out.println("\t\tbirth year: "+birthYear);
		if( father != null ){
			out.println(father.toSimpleString("father", thisYear));
		}
		if( mother != null ){
			out.println(mother.toSimpleString("mother", thisYear));
		}
		if( marriage != null ){
			Person spouse = marriage.getSpouse(this);
			out.println(spouse.toSimpleString("spouse", thisYear));
			List<Person> children = marriage.getChildren();
			Iterator<Person> it = children.iterator();
			while( it.hasNext() ){
				Person child = it.next();
				out.println(child.toSimpleString("  child", thisYear));
			}
		}
	}
}
