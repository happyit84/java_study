import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Tank {
	public final static int TOTAL_ABILITY = 100;
	private int id;
	private int maxHP;
	private	int attack;
	private int accurate;
	private double hp = 0;
	
	private Position pos = new Position();
	
	public final static int RANK_1ST_SCORE = 10;
	public final static int RANK_2ND_SCORE = 5;
	public final static int RANK_3RD_SCORE = 2;
	
	private int rank1st = 0;
	private int rank2nd = 0;
	private int rank3rd = 0;
	
	private int score = 0;
	
	private static HashMap<IdTuple, Double> distMap;
	
	public Tank(int id){
		this.id = id;
		
		// HP와 attack, accurate의 합이 100이 되도록 랜덤으로 설정
		int rand1;
		int rand2;
		do{
			rand1 = (int)(Math.random()*(TOTAL_ABILITY-2)) + 1;
			rand2 = (int)(Math.random()*(TOTAL_ABILITY-2)) + 1;	
		}while(rand1 == rand2);
		
		if( rand1 > rand2 ){
			int temp = rand1;
			rand1 = rand2;
			rand2 = temp;
		}
				
		maxHP = rand1;
		hp = maxHP;
		attack = rand2 - rand1;
		accurate = TOTAL_ABILITY - rand2;
	}
	
	public void addRank1stCount(){
		rank1st++;
		score += RANK_1ST_SCORE;
	}
	public void addRank2ndCount(){
		rank2nd++;
		score += RANK_2ND_SCORE;
	}
	public void addRank3rdCount(){
		rank3rd++;
		score += RANK_3RD_SCORE;
	}
	
	public double calcDemage(Tank target, double distance) throws DemageLessThanZeroException{
		double coef = 0.2;
		double demage = coef*attack*
				((accurate*Math.random())/ TOTAL_ABILITY)*
				(1 - distance/(2.0*Position.MAX_DISTANCE));
		if( demage < 0 ){
			throw new DemageLessThanZeroException();
		}
		return demage;
	}
	
	public boolean decideShootMiss(double distance){
		final double coeff = 0.007;
		double missProb = (TOTAL_ABILITY - accurate)*distance*coeff / Position.MAX_DISTANCE;
		double rand = Math.random();
		boolean miss = rand < missProb;
		return miss;
	}
	
	public double distanceFrom(Tank t){
		return pos.distanceFrom(t.pos);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tank other = (Tank) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public Tank findNearest(
			ArrayList<Tank> aliveList, 
			HashMap<IdTuple, Double> distMap
			) throws NoTankInAliveListException
	{
		Iterator<Tank> it = aliveList.iterator();
		if( !it.hasNext() ){
			throw new NoTankInAliveListException();
		}
		Tank nearest = null;
		double nearestDistance = Position.MAX_DISTANCE;
		while( it.hasNext() ){
			Tank t = it.next();
			if( !t.equals(this) ){
				double distance = distMap.get(new IdTuple(this.id, t.id));
				if( null == nearest ){
					nearest = t;
					nearestDistance = distance;
				}else if( distance < nearestDistance ){
					nearest = t;
					nearestDistance = distance;
				}
			}
		}
		return nearest;
	}
	
	public int getAccurate() {
		return accurate;
	}
	public int getAttack() {
		return attack;
	}
	public double getHp() {
		return hp;
	}
	public int getId() {
		return id;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public Position getPos(){
		return pos;
	}	
	public int getScore(){
		return score;
	}
			
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public boolean isDead(){
		return (0.0 == hp);
	}
	
	public void playTurn(
			ArrayList<Tank> aliveList,			
			ArrayList<Tank> toBeDeadList
			) throws NoTankInAliveListException, NullDistMapException, DemageLessThanZeroException
	{
		Tank target = findNearest(aliveList, distMap);
		this.shoot(target);
		if( target.isDead() && !toBeDeadList.contains(target) ){
			//aliveList.remove(target);
			toBeDeadList.add(target);
		}
	}
	
	public void recoverHp(){
		hp = maxHP;
	}
	
	public void resetPosition(){
		pos.reset();
	}

	public static void setDistMap(HashMap<IdTuple, Double> distMap){
		Tank.distMap = distMap;
	}
	
	public boolean shoot(Tank target) throws NullDistMapException, DemageLessThanZeroException{
		TankSimul.out.print("tank["+id+"] shot at tank["+target.id+"] : ");
		if( null == distMap ){
			throw new NullDistMapException();
		}
		double distance = distMap.get(new IdTuple(this.id, target.id));		
		boolean miss = decideShootMiss(distance);
		if( miss ){
			TankSimul.out.print("MISS!\n");
			return false;
		}
		double demage = calcDemage(target, distance);
		target.takeDamage(demage);
		TankSimul.out.print("DEMAGE "+demage+ "! [remain hp: "+target.hp+"]\n");
		return true;
	}
	
	public void takeDamage(double demage) throws DemageLessThanZeroException{
		if( demage < 0 ){
			throw new DemageLessThanZeroException();
		}
		hp -= demage;
		if( hp < 0 ){
			hp = 0.0; 
		}
	}
	
	public String toString4Ranking(){
		return "tank[id:"+id+" / "+"hp:"+maxHP+
				" / att:"+attack+" / acc:"+accurate+" / 1st:"+rank1st+
				" / 2st:"+rank2nd+" / 3rd:"+rank3rd+" / score: "+score+"]";
	}
	
	public String toString(){
		return "tank[id:"+id+" / pos("+pos.getX()+","+pos.getY()+") / "+"hp:"+maxHP+
					" / att:"+attack+" / acc:"+accurate+" / 1st:"+rank1st+
					" / 2st:"+rank2nd+" / 3rd:"+rank3rd+" / score: "+score+"]";
	}
}

class DemageLessThanZeroException extends Exception{}
class NoTankInAliveListException extends Exception{}
class NullDistMapException extends Exception{}