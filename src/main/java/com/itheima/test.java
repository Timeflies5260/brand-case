package com.itheima;

public class test {
    public static void main(String args[]) {
        String Str =" ";
        String Str1=Str.trim();
        if(Str1.equals(" ") ||Str1==null){
            System.out.println("yes");
        }
        System.out.println(Str.length());
        System.out.print("原始值 :" );
        System.out.println( Str );

        Str=Str.trim();
        System.out.print("删除头尾空白 :" );
       System.out.println( Str );
       if(Str.equals(" ") ||Str==null){
           System.out.println("null");
       }
        System.out.println(Str.length());
    }
}