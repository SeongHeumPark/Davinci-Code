package Block;

import java.util.Random;

public class PackOfBlocks {
	public static final int NUMBER_OF_BLOCKS = 26;

	private Block[] pack;
	private int currentBlock;

	public PackOfBlocks() {
		String[] colors = { "black", "white" };
		pack = new Block[NUMBER_OF_BLOCKS];
		for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
			pack[i] = new Block(colors[i % 2], i / 2);
		}
		shuffle();
	}

	public void shuffle() {
		Random randomNumbers = new Random();
		currentBlock = 0;
		for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
			int j = randomNumbers.nextInt(NUMBER_OF_BLOCKS);
			Block temp = pack[i];
			pack[i] = pack[j];
			pack[j] = temp;
		}
	}

	public Block getABlock() {
		if (currentBlock < NUMBER_OF_BLOCKS) {
			return pack[currentBlock++];
		} else
			return null;
	}
	
	public boolean isEmpty(){
		if(currentBlock==NUMBER_OF_BLOCKS){
			return true;
		}
		else 
			return false;
	}
	
	public String[] show(){
		String img[] = new String[pack.length];
		for(int i=0;i<pack.length;i++){
			System.out.print(pack[i].toString()+"\n");
			img[i] = "../images/"+pack[i].toString();
		}
		return img;
	}
}
