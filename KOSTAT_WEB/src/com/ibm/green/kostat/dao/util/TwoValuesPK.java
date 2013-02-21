package com.ibm.green.kostat.dao.util;

import java.io.Serializable;

/**
 * The class that represents a primary key composed of two value combination.
 * 
 * <p> It provides it own <code>hashCode()</code> and <code>equals(Object o)</code>. 
 */
public class TwoValuesPK<T1, T2> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	T1 pkPartOne;
	T2 pkPartTwo;
	
	public TwoValuesPK(T1 part1, T2 part2) {
		this.pkPartOne = part1;
		this.pkPartTwo = part2;
	}
	
	public T1 getPKPartOne() {
		return this.pkPartOne;
	}
	
	public T2 getPKPartTwo() {
		return this.pkPartTwo;
	}

	@Override
	public int hashCode() {

		return pkPartOne.hashCode() + pkPartTwo.hashCode();
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof TwoValuesPK) {
			TwoValuesPK<?, ?> opk = (TwoValuesPK<?,?>)o;
			
			if ((pkPartOne.equals(opk.getPKPartOne())) && (pkPartTwo.equals(opk.getPKPartTwo()))) {
				return true;
			}
		}
		
		return super.equals(o);
	}

	
}
