package com.xinghen.template;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

//======================employee and manager test ==========================================
class Employee {}
class Manager extends Employee{}

/**
 * @ClassName: Pair
 * @Description: 泛型类
 * @author 赵志强
 * @date 2017年8月1日 下午4:11:40
 */
class Pair<T> {
	private T first;
	private T second;

	public Pair() {
		super();
	}

	public Pair(T first, T second) {
		super();
		this.first = first;
		this.second = second;
	}

	public Boolean equals(T other){
		
		if(this.first.equals(other) && this.second.equals(other)){
			return true;
		}else{
			return false;
		}
	}
	
	public T getFirst() {
		return first;
	}


	public void setFirst(T first) {
		this.first = first;
	}

	public T getSecond() {
		return second;
	}

	public void setSecond(T second) {
		this.second = second;
	}
}

/**
 * @ClassName: ArrayAlg
 * @Description: 通用比较算法
 * @author 赵志强
 * @date 2017年8月1日 下午4:10:42
 */
class ArrayAlg {
	/**
	 * @Title: min
	 * @Description: 泛型方法,extend 限制泛型类型
	 * @param @param a
	 * @param @return
	 * @return T
	 * @throws
	 */
	public static <T extends Comparable> Pair<T> minMax(ArrayList<T> a) {
		if (null == a || a.size() == 0)
			return null;
		else {
			T m1 = a.get(0);
			T m2 = a.get(0);
			for (int i = 0; i < a.size(); i++) {
				if (m1.compareTo(a.get(i)) < 0) {
					m1 = a.get(i);
				}

				if (m2.compareTo(a.get(i)) > 0) {
					m2 = a.get(i);
				}
			}
			return new Pair<T>(m1, m2);
		}
	}
}

class Interval extends Pair<Date>{
	public Interval (Date t1, Date t2){
		super.setFirst(t1);
		super.setSecond(t2);
	}
	
	public Date getSecond(){
		return super.getSecond();
	}
	
	public void setSecond(Date second){
		super.setSecond(second);
	}
}

public class PairTest_12 {

	public static void main(String[] args) {
		
		ArrayList<Date> dateItems = new ArrayList<Date>() {
			{
				add(new Date(2017, 1, 1));
				add(new Date(2017, 1, 2));
				add(new Date(2017, 1, 3));
				add(new Date(2017, 1, 4));
				add(new Date(2017, 1, 5));
				add(new Date(2017, 1, 6));
				add(new Date(2017, 1, 7));
			}
		};
		
		ArrayList<Double> doubleItems = new ArrayList<Double>(){{
			add(111d);
			add(111d);
			add(111d);
			add(111d);
			add(111d);
		}};
		
		ArrayList<Object> objectItems = new ArrayList<Object>(){{
			add(new Object());
			add(new Object());
			add(new Object());
			add(new Object());
			add(new Object());
		}};
		
		Pair<Date> pair = ArrayAlg.minMax(dateItems);
		System.out.println(pair.equals(new Date(2017, 1, 1)));
		ArrayAlg.minMax(doubleItems);
		//ArrayAlg.minMax(objectItems); //error,The inferred type Object is not a valid substitute for
										//the bounded parameter <T extends Comparable>
		Interval i = new Interval(new Date(), new Date());
		System.out.println(i.getSecond());
		Pair <Date> p1 = (Pair <Date> )pair;
		
		//通用类型不能使用instance 
		//if(pair instanceof Pair<String>){
		//	System.out.println(11111);
		//}
		
		//Pair<String>[] p = new Pair<String>[10]; //ERROR ，不能创建参数化类型数组
		Pair<String>[] p = (Pair<String>[]) new Pair<?>[10]; // 可以使用通用类型，再强转
		Pair<?>[] p2 = new Pair<?>[10];//或者直接声明称通用类型
		p2[0] = new Pair<String>();
		ArrayList<Pair<String>> p3 = new ArrayList<Pair<String>>(); //可以使用集合形式创建参数化数据
				
		
		ArrayList<Pair<Date>> arr = null;
		Employee [] e = new Employee [10];
		Manager[] m = new Manager[5];
		e = m;
		e [3] = new Manager();
		
		System.out.println(pair.getFirst());
		System.out.println(pair.getSecond());
		Pair<Manager> ms = new Pair(new Manager(), new Manager());
		
		//TODO 此处还存在一些问题，理解不是很透彻
		Pair<? extends Manager> ms2 = ms;
		Pair<? super Manager> ms3 = ms;
//		ms3.setFirst(new Employee());
		Manager m9 = (Manager) ms3.getFirst();
//		ms2.setFirst(new Manager());
		Manager first = ms2.getFirst();
		
//		Pair pp2 = pair;
//		pp2.setFirst(new File(""));
//		pair.setFirst(new Manager());
		
		
	}

	
	
	
	
	
	
	
	
	
}
