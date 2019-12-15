package risk;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner scnr = new Scanner(System.in);

		//intro prompt
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("Welcome to Risk: Nuclear Mode");
		System.out.println("*****************************");
		
		System.out.print("\n" + "Population of attacking state: ");
		Army attack = new Army(scnr.nextInt());

		System.out.print("\n" + "Population of defending state: ");
		Army defend = new Army(scnr.nextInt());

		System.out.print("\nATTACKING GENERAL> ");
		attackCommit(scnr, attack, defend);

	}


	//runs dice roll battle mechanics
	public static boolean battle(Army attack, Army defend) {
		//battle end switch
		boolean end = false;
		
		Random rand = new Random();
		
		//dice roll arrays
		int[] dRolls = new int[2];
		int[] aRolls = new int[3];
		
		// if defenderPop is >=2, roll 2 dice. if defenders < 2, roll 1 die
		if (defend.getPopulation() >= 2) {
			dRolls[0] = rand.nextInt(5) + 1;
			dRolls[1] = rand.nextInt(5) + 1;
		}

		else if (defend.getPopulation() == 1) {
			dRolls[0] = rand.nextInt(5) + 1;
			dRolls[1] = 0;
		}

		else {
			System.out.println("Defense is defeated.");
		}

		// if attackerPop is >=4, roll 3 dice. =3 roll 2. =2 roll 1.
		if (attack.getPopulation() >= 4) {
			aRolls[0] = rand.nextInt(5) + 1;
			aRolls[1] = rand.nextInt(5) + 1;
			aRolls[2] = rand.nextInt(5) + 1;
		}

		else if (attack.getPopulation() == 3) {
			aRolls[0] = rand.nextInt(5) + 1;
			aRolls[1] = rand.nextInt(5) + 1;
			aRolls[2] = 0;
		}

		else if (attack.getPopulation() == 2) { 
			aRolls[0] = rand.nextInt(5) + 1;
			aRolls[1] = 0; 
			aRolls[2] = 0; 
		}

		else {
			System.out.println("Offense is defeated.");
		}

		
		//sort array ascending
		Arrays.sort(aRolls);
		Arrays.sort(dRolls);
		
		//print array desc
		System.out.println("Attack Rolls");
		for (int x = aRolls.length - 1; x >= 0; x--) {
			System.out.print(aRolls[x] + " ");
		}

		System.out.println();

		
		System.out.println("Defend Rolls");
		for (int x = dRolls.length - 1; x >= 0; x--) {
			System.out.print(dRolls[x] + " ");
		}

		System.out.println();
		
		
		// comparing 1st die
		if (aRolls[2] > dRolls[1]) {
			defend.setPopulation(defend.getPopulation() -1);
			System.out.println("**Defender down**");
		} else {
			attack.setPopulation(attack.getPopulation() -1);
			System.out.println("**Attacker down**");
		}

		// comparing 2nd die
		if (aRolls[1] != 0 && dRolls[0] != 0) {
			if (aRolls[1] > dRolls[0]) {
				defend.setPopulation(defend.getPopulation() -1);
				System.out.println("**Defender down**");
			} else {
				attack.setPopulation(attack.getPopulation() -1);
				System.out.println("**Attacker down**");

			}
		}
		
		//Population update
		System.out.println();
		System.out.println("Attackers left: " + attack.getPopulation());
		System.out.println("Defenders left: " + defend.getPopulation());
		System.out.println("#####################");
		
		
		//battle end message
		if(attack.getPopulation() == 1) {
			System.out.println("\n                Defending Army REPELLED the Attack");
			end = true;
		}
		if(defend.getPopulation() == 0) {
			System.out.println("\n                Defending Army DEFEATED");
			end = true;
		}
		//battle end switch return
		return end;
	}


	//determines attack commitment
	public static void attackCommit(Scanner scnr, Army attack, Army defend) {
		//battle end switch
		boolean end = false;
		
		//continue?(y/n)
		String y = "y";
		
		//interface
		while (y.equals("y") && end == false) {
			System.out.println("How hard will you attack?");
			System.out.println("a. Re-evaluate after this battle");
			System.out.println("b. Stop attacking if troops numbers drop to ___ men");
			System.out.println("c. Fight to the death");
			String abc = scnr.next();

			switch(abc) {

			case "a":
				end = battle(attack, defend);
				if (end == false) {
					System.out.println("Continue? (y/n)");
					y = scnr.next();
				}
				break;
				
			case "b":
				System.out.print("How many? ");
				
				//population minimum
				int min = scnr.nextInt();
				
				//battle count
				int battleNo = 1;
				int attackInit = attack.getPopulation();
				int defendInit = defend.getPopulation();
				
				while (attack.getPopulation() > min && end == false) {
					
					System.out.println("############");
					System.out.println("Battle " + battleNo);
					end = battle(attack, defend);
					battleNo++;
				
				}
				
				//war summary
				System.out.println("-----------------------------");
				System.out.println(">>>>>>>> WAR SUMMARY <<<<<<<<");
				System.out.println("Attack casualties: " + (attackInit - attack.getPopulation()) + " troops dead, "
						+ attack.getPopulation() + " left.");
				System.out.println("Defense casualties: " + (defendInit - defend.getPopulation()) + " troops dead, "
						+ defend.getPopulation() + " left.");
				System.out.println("========================================");
				
				if (end == false) {
					System.out.println("\nContinue? (y/n)");
					y = scnr.next();
				}
				
				break;
				
			case "c":
				
				battleNo = 1;
				attackInit = attack.getPopulation();
				defendInit = defend.getPopulation();
				while (end == false) {
					
					System.out.println("############");
					System.out.println("Battle " + battleNo);
					end = battle(attack, defend);
					battleNo++;
				
				}
				
				//war summary
				System.out.println("-----------------------------");
				System.out.println(">>>>>>>> WAR SUMMARY <<<<<<<<");
				System.out.println("Attack casualties: " + (attackInit - attack.getPopulation()) + " troops dead, "
						+ attack.getPopulation() + " left.");
				System.out.println("Defense casualties: " + (defendInit - defend.getPopulation()) + " troops dead, "
						+ defend.getPopulation() + " left.");
				System.out.println("========================================");
				
				
				break;
				
			}
			
			
			
		}
	}
}
