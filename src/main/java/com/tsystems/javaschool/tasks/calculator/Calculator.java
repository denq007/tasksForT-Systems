package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        try {
            double result = Calculator.getResult(prepareStr(statement));
            if (Double.isInfinite(result))//проверка на бесконечность
                return null;
            if (result % 1 == 0)//проверяем есть ли дробная часть
                return "" + (int) result;
            return "" + result;
        } catch (Exception e) {
            return null;
        }
    }

    private String prepareStr(String input) throws Exception {
        int curentPriority;
        int countBrace = 0;
        String currentStr = "";
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            curentPriority = getPriority(input.charAt(i));
            if (curentPriority == 0)
                currentStr += input.charAt(i);
            if (curentPriority == 1) {
                list.add(input.charAt(i));
                countBrace++;
            }
            if (curentPriority > 1) {
                currentStr += ' ';
                while (!list.isEmpty())
                {
                    if (getPriority(list.get(list.size() - 1)) >= curentPriority)
                    {
                        currentStr += list.get(list.size() - 1);
                        list.remove(list.get(list.size() - 1));
                    } else
                        break;
                }
                list.add(input.charAt(i));
            }
            if (curentPriority == -1) {
                currentStr += ' ';
                countBrace--;
                while (getPriority(list.get(list.size() - 1)) != 1) {
                    currentStr += list.get(list.size() - 1);
                    list.remove(list.get(list.size() - 1));
                }
                list.remove(list.get(list.size() - 1));

            }
        }
        while (!list.isEmpty())
        {
            currentStr += list.get(list.size() - 1);
            list.remove(list.get(list.size() - 1));
        }
        if (countBrace == 0) {
            return currentStr;
        }
        return null;
    }

    private static double getResult(String str) {
        String action = new String();
        Stack<Double> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ')
                continue;
            if (getPriority(str.charAt(i)) == 0) {
                while (str.charAt(i) != ' ' && getPriority(str.charAt(i)) == 0) {
                    action += str.charAt(i++);
                    if (i == str.length())
                        break;
                }
                stack.push(Double.parseDouble(action));
                action = "";
            }
            if (getPriority(str.charAt(i)) > 1) {
                double a = stack.pop(), b = stack.pop();
                switch (str.charAt(i)) {
                    case '+' -> stack.push(b + a);
                    case '-' -> stack.push(b - a);
                    case '*' -> stack.push(b * a);
                    case '/' -> stack.push(b / a);
                }
            }
        }
        return stack.pop();
    }


    private static int getPriority(char operation) {
        int priority = switch (operation) {
            case '*', '/' -> 3;
            case '+', '-' -> 2;
            case '(' -> 1;
            case ')' -> -1;
            default -> 0;
        };
        return priority;
    }

    public static void main(String[] args) {
        Calculator c = new Calculator();
        System.out.println(c.evaluate("5"));
    }
}
