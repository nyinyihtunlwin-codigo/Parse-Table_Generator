package sourceCodes;

import java.util.ArrayList;

public class Production {
	String left;
	ArrayList<String> right=new ArrayList<String>();
	ArrayList<String> firstplus=new ArrayList<String>();
	int pno;
	public void setright(String arg0){
		right.add(arg0);
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public ArrayList<String> getRight() {
		return right;
	}
	public void setRight(ArrayList<String> right) {
		this.right = right;
	}
	public ArrayList<String> getFirstplus() {
		return firstplus;
	}
	public void setFirstplus(ArrayList<String> firstplus) {
		this.firstplus = firstplus;
	}

}
