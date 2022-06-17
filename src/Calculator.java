import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        input();
    }

    public static void input() {
        System.out.println("Input:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input = input.toUpperCase();
        String[] operation = input.split(" ");
        try {
            String leftOperand = operation[0];
            char sign = operation[1].charAt(0);
            String rightOperand = operation[2];

            if (operation.length > 3) {
                throw new IllegalArgumentException();
            }
            if ((isArabic(leftOperand) && isRoman(rightOperand)) || (isRoman(leftOperand) && isArabic(rightOperand))) {
                throw new NumberFormatException();
            }
            if (isRoman(leftOperand) && isRoman(rightOperand)) {
                if (calculations(romanToArabic(leftOperand), romanToArabic(rightOperand), sign) <= 0 ||
                        calculations(romanToArabic(leftOperand), romanToArabic(rightOperand), sign) > 3999) {
                    throw new ArithmeticException();
                }
            }
            operation(leftOperand, rightOperand, sign);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("throws Exception //since string is not mathematical operation");
        } catch (NumberFormatException e) {
            System.out.println("throws Exception //because using different notations at the same time");
        } catch (ArithmeticException e) {
            System.out.println("throws Exception //since roman numeral cannot be zero or negative or more than 3999");
        } catch (IllegalArgumentException e) {
            System.out.println("throws Exception //because format of the mathematical operation doesn't satisfy the task - two operands and one operator(+, -, /, *)");
        }
    }

    public static void operation(String leftOperand, String rightOperand, char sign) {
        if (isArabic(leftOperand) && (isArabic(rightOperand))) {
            if (isAvailableSign(sign)) {
                int localLeftOperand = Integer.parseInt(leftOperand);
                int localRightOperand = Integer.parseInt(rightOperand);

                if (isNumberInAvailableRange(localLeftOperand) && isNumberInAvailableRange(localRightOperand)) {
                    System.out.println("Output:");
                    System.out.println(calculations(localLeftOperand, localRightOperand, sign));
                } else {
                    System.out.println("One of operands is out of range (1..10)");
                }
            } else {
                System.out.println("Mathematical sign is incorrect...");
            }
        } else if (isRoman(leftOperand) && isRoman(rightOperand)) {
            if (isAvailableSign(sign)) {
                int localLeftOperand = romanToArabic(leftOperand);
                int localRightOperand = romanToArabic(rightOperand);

                if (isNumberInAvailableRange(localLeftOperand) && isNumberInAvailableRange(localRightOperand)) {
                    int result = calculations(localLeftOperand, localRightOperand, sign);
                    System.out.println("Output:");
                    arabicToRoman(result);
                    System.out.println(arabicToRoman(result));
                } else {
                    System.out.println("One/both of operands is/are greater than X...)");
                }
            }
        } /* else if ((isArabic(leftOperand) && !isAvailableSign(sign) && isRoman(rightOperand)) ||
                (isRoman(leftOperand) && !isAvailableSign(sign) && isArabic(rightOperand))) {
            System.out.println("Completely incorrect input...");
        } else if ((isArabic(leftOperand) && isRoman(rightOperand)) || (isRoman(leftOperand) && isArabic(rightOperand))) {
            System.out.println("Using different notations at the same time...");
        }*/
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static int divide(int a, int b) {
        return a / b;
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static boolean romanContains(char input) {
        for (Roman ch : Roman.values()) {
            if (ch.asChar() == input) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRoman(String operand) {
        for (int i = 0; i < operand.length(); i++) {
            if (!romanContains(operand.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean arabicContains(char input) {
        for (Arabic ch : Arabic.values()) {
            if (ch.asChar() == input) {
                return true;
            }
        }
        return false;
    }

    public static boolean isArabic(String operand) {
        for (int i = 0; i < operand.length(); i++) {
            if (!arabicContains(operand.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAvailableSign(char inputSign) {
        for (Sign sign : Sign.values()) {
            if (sign.sign() == inputSign) {
                return true;
            }
        }
        return false;
    }

    public static int calculations(int leftOperand, int rightOperand, char operator) {
        switch (operator) {
            case '*' -> {
                return multiply(leftOperand, rightOperand);
            }
            case '/' -> {
                return divide(leftOperand, rightOperand);
            }
            case '+' -> {
                return add(leftOperand, rightOperand);
            }
            case '-' -> {
                return subtract(leftOperand, rightOperand);
            }
        }
        return -1;
    }

    public static boolean isNumberInAvailableRange(int operand) {
        return operand > 0 && operand <= 10;
    }

    public static int romanToArabic(String operand) {
        int result = 0;
        for (int i = 0; i < operand.length(); i++) {
            switch (operand.charAt(i)) {
                case 'I' -> {
                    int increasedBy = getArabicForI(i, operand);
                    result += increasedBy;
                    if (increasedBy == 2 || increasedBy == 4 || increasedBy == 5 || increasedBy == 9) {
                        i++;
                    } else if (increasedBy == 3) {
                        i += 2;
                    }
                }
                case 'V' -> result += 5;
                case 'X' -> {
                    int increasedBy = getArabicForX(i, operand);
                    result += increasedBy;
                    if (increasedBy == 20 || increasedBy == 40 || increasedBy == 50 || increasedBy == 90) {
                        i++;
                    } else if (increasedBy == 30) {
                        i += 2;
                    }
                }
                case 'L' -> result += 50;
                case 'C' -> {
                    int increasedBy = getArabicForC(i, operand);
                    result += increasedBy;
                    if (increasedBy == 200 || increasedBy == 400 || increasedBy == 500 || increasedBy == 900) {
                        i++;
                    } else if (increasedBy == 300) {
                        i += 2;
                    }
                }
                case 'D' -> result += 500;
                case 'M' -> {
                    if (howManySameDigitsInARow(i, operand) == 2) {
                        result += 3000;
                        i += 2;
                    } else if (howManySameDigitsInARow(i, operand) == 1) {
                        result += 2000;
                        i++;
                    } else {
                        result += 1000;
                    }
                }
            }
        }
        return result;
    }

    public static int howManySameDigitsInARow(int index, String input) {
        int countOfDigits = 0;
        String[] digits = input.split("");
        while (index < input.length() - 1) {
            if (digits[index].charAt(0) == digits[index + 1].charAt(0)) {
                countOfDigits++;
            } else {
                break;
            }
            index++;
        }
        return countOfDigits;
    }

    public static int getArabicForI(int index, String operand) {
        int result = 0;
        if (operand.length() == 1 || index == operand.length() - 1) {
            result++;
        } else if (howManySameDigitsInARow(index, operand) == 1) {
            result += 2;
        } else if (howManySameDigitsInARow(index, operand) == 2) {
            result += 3;
        } else {
            int i = index + 1;
            if (operand.charAt(i) == 'I') {
                result += 1;
            } else if (operand.charAt(i) == 'V') {
                result += 4;
            } else if (operand.charAt(i) == 'X') {
                result += 9;
            }
        }
        return result;
    }

    public static int getArabicForX(int index, String operand) {
        int result = 0;
        if (operand.length() == 1 || index == operand.length() - 1) {
            result += 10;
        } else if (howManySameDigitsInARow(index, operand) == 1) {
            result += 20;
        } else if (howManySameDigitsInARow(index, operand) == 2) {
            result += 30;
        } else {
            int i = index + 1;
            if (operand.charAt(i) == 'I' || operand.charAt(i) == 'V') {
                result += 10;
            } else if (operand.charAt(i) == 'L') {
                result += 40;
            } else if (operand.charAt(i) == 'C') {
                result += 90;
            }
        }
        return result;
    }

    public static int getArabicForC(int index, String operand) {
        int result = 0;
        if (operand.length() == 1 || index == operand.length() - 1) {
            result += 100;
        } else if (howManySameDigitsInARow(index, operand) == 1) {
            result += 200;
        } else if (howManySameDigitsInARow(index, operand) == 2) {
            result += 300;
        } else {
            int i = index + 1;
            if (operand.charAt(i) == 'I' || operand.charAt(i) == 'V' || operand.charAt(i) == 'X' || operand.charAt(i) == 'L') {
                result += 100;
            } else if (operand.charAt(i) == 'D') {
                result += 400;
            } else if (operand.charAt(i) == 'M') {
                result += 900;
            }
        }
        return result;
    }

    public static String arabicToRoman(int input) {
        String[] rank = String.valueOf(input).split("");
        StringBuilder resultRoman = new StringBuilder();
        if (input <= 0) {
            System.out.println("Roman numeral cannot be 0 or negative...");
            resultRoman.append(-1);
        } else if (input > 3999) {
            System.out.println("Roman numeral cannot be greater than 3999...");
            resultRoman.append(-2);
        } else {
            switch (rank.length) {
                case 1 -> resultRoman.append(getUnits(rank[0]));
                case 2 -> {
                    for (int i = 0; i < rank.length; i++) {
                        if (i == 0) {
                            resultRoman.append(getTens(rank[i]));
                        } else {
                            resultRoman.append(getUnits(rank[i]));
                        }
                    }
                }
                case 3 -> {
                    for (int i = 0; i < rank.length; i++) {
                        if (i == 0) {
                            resultRoman.append(getHundreds(rank[i]));
                        } else if (i == 1) {
                            resultRoman.append(getTens(rank[i]));
                        } else {
                            resultRoman.append(getUnits(rank[i]));
                        }
                    }
                }
                case 4 -> {
                    for (int i = 0; i < rank.length; i++) {
                        if (i == 0) {
                            resultRoman.append(getThousands(rank[i]));
                        } else if (i == 1) {
                            resultRoman.append(getHundreds(rank[i]));
                        } else if (i == 2) {
                            resultRoman.append(getTens(rank[i]));
                        } else {
                            resultRoman.append(getUnits(rank[i]));
                        }
                    }
                }
            }
        }
        return resultRoman.toString();
    }

    public static String getUnits(String input) {
        StringBuilder resultRoman = new StringBuilder();
        int units = Integer.parseInt(input);
        switch (units) {
            case 1 -> resultRoman.append("I");
            case 2 -> resultRoman.append("II");
            case 3 -> resultRoman.append("III");
            case 4 -> resultRoman.append("IV");
            case 5 -> resultRoman.append("V");
            case 6 -> resultRoman.append("VI");
            case 7 -> resultRoman.append("VII");
            case 8 -> resultRoman.append("VIII");
            case 9 -> resultRoman.append("IX");
        }
        return resultRoman.toString();
    }

    public static String getTens(String input) {
        StringBuilder resultRoman = new StringBuilder();
        int tens = Integer.parseInt(input);
        switch (tens) {
            case 1 -> resultRoman.append("X");
            case 2 -> resultRoman.append("XX");
            case 3 -> resultRoman.append("XXX");
            case 4 -> resultRoman.append("XL");
            case 5 -> resultRoman.append("L");
            case 6 -> resultRoman.append("LX");
            case 7 -> resultRoman.append("LXX");
            case 8 -> resultRoman.append("LXXX");
            case 9 -> resultRoman.append("XC");
        }
        return resultRoman.toString();
    }

    public static String getHundreds(String input) {
        StringBuilder resultRoman = new StringBuilder();
        int hundreds = Integer.parseInt(input);
        switch (hundreds) {
            case 1 -> resultRoman.append("C");
            case 2 -> resultRoman.append("CC");
            case 3 -> resultRoman.append("CCC");
            case 4 -> resultRoman.append("CD");
            case 5 -> resultRoman.append("D");
            case 6 -> resultRoman.append("DC");
            case 7 -> resultRoman.append("DCC");
            case 8 -> resultRoman.append("DCCC");
            case 9 -> resultRoman.append("CM");
        }
        return resultRoman.toString();
    }

    public static String getThousands(String input) {
        StringBuilder resultRoman = new StringBuilder();
        int thousands = Integer.parseInt(input);
        switch (thousands) {
            case 1 -> resultRoman.append("M");
            case 2 -> resultRoman.append("MM");
            case 3 -> resultRoman.append("MMM");
        }
        return resultRoman.toString();
    }
}
