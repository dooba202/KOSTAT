package com.ibm.green.kostat.dao.util;

import java.io.Serializable;

public class ThreeValuesPK<T1, T2, T3> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	T1 pkPartOne;
	T2 pkPartTwo;
	T3 pkPartThree;
	
	public ThreeValuesPK(T1 pk1, T2 pk2, T3 pk3) {
		this.pkPartOne = pk1;
		this.pkPartTwo = pk2;
		this.pkPartThree = pk3;
	}

	public T1 getPKPartOne() {
		return pkPartOne;
	}

	public T2 getPKPartTwo() {
		return pkPartTwo;
	}

	public T3 getPKPartThree() {
		return pkPartThree;
	}

	@Override
	public int hashCode() {
		
		return pkPartOne.hashCode() + pkPartTwo.hashCode() + pkPartThree.hashCode();
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof ThreeValuesPK) {
			ThreeValuesPK<?, ?, ? > opk = (ThreeValuesPK<?,?, ?>)o;
			
			if ((pkPartOne.equals(opk.getPKPartOne())) 
				&& (pkPartTwo.equals(opk.getPKPartTwo())) 
				&& (pkPartThree.equals(opk.getPKPartThree()))) {
				return true;
			}
		}
		
		return super.equals(o);
	}	
}
