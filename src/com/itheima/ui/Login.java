package com.itheima.ui;

import com.itheima.domain.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {
    Scanner sc=new Scanner(System.in);
    public void star(){

        ArrayList<User> list=new ArrayList<>();


        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");


            String choose=sc.next();

            switch (choose){
                case"1"->login(list);
                case"2"->register(list);
                case"3"-> {
                    System.out.println("用户选择了退出操作");
                    System.exit(0);
                }
                default -> System.out.println("输出有误，请重新输入");
            }
        }
    }
//登录
    public void login(ArrayList<User> list){
    /*用户名如果未注册提示：用户名未注册，请先注册
   用户被锁定提示：用户xxx已经锁定，请联系黑马程序员官方客服：XXX-XXXXX
   验证码错误提示：验证码输入错误，请重新输入，并生成一个新的验证码
    判断用户名和密码是否正确，有3次机会，满3次账户锁定。*/
        System.out.println("请输入用户名");
        String TempUsername=sc.next();
        if(!Contains(list,TempUsername)){
            System.out.println("该用户不存在，请先注册再登陆");
            return;
        }
        int index=GetIndex(list,TempUsername);
        if(index==-1){
            System.out.println("用户"+TempUsername+"已经锁定，请联系黑马程序员官方客服：XXX-XXXXX");
        }
        if(!list.get(index).isStatus()){
            System.out.println("用户"+TempUsername+"已经锁定，请联系黑马程序员官方客服：XXX-XXXXX");
            return;
        }

        for (int i=0;i<=2;i++) {
            System.out.println("请输入密码");
            String temppassword=sc.next();
            if(!temppassword.equals(list.get(GetIndex(list,TempUsername)).getPassword())){
                System.out.println("密码错误，还剩"+(2-i)+"次机会");
                if(i==2){
                    System.out.println("用户已锁定，请联系黑马程序员官方客服：XXX-XXXXX");

                    list.get(GetIndex(list,TempUsername)).setStatus(false);
                    return;
                }
                continue;
            }
            while (true) {
                String rightcode=GetCode();
                System.out.println("请输入验证码："+rightcode);
                String code=sc.next();
                if(code.equals(rightcode)){
                    System.out.println("验证通过，登录成功，游戏开始！");
                    FightingGame fg=new FightingGame();
                    fg.star(TempUsername);
                    break;
                }else{
                    System.out.println("验证码错误，请重新输入");
                    continue;
                }
            }
        }



    /*状态state
      默认是可使用状态
      如果密码连续输错3次，修改为不可使用状态*/
    }

//注册
    public void register(ArrayList<User> list){
//创建user--键盘录入--添加到list
        User user=new User();
        Scanner sc=new Scanner(System.in);

       /* 用户名username：
        用户名唯一
        长度必须在3 ~ 16位*/
        while (true) {
            System.out.println("请输入用户名，长度必须在3 ~ 16位，只能由字母、数字组成，不能是纯数字");

            String username=sc.next();
            if(!Checklen(username,3,16)){
                System.out.println("用户名长度必须在3 ~ 16位");
                continue;
            }
            if(!Checkusername(username)){
                System.out.println("只能由字母、数字组成，不能是纯数字");
                continue;
            }
            if (Contains(list,username)) {
                System.out.println("该用户名已存在");
                continue;
            }
            user.setUsername(username);
            break;
        }


        /*
        键盘录入验证码
        登录最多重试三次，三次错误账号锁定
        3，密码要求：
            长度3 ~ 8位
            注册时候，各属性需要满足要求
    密码键盘输入两次，两次一致才可以进行注册*/
        while (true) {
            System.out.println("请输入密码，长度必须在3 ~ 8位，只能是字母加数字的组合，不能有其他字母");

            String password=sc.next();
            if(!Checklen(password,3,8)){
                System.out.println("请重新输入密码，长度必须在3 ~ 8位");
                continue;
            }
            if(!CheckPassword(password)){
                System.out.println("只能是字母加数字的组合，不能有其他字母");
                continue;
            }
            System.out.println("请输入相同密码");
            String temppassword=sc.next();
            if(!password.equals(temppassword)){
                System.out.println("两次密码不一致，请重新输入密码");
                continue;
            }
            user.setPassword(password);
            break;
        }

        list.add(user);
        System.out.println("用户"+user.getUsername()+"注册成功");

    }

//生成验证码
    public static String GetCode(){
     /*  长度为5
        由4位大写或者小写字母和1位数字组成，同一个字母可重复
        数字可以出现在任意位置*/

    //字母库
    ArrayList<Character> list=new ArrayList<>();
    for (int i = 0; i < 26; i++) {
        list.add((char)('a'+i));
        list.add((char)('A'+i));
    }

    Random r=new Random();
    //生成4个随机字母
    StringBuilder sb=new StringBuilder();
    for (int i=1;i<=4;i++) {
        int j = r.nextInt(list.size());
        sb.append(list.get(j));
    }
    //生成一个随机数字
    int index=r.nextInt(10);
    sb.append(index);
    String tempcode=sb.toString();

    char[] arr=tempcode.toCharArray();
    int j=r.nextInt(arr.length-1);
    char c=arr[j];
    arr[j]=arr[4];
    arr[4]=c;

    String code=new String(arr);

    return code;
}


    public boolean Checklen(String s,int min,int max){
        int len=s.length();
        return len>=min && len<=max;
    }

    public int[] Count(String s){
        int charcount=0;
        int numcount=0;
        int othercount=0;

        for (int i = 0; i < s.length(); i++) {
            char c=s.charAt(i);
            if(c>='a' && c<='z' ||c>='A' && c<='Z'){
                charcount++;
            }else if(c>='0' && c<='9'){
                numcount++;
            }else {
                othercount++;
            }
        }
        return new int[]{charcount,numcount,othercount};
    }

    public boolean Checkusername(String s){
        int[] arr=Count(s);
        //只能由字母、数字组成，不能是纯数字
        return arr[0]>0 && arr[1]>=0 && arr[2]==0;
    }

    public boolean CheckPassword(String s){
        int[] arr=Count(s);
        //只能是字母加数字的组合，不能有其他字fu
        return arr[0]>0 && arr[1]>0 && arr[2]==0;
    }

    public boolean Contains(ArrayList<User> list,String s){
        for (int i = 0; i < list.size(); i++) {
            User temp=list.get(i);
            if(temp.getUsername().equals(s)){
                return true;
            }
        }
         return false;
    }

    public int GetIndex(ArrayList<User> list,String username){
        for (int i = 0; i < list.size(); i++) {
            if(username.equals(list.get(i).getUsername())){
                return i;
            }
        }
        return -1;
    }
}
