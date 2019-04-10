package com.www.calculate;

import java.util.*;

public class CreateQuestion {

    Map<String, String> map = new HashMap<String, String>();
    Map<String, String> brackets = new HashMap<String, String>();

    public CreateQuestion() {
        map.put(String.valueOf(1), "+");
        map.put(String.valueOf(2), "-");
        map.put(String.valueOf(3), "*");
        map.put(String.valueOf(4), "÷");
        brackets.put(String.valueOf(1), "(");
        brackets.put(String.valueOf(1), ")");
    }

//	public static void main(String[] args) {
//		int i;
//		CreateQuestion question = new CreateQuestion();
//		List<String> list = new ArrayList<String>();
//		for( i=0;i<20;i++ ){
//			question.CreateInteger();
//		}
//	}

    public List<String> Create(int ran, int num, int bound) {
        if (ran == 0) {
            return CreateInteger(num, bound);//list中最后一个值返回0代表整数式子
        } else if (ran == 1) {
            return CreatFraction(num, bound);//list中最后一个值返回1代表分数式子
        } else {
            return CreateBrackets(num, bound);
        }
    }

    //生成整数题目的函数
    public List<String> CreateInteger(int num, int bound) {
        int i;
        int data1, data2;//分别存储操作数和运算符
        Random r = new Random();
        int[] sign = new int[num];//存储式子中的运算符
        List<String> list = new ArrayList<String>();
        for (i = 0; i < num; i++) {
            data1 = r.nextInt(bound + 1);//生成操作数
            data2 = r.nextInt(4) + 1;//生成运算符
            sign[i] = data2;
            list.add(String.valueOf(data1));
            list.add(new CreateQuestion().map.get(String.valueOf(data2)));//加入运算符
        }
        //打印最后一个操作数
        data1 = r.nextInt(bound + 1);
        list.add(String.valueOf(data1));
        if (Util.isSingle(sign, num)) {
            return CreateInteger(num, bound);
        }
        //打印list中的元素实验
//		for( i=0;i<list.size();i++ ){
//			System.out.print(list.get(i));
//		}
//		System.out.println();
        list.add(String.valueOf(0));
        return list;
    }

    //生成真分数题目的函数
    public List<String> CreatFraction(int num, int bound) {
        int i;
        int numerator, denominator;//分别存储操作数和运算符
        int sign;//存储运算符
        Random r = new Random();
        List<String> list = new ArrayList<String>();
        for (i = 0; i < num; i++) {
            denominator = r.nextInt(bound + 1);//生成分母
            numerator = r.nextInt(denominator + 1);//生成分子
            while (!Util.isLegalFraction(numerator, denominator)) {
                denominator = r.nextInt(bound + 1);
                numerator = r.nextInt(denominator + 1);
            }
            sign = r.nextInt(2) + 1;//生成运算符
            list.add(String.valueOf(numerator));
            list.add("/");
            list.add(String.valueOf(denominator));
            list.add(new CreateQuestion().map.get(String.valueOf(sign)));
        }
        //生成最后一个分数
        denominator = r.nextInt(bound + 1);//生成分母
        numerator = r.nextInt(denominator + 1);//生成分子
        while (!Util.isLegalFraction(numerator, denominator)) {
            denominator = r.nextInt(bound + 1);
            numerator = r.nextInt(denominator + 1);
        }
        sign = r.nextInt(2) + 1;//生成运算符
        list.add(String.valueOf(numerator));
        list.add("/");
        list.add(String.valueOf(denominator));
        list.add(String.valueOf(1));

        return list;
    }

    public List<String> CreateBrackets(int num, int bound) {
        int i;
        int s = 0;//保证至少有两个括号
        int data1, data2;//分别存储操作数和运算符
        Random r = new Random();
        int[] sign = new int[num];
        int recording = 0;//用来记录括号的信息，若为整数则表示左括号个数，若为负数则表示右括号个数
        boolean bool = false;//用来防止出现括号包裹一个数字的情况
        List<String> list = new ArrayList<String>();
        for (i = 0; i < num; i++) {
            data1 = r.nextInt(bound + 1);//生成操作数
            data2 = r.nextInt(4) + 1;//生成运算符
            sign[i] = data2;
            if (r.nextInt(2) == 0 && i != 0) {//控制最外层的无效括号
                list.add("(");//添加左括号
                recording++;
                bool = true;
                s++;
            }
            list.add(String.valueOf(data1));//添加操作数
            if (r.nextInt(2) == 1 && recording > 0 && bool != true) {
                list.add(")");//添加右括号
                recording--;
            }
            bool = false;
            list.add(new CreateQuestion().map.get(String.valueOf(data2)));//添加操作符
        }
        //打印最后一个操作数
        data1 = r.nextInt(bound + 1);
        list.add(String.valueOf(data1));
        for (recording = recording; recording > 0; recording--) {
            list.add(")");
        }
        if (s == 0) {
            for (i = 0; i < 2; i++) {
                data1 = r.nextInt(bound + 1);
                data2 = r.nextInt(4) + 1;
                list.add(new CreateQuestion().map.get(String.valueOf(data2)));
                list.add("(");
                list.add(String.valueOf(data1));
                list.add(new CreateQuestion().map.get(String.valueOf(data2)));
                list.add(String.valueOf(data1));
                list.add(")");
            }
        } else if (s == 1) {
            data1 = r.nextInt(bound + 1);
            data2 = r.nextInt(4) + 1;
            list.add(new CreateQuestion().map.get(String.valueOf(data2)));
            list.add("(");
            list.add(String.valueOf(data1));
            list.add(new CreateQuestion().map.get(String.valueOf(data2)));
            list.add(String.valueOf(data1));
            list.add(")");
        }
        if (Util.isSingle(sign, num)) {
            return CreateBrackets(num, bound);
        }
//        list.add("=");
        list.add(String.valueOf(2));

        return list;

    }

}
