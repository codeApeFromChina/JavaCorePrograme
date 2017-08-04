package com.xinghen.template;

class Food {
}

class Fruit extends Food {
}

class Meat extends Food {
}

class Apple extends Fruit {
}

class Banana extends Fruit {
}

class Beef extends Meat {
}

class ReadApple extends Apple {
}

class Plate<T> {
	private T item;

	public Plate(T item) {
		super();
		this.item = item;
	}

	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}
}

public class FruitTest {

	public static void main(String[] args) {
		Plate<Apple> p = new Plate<Apple>(new Apple());
		Plate<? extends Apple> p2 = new Plate<Apple>(new ReadApple());
		p2.getItem();
//		p2.setItem(new Apple());
		
		
		Plate<? super Apple> p3 = new Plate<Apple>(new ReadApple());
		p3.setItem(new ReadApple());
		
	}

}
