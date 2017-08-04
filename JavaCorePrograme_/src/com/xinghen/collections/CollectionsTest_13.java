package com.xinghen.collections;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CollectionsTest_13 {
	
	public static void main(String[] args) {
		
		/*collections views*/ 

//		return a array list view, can't operate sv, if write sv.add()  av.remove() will
//		get an syntax error 
		
		String[] s = {"222", "333", "444","111"};
		List<String> sv = Arrays.asList(s);
		
//		copy string "test" as ten times, return a list view ,can not add,remove 
		List<String> ncps = Collections.nCopies(10, "test");
//		ncps.add("bbb");   // can not add object to copied list
		System.out.println(ncps.getClass());
		
//		checklist view ,can check added object type , when add miss type object
//		the vm will take an error 
		ArrayList st = new ArrayList<String>(){{add("111"); add("222");}};
		List checkedList = Collections.checkedList(st, String.class);
//		checkedList.add(new Date()); // there an error while add miss type into checklist 
//		assert checkedList.get(2) instanceof String;
		
//		get a unmodifiable list
		List unmodifiableList = Collections.unmodifiableList(st);
		
//		get an singleton list,set,map
		Set singleton = Collections.singleton("111");
		List singletonList = Collections.singletonList("222");
		System.out.println(singletonList);
		System.out.println(singleton);
		
//		get an sublist , includes first index  excludes second index
		List subList = st.subList(1, 2);
		
//		return a synchronized list ,thread safe collections
		List synchronizedList = Collections.synchronizedList(st);
		
//		array and arrayList transform to eachother 
		List<String> asList = Arrays.asList(s);
//		String[] array = (String[]) st.toArray(); //just can transform to object array ,can not cast to other type
		
//		String[] as = st.toArray(new String [0]);
		
//		System.out.println(st.get(-1));
//		Collections.binarySearch(list, key);
		List<String> items = CollectionsTest_13.getAllItems(s);
		System.out.println(items);
		
	}
	
	/**
	* @Title: getAllItems
	* @Description: 返回不可修改的列表
	* @param @param st
	* @param @return    
	* @return List<String>    
	* @throws
	*/ 
	public static List <String>getAllItems(final String [] st){
		return new AbstractList<String>() {
			@Override
			public String get(int index) {
				return st[index];
			}

			@Override
			public int size() {
				return st.length;
			}
			
		};
		
	}

	
	/**
	* @Title: max
	* @Description: common collection compare algorithm
	* @param @param c
	* @param @return    
	* @return T    
	* @throws
	*/ 
	public static <T extends Comparable<T>> T  max(Collection<T> c){
		Iterator<T> itr = c.iterator();
		T max = itr.next();
		while(itr.hasNext()){
			T nextItem = itr.next();
			if(nextItem.compareTo(max) > 0){
				max = nextItem;
			}
		}
		return max;
	}
	
	
}
