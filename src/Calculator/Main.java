package Calculator;

import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.util.Arrays;
        import java.util.Comparator;
        import java.util.List;
        import java.util.stream.Collectors;

public class Main {
    private static void HelloWorld() {
        System.out.println("Welcome to the calculator, enter an equation with Arabic or Roman numbers from 1 to 10");
        System.out.println("Supports addition, subtraction, multiplication and division of integers");
    }
    public static void main(String[] args) throws Exception {
        HelloWorld();
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String expr = reader.readLine();
            try {
                String[] symbols = expr.split(" ");
                if (symbols.length != 3) throw new Exception("Something went wrong, try again");
                if (isArabic(symbols[0])) {
                    var num1 = Integer.parseInt(symbols[0]);
                    var num2 = Integer.parseInt(symbols[2]);
                    if ((num1 < 0) || (num1 > 10) || (num2 < 0) || (num2 > 10)) {
                        throw new IllegalArgumentException("Try again");
                    }
                    switch (symbols[1]) {
                        case "+": {
                            System.out.println(num1 + num2);
                            break;
                        }
                        case "-": {
                            System.out.println(num1 - num2);
                            break;
                        }
                        case "*": {
                            System.out.println(num1 * num2);
                            break;
                        }
                        case "/": {
                            System.out.println(num1 / num2);
                            break;
                        }
                        default:
                            throw new Exception("The operation symbol is not entered correctly, use only +, -, *, /");
                    }
                } else {
                    var num1 = romanToArabic(symbols[0]);
                    var num2 = romanToArabic(symbols[2]);
                    if ((num1 < 1) || (num1 > 10) || (num2 < 1) || (num2 > 10)) {
                        throw new IllegalArgumentException("Try again");
                    }
                    switch (symbols[1]) {
                        case "+": {
                            System.out.println(arabicToRoman(num1 + num2));
                            break;
                        }
                        case "-": {
                            System.out.println(arabicToRoman(num1 - num2));
                            break;
                        }
                        case "*": {
                            System.out.println(arabicToRoman(num1 * num2));
                            break;
                        }
                        case "/": {
                            System.out.println(arabicToRoman(num1 / num2));
                            break;
                        }
                        default:
                            throw new Exception("The operation symbol is not entered correctly, use only +, -, *, /");
                    }
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
    private static boolean isArabic(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }
    private static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException("Incorrect Roman symbol");
        }

        return result;
    }
    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 100)) {
            throw new IllegalArgumentException("The result is out of the range of acceptable values");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

}