import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
https://algospot.com/judge/problem/read/XHAENEUNG

���� ����

���� ID�ð� ���Ѹ޸� �������� Ƚ������ Ƚ�� (����)
XHAENEUNG1000ms65536kb56991456 (25%)
��������ó�з�
hyunhwanCoder's high 2013����
����

��� ��� ��� ������ ������ ��ġ�� �б��� ���ƿ� xhae�� �ֱ� ������ ���� ���� ����� ���� �ڱݳ��� ����̰� �־���. �̷��� xhae�� ������ ���� �ٸ� �ƴ� °�� ���� ���� ä�� �Ƹ�����Ʈ��. ���� �� ���� ������ ICPC ��ȸ �غ�� ���� �ð��� ���� xhae���Դ� ���� ���� ���� �ڱ� ���޿��̴�.
°�� ���� ����� �ʵ��б� ���г� �л����� ������� ���а� ��� ���ÿ� ����ġ���� ������ �н�����, xhae�� �ϴ� ���� ��Ģ���� ���� �н����� ä���ϴ� ���̴�.
�ٸ� ���� �н����� �ٸ��� °�� ���� ����� ��Ģ ���� ������ ��Ư�ϰ� ����. ���� "1 + 2 = "�� ���� ������ ��� ���� �������� Ǯ�� �����ν� "one + two = "�� ������ �����ȴ�. ���� �� ���� �ƶ��� ���ڰ� �ƴ� �������� Ǯ�� �� ���� ���� ����� �Ѵ�. ��, ���� ������ ��� "three" ��� ���� �ۼ��Ͽ��� �Ѵ�.
ó������ ä���� ������ �޾ƴ� xhae�� ���� ICPC�� �������� ���ؼ����� xhae�� ä���� �� �� ��ȿ� ���ڰ� ���� ��� ���� ���� �̸� �������� ä���ߴ�. ���� ��� "four"�� "fuor" �� �߸� �� ���� �������� ä���Ͽ���. ������ ä�� ����� Ȯ���� ���� �к����� ���Ƿ� ���� xhae�� �����ؼ� ������ ���� ä���� �Ϸ��� �Ѵ�.
���� ���� 'seven'�� ��� ���� ���ĺ��� ������ ���� �����ϰ�(���⼭�� 's' 1��, 'e' 2��, 'v' 1�� �׸��� 'n' 1��), ������ �ڼ��� �ִ� �������� �������� �����ϱ�� �ߴ�.
������ �� ������ �ִ�. ������ ������ xhae�� ���� ������� ������ �ϴ� ���� ä���� �ؾ� �ϴ� ������ �ʹ� ���� ���̰� �� ���̴�. �Դٰ� xhae�� �л����� �������̷��� 0���� 10������ ���ۿ��� ���������� ���� ä ���� �� ������ �Ѿ �� �ִ� ��쵵 ������ ���� �ٶ��� ä���� �� ��ġ������ �Ǿ���. �������� xhae�� ������ ��ĵ�Ͽ� �̸� �ؽ�Ʈ�� �Ϻ��ϰ� ��ȯ ��ų �� �ִ� ���� �ְ��� OCR��ġ�� ������ �ֱ� ������ ��� ������ �ؽ�Ʈ�� ��ȯ�س��� �����̸�, �̸� �ڵ����� ä���� �� �ִ� ���α׷��� ¥���� �Ѵ�. ������ Coder's High 2013 ���� ���� ��� ����ϰ� �־��� xhae�� �� ����̶�� ������ �̸� ������ �� ���� ���̴�.

���, xhae�� ����� ���� ä�� ���α׷��� �ۼ��϶�.

�Է�

�Է��� ù �ٿ��� ä���� �ؾ� �� ������ �� T�� �Էµȴ�. �� ������ �� �ٿ� �ԷµǸ� ������ ������ ����.


A operation B = C
.
���⼭ A, B, C�� ���� �ܾ�� ��Ÿ�� ���� ���ϸ�, operation�� ���ϱ�(+), ����(-), ���ϱ�(*)�� ���´�. A�� B�� �����ϴ� ���� �ܾ�� 0 �̻� 10 ������ ���� ��Ÿ���� �����ν� ������� zero, one, two, three, four, five, six, seven, eight, nine, ten �� �ϳ��� �׻� �ùٸ��� �־�����. �ݸ� C�� ��� ä���� ����̹Ƿ� 0���� 10������ ���� ���� �ܾ�� ǥ���� ���� �ƴ� �߸��� ���ڿ�(���� ���, 'hello')�� �Էµ� �� �ִ�. �׷����� �ұ��ϰ� �� ���ڿ��� �׻� ���̰� 10�� ���� �ʴ� ���ĺ� �ҹ��ڷθ� ������ ���ڿ����� ����ȴ�.

���

�� ���Ŀ� ���� �Էµ� ������� �� �ٿ� ���� �ùٸ��� ���� �Ǿ� ������ ��Yes����, �׷��� ���� ��쿡�� ��No���� ����Ѵ�. ���� ������ ����� 0���� �۰ų� 10���� Ŭ ��쿡�� ������ �������� �����Ѵ�.

���� �Է�
2
two + three = ivef
zero * zero = one

