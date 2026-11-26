package com.itheima.ui;

import com.itheima.domain.EnemyCharscter;
import com.itheima.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {
    public void star(String username) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("    🎮" + username + "欢迎来到文字格斗游戏 🎮   ");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.println("创建你的角色:");
        System.out.println("您的角色名为: " + username);
        System.out.println("请分配属性点 (共20点):");
        System.out.println("角色创建成功！");

        HeroCharacter player=new HeroCharacter();
        player=CreatChracter(username);

        System.out.println("\uD83C\uDF1F 初始属性:"+player.show());
        //🌟 拥有技能: 普通攻击, 强力一击, 生命汲取
        player.skilllist.add("普通攻击");
        player.skilllist.add("强力一击");
        player.skilllist.add("生命汲取");

        System.out.println("\uD83C\uDF1F 拥有技能:"+player.showSkill());

/*        初级战士	80	15	10	猛击（150%伤害）
          敏捷刺客	60	20	5	快速攻击（2次50%伤害）
          重装坦克	120	10	20	防御姿态（下回合伤害减半） buff（ boolean defendding）
          神秘法师	70	25	8	火球术（180%伤害）*/
        ArrayList<EnemyCharscter> enemylist=new ArrayList<>();
        enemylist.add(new EnemyCharscter("初级战士",80,15,10,"猛击"));
        enemylist.add(new EnemyCharscter("敏捷刺客",60,20,5,"快速攻击"));
        enemylist.add(new EnemyCharscter("重装坦克",120,10,20,"防御姿态"));
        enemylist.add(new EnemyCharscter("神秘法师",70,25,8,"火球术"));

        System.out.println("═══════════════════════════════════════");
        int count=1;
        int win=0;
        while (player.isAlive()) {
            if (win!=0){
                for (int i = 0; i < enemylist.size(); i++) {
                    EnemyCharscter c=enemylist.get(i);
                    c.maxHP+=10;
                    c.HP= c.maxHP;
                    c.attack+=3;
                    c.defence+=2;
                    c.defending=false;
                }
            }

            Random r=new Random();
            int enemycount=r.nextInt(4);
            EnemyCharscter enemy=enemylist.get(enemycount);
            System.out.println("⚔\uFE0F 第 "+count+" 场战斗开始！对手: "+enemy.name);

//        1 回合开始！
//        zhangsan: [████████████████████] 100/100 HP
//        初级战士: [████████████████████] 80/80 HP
            System.out.println(player.showHealBar(player.name,player.HP,player.maxHP));
            System.out.println(player.showHealBar(enemy.name,enemy.HP,enemy.maxHP));

            while (enemy.isAlive()) {
                System.out.println("---------------------------------------");
                playerTurn(player,enemy);
                if(!enemy.isAlive()){
                    System.out.println("\uD83C\uDF89 你击败了"+enemy+"！");
                    win++;
                    break;
                }
                enemyTurn(enemy,player);
                if(!player.isAlive()){
                    System.out.println("\uD83C\uDF89 你被"+enemy.name+"击败了！");
                    break;
                }
                count++;
            }

            if (player.isAlive()){

                int healHP=r.nextInt(21)+20;
                player.heal(healHP);
                System.out.println("\uD83D\uDC9A 战斗结束！你恢复了"+healHP+"点生命值");
                System.out.println("\uD83C\uDFC6 当前胜场: "+win);
                System.out.println("═══════════════════════════════════════");
            }

            if (player.isAlive() && win>0 && win%3==0 ){
                System.out.println("\uD83D\uDC9A 恭喜您，属性得到提升！");
                player.maxHP+=30;
                player.attack+=5;
                player.defence+=3;
                System.out.println("最大生命值+30，攻击+5，防御+3");
                System.out.println("当前属性"+player.show());
            }

            if (player.isAlive()){
                System.out.println("继续下一场战斗？(y/n): ");
                Scanner sc=new Scanner(System.in);
                String choose=sc.next();
                if ("y".equalsIgnoreCase(choose)){
                    count++;
                    continue;
                } else if ("n".equalsIgnoreCase(choose)) {
                    break;
                }else{
                    System.out.println("没有这个选项，默认游戏继续");
                    continue;
                }
            }
        }
        System.out.println("═══════════════════════════════════════");
        System.out.println("游戏结束");
        System.out.println("总胜场"+win);
        System.out.println("感谢您游玩本游戏");



        return;
    }

    public HeroCharacter CreatChracter(String username) {
        Scanner sc = new Scanner(System.in);

        String[] arr = {"生命值", "攻击力", "防御力"};
        int[] num = new int[3];

        int point = 20;
        for (int i = 0; i < num.length; i++) {
            System.out.println("分配点数到 " + arr[i] + "(剩余点数: " + point + "):");
            int input = sc.nextInt();

            if (input > point) {
                System.out.println("分配值超出剩余值，已将剩余值全部分配给" + arr[i]);
                input = point;
            }
            if (input < 0) {
                System.out.println("无效输入，默认输入0");
                num[i]=0;
            }
            point-=input;
            num[i]=input;
        }

        HeroCharacter player=new HeroCharacter(
                username,
                num[0] * 10,
                num[1] * 2,
                num[2]
        );
        return player;


    }

    //玩家打敌人
    public void playerTurn(HeroCharacter player,EnemyCharscter enemy){
        /*
        1. 普通攻击
        2. 强力一击 (消耗10HP)
        3. 生命汲取 (消耗10HP，恢复生命)
        选择行动 (1-3): 2
💥 消耗10HP，你对 敏捷刺客 使用了强力一击，造成 31 点伤害！*/
        System.out.println("===== 你的回合 =====");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击 (消耗10HP)");
        System.out.println("3. 生命汲取 (消耗10HP，恢复生命)");
        System.out.println("选择行动 (1-3):");
        Scanner sc = new Scanner(System.in);
        int choose=sc.nextInt();
        switch (choose){
            default:
                System.out.println("默认选择普通攻击");
            case 1:
                int damage1=CalculateDamage(player.attack, enemy.defence);
                enemy.takeDamage(damage1);
                System.out.println("\uD83D\uDCA5 你对"+enemy.name+" 使用了普通攻击，造成"+damage1+"点伤害！");
                break;
            case 2:
                if (player.HP>10) {
                    int damage2=CalculateDamage((int)(player.attack*1.8), enemy.defence);
                    player.takeDamage(10);
                    enemy.takeDamage(damage2);
                    System.out.println("\uD83D\uDCA5消耗10HP，你对"+enemy.name+" 使用了强力一击，造成"+damage2+"点伤害！");
                } else {
                    System.out.println("体力不足，攻击失败");
                }
                break;
            case 3:
                if (player.HP>10) {
                    Random r=new Random();
                    player.takeDamage(10);
                    int reserve=r.nextInt(21);
                    player.heal(reserve);
                    System.out.println("\uD83D\uDCA5消耗10HP，你使用了生命汲取，恢复了点"+reserve+"生命！");
                } else {
                    System.out.println("体力不足，攻击失败");
                }
                break;
        }
        System.out.println(player.showHealBar(player.name,player.HP,player.maxHP));
    }

    //敌人打玩家
    public void enemyTurn(EnemyCharscter enemy,HeroCharacter player){
        System.out.println("===== "+enemy.name+"的回合 =====");
        String type=enemy.skill;
      /*  初级战士	80	15	10	猛击（150%伤害）
        敏捷刺客	60	20	5	快速攻击（2次50%伤害）
        重装坦克	120	10	20	防御姿态（下回合伤害减半） buff（ boolean defendding）
        神秘法师	70	25	8	火球术（180%伤害）*/
        switch (type){
            case "猛击":
                System.out.println("当前战士采用了"+enemy.skill);
                int damage1=CalculateDamage((int)(enemy.attack*1.5), player.defence);
                player.takeDamage(damage1);
                System.out.println("⚔\uFE0F"+enemy.name+"对你使用了"+enemy.skill+"，造成"+damage1+"点伤害！");
                break;
            case "快速攻击":
                System.out.println("当前战士采用了"+enemy.skill);
                int damage2=0;
                for (int i = 0; i < 2; i++) {
                    damage2+=CalculateDamage((int)(enemy.attack*0.5), player.defence);
                }
                player.takeDamage(damage2);
                System.out.println("⚔\uFE0F"+enemy.name+"对你使用了"+enemy.skill+"，造成"+damage2+"点伤害！");
                break;
            case "防御姿态":
                System.out.println("当前战士采用了"+enemy.skill);
                enemy.defending=true;

                System.out.println("⚔\uFE0F"+enemy.name+"采取防御姿态");
                break;
            case "火球术":
                System.out.println("当前战士采用了"+enemy.skill);
                int damage4=CalculateDamage((int)(enemy.attack*1.8), player.defence);
                player.takeDamage(damage4);
                System.out.println("⚔\uFE0F"+enemy.name+"对你使用了"+enemy.skill+"，造成"+damage4+"点伤害！");
                break;
        }
        System.out.println(player.showHealBar(enemy.name,enemy.HP,enemy.maxHP));
    }


    public int CalculateDamage(int attack,int defence){
        int damage=attack-defence;
        if(damage<1){
            damage=1;
        }
        return damage;
    }

}

