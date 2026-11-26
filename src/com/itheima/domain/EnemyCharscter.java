package com.itheima.domain;

public class EnemyCharscter extends Character {
     public String skill;
     public boolean defending;

     public void takeDamage(int damage){
         if(defending){
             damage = damage>1? damage/2 : 1;
             defending=false;
         }
         //修改damage的值之后，再次调用父类方法就行
         super.takeDamage(damage);
     }

    public EnemyCharscter(){
        //继承父类中的参数
        super();

    }

    public EnemyCharscter(String name,int HP,int attack,int defence,String skill){
        super(name,HP,attack,defence);
        this.skill=skill;
    }
}
