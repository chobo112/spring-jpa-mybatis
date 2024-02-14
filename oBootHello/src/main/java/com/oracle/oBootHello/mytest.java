package com.oracle.oBootHello;

class paraPassing {
	public void change(int i, int[] j) {
		i = 20;
		j[3] = 400;
		System.out.println("change메서드안에서는 : "+i);
		System.out.println("change메서드안에서는 : "+j[3]);
	}

	public void display(int i, int [] j) {
		System.out.println("i : "+i);
		System.out.println("j");
		for(int k=0; k<j.length; k++)
			System.out.println(j[k]+"");
		
	}
	//밑에 메인
	public static void main(String[] args) {
		paraPassing pp = new paraPassing();
		int i=10, j[]= {1,2,3,4};
		System.out.println("change메서드 호출전은 : "+i);
		pp.change(i, j); //10 -> 20
		System.out.println("change메서드 호출후 에는 : "+i);
		pp.display(i, j); // 10
	}
	
}
