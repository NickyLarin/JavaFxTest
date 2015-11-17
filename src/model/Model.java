package model;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Nicky on 03.10.2015.
 */
public class Model {
    private final int X = 1;
    private final int ZERO = 2;
    private final int DRAW = 3;

    private static Model instance;

    private int[][] fieldArray = new int[3][3];
    private boolean zeroesTurn = false;
    private int winner = 0;
    private int numberOfTurns = 0;

    private Model() {}

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public int setCell(int x, int y) {
        int tmp = 0;
        if(fieldArray[x-1][y-1] == 0) {
            if (!zeroesTurn) {
                tmp = X;
            } else {
                tmp = ZERO;
            }
            fieldArray[x - 1][y - 1] = tmp;
            numberOfTurns++;
            if (checkWin(x - 1, y - 1)) {
                winner = tmp;
            }
            // Ничья
            if (numberOfTurns == 9 && winner == 0) {
                winner = DRAW;
            }
            zeroesTurn = !zeroesTurn;
        }
        return tmp;
    }

    public int getWinner(){
        return winner;
    }

    private boolean checkWin(int x, int y){
        // Проверка горизонтали
        int count = 1;
        for (int i = 0; i < fieldArray[x].length-1; i++) {
            if (fieldArray[x][i]== fieldArray[x][i+1] && fieldArray[x][i] != 0) {
                count++;
                if (count == 3) {
                    return true;
                }
            }
        }
        // Проверка вертикали
        count = 1;
        for (int j = 0; j < fieldArray.length-1; j++) {
            if (fieldArray[j][y]== fieldArray[j+1][y] && fieldArray[j][y] != 0) {
                count++;
                if (count == 3) {
                    return true;
                }
            }
        }
        // Проверка диагоналей
        count = 1;
        for (int i = 0; i < fieldArray.length-1; i++) {
            if (fieldArray[i][i]== fieldArray[i+1][i+1] && fieldArray[i][i] != 0) {
                count++;
                if (count == 3) {
                    return true;
                }
            }
        }
        count = 1;
        for (int j = 0; j < fieldArray.length-1; j++) {
            if (fieldArray[2-j][j]== fieldArray[2-j-1][j+1] && fieldArray[2-j][j] != 0) {
                count++;
                if (count == 3) {
                    return true;
                }
            }
        }
        return false;
    }

    public void reset() {
        for (int[] tmp : fieldArray) {
            Arrays.fill(tmp, 0);
        }
        zeroesTurn = false;
        winner = 0;
        numberOfTurns = 0;
    }
}
