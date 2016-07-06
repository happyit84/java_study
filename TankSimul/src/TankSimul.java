import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class TankSimul {
	public final static int TANK_NUM = 200;
	public final static int GAME_NUM = 1000;
	ArrayList<Tank> tankList;
	HashMap<IdTuple, Double> distMap; // distance map

	ArrayList<Tank> aliveList;
	ArrayList<Tank> toBeDeadList;
	ArrayList<Tank> deadList;
	
	public static PrintWriter out;
	
	public static void main(String[] args) throws NoTankInAliveListException, NullDistMapException, DemageLessThanZeroException, FileNotFoundException {
		// TODO Auto-generated method stub
		out = new PrintWriter("output.txt");
		new TankSimul();
		out.close();
	}

	public TankSimul() throws NoTankInAliveListException, NullDistMapException, DemageLessThanZeroException{
		tankList = new ArrayList<Tank>();
		initTanks(TANK_NUM);
		//printTanks();
		runGames();
	}
	
	void checkRank(){
		if( 2 == aliveList.size() ){
			out.print("Rank 3rd: ");
			for(Tank rank3rdTank : toBeDeadList){
				rank3rdTank.addRank3rdCount();
				out.print("tank["+rank3rdTank.getId()+"] ");
			}
			out.println();
		}else if( 1 == aliveList.size() ){
			out.print("Rank 2nd: ");
			for(Tank rank2ndTank : toBeDeadList){
				rank2ndTank.addRank2ndCount();
				out.print("tank["+rank2ndTank.getId()+"] ");
			}
			out.println();
			Tank rank1st = aliveList.get(0);
			aliveList.remove(0);
			rank1st.addRank1stCount();
			out.println("Rank 1ST: tank["+rank1st.getId()+"]\n");
		}else if( 0 == aliveList.size() ){
			out.print("Rank 1ST: ");
			for( Tank rank1st : toBeDeadList){
				rank1st.addRank1stCount();
				out.print("tank["+rank1st.getId()+"] ");
			}
			out.println();
		}
	}
	
	void initTanks(int tankNum){
		for(int id=0 ; id < tankNum ; id++){
			tankList.add(new Tank(id));
		}
	}
	
	void initTanksHpPosition(){
		for(Tank t : tankList){
			t.recoverHp();
			t.resetPosition();
		}
	}
	
	void initDistMap(){
		distMap = new HashMap<IdTuple, Double>();
		for(Tank t1 : tankList){
			for(Tank t2 : tankList){
				if( t1.equals(t2) == false ){
					IdTuple tuple = new IdTuple(t1.getId(), t2.getId());
					if( false == distMap.containsKey(tuple) ){
						Double distance = t1.distanceFrom(t2);
						distMap.put(tuple, distance);	
					}					
				}
			}
		}
		Tank.setDistMap(distMap);
	}
	
	void printRank(){
		ArrayList<Tank> rankList = (ArrayList<Tank>) tankList.clone();
		rankList.sort(new Comparator<Tank>(){
			@Override
			public int compare(Tank o1, Tank o2) {
				// TODO Auto-generated method stub
				return o2.getScore() - o1.getScore();
			}
		});
		out.println("\n[Ranking]");
		Iterator<Tank> it = rankList.iterator();
		int rank = 0;
		while(it.hasNext()){
			Tank t = it.next();
			rank++;
			out.println("Rank "+rank+" : "+t.toString4Ranking());
		}
	}
	
	void printTanks(){
		for(Tank t : tankList){
			out.println(t);
		}
	}
	
	void runGames() throws NoTankInAliveListException, NullDistMapException, DemageLessThanZeroException{
		for(int i=1 ; i <= GAME_NUM ; i++){
			out.println("\n[Game "+i+"]");
			System.out.println("[Game "+i+"]");
			runGame(i);
			printRank();
		}
	}
	
	void runGame(int gameNo) throws NoTankInAliveListException, NullDistMapException, DemageLessThanZeroException{
		initTanksHpPosition();
		initDistMap();
		initAliveDeadList();
		printTanks();
		//Scanner sc = new Scanner(System.in);
		for(int turn=1 ;  aliveList.size() > 0 ; turn++){
			playTurn(gameNo, turn);
			//out.println("press enter key to continue...");
			//sc.nextLine();
		}
	}
	
	void initAliveDeadList() {
		// TODO Auto-generated method stub
		aliveList = new ArrayList<Tank>(tankList);
		deadList = new ArrayList<Tank>();
		toBeDeadList = new ArrayList<Tank>();
	}

	void playTurn(int gameNo, int turn) throws NoTankInAliveListException, NullDistMapException, DemageLessThanZeroException{
		out.println("\n[Game "+gameNo+" - TURN "+turn+"]");
		for(Tank t : aliveList){
			t.playTurn(aliveList, toBeDeadList);
		}		
		if( toBeDeadList.size() > 0 ){
			aliveList.removeAll(toBeDeadList);
			checkRank();
			deadList.addAll(toBeDeadList);
			toBeDeadList.clear();	
		}		
	}
}
