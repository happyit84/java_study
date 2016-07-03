import java.util.LinkedList;
import java.util.List;

public class Marriage {
	protected Person husband;
	protected Person wife;
	protected int marriageYear;
	List<Person> children;
	
	public Marriage(Person husband, Person wife, int marriageYear){
		this.husband = husband;
		this.wife = wife;
		this.marriageYear = marriageYear;
		this.children = new LinkedList<Person>();
	}
	
	public void addChild(Person child){
		children.add(child);
	}
	
	public List<Person> getChildren(){
		return children;
	}
	
	public String getFamilyName(){
		return husband.getLastName();
	}
			
	public Person getHusband() {
		return husband;
	}

	public Person getWife() {
		return wife;
	}

	public Person getSpouse(Person p){
		Person spouse = null;
		if( husband.equals(p) ){
			spouse = wife;
		}else if( wife.equals(p) ){
			spouse = husband;
		}
		return spouse;
	}
	
	public boolean isAlive(int thisYear){
		return (husband.isAlive(thisYear) && wife.isAlive(thisYear));
	}
}
