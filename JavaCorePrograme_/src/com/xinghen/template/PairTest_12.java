package com.xinghen.template;

import java.util.ArrayList;
import java.util.Date;

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
		ArrayAlg.minMax(doubleItems);
		//ArrayAlg.minMax(objectItems); //error,The inferred type Object is not a valid substitute for
										//the bounded parameter <T extends Comparable>
		
		System.out.println(pair.getFirst());
		System.out.println(pair.getSecond());
	}

}
