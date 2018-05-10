package com.edplan.test.osbplayer;

import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		/*
		 System.out.println("Hello World!");
		 System.out.print("Enter a number: ");
		 double number1 = input.nextDouble();

		 System.out.print("Enter second number: ");
		 double number2 = input.nextDouble();

		 double product = number1 * number2;
		 System.out.printf("The product of both numbers is: %f", product);
		 */
		Scanner input = new Scanner(System.in);

		vertex[] list=new vertex[1000000];
		for(int i=0;i<list.length;i++){
			list[i]=new vertex();
			double s=Math.sin(i);
			double c=Math.cos(i);
		}
		list=null;
		System.gc();
		int i=0;
		while(true){
			input.nextLine();
			{
				Main m=new Main();
				m.main();
			}
			System.gc();
			i++;
			if(i==10)break;
		}

		i=0;
		while(true){
			input.nextLine();
			{
				Main m=new Main();
				m.main();
			}
			System.gc();
			i++;
			if(i==10)break;
		}


	}


	static class vertex {
		float x;
		float y;
		float z;
		float u;
		float v;
		float color;
	}
	static class vec2{
		float x;
		float y;
	}
	vertex[] Vertex=new vertex[10000];

	/*
	 float x11 = 0;
	 float y11 = 0;
	 float x22 = 1;
	 float y22 = 1;
	 float x33 = 0;
	 float y33 = 1;
	 float x44 = 1;
	 float y44 = 0;
	 */

	vec2 v1=new vec2(),v2=new vec2(),v3=new vec2(),v4=new vec2();

	int length = 100;
	int idx = 0;

	void addVertex(float x, float y, float z, float u, float v,float c)
	{
		if(Vertex[idx]==null)
			Vertex[idx]=new vertex();
		Vertex[idx].x = x;
		Vertex[idx].y = y;
		Vertex[idx].z = z;
		Vertex[idx].u = u;
		Vertex[idx].v = v;
		Vertex[idx].color = c;
		idx++;
		idx %= length;
	}

	void rotate(vec2 v){
		double s = Math.sin(0.2);
		double c = Math.cos(0.2);
		float ox = 0, oy = 0;
		float dx = v.x - ox;
		float dy = v.y - oy;
		v.x =(float)(ox + dx * s - dy * c);
		v.y =(float)(oy + dx * c + dy * s);
	}

	void rotate(){
		rotate(v1);
		rotate(v2);
		rotate(v3);
		rotate(v4);
	}


	void draw()
	{
		rotate();
		addVertex(v1.x,v1.y,0,1,0,100);
		addVertex(v2.x,v2.y,0,1,0,100);
		addVertex(v3.x,v3.y,0,1,0,100);
		addVertex(v1.x,v1.y,0,1,0,100);
		addVertex(v3.x,v3.y,0,1,0,100);
		addVertex(v4.x,v4.y,0,1,0,100);
	}

	public static double gt(){
		//return System.currentTimeMillis();
		return System.nanoTime()/1000000d;
	}

	int main()
	{
		/*
		 for(int i=0;i<Vertex.length;i++){
		 Vertex[i]=new vertex();
		 }
		 */
		/*
		 try
		 {
		 Thread.currentThread().sleep(30);
		 }
		 catch (InterruptedException e)
		 {}
		 */

		double time=gt();//System.nanoTime()/1000000d;
		for (int i = 0; i < 10000; i++) {
			draw();
		}
		System.out.println(gt()-time+"ms");
		/*
		 end = clock();
		 cout <<1000*((double)(end-start))/CLOCKS_PER_SEC<< endl;
		 */
		return 0;
	}
}
