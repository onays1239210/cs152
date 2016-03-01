package edu.sjsu.fwjs;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class test {

	public static void main(String[] args) {
		Hashtable<Integer, String> map = new Hashtable<Integer,String>();
		map.put(21, "Twenty One"); 
		map.put(31, "Thirty One"); 
		map.put(41, "Thirty One"); 
		
		//int[] key = map.getkeys();
		List<Integer> aaa = new ArrayList<Integer> ();

		map.put(21, "aaaaaaaaaaaaa");
		System.out.println(map);
		System.out.println(map.get(1));
		
		
		aaa.add(1);
		aaa.add(2);
		aaa.add(3);
		
		System.out.println(aaa);
		System.out.println(aaa.size());
		System.out.println(aaa.get(0));
		
		System.out.println(4%3);
		

	}

}
