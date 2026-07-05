/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

public class AvailableMove {
    public int row;
    public int col;

    public AvailableMove(int rowIndex, int columnIndex) {
        row = rowIndex;
        col = columnIndex;
    }

    @Override
    public String toString() {
        char columnLetter = (char) ('a' + col);
        int rowNumber = row + 1;
        String result = "" + columnLetter + rowNumber;
        return result;
    }
}
    

