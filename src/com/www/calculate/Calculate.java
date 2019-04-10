package com.www.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
 * 1.如何实现运算的优先级
 * 2.如何控制必须有两种运算符
 */

public class Calculate {
	//测试代码
//	public static void main(String[] args) {
//		
//		Calculate cal = new Calculate();
//		List<String> list = new ArrayList<String>();
//		list.add("(");
//		list.add("57");
//		list.add("+");
//		list.add("(");
//		list.add("21");
//		list.add("÷");
//		list.add("20");
//		list.add(")");
//		list.add("+");
//		list.add("26");
//		list.add("*");
//		list.add("10");
//		list.add("=");
//		list.add("2");
////		cal.calculateBrackets(list);
//		System.out.println( cal.calculate(list).get(0) );
//		System.out.println( cal.calculate(list).get(1) );
//		//cal.calculateFraction(list);
//	}

	public List<Integer> calculate( List<String> list ){
		int i = Integer.parseInt(list.get(list.size()-1));
		if( i == 0 ){
			return calculateInteger(list);
		}else if( i == 1 ){
			return calculateFraction(list);
		}else{
			return calculateBrackets(list);
		}
	}
	//整数式子的计算
	public List<Integer> calculateInteger( List<String> list ){
		//将list中的元素放入两个不同的栈
		List<Integer> signs = new ArrayList<Integer>();//用来返回计算结果或者运算过程是否合法的集合类
		signs.add(Integer.valueOf(0));//第一个参数存储运算过程是否合法的信息,默认为0为合法
		int i;
		Stack<Integer> sNumber = new Stack<Integer>();//存储运算值的堆栈,其中的类型为Integer
		Stack<String> sSign = new Stack<String>();//存储运算符的堆栈，其中的类型为String

		for( i=list.size()-2;i>=0;i-- ){
			try{
				Integer integer = Integer.valueOf( list.get(i) );
				sNumber.push(integer);
			}catch( Exception e ){
				sSign.push( list.get(i) );
			}
		}

//		Util.printStack(sNumber);
//		System.out.println();
//		Util.printStack(sSign);

		//具体运算部分
		Stack<Integer> sRes1 = new Stack<Integer>();//存储中间结果操作数的堆栈
		Stack<String> sRes2 = new Stack<String>();//存储中间结果运算符的堆栈
		String sign;//用来存储中间的运算符
		double operand1,operand2;//运算的两个运算符，其中结果存储在第一个操作数
		while( !sSign.isEmpty() ){
			sign = sSign.pop();
			if( sRes1.isEmpty() ){
				sRes1.push( sNumber.pop() );
			}

			if( sign == "*" ){
				operand1 = sNumber.pop();
				operand2 = ( Integer )sRes1.pop();
				operand1 = operand1*operand2;
//				System.out.println("计算乘法结果为"+operand1);
				sRes1.push(Integer.valueOf((int)operand1));
			}

			if( sign == "÷" ){
				operand1 = sNumber.pop();
				operand2 = ( Integer )sRes1.pop();
				operand1 = operand2/operand1;
				if( Util.isLeagal(operand1) ){
					sRes1.push(Integer.valueOf((int)operand1));
				}else{
					signs.set(0,Integer.valueOf(1));
					return signs;
				}
			}

			if( sign == "+"||sign == "-" ){
				sRes2.push(sign);
				sRes1.push( sNumber.pop() );
			}

		}
		Util.reverseStack(sRes1);
		Util.reverseStack(sRes2);

		while( !sRes2.empty() ){//注意减法的被减数和减数
			sign = ( String )sRes2.pop();
			operand1 = sRes1.pop();
			operand2 = sRes1.pop();
			if( sign == "+" ){
				operand1 = operand1+operand2;
				sRes1.push((int)operand1);
			}
			if( sign == "-" ){
				operand1 = operand1-operand2;
				if( Util.isLeagal(operand1) ){
					sRes1.push((int)operand1);
				}else{
					signs.set(0,Integer.valueOf(1));
					return signs;
				}
			}
		}
		signs.add(sRes1.pop());

		return signs;
	}
	//分数式子的计算
	public List<Integer> calculateFraction( List<String> list ){//list中最后一位值为标志位
		int i,j;
		int n = list.size();
		List<Integer> signs = new ArrayList<Integer>();//用来返回计算结果或者运算过程是否合法的集合类
		signs.add(Integer.valueOf(0));//第一个参数存储运算过程是否合法的信息,默认为0为合法
		int[] num = new int[n];//存储分子的数组
		int[] den = new int[n];//存储分母的数组
		List<String> signList = new ArrayList<String>();
		//将算式中的分子、分母、运算符分别放到三个存储结构当中
		for( i=0,j=1;i<n-1;i=i+4,j++ ){
			num[j] = Integer.parseInt(list.get(i));
		}

		for( i=2,j=1;i<n-1;i=i+4,j++ ){
			den[j] = Integer.parseInt(list.get(i));
		}

		for( i=3;i<n-1;i=i+4 ){
			signList.add(list.get(i));
		}

//		//打印验证代码
//		for( i=0;i<signList.size();i++ ){
//			System.out.println(signList.get(i));
//		}

		//正式计算部分
		int multiple;//用来存储最小公倍数
		int data0,data1;
		num[0] = num[1];
		den[0] = den[1];
		for( i=2;i<=signList.size()+1;i++ ){
			multiple = Util.minMultiple(den[0], den[i]);
			data0 = num[0]*(multiple/den[0]);
			data1 = num[i]*(multiple/den[i]);
			den[0] = multiple;
			if( signList.get(i-2) == "+" ){
				num[0] = data0+data1;
				if( num[0]>den[0] ){
					signs.set(0, Integer.valueOf(1));//运算过程中的非法情况:负数出现
				}
			}else if( signList.get(i-2) == "-" ){
				num[0] = data0-data1;
				if( num[0]<0 ){
					signs.set(0, Integer.valueOf(1));//运算过程中的非法情况:负数出现
				}
			}
		}
		if( den[0]>200||num[0]<0||den[0]<0 ){
			signs.set(0, Integer.valueOf(1));//分母太大的情况舍去
		}
		List<Integer> interRes = Util.simFraction(num[0], den[0]);
		signs.add(interRes.get(0));//加入分子
		signs.add(interRes.get(1));//加入分母

		return signs;
	}
	//带括号式子的计算
	public List<Integer> calculateBrackets( List<String> list ){
		int i;
		Stack<Integer> numStack = new Stack<Integer>();//用来存放操作数的堆栈
		Stack<String> symStack = new Stack<String>();//用来存放运算符的堆栈
		List<Integer> signs = new ArrayList<Integer>();//用来返回计算结果或者运算过程是否合法的集合类
		signs.add(Integer.valueOf(0));//第一个参数存储运算过程是否合法的信息,默认为0为合法
		for( i=0;i<list.size()-1;i++ ){
			String str = list.get(i);
			try{
				Integer integer = Integer.valueOf( list.get(i) );
				numStack.push(integer);
			}catch( Exception e ){
				while( !Util.comparePri(symStack,str)&&!symStack.empty() ){
					int b = numStack.pop().intValue(); // 出栈，取出数字，后进先出
                    int a = numStack.pop().intValue();
                    switch ((String) symStack.pop()) {
                    case "+":
                        numStack.push(a + b);
                        break;
                    case "-":
                    	if( Util.isLeagal(a-b) ){
                    		numStack.push(a - b);
                    	}else{
                    		signs.set(0,Integer.valueOf(1));
        					return signs;
                    	}
                        break;
                    case "*":
                        numStack.push(a * b);
                        break;
                    case "÷":
                    	double data = (double)a/b;
//                    	System.out.println(a+"/"+b+"="+data);
                    	if( Util.isLeagal(data) ){
                    		numStack.push((int)data);
                    	}else{
                    		signs.set(0,Integer.valueOf(1));
        					return signs;
                    	}
                        break;
                    default:
                        break;
                    }
				}
				if (str != "=") {
                    symStack.push(str); // 符号入栈
                    if (str == ")") { // 去括号
                        symStack.pop();
                        symStack.pop();
                    }
                }
			}
		}
//		Util.printStack(numStack);
//		System.out.println();
//		Util.printStack(symStack);
//		System.out.println("结果为： "+numStack.pop());
		signs.add(numStack.pop()); // 返回计算结果

		return signs;
	}

}
