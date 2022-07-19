import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        int noSize1, noSize2, noSize3;
        int [][] sizePrices ={{5,30},{10,50},{15,60}};
        Scanner jobDetails = new Scanner(System.in);

        System.out.print("\nWelcome to the Paint Job Estimator." + "\nPlease fill in the details below to obtain an estimate.\n\n" +
        "Number of walls: ");
        int noOfWalls = jobDetails.nextInt();
        float [][] wallInfo = new float[noOfWalls][5];
        float neededVolume = 0;


        System.out.print("How many metres squared does one litre of paint cover: ");
        float m2PerLitre = jobDetails.nextFloat();

            for (int i = 0; i < noOfWalls; i++) {
                System.out.print("\nWall (" + (1 + i) + "):\n  - Height (m): ");
                wallInfo[i][0] = jobDetails.nextFloat();
                System.out.print("  - Width (m): ");
                wallInfo[i][1] = jobDetails.nextFloat();
                System.out.print("  - Number of paint coats to be applied: ");
                wallInfo[i][2] = jobDetails.nextInt();
                System.out.print("  - Please enter the number of areas you do not wish to paint in this wall: ");
                wallInfo[i][3] = jobDetails.nextInt();

                if (wallInfo[i][3] > 0){

                    int noOfExcAreas = (int) wallInfo[i][3];
                    float [][] excAreasInfo = new float[noOfExcAreas][4];

                    System.out.print("\nPlease select the type of obstruction and provide details about its size:\n" +
                            "<[1]Rectangle, [2]Circle, [3]Ellipse, [4]Triangle, [5]Other>:\n");

                    for (int j = 0; j < noOfExcAreas; j++) {

                        System.out.print("\nArea (" + (1 + j) + "): ");
                        excAreasInfo[j][0] = jobDetails.nextFloat();

                        switch ((int) excAreasInfo[j][0]) {
                            case 1 -> {
                                System.out.print("* Note: Length = Width for a square.\n");
                                System.out.print("  - Length (cm): ");
                                excAreasInfo[j][1] = jobDetails.nextFloat();
                                System.out.print("  - Width (cm): ");
                                excAreasInfo[j][2] = jobDetails.nextFloat();

                                wallInfo[i][4] += excAreasInfo[j][1] * excAreasInfo[j][2];

                            }
                            case 2 -> {
                                System.out.print("* Note: Angle = 360 degrees for a full circle.\n");
                                System.out.print("  - Angle (degrees): ");
                                excAreasInfo[j][1] = jobDetails.nextFloat();
                                System.out.print("  - Radius (cm): ");
                                excAreasInfo[j][2] = jobDetails.nextFloat();

                                wallInfo[i][4] += (excAreasInfo[j][1] * Math.PI/360) * (Math.pow(excAreasInfo[j][2],2));

                            }

                            case 3 -> {
                                System.out.print("  - Vertical radius (cm): ");
                                excAreasInfo[j][1] = jobDetails.nextFloat();
                                System.out.print("  - Horizontal radius (cm): ");
                                excAreasInfo[j][2] = jobDetails.nextFloat();

                                wallInfo[i][4] += excAreasInfo[j][1] * Math.PI * excAreasInfo[j][2];

                            }

                            case 4 -> {
                                System.out.print("  - Side A (cm): ");
                                excAreasInfo[j][1] = jobDetails.nextFloat();
                                System.out.print("  - Side B (cm): ");
                                excAreasInfo[j][2] = jobDetails.nextFloat();
                                System.out.print("  - Angle between A and B - 90 degrees for a right-angled triangle (degrees): ");
                                excAreasInfo[j][3] = jobDetails.nextFloat();

                                wallInfo[i][4] += excAreasInfo[j][1] * Math.PI * Math.sin(Math.toRadians(excAreasInfo[j][2]));


                            }

                            default -> {
                                System.out.print("  - Shape area (cm squared): ");
                                excAreasInfo[j][1] = jobDetails.nextFloat();
                                wallInfo[i][4] += excAreasInfo[j][1];
                            }
                        }
                    }

                }

                neededVolume += (wallInfo[i][0] * wallInfo[i][1] - wallInfo[i][4]/10000) / (m2PerLitre / wallInfo[i][2]);

            }

        double paintNeeded = Math.round((neededVolume) * 10.0)/10.0;

        noSize1 = noSize2 = noSize3 = 0;

        while (neededVolume > 0) {

            if (neededVolume > (sizePrices[1][0])) {
                neededVolume -= sizePrices[2][0];
                noSize3++;

            } else if (neededVolume > (sizePrices[0][0])) {
                neededVolume -= sizePrices[1][0];
                noSize2++;

            } else {
                  noSize1++;
                  break;
            }
        }
                System.out.println("\nYou will need " + paintNeeded + " litres of paint. The following is the most economical purchase for the job:\n" +
                (noSize1 != 0? "- " + noSize1 + " x 5L bucket\n" : "") +
                (noSize2 != 0? "- " + noSize2 + " x 10L bucket\n" : "") +
                (noSize3 != 0? "- " + noSize3 + " x 15L bucket(s).\n" : ""));

        int cost = noSize1 * sizePrices[0][1] + noSize2 * sizePrices[1][1] + noSize3 * sizePrices[2][1];
        System.out.println("This total cost of paint will be " + cost + " GBP.");
        System.out.println("Left over paint will be under " + Math.round((noSize1*sizePrices[0][0]+
        noSize2*sizePrices[1][0]+noSize3*sizePrices[2][0] - paintNeeded)*10.0)/10.0 + " litres.");





    }
}