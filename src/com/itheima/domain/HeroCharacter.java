package com.itheima.domain;

import java.util.ArrayList;

public class HeroCharacter extends Character{
    public ArrayList<String> skilllist;

    public HeroCharacter(){
        //继承父类中的参数
        super();
        skilllist=new ArrayList<>();
    }

    public HeroCharacter(String name,int HP,int attack,int defence){
        super(name,HP,attack,defence);
        skilllist=new ArrayList<>();
    }

    public String showSkill(){
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < skilllist.size(); i++) {
            sb.append(skilllist.get(i)+",");
            if(i==skilllist.size()-1){
                sb.append(skilllist.get(i));
            }
        }
        return sb.toString();
    }
}
