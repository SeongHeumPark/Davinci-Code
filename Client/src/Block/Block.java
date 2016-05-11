package Block;

public class Block implements Comparable<Block> {
	private String color;
	private double number;
	private boolean open;
	private boolean isJoker;

	public Block(String color, double number) {
		this.color = color;
		this.number = number;
		open = false;
		if(number==12){
			isJoker=true;
		}else
			isJoker=false;
	}

	public double getNum() {
		return number;
	}

	public String getColor() {
		return color;
	}

	public boolean select(double num) {
		if (number == num) {
			open = true;
			return true;
		} else
			return false;
	}

	public boolean isOpen() {
		return open;
	}

	public void Open(){
		open=true;
	}
	public void setJoker(double num) {
		number = num;
		isJoker=true;
	}

	public String toString() {
		if ((number - (int) number != 0) || number == 12) {
			return color + "_JOKER.jpg";
		} else
			return color + "_" + (int) number + ".jpg";
	}

	public String toStringOthers() {
		if (open) {
			return toString();
		} else {
			return color + "_back.jpg";
		}
	}
	
	public boolean isJoker(){
		if(number==12){
			isJoker=true;
			return isJoker;
		}
		if(number - (int)number == 0){
			isJoker=false;
		}else{
			isJoker=true;
		}
			return isJoker;		
	}

	@Override
	public int compareTo(Block o) {
		double result = number - o.getNum();
		if (result == 0) {
			if (color.equals("black")) { 
				return -1;
			} else
				return 1;
		} else if (result < 0) {
			return -1;
		} else
			return 1;
	}
	
	public String getMessage(){
		if(color.equals("black")){
			return "b_" + number;
		}else
			return "w_"+number;
	}
}
