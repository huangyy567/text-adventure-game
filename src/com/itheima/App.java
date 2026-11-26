package com.itheima;

import com.itheima.ui.FightingGame;
import com.itheima.ui.Login;

//运行窗口
public class App {
    public static void main(String[] args){
        Login login=new Login();
        login.star();

        FightingGame fg=new FightingGame();
        fg.star("karry");
    }


}
