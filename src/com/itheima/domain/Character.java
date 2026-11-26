package com.itheima.domain;

public class Character {
    //角色名,生命值(HP) / maxHP,攻击力(ATK),防御力(DEF)
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defence;

    //判断是否活着
    public boolean isAlive(){
        if(HP<=0){
            return false;
        }
        return true;
    }

    //恢复之后血量
    public void heal(int mount){
        HP+=mount;
        if(HP>maxHP){
            HP=maxHP;
        }
    }

    //受到伤害
    public void takeDamage(int damage){
        HP-=damage;
        if(HP<0){
            HP=0;
        }
    }

    //展示人物属性
    public String show(){

        return name+"[当前生命"+HP+",攻击"+attack+",防御"+defence+"]";
    }

    //展示人物血条
    public String showHealBar(String name,int HP,int maxHP){
        StringBuilder sb=new StringBuilder();
        sb.append(name).append(":").append("[");
        int hpcount=(int)((HP*1.0/maxHP)*20);
        for (int i = 0; i < hpcount; i++) {
            sb.append("█");
        }
        for (int i = hpcount; i <20 ; i++) {
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }

    //


    public Character(){

    }

    public Character(String name,int HP,int attack,int defence){
        this.name=name;
        this.HP=HP;
        this.maxHP=HP;
        this.attack=attack;
        this.defence=defence;
    }
}
