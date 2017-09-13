package sourceCodes;

import java.util.ArrayList;

public class NonTerminal {
	String name;
	int no;
	
	ArrayList<Production> p=new ArrayList<Production>();
	ArrayList<String> first=new ArrayList<String>();
	ArrayList<String> follow=new ArrayList<String>();
	public void setproduction(Production arg0){
		p.add(arg0);
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Production> getProduction() {
		return p;
	}
	public void setP(ArrayList<Production> p) {
		this.p = p;
	}
	public ArrayList<String> getFirst() {
		return first;
	}
	public void setFirst(ArrayList<String> first) {
		this.first = first;
	}
	public ArrayList<String> getFollow() {
		return follow;
	}
	public void setFollow(ArrayList<String> follow) {
		this.follow = follow;
	}
}
