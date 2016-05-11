package Block;

import java.util.ArrayList;
import java.util.Collections;

import Board.Board;

public class SetOfBlocks {
	public ArrayList<Block> set;

	public SetOfBlocks(ArrayList<Block> blocks) {
		set = blocks;
	}

	public void sorting() {
		Collections.sort(set);
	}

	public void add(Block block) {
		set.add(block);
		sorting();		
	}

	public String getColor(int i) {
		return set.get(i).getColor();
	}

	public void addJoker(Block block, int loc) {
		double near;
		if (loc == 0) { // �� �տ� �����ϸ�
			near = set.get(loc).getNum();
			block.setJoker(near - 0.5);
			add(block);
		} else { // �� �ڿ� �����ϸ�
			near = set.get(loc - 1).getNum();
			block.setJoker(near + 0.5);
			add(block);
		}
		sorting();
	}

	public int getNumberOfBlocks() {
		return set.size();
	}

	public String[] show() {
		String img[] = new String[set.size()];
		for (int i = 0; i < set.size(); i++) {
			img[i] = "../images/" + set.get(i).toString();
		}
		return img;
	}

	public String[] showOthers() {
		String img[] = new String[set.size()];
		for (int i = 0; i < set.size(); i++) {
			img[i] = "../images/" + set.get(i).toStringOthers();
		}
		return img;
	}

	public Block delete(int loc) {
		return set.remove(loc);
	}

	public boolean compareNum(int loc, String num) {
		if (set.get(loc).isJoker()) {
			if (num.equalsIgnoreCase("Joker")) {
				set.get(loc).Open();
				return true;
			} else
				return false;
		} else {
			if (set.get(loc).getNum() == Integer.parseInt(num)) {
				set.get(loc).Open();
				return true;
			} else
				return false;
		}
	}
	public String getStringMessage(){
		String message="";
		int i;
		for(i=0;i<set.size();i++){
			message += set.get(i).getMessage()+" ";
		}
		return message;
	}
	public double getBlockNum(int loc){
		return set.get(loc).getNum();
	}
	public void openBlock(int loc){
		set.get(loc).Open();
	}
	
	public String getBlockInfo(Block block){
		String message="";
		for(int i=0;i<set.size();i++){
			if(set.get(i)==block){
				message = i+"_"+block.getColor()+"_"+block.getNum();	//��ġ ���� ��ȣ
				break;
			}
		}
		return message;
	}
	
	public void addBlock(int loc,Block block){
		set.add(loc, block);
	}

	public boolean getOpenState(int loc) {
		if(set.get(loc).isOpen())
			return true;
		else
			return false;
	}
}
