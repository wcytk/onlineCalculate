package com.www.calculate;

import java.util.*;

public class Util {
	int i;
//	public static void main(String[] args) {
//		List<Integer> list = Util.simFraction(100, 200);
//		System.out.println(list.get(0)+"/"+list.get(1));
//	}
//	
	//判断操作数是否合法
	static boolean isLeagal( double number ){
		double eps = 1e-10;
		if( number-Math.floor(number)<eps&&number>0 ){
			return true;
		}else{
			return false;
		}
	}
	//打印堆栈元素
	static void printStack( Stack stack ){
		while( !stack.empty() ){
			System.out.print(stack.pop()+" ");
		}
	}
	//逆转堆栈中的元素
	static void reverseStack( Stack stack ){
		Queue<Object> queue = new LinkedList<Object>();
		Object item = new Object();
		while( !stack.empty() ){
			item = stack.pop();
			queue.offer(item);
		}
		while( !queue.isEmpty() ){
			item = queue.poll();
			stack.push(item);
		}
	}
	//判断运算符是否单一的函数
	static boolean isSingle( int[] a,int n ){
		int i;
		boolean bool = true;
		for( i=0;i<n-1;i++ ){
			if( a[i]!=a[i+1] ){
				bool = false;
			}
		}
		return bool;
	}
	//判断生成的分数是否合法的函数
	static boolean isLegalFraction( int numerator,int denominator ){
		int temp = 1;
		if( numerator>=denominator ){
			return false;
		}
		while( numerator!=0 ){
			temp = denominator%numerator;
			denominator = numerator;
			numerator = temp;
		}
		if( denominator == 1 ){
			return true;
		}else{
			return false;
		}
	}
	//返回计算最大公因数并化简,化简后分子存储在第一个位置，分母存储在第二个位置
	static List<Integer> simFraction( int numerator,int denominator ){
		int temp = 1;
		int num = numerator;
		int den = denominator;
		List<Integer> list = new ArrayList<Integer>();
		while( num!=0 ){//公因数保存在den当中
			temp = den%num;
//			System.out.println(denominator+"%"+numerator+"="+temp);
			den = num;
			num = temp;
//			System.out.println(denominator);
		}
		numerator = numerator/den;
		denominator = denominator/den;
		list.add(Integer.valueOf(numerator));
		list.add(Integer.valueOf(denominator));

		return list;
	}
	//返回最小公倍数
	static int minMultiple( int data1,int data2 ){
		int temp = 1;
		int num = data1;
		int den = data2;
		List<String> list = new ArrayList<String>();
		while( num!=0 ){//公因数保存在den当中
			temp = den%num;
			den = num;
			num = temp;
		}

		return data1*data2/den;
	}
	//返回运算符优先级的情况
	static boolean comparePri( Stack stack,String str ){
		if (stack.empty()) { // 空栈返回ture
            return true;
        }
        String top = (String) stack.peek(); // 查看堆栈顶部的对象，注意不是出栈
        if (top == "(") {//如果栈顶元素为(，则默认当前栈顶元素高
            return true;
        }
        // 比较优先级
        switch (str) {
        case "(": // 优先级最高
            return true;
        case "*": {
            if (top == "+" || top == "-") // 优先级比+和-高
                return true;
            else
                return false;
        }
        case "÷": {
            if (top == "+" || top == "-") // 优先级比+和-高
                return true;
            else
                return false;
        }
        case "+":
            return false;
        case "-":
            return false;
        case ")": // 优先级最低
            return false;
        case "=": // 结束符
            return false;
        default:
            break;
        }

        return true;
	}

}