���� ���
Yes
No
 */


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int testCaseNum = Integer.parseInt(sc.nextLine().trim());
		for(int testNo = 1 ; testNo <= testCaseNum ; testNo++){
			String equationLine = sc.nextLine();
			Equation equation = new Equation();
			if( equation.parseEquation(equationLine) == false ){
				System.out.println("No");
				continue;
			}
			if( equation.isRight() ){
				System.out.println("Yes");
			}else{
				System.out.println("No");
			}
		}
	}

}

enum Operator{
	
	
	PLUS, MINUS, MULTIPLY;
	
	static final String PLUS_STRING = "+";
	static final String MINUS_STRING = "-";
	static final String MULTIPLY_STRING = "*";
		
	static Operator fromString2Operator(String opStr){
		Operator op = null;
		if(opStr.equals(PLUS_STRING)){
			op = Operator.PLUS;
		}else if(opStr.equals(MINUS_STRING)){
			op = Operator.MINUS;
		}else if(opStr.equals(MULTIPLY)){
			op = Operator.MULTIPLY;
		}
		return op;
	}
	
	public Operand calcuate(Operand o1, Operand o2){
		Operand result = null;
		switch(this){
		case PLUS:
			result = Operand.add(o1, o2);
			break;
		case MINUS:
			result = Operand.subtract(o1, o2);
			break;
		case MULTIPLY:
			result = Operand.multiply(o1, o2);
		}
		return result;
	}
}

class Operand{
	static final int INVALID_VALUE = -1;
	static final int MAX = 10;
	static final int MIN = 0;
	
	static Map<String, Integer> string2intMap;
	static Map<Integer, String> int2stringMap;
		
	int value;
	
	static{
		string2intMap = new HashMap<String, Integer>();
		string2intMap.put( "zero",	0);
		string2intMap.put(  "one",	1);
		string2intMap.put(  "two",	2);
		string2intMap.put("three",	3);
		string2intMap.put( "four",	4);
		string2intMap.put( "five",	5);
		string2intMap.put(  "six",	6);
		string2intMap.put("seven",	7);
		string2intMap.put("eight",	8);
		string2intMap.put( "nine",	9);
		string2intMap.put(  "ten", 10);
		
		int2stringMap = new HashMap<Integer, String>();
		int2stringMap.put(0, "zero");
		int2stringMap.put(1, "one");
		int2stringMap.put(2, "two");
		int2stringMap.put(3, "three");
		int2stringMap.put(4, "four");
		int2stringMap.put(5, "five");
		int2stringMap.put(6, "six");
		int2stringMap.put(7, "seven");
		int2stringMap.put(8, "eight");
		int2stringMap.put(9, "nine");
		int2stringMap.put(10,"ten");		
	}
	
	public Operand(String str){
		if( string2intMap.containsKey(str.trim()) ){
			value = string2intMap.get(str);
		}else{
			value = INVALID_VALUE;
		}
	}
	
	public Operand(int value){
		if( value < MIN || value > MAX ){
			value = INVALID_VALUE;
		}
		this.value = value;
	}
	
	public String toString(){
		return int2stringMap.get(value);
	}
	
	public int getValue(){
		return value;
	}
	
	public static Operand add(Operand o1, Operand o2){
		return new Operand(o1.value + o2.value);
	}
	
	public static Operand subtract(Operand o1, Operand o2){
		return new Operand(o1.value - o2.value);
	}
	
	public static Operand multiply(Operand o1, Operand o2){
		return new Operand(o1.value * o2.value);
	}
	
	
	
	@Override
	public int hashCode() {		
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operand other = (Operand) obj;
		if (value != other.value)
			return false;
		return true;
	}
}

class Equation{
	static Map<String, String> alphaSortedKeyMap = new HashMap<String, String>();
	
	static{
		Set<String> keyset = Operand.string2intMap.keySet();
		Iterator<String> it = keyset.iterator();
		while(it.hasNext()){
			String key = it.next();
			char [] keyCharArr = key.toCharArray();
			Arrays.sort(keyCharArr);
			alphaSortedKeyMap.put(new String(keyCharArr), key);
		}
	}
	
	Operand lOperand;
	Operand rOperand;
	Operator op;
	Operand answer;
	
	public Equation(){}
	public Equation(String eqStr){
		parseEquation(eqStr);
	}
		
	public boolean parseEquation(String eqStr){
		int opIndex;
		if( (opIndex = eqStr.indexOf(Operator.PLUS_STRING)) > 0 ){
			op = Operator.PLUS;
		}else if( (opIndex = eqStr.indexOf(Operator.MINUS_STRING)) > 0 ){
			op = Operator.MINUS;
		}else if( (opIndex = eqStr.indexOf(Operator.MULTIPLY_STRING)) > 0 ){
			op = Operator.MULTIPLY;
		}
		
		lOperand = new Operand(eqStr.substring(0, opIndex).trim());
		
		int equalIndex = eqStr.indexOf('=');
		rOperand = new Operand(eqStr.substring(opIndex+1, equalIndex).trim());
		
		String keyMixed = eqStr.substring(equalIndex+1).trim();
		char [] charKey = keyMixed.toCharArray();
		Arrays.sort(charKey);
		String keySorted = new String(charKey);
		if( alphaSortedKeyMap.containsKey(keySorted) == false ){
			lOperand = null;
			rOperand = null;
			op = null;
			answer = null;
			return false;
		}
		answer = new Operand(alphaSortedKeyMap.get(keySorted));
		return true;
	}
	
	public boolean isRight(){
		Operand rightAnswer = op.calcuate(lOperand, rOperand);
		return answer.equals(rightAnswer);		
	}
}